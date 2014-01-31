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
public class LayoutGroupInfo extends LayoutList {

	public static String toJsonArr(Collection<LayoutGroupInfo> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "expanded", "leaf");
		serializer.include("id", "name", "pagesnumber", "groupid", "itemtype", "directory", "description", "iconCls");

		serializer.transform(new FieldNameTransformer("layoutsnumber"), "pagesnumber");
		serializer.transform(new FieldNameTransformer("pagetypeid"), "pages.cmsPageTypeId.id");
		serializer.transform(new FieldNameTransformer("pagetypename"), "pages.cmsPageTypeId.name");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<LayoutGroupInfo> findAllGroupInfos() {
		List<LayoutGroupInfo> result = new ArrayList<LayoutGroupInfo>();

		List<CmsLayoutGroup> layoutGroups = CmsLayoutGroup.findAllCmsLayoutGroups();

		if (layoutGroups != null && layoutGroups.size() > 0) {

			Iterator<CmsLayoutGroup> layoutGroupsIterator = layoutGroups.iterator();
			while (layoutGroupsIterator.hasNext()) {
				CmsLayoutGroup layoutGroup = (CmsLayoutGroup) layoutGroupsIterator.next();

				Integer parentId = layoutGroup.getParentId() == null ? null : layoutGroup.getParentId().getId();

				result.add(new LayoutGroupInfo(layoutGroup.getId(), layoutGroup.getName(), layoutGroup.getCmsLayouts()
						.size(), parentId, "layoutgroup", getLayoutGroupPath(layoutGroup),
						layoutGroup.getDescription(), (layoutGroup.getCmsLayouts().size() == 0 && layoutGroup
								.getCmsLayoutGroups().size() == 0)));
			}
		}

		return result;
	}

	public static LayoutGroupInfo findGroupInfo(Integer id) {

		// LayoutList layoutList = findLayoutList(id);
		/*
		 * boolean leaf; int layoutsNumber;
		 * 
		 * if (layoutList != null) { if
		 * (layoutList.getItemtype().equals("layoutgroup")) { CmsLayoutGroup
		 * layoutGroup = CmsLayoutGroup.findCmsLayoutGroup(layoutList.getId());
		 * layoutsNumber = layoutGroup.getCmsLayouts().size(); leaf =
		 * (layoutsNumber == 0 && layoutGroup.getCmsLayoutGroups().size() == 0);
		 * 
		 * return new LayoutGroupInfo(layoutList.getId(), layoutList.getName(),
		 * layoutsNumber, layoutList.getGroupid(), layoutList.getItemtype(),
		 * layoutList.getDirectory(), layoutList.getDescription(), leaf); }
		 * 
		 * } return null;
		 */

		CmsLayoutGroup layoutGroup = CmsLayoutGroup.findCmsLayoutGroup(id);

		boolean leaf;
		int layoutsNumber;

		if (layoutGroup != null) {
			layoutsNumber = layoutGroup.getCmsLayouts().size();
			leaf = (layoutsNumber == 0 && layoutGroup.getCmsLayoutGroups().size() == 0);
			return new LayoutGroupInfo(layoutGroup.getId(), layoutGroup.getName(), layoutsNumber,
					layoutGroup.getParentId() != null ? layoutGroup.getParentId().getId() : null, "layoutgroup",
					getLayoutGroupPath(layoutGroup), layoutGroup.getDescription(), leaf);
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

	private boolean expanded = true;

	public LayoutGroupInfo(Integer id, String name, Integer layoutnumber, Integer groupid, String itemtype,
			String directory, String description, boolean leaf) {
		super(id, name, layoutnumber, groupid, itemtype, directory, description, "layoutgroup");
		setLeaf(leaf);
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "expanded", "leaf");
		serializer.include("id", "name", "pagesnumber", "groupid", "itemtype", "directory", "description", "iconCls");

		serializer.transform(new FieldNameTransformer("layoutsnumber"), "pagesnumber");
		serializer.transform(new FieldNameTransformer("pagetypeid"), "pages.cmsPageTypeId.id");
		serializer.transform(new FieldNameTransformer("pagetypename"), "pages.cmsPageTypeId.name");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
