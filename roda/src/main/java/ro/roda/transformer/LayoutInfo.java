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

		serializer.exclude("*.class");
		serializer.include("id", "name", "pagesnumber", "groupid", "itemtype", "directory", "description");

		serializer.exclude("pages.cmsLayoutId", "pages.cmsPageContents", "pages.navigable");

		// TODO add "pages.lang"
		serializer.include("pages.id", "pages.name", "pages.url", "pages.visible", "pages.cmsPageTypeId.id",
				"pages.cmsPageTypeId.name");

		serializer.transform(new FieldNameTransformer("layoutusage"), "pages");
		serializer.transform(new FieldNameTransformer("pagetypeid"), "pages.cmsPageTypeId.id");
		serializer.transform(new FieldNameTransformer("pagetypename"), "pages.cmsPageTypeId.name");

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
						"layout", getLayoutPath(layout), "TODO", layout.getCmsPages()));
			}
		}

		List<CmsLayoutGroup> layoutGroups = CmsLayoutGroup.findAllCmsLayoutGroups();

		if (layoutGroups != null && layoutGroups.size() > 0) {

			Iterator<CmsLayoutGroup> layoutGroupsIterator = layoutGroups.iterator();
			while (layoutGroupsIterator.hasNext()) {
				CmsLayoutGroup layoutGroup = (CmsLayoutGroup) layoutGroupsIterator.next();

				Integer parentId = layoutGroup.getParentId() == null ? null : layoutGroup.getParentId().getId();

				result.add(new LayoutInfo(layoutGroup.getId(), layoutGroup.getName(),
						getLayoutGroupPagesNumber(layoutGroup), parentId, "layoutgroup",
						getLayoutGroupPath(layoutGroup), "TODO", getLayoutGroupPages(layoutGroup)));
			}
		}

		return result;
	}

	public static LayoutInfo findLayoutInfo(Integer id) {

		LayoutList layoutList = findLayoutList(id);
		Set<CmsPage> pages = null;

		if (layoutList != null) {
			if (layoutList.getItemtype().equals("layout")) {
				pages = CmsLayout.findCmsLayout(layoutList.getId()).getCmsPages();
			} else {
				pages = getLayoutGroupPages(CmsLayoutGroup.findCmsLayoutGroup(layoutList.getId()));
			}

			return new LayoutInfo(layoutList.getId(), layoutList.getName(), layoutList.getPagesnumber(),
					layoutList.getGroupid(), layoutList.getItemtype(), layoutList.getDirectory(),
					layoutList.getDescription(), pages);
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

	private Set<CmsPage> pages;

	public LayoutInfo(Integer id, String name, Integer pagesnumber, Integer groupid, String itemtype, String directory,
			String description, Set<CmsPage> pages) {
		super(id, name, pagesnumber, groupid, itemtype, directory, description);
		this.pages = pages;
	}

	public Set<CmsPage> getPages() {
		return pages;
	}

	public void setPages(Set<CmsPage> pages) {
		this.pages = pages;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.include("id", "name", "pagesnumber", "groupid", "itemtype", "directory", "description");

		serializer.exclude("pages.cmsLayoutId", "pages.cmsPageContents", "pages.navigable");

		// TODO add "pages.lang"
		serializer.include("pages.id", "pages.name", "pages.url", "pages.visible", "pages.cmsPageTypeId.id",
				"pages.cmsPageTypeId.name");

		serializer.transform(new FieldNameTransformer("layoutusage"), "pages");
		serializer.transform(new FieldNameTransformer("pagetypeid"), "pages.cmsPageTypeId.id");
		serializer.transform(new FieldNameTransformer("pagetypename"), "pages.cmsPageTypeId.name");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
