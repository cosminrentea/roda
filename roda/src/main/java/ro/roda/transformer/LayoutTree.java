package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CmsLayout;
import ro.roda.domain.CmsLayoutGroup;
import flexjson.JSONSerializer;

@Configurable
public class LayoutTree extends LayoutList {

	public static String toJsonArray(Collection<LayoutTree> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth");
		serializer.include("id", "name", "itemtype", "expanded");

		int maxDepth = 0;
		for (LayoutTree layoutTree : collection) {
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
			serializer.exclude(includeData + ".depth");
			serializer.include(includeData + ".name", includeData + ".id", includeData + ".itemtype", includeData
					+ ".groupid", includeData + ".directory", includeData + ".description", includeData + ".leaf");
			serializer.transform(new FieldNameTransformer("indice"), includeData + ".id");
		}

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"children\":[{\"name\":\"Root\",\"indice\":\"0\",\"expanded\":\"true\",\"children\":"
				+ serializer.serialize(collection) + "}]}";
	}

	public static List<LayoutTree> findAllLayoutTrees() {
		List<LayoutTree> result = new ArrayList<LayoutTree>();

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

	public static LayoutTree findLayoutGroupTree(Integer id) {
		LayoutTree result = null;
		CmsLayoutGroup layoutGroup = CmsLayoutGroup.findCmsLayoutGroup(id);

		if (layoutGroup != null) {
			result = new LayoutTree(layoutGroup);
			result.setName(layoutGroup.getName());

			Set<LayoutList> dataByLayoutGroupSet = null;

			Set<CmsLayoutGroup> children = layoutGroup.getCmsLayoutGroups();
			Set<CmsLayout> layouts = layoutGroup.getCmsLayouts();

			int maxDepth = 0;
			if (children != null && children.size() > 0) {
				dataByLayoutGroupSet = new HashSet<LayoutList>();

				Iterator<CmsLayoutGroup> childrenIterator = children.iterator();
				while (childrenIterator.hasNext()) {
					CmsLayoutGroup childCmsLayoutGroup = childrenIterator.next();
					LayoutTree layoutTree = findLayoutGroupTree(childCmsLayoutGroup.getId());
					dataByLayoutGroupSet.add(layoutTree);
					if (maxDepth < layoutTree.getDepth()) {
						maxDepth = layoutTree.getDepth();
					}
				}
				result.setDepth(maxDepth + 1);
			}

			if (layouts != null && layouts.size() > 0) {
				if (dataByLayoutGroupSet == null) {
					dataByLayoutGroupSet = new HashSet<LayoutList>();
				}

				Iterator<CmsLayout> layoutsIterator = layouts.iterator();
				while (layoutsIterator.hasNext()) {
					dataByLayoutGroupSet.add(new LayoutList(layoutsIterator.next()));
				}
			}
			result.setChildren(dataByLayoutGroupSet);
		}
		return result;

	}

	private Set<LayoutList> children;

	private boolean leaf;

	private int depth;

	private boolean expanded = true;

	public Set<LayoutList> getChildren() {
		return children;
	}

	public void setChildren(Set<LayoutList> children) {
		this.children = children;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public LayoutTree(CmsLayoutGroup layoutGroup) {
		super(layoutGroup.getId(), layoutGroup.getName(), null, layoutGroup.getParentId().getId(), "layoutgroup",
				getLayoutGroupPath(layoutGroup), "TODO");
		if ((layoutGroup.getCmsLayoutGroups() == null || layoutGroup.getCmsLayoutGroups().size() == 0)
				&& (layoutGroup.getCmsLayouts() == null || layoutGroup.getCmsLayouts().size() == 0)) {
			this.leaf = false;
		} else {
			this.leaf = true;
		}
		this.children = new HashSet<LayoutList>();
	}

	public LayoutTree(CmsLayoutGroup layoutGroup, Set<LayoutList> children) {
		this(layoutGroup);
		this.children = children;
	}

	public LayoutTree(CmsLayoutGroup layoutGroup, String name, Set<LayoutList> children) {
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
			serializer.exclude(includeData + ".depth");
			serializer.include(includeData + ".name", includeData + ".id", includeData + ".itemtype", includeData
					+ ".groupid", includeData + ".directory", includeData + ".description", includeData + ".leaf");
			serializer.transform(new FieldNameTransformer("indice"), includeData + ".id");
		}

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"children\":[{\"name\":\"Root\",\"indice\":\"0\",\"expanded\":\"true\",\"children\":"
				+ serializer.serialize(this) + "}]}";
	}
}
