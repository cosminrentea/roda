package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CmsLayoutGroup;
import flexjson.JSONSerializer;

@Configurable
public class LayoutGroupTree extends LayoutList {

	public static String toJsonArray(Collection<LayoutGroupTree> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth");
		serializer.include("id", "name", "itemtype", "expanded");

		int maxDepth = 0;
		for (LayoutGroupTree layoutTree : collection) {
			if (maxDepth < layoutTree.getDepth()) {
				maxDepth = layoutTree.getDepth();
			}
		}

		String includeData = "";
		for (int i = 0; i < maxDepth + 1; i++) {
			if (i > 0) {
				includeData += ".";
			}
			includeData += "children";
			serializer.exclude(includeData + ".depth", includeData + ".groupid", includeData + ".directory",
					includeData + ".description", includeData + ".leaf");
			serializer.include(includeData + ".name", includeData + ".id", includeData + ".itemtype");
			serializer.transform(new FieldNameTransformer("indice"), includeData + ".id");
		}

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"children\":[{\"name\":\"Root\",\"indice\":\"0\",\"expanded\":\"true\",\"itemtype\":\"layoutgroup\",\"children\":"
				+ serializer.serialize(collection) + "}]}";
	}

	public static List<LayoutGroupTree> findAllLayoutGroupTrees() {
		List<LayoutGroupTree> result = new ArrayList<LayoutGroupTree>();

		List<CmsLayoutGroup> layoutGroups = CmsLayoutGroup.entityManager()
				.createQuery("SELECT o FROM CmsLayoutGroup o WHERE o.parentId IS NULL", CmsLayoutGroup.class)
				.getResultList();

		if (layoutGroups != null && layoutGroups.size() > 0) {
			Iterator<CmsLayoutGroup> layoutGroupsIterator = layoutGroups.iterator();

			while (layoutGroupsIterator.hasNext()) {
				CmsLayoutGroup layoutGroup = (CmsLayoutGroup) layoutGroupsIterator.next();

				result.add(findLayoutGroupTree(layoutGroup.getId()));
			}
		}
		return result;
	}

	public static LayoutGroupTree findLayoutGroupTree(Integer id) {
		LayoutGroupTree result = null;
		CmsLayoutGroup layoutGroup = CmsLayoutGroup.findCmsLayoutGroup(id);

		if (layoutGroup != null) {
			result = new LayoutGroupTree(layoutGroup);
			result.setName(layoutGroup.getName());

			Set<LayoutList> dataByLayoutGroupSet = null;

			Set<CmsLayoutGroup> children = layoutGroup.getCmsLayoutGroups();

			int maxDepth = 0;
			if (children != null && children.size() > 0) {
				dataByLayoutGroupSet = new HashSet<LayoutList>();

				Iterator<CmsLayoutGroup> childrenIterator = children.iterator();
				while (childrenIterator.hasNext()) {
					CmsLayoutGroup childCmsLayoutGroup = childrenIterator.next();
					LayoutGroupTree layoutGroupTree = findLayoutGroupTree(childCmsLayoutGroup.getId());
					dataByLayoutGroupSet.add(layoutGroupTree);
					if (maxDepth < layoutGroupTree.getDepth()) {
						maxDepth = layoutGroupTree.getDepth();
					}
				}
				result.setDepth(maxDepth + 1);
			}

			result.setChildren(dataByLayoutGroupSet);
		}
		return result;

	}

	private Set<LayoutList> children;

	private boolean expanded = true;

	private int depth;

	public Set<LayoutList> getChildren() {
		return children;
	}

	public void setChildren(Set<LayoutList> children) {
		this.children = children;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public LayoutGroupTree(CmsLayoutGroup layoutGroup) {
		super(layoutGroup.getId(), layoutGroup.getName(), null, layoutGroup.getParentId().getId(), "layoutgroup",
				getLayoutGroupPath(layoutGroup), "TODO");

		this.children = new HashSet<LayoutList>();
	}

	public LayoutGroupTree(CmsLayoutGroup layoutGroup, Set<LayoutList> children) {
		this(layoutGroup);
		this.children = children;
	}

	public LayoutGroupTree(CmsLayoutGroup layoutGroup, String name, Set<LayoutList> children) {
		this(layoutGroup, children);
		this.setName(name);
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth");
		serializer.include("id", "name", "itemtype", "expanded");

		String includeData = "";
		for (int i = 0; i < getDepth() + 1; i++) {
			if (i > 0) {
				includeData += ".";
			}
			includeData += "children";
			serializer.exclude(includeData + ".depth", includeData + ".groupid", includeData + ".directory",
					includeData + ".description", includeData + ".leaf");
			serializer.include(includeData + ".name", includeData + ".id", includeData + ".itemtype");
			serializer.transform(new FieldNameTransformer("indice"), includeData + ".id");
		}

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"children\":[{\"name\":\"Root\",\"indice\":\"0\",\"expanded\":\"true\",\"children\":"
				+ serializer.serialize(this) + "}]}";
	}
}
