package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CmsSnippet;
import ro.roda.domain.CmsSnippetGroup;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class SnippetTree extends SnippetList {

	public static String toJsonArr(Collection<SnippetTree> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth", "type");
		serializer.include("id", "name", "expanded", "itemtype", "leaf", "iconCls");

		int maxDepth = 0;
		for (SnippetTree snippetTree : collection) {
			if (maxDepth < snippetTree.getDepth()) {
				maxDepth = snippetTree.getDepth();
			}
		}

		String includeData = "";
		for (int i = 0; i < maxDepth + 1; i++) {
			if (i > 0) {
				includeData += ".";
			}
			includeData += "children";
			serializer.exclude(includeData + ".depth", includeData + ".type");
			serializer.include(includeData + ".name", includeData + ".id", includeData + ".groupid", includeData
					+ ".directory", includeData + ".content", includeData + ".itemtype", includeData + ".leaf",
					includeData + ".iconCls");
			serializer.transform(new FieldNameTransformer("indice"), includeData + ".id");
		}

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"children\":[{\"name\":\"Root\",\"indice\":\"0\",\"expanded\":\"true\",\"children\":"
				+ serializer.serialize(collection) + "}]}";
	}

	public static List<SnippetTree> findAllSnippetTrees() {
		List<SnippetTree> result = new ArrayList<SnippetTree>();

		List<CmsSnippetGroup> snippetGroups = CmsSnippetGroup.entityManager()
				.createQuery("SELECT o FROM CmsSnippetGroup o WHERE o.parentId IS NULL", CmsSnippetGroup.class)
				.getResultList();

		if (snippetGroups != null && snippetGroups.size() > 0) {
			Iterator<CmsSnippetGroup> snippetGroupsIterator = snippetGroups.iterator();

			while (snippetGroupsIterator.hasNext()) {
				CmsSnippetGroup snippetGroup = (CmsSnippetGroup) snippetGroupsIterator.next();

				result.add(findSnippetGroupTree(snippetGroup.getId()));
			}
		}
		return result;
	}

	public static SnippetTree findSnippetGroupTree(Integer id) {
		SnippetTree result = null;
		CmsSnippetGroup snippetGroup = CmsSnippetGroup.findCmsSnippetGroup(id);

		if (snippetGroup != null) {
			result = new SnippetTree(snippetGroup);
			result.setName(snippetGroup.getName());

			Set<SnippetList> dataBySnippetGroupSet = null;

			Set<CmsSnippetGroup> children = snippetGroup.getCmsSnippetGroups();
			Set<CmsSnippet> snippets = snippetGroup.getCmsSnippets();

			int maxDepth = 0;
			if (children != null && children.size() > 0) {
				dataBySnippetGroupSet = new HashSet<SnippetList>();

				Iterator<CmsSnippetGroup> childrenIterator = children.iterator();
				while (childrenIterator.hasNext()) {
					CmsSnippetGroup childCmsSnippetGroup = childrenIterator.next();
					SnippetTree snippetTree = findSnippetGroupTree(childCmsSnippetGroup.getId());
					dataBySnippetGroupSet.add(snippetTree);
					if (maxDepth < snippetTree.getDepth()) {
						maxDepth = snippetTree.getDepth();
					}
				}
				result.setDepth(maxDepth + 1);
			}

			if (snippets != null && snippets.size() > 0) {
				if (dataBySnippetGroupSet == null) {
					dataBySnippetGroupSet = new HashSet<SnippetList>();
				}

				Iterator<CmsSnippet> snippetsIterator = snippets.iterator();
				while (snippetsIterator.hasNext()) {
					dataBySnippetGroupSet.add(new SnippetList(snippetsIterator.next()));
				}
			}
			result.setChildren(dataBySnippetGroupSet);
		}
		return result;

	}

	private Set<SnippetList> children;

	private int depth;

	private boolean expanded = true;

	public Set<SnippetList> getChildren() {
		return children;
	}

	public void setChildren(Set<SnippetList> children) {
		this.children = children;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public SnippetTree(CmsSnippetGroup snippetGroup) {
		// super(snippetGroup.getId(), snippetGroup.getName(),
		// snippetGroup.getParentId() == null ? null : snippetGroup
		// .getParentId().getId(), null, getSnippetGroupPath(snippetGroup),
		// null);

		super(snippetGroup);
		this.children = new HashSet<SnippetList>();
	}

	public SnippetTree(CmsSnippetGroup snippetGroup, Set<SnippetList> children) {
		this(snippetGroup);
		this.children = children;
	}

	public SnippetTree(CmsSnippetGroup snippetGroup, String name, Set<SnippetList> children) {
		this(snippetGroup, children);
		this.setName(name);
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth", "type");
		serializer.include("id", "name", "expanded", "itemtype", "leaf", "iconCls");

		String includeData = "";
		for (int i = 0; i < getDepth() + 1; i++) {
			if (i > 0) {
				includeData += ".";
			}
			includeData += "children";
			serializer.exclude(includeData + ".depth", includeData + ".type");
			serializer.include(includeData + ".name", includeData + ".id", includeData + ".groupid", includeData
					+ ".directory", includeData + ".content", includeData + ".itemtype", includeData + ".leaf",
					includeData + ".iconCls");
			serializer.transform(new FieldNameTransformer("indice"), includeData + ".id");
		}

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"children\":[{\"name\":\"Root\",\"indice\":\"0\",\"expanded\":\"true\",\"children\":"
				+ serializer.serialize(this) + "}]}";
	}
}
