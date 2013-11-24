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
import ro.roda.domain.CmsPage;
import flexjson.JSONSerializer;

@Configurable
public class LayoutInfo extends LayoutList {

	public static String toJsonArray(Collection<LayoutInfo> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "leaf", "type");
		serializer.include("id", "name", "pagesnumber", "groupid", "itemtype", "directory", "description", "content");

		serializer.exclude("layoutUsage.cmsLayoutId", "layoutUsage.cmsPageContents", "layoutUsage.navigable");

		serializer.transform(new FlatCmsPageTypeTransformer("pagetype"), "layoutUsage.cmsPageTypeId");

		// TODO add "layoutUsage.lang"
		serializer.include("layoutUsage.id", "layoutUsage.name", "layoutUsage.url", "layoutUsage.visible",
				"layoutUsage.cmsPageTypeId.id", "layoutUsage.cmsPageTypeId.name");

		// serializer.transform(new FieldNameTransformer("layoutusage"),
		// "pages");
		// serializer.transform(new FieldNameTransformer("pagetypeid"),
		// "layoutUsage.cmsPageTypeId.id");
		// serializer.transform(new FieldNameTransformer("pagetypename"),
		// "layoutUsage.cmsPageTypeId.name");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<LayoutInfo> findAllLayoutInfos() {
		List<LayoutInfo> result = new ArrayList<LayoutInfo>();

		List<CmsLayout> layouts = CmsLayout.findAllCmsLayouts();

		if (layouts != null && layouts.size() > 0) {

			Iterator<CmsLayout> layoutsIterator = layouts.iterator();
			while (layoutsIterator.hasNext()) {
				CmsLayout layout = (CmsLayout) layoutsIterator.next();

				Integer groupId = layout.getCmsLayoutGroupId() == null ? null : layout.getCmsLayoutGroupId().getId();

				result.add(new LayoutInfo(layout.getId(), layout.getName(), layout.getCmsPages().size(), groupId,
						"layout", getLayoutPath(layout), layout.getDescription(), layout.getCmsPages(), layout
								.getLayoutContent()));
			}
		}

		/*
		 * List<CmsLayoutGroup> layoutGroups =
		 * CmsLayoutGroup.findAllCmsLayoutGroups();
		 * 
		 * if (layoutGroups != null && layoutGroups.size() > 0) {
		 * 
		 * Iterator<CmsLayoutGroup> layoutGroupsIterator =
		 * layoutGroups.iterator(); while (layoutGroupsIterator.hasNext()) {
		 * CmsLayoutGroup layoutGroup = (CmsLayoutGroup)
		 * layoutGroupsIterator.next();
		 * 
		 * Integer parentId = layoutGroup.getParentId() == null ? null :
		 * layoutGroup.getParentId().getId();
		 * 
		 * result.add(new LayoutInfo(layoutGroup.getId(), layoutGroup.getName(),
		 * getLayoutGroupPagesNumber(layoutGroup), parentId, "layoutgroup",
		 * getLayoutGroupPath(layoutGroup), layoutGroup.getDescription(),
		 * getLayoutGroupPages(layoutGroup))); } }
		 */

		return result;
	}

	public static LayoutInfo findLayoutInfo(Integer id) {

		LayoutList layoutList = findLayoutList(id);
		Set<CmsPage> pages = null;
		String content;

		if (layoutList != null) {
			// if (layoutList.getItemtype().equals("layout")) {
			pages = CmsLayout.findCmsLayout(layoutList.getId()).getCmsPages();
			content = CmsLayout.findCmsLayout(layoutList.getId()).getLayoutContent();
			// }
			/*
			 * else { pages =
			 * getLayoutGroupPages(CmsLayoutGroup.findCmsLayoutGroup
			 * (layoutList.getId())); }
			 */

			return new LayoutInfo(layoutList.getId(), layoutList.getName(), layoutList.getPagesnumber(),
					layoutList.getGroupid(), layoutList.getItemtype(), layoutList.getDirectory(),
					layoutList.getDescription(), pages, content);
		}

		return null;
	}

	public static Set<CmsPage> getLayoutGroupPages(CmsLayoutGroup layoutGroup) {
		Set<CmsPage> pages = new HashSet<CmsPage>();

		Iterator<CmsLayout> layoutsIterator = layoutGroup.getCmsLayouts().iterator();

		while (layoutsIterator.hasNext()) {
			pages.addAll(layoutsIterator.next().getCmsPages());
		}

		Iterator<CmsLayoutGroup> layoutGroupsIterator = layoutGroup.getCmsLayoutGroups().iterator();

		while (layoutGroupsIterator.hasNext()) {
			pages.addAll(getLayoutGroupPages(layoutGroupsIterator.next()));
		}

		return pages;
	}

	private Set<CmsPage> layoutUsage;

	private String content;

	public LayoutInfo(Integer id, String name, Integer pagesnumber, Integer groupid, String itemtype, String directory,
			String description, Set<CmsPage> pages, String content) {
		super(id, name, pagesnumber, groupid, itemtype, directory, description);
		this.layoutUsage = pages;
		this.content = content;
	}

	public Set<CmsPage> getLayoutUsage() {
		return layoutUsage;
	}

	public void setLayoutUsage(Set<CmsPage> pages) {
		this.layoutUsage = pages;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name", "pagesnumber", "groupid", "itemtype", "directory", "description", "content");

		serializer.exclude("layoutUsage.cmsLayoutId", "layoutUsage.cmsPageContents", "layoutUsage.navigable");

		serializer.transform(new FlatCmsPageTypeTransformer("pagetype"), "layoutUsage.cmsPageTypeId");

		// TODO add "layoutUsage.lang"
		serializer.include("layoutUsage.id", "layoutUsage.name", "layoutUsage.url", "layoutUsage.visible",
				"layoutUsage.cmsPageTypeId.id", "layoutUsage.cmsPageTypeId.name");

		// serializer.transform(new FieldNameTransformer("layoutusage"),
		// "pages");
		// serializer.transform(new FieldNameTransformer("pagetypeid"),
		// "layoutUsage.cmsPageTypeId.id");
		// serializer.transform(new FieldNameTransformer("pagetypename"),
		// "layoutUsage.cmsPageTypeId.name");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
