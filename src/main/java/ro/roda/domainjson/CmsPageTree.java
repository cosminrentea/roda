package ro.roda.domainjson;

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
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class CmsPageTree extends CmsPageInfo {

	public static String toJsonArr(Collection<CmsPageTree> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth", "type", "content");
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
			serializer.exclude(includeData + ".depth", includeData + ".type", includeData + ".content");
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

			Set<CmsPageTree> dataByCmsPageSet = null;

			Set<CmsPage> children = page.getCmsPages();

			int maxDepth = 0;
			if (children != null && children.size() > 0) {

				dataByCmsPageSet = new TreeSet<CmsPageTree>();

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

			result.setChildren(dataByCmsPageSet);
		}
		return result;

	}

	private Set<CmsPageTree> children;

	private boolean leaf;

	private int depth;

	private boolean expanded = true;

	public Set<CmsPageTree> getChildren() {
		return children;
	}

	public void setChildren(Set<CmsPageTree> children) {
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
		super(cmsPage);
		if (cmsPage.getCmsPages() == null || cmsPage.getCmsPages().size() == 0) {
			this.leaf = true;
		} else {
			this.leaf = false;
		}
		this.children = new HashSet<CmsPageTree>();
	}

	public CmsPageTree(CmsPage cmsPage, Set<CmsPageTree> children) {
		this(cmsPage);
		this.children = children;
	}

	public CmsPageTree(CmsPage cmsPage, String name, Set<CmsPageTree> children) {
		this(cmsPage, children);
		this.setName(name);
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth", "type", "content");
		serializer.include("id", "name", "lang", "menutitle", "synopsis", "target", "url", "default",
				"externalredirect", "internalredirect", "layout", "cacheable", "published", "pagetype");

		String includeData = "";
		for (int i = 0; i < getDepth() + 1; i++) {
			if (i > 0) {
				includeData += ".";
			}
			includeData += "children";
			serializer.exclude(includeData + ".depth", includeData + ".type", includeData + ".content");
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
