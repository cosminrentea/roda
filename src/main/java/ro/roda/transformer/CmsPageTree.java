package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CmsLayoutGroup;
import ro.roda.domain.CmsPage;
import flexjson.JSONSerializer;

@Configurable
public class CmsPageTree extends CmsPageInfo {

	public static String toJsonArr(Collection<CmsPageTree> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth", "type");
		serializer.include("id", "name", "lang", "menutitle", "synopsis", "target", "url", "default",
				"externalredirect", "internalredirect", "layout", "cacheable", "published", "pagetype");

		int maxDepth = 0;
		for (CmsPageTree cmsPageTree : collection) {
			if (maxDepth < cmsPageTree.getDepth()) {
				maxDepth = cmsPageTree.getDepth();
			}
		}

		String includeData = "";
		for (int i = 0; i < maxDepth + 1; i++) {
			if (i > 0) {
				includeData += ".";
			}
			includeData += "children";
			serializer.exclude(includeData + ".depth", includeData + ".type");
			serializer.include(includeData + ".name", includeData + ".id", includeData + ".lang", includeData
					+ ".menutitle", includeData + ".synopsis", includeData + ".target", includeData + ".url",
					includeData + ".default", includeData + ".externalredirect", includeData + ".internalredirect",
					includeData + ".layout", includeData + ".cacheable", includeData + ".published", includeData
							+ ".pagetype");
			serializer.transform(new FieldNameTransformer("indice"), includeData + ".id");
			serializer.transform(new FieldNameTransformer("title"), includeData + ".name");
		}

		serializer.transform(new FieldNameTransformer("indice"), "id");
		serializer.transform(new FieldNameTransformer("title"), "name");

		return "{\"children\":[{\"title\":\"Pages\",\"indice\":\"0\",\"expanded\":\"true\",\"children\":"
				+ serializer.serialize(collection) + "}]}";
	}

	public static List<CmsPageTree> findAllCmsPageTrees() {
		List<CmsPageTree> result = new ArrayList<CmsPageTree>();
		List<CmsPage> cmsPages = CmsLayoutGroup.entityManager()
				.createQuery("SELECT o FROM CmsPage o WHERE o.cmsPageId IS NULL", CmsPage.class).getResultList();

		if (cmsPages != null && cmsPages.size() > 0) {
			Iterator<CmsPage> cmsPagesIterator = cmsPages.iterator();

			while (cmsPagesIterator.hasNext()) {
				CmsPage cmsPage = (CmsPage) cmsPagesIterator.next();

				result.add(findCmsPageTree(cmsPage.getId()));
			}
		}
		return result;
	}

	public static CmsPageTree findCmsPageTree(Integer id) {
		CmsPageTree result = null;
		CmsPage page = CmsPage.findCmsPage(id);

		if (page != null) {
			result = new CmsPageTree(page);
			result.setName(page.getName());

			Set<CmsPageInfo> dataByCmsPageSet = null;

			Set<CmsPage> children = page.getCmsPages();
			Set<CmsPage> pages = page.getCmsPages();

			int maxDepth = 0;
			if (children != null && children.size() > 0) {
				dataByCmsPageSet = new TreeSet<CmsPageInfo>();

				Iterator<CmsPage> childrenIterator = children.iterator();
				while (childrenIterator.hasNext()) {
					CmsPage childCmsPage = childrenIterator.next();
					CmsPageTree cmsPageTree = findCmsPageTree(childCmsPage.getId());
					dataByCmsPageSet.add(cmsPageTree);
					if (maxDepth < cmsPageTree.getDepth()) {
						maxDepth = cmsPageTree.getDepth();
					}
				}
				result.setDepth(maxDepth + 1);
			}

			if (pages != null && pages.size() > 0) {
				if (dataByCmsPageSet == null) {
					dataByCmsPageSet = new TreeSet<CmsPageInfo>();
				}

				Iterator<CmsPage> pagesIterator = pages.iterator();
				while (pagesIterator.hasNext()) {
					dataByCmsPageSet.add(new CmsPageInfo(pagesIterator.next()));
				}
			}
			result.setChildren(dataByCmsPageSet);
		}
		return result;

	}

	// List<CmsPage> cmsPages = CmsPage.entityManager()
	// .createQuery("SELECT o FROM CmsPage o WHERE o.parentId IS NULL",
	// CmsLayoutGroup.class)
	// .getResultList();
	//
	// if (layoutGroups != null && layoutGroups.size() > 0) {
	// Iterator<CmsLayoutGroup> layoutGroupsIterator = layoutGroups.iterator();
	//
	// while (layoutGroupsIterator.hasNext()) {
	// CmsLayoutGroup layoutGroup = (CmsLayoutGroup)
	// layoutGroupsIterator.next();
	//
	// result.add(findLayoutTree(layoutGroup.getId()));
	// }
	// }
	// return result;
	// }
	//
	// public static CmsPageTree findCmsPageTree(Integer id) {
	// CmsPageTree result = null;
	// CmsLayoutGroup layoutGroup = CmsLayoutGroup.findCmsLayoutGroup(id);
	//
	// if (layoutGroup != null) {
	// result = new CmsPageTree(layoutGroup);
	// result.setName(layoutGroup.getName());
	//
	// Set<LayoutList> dataByLayoutGroupSet = null;
	//
	// Set<CmsLayoutGroup> children = layoutGroup.getCmsLayoutGroups();
	// Set<CmsLayout> layouts = layoutGroup.getCmsLayouts();
	//
	// int maxDepth = 0;
	// if (children != null && children.size() > 0) {
	// dataByLayoutGroupSet = new TreeSet<LayoutList>();
	//
	// Iterator<CmsLayoutGroup> childrenIterator = children.iterator();
	// while (childrenIterator.hasNext()) {
	// CmsLayoutGroup childCmsLayoutGroup = childrenIterator.next();
	// CmsPageTree layoutTree = findLayoutTree(childCmsLayoutGroup.getId());
	// dataByLayoutGroupSet.add(layoutTree);
	// if (maxDepth < layoutTree.getDepth()) {
	// maxDepth = layoutTree.getDepth();
	// }
	// }
	// result.setDepth(maxDepth + 1);
	// }
	//
	// if (layouts != null && layouts.size() > 0) {
	// if (dataByLayoutGroupSet == null) {
	// dataByLayoutGroupSet = new TreeSet<LayoutList>();
	// }
	//
	// Iterator<CmsLayout> layoutsIterator = layouts.iterator();
	// while (layoutsIterator.hasNext()) {
	// dataByLayoutGroupSet.add(new LayoutList(layoutsIterator.next()));
	// }
	// }
	// result.setChildren(dataByLayoutGroupSet);
	// }
	// return result;
	//
	// }

	private Set<CmsPageInfo> children;

	private boolean leaf;

	private int depth;

	private boolean expanded = true;

	public Set<CmsPageInfo> getChildren() {
		return children;
	}

	public void setChildren(Set<CmsPageInfo> children) {
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

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public CmsPageTree(CmsPage cmsPage) {
		// super(layoutGroup.getId(), layoutGroup.getName(), null,
		// layoutGroup.getParentId().getId(), "layoutgroup",
		// getLayoutGroupPath(layoutGroup), layoutGroup.getDescription());
		super(cmsPage);
		if (cmsPage.getCmsPages() == null || cmsPage.getCmsPages().size() == 0) {
			this.leaf = true;
		} else {
			this.leaf = false;
		}
		this.children = new HashSet<CmsPageInfo>();
	}

	public CmsPageTree(CmsPage cmsPage, Set<CmsPageInfo> children) {
		this(cmsPage);
		this.children = children;
	}

	public CmsPageTree(CmsPage cmsPage, String name, Set<CmsPageInfo> children) {
		this(cmsPage, children);
		this.setName(name);
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth", "type");
		serializer.include("id", "name", "lang", "menutitle", "synopsis", "target", "url", "default",
				"externalredirect", "internalredirect", "layout", "cacheable", "published", "pagetype");

		String includeData = "";
		for (int i = 0; i < getDepth() + 1; i++) {
			if (i > 0) {
				includeData += ".";
			}
			includeData += "children";
			serializer.exclude(includeData + ".depth", includeData + ".type");
			serializer.include(includeData + ".name", includeData + ".id", includeData + ".lang", includeData
					+ ".menutitle", includeData + ".synopsis", includeData + ".target", includeData + ".url",
					includeData + ".default", includeData + ".externalredirect", includeData + ".internalredirect",
					includeData + ".layout", includeData + ".cacheable", includeData + ".published", includeData
							+ ".pagetype");
			serializer.transform(new FieldNameTransformer("indice"), includeData + ".id");
			serializer.transform(new FieldNameTransformer("title"), includeData + ".name");
		}

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"children\":[{\"name\":\"Pages\",\"indice\":\"0\",\"expanded\":\"true\",\"children\":"
				+ serializer.serialize(this) + "}]}";
	}
}
