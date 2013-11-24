package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CmsSnippetGroup;
import flexjson.JSONSerializer;

@Configurable
public class SnippetGroupTree extends SnippetList {

	public static String toJsonArray(Collection<SnippetGroupTree> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth", "type");
		serializer.include("id", "name", "expanded");

		int maxDepth = 0;
		for (SnippetGroupTree snippetTree : collection) {
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
			serializer.exclude(includeData + ".depth", includeData + ".groupid", includeData + ".directory",
					includeData + ".content", includeData + ".n", includeData + ".type");
			serializer.include(includeData + ".name", includeData + ".id", includeData + ".expanded");
			serializer.transform(new FieldNameTransformer("indice"), includeData + ".id");
		}

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"children\":[{\"name\":\"Root\",\"indice\":\"0\",\"expanded\":\"true\",\"children\":"
				+ serializer.serialize(collection) + "}]}";
	}

	public static List<SnippetGroupTree> findAllSnippetGroupTrees() {
		List<SnippetGroupTree> result = new ArrayList<SnippetGroupTree>();

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

	public static SnippetGroupTree findSnippetGroupTree(Integer id) {
		SnippetGroupTree result = null;
		CmsSnippetGroup snippetGroup = CmsSnippetGroup.findCmsSnippetGroup(id);

		if (snippetGroup != null) {
			result = new SnippetGroupTree(snippetGroup);
			result.setName(snippetGroup.getName());

			Set<SnippetList> dataBySnippetGroupSet = null;

			Set<CmsSnippetGroup> children = snippetGroup.getCmsSnippetGroups();

			int maxDepth = 0;
			if (children != null && children.size() > 0) {
				dataBySnippetGroupSet = new HashSet<SnippetList>();

				Iterator<CmsSnippetGroup> childrenIterator = children.iterator();
				while (childrenIterator.hasNext()) {
					CmsSnippetGroup childCmsSnippetGroup = childrenIterator.next();
					SnippetGroupTree snippetGroupTree = findSnippetGroupTree(childCmsSnippetGroup.getId());
					dataBySnippetGroupSet.add(snippetGroupTree);
					if (maxDepth < snippetGroupTree.getDepth()) {
						maxDepth = snippetGroupTree.getDepth();
					}
				}
				result.setDepth(maxDepth + 1);
			}

			result.setChildren(dataBySnippetGroupSet);
		}
		return result;

	}

	private Set<SnippetList> children;

	private boolean expanded = true;

	private int depth;

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

	public SnippetGroupTree(CmsSnippetGroup snippetGroup) {
		// super(snippetGroup.getId(), snippetGroup.getName(),
		// snippetGroup.getParentId() == null ? null : snippetGroup
		// .getParentId().getId(), getSnippetGroupPath(snippetGroup), 200,
		// "snippetgroup");

		super(snippetGroup);
		this.children = new HashSet<SnippetList>();
		if ((snippetGroup.getCmsSnippetGroups() != null && snippetGroup.getCmsSnippetGroups().size() > 0)) {
			setLeaf(false);
		} else {
			setLeaf(true);
		}
	}

	public SnippetGroupTree(CmsSnippetGroup snippetGroup, Set<SnippetList> children) {
		this(snippetGroup);
		this.children = children;
	}

	public SnippetGroupTree(CmsSnippetGroup snippetGroup, String name, Set<SnippetList> children) {
		this(snippetGroup, children);
		this.setName(name);
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth", "type");
		serializer.include("id", "name", "expanded");

		String includeData = "";
		for (int i = 0; i < getDepth() + 1; i++) {
			if (i > 0) {
				includeData += ".";
			}
			includeData += "children";
			serializer.exclude(includeData + ".depth", includeData + ".groupid", includeData + ".directory",
					includeData + ".content", includeData + ".n", includeData + ".type");
			serializer.include(includeData + ".name", includeData + ".id", includeData + ".expanded");
			serializer.transform(new FieldNameTransformer("indice"), includeData + ".id");
		}

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"children\":[{\"name\":\"Root\",\"indice\":\"0\",\"expanded\":\"true\",\"children\":"
				+ serializer.serialize(this) + "}]}";
	}
}
