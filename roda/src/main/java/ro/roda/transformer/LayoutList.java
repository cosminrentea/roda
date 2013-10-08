package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CmsLayout;
import ro.roda.domain.CmsLayoutGroup;
import flexjson.JSONSerializer;

@Configurable
public class LayoutList extends JsonInfo {

	public static String toJsonArray(Collection<LayoutList> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.include("id", "name", "pagesnumber", "groupid", "itemtype", "directory", "description");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<LayoutList> findAllLayoutLists() {
		List<LayoutList> result = new ArrayList<LayoutList>();

		List<CmsLayout> layouts = CmsLayout.findAllCmsLayouts();

		if (layouts != null && layouts.size() > 0) {

			Iterator<CmsLayout> layoutsIterator = layouts.iterator();
			while (layoutsIterator.hasNext()) {
				CmsLayout layout = (CmsLayout) layoutsIterator.next();

				Integer groupId = layout.getCmsLayoutGroupId() == null ? null : layout.getCmsLayoutGroupId().getId();

				result.add(new LayoutList(layout.getId(), layout.getName(), layout.getCmsPages().size(), groupId,
						"layout", getLayoutPath(layout), "TODO"));
			}
		}

		List<CmsLayoutGroup> layoutGroups = CmsLayoutGroup.findAllCmsLayoutGroups();

		if (layoutGroups != null && layoutGroups.size() > 0) {

			Iterator<CmsLayoutGroup> layoutGroupsIterator = layoutGroups.iterator();
			while (layoutGroupsIterator.hasNext()) {
				CmsLayoutGroup layoutGroup = (CmsLayoutGroup) layoutGroupsIterator.next();

				Integer parentId = layoutGroup.getParentId() == null ? null : layoutGroup.getParentId().getId();

				result.add(new LayoutList(layoutGroup.getId(), layoutGroup.getName(),
						getLayoutGroupPagesNumber(layoutGroup), parentId, "layoutgroup",
						getLayoutGroupPath(layoutGroup), "TODO"));
			}
		}

		return result;
	}

	public static String getLayoutPath(CmsLayout layout) {
		if (layout.getCmsLayoutGroupId() != null) {
			return getLayoutGroupPath(layout.getCmsLayoutGroupId()) + "/" + layout.getName();
		} else {
			return layout.getName();
		}
	}

	public static String getLayoutGroupPath(CmsLayoutGroup layoutGroup) {
		if (layoutGroup.getParentId() != null) {
			return getLayoutGroupPath(layoutGroup.getParentId()) + "/" + layoutGroup.getName();
		} else {
			return layoutGroup.getName();
		}
	}

	public static int getLayoutGroupPagesNumber(CmsLayoutGroup layoutGroup) {
		int result = 0;

		Iterator<CmsLayout> layoutsIterator = layoutGroup.getCmsLayouts().iterator();

		while (layoutsIterator.hasNext()) {
			result += layoutsIterator.next().getCmsPages().size();
		}

		Iterator<CmsLayoutGroup> layoutGroupsIterator = layoutGroup.getCmsLayoutGroups().iterator();

		while (layoutGroupsIterator.hasNext()) {
			result += getLayoutGroupPagesNumber(layoutGroupsIterator.next());
		}

		return result;
	}

	public static LayoutList findLayoutList(Integer id) {
		CmsLayout layout = CmsLayout.findCmsLayout(id);

		if (layout != null) {
			return new LayoutList(layout.getId(), layout.getName(), layout.getCmsPages().size(),
					layout.getCmsLayoutGroupId() != null ? layout.getCmsLayoutGroupId().getId() : null, "layout",
					getLayoutPath(layout), "TODO");
		} else {
			CmsLayoutGroup layoutGroup = CmsLayoutGroup.findCmsLayoutGroup(id);

			if (layoutGroup != null) {
				return new LayoutList(layoutGroup.getId(), layoutGroup.getName(),
						getLayoutGroupPagesNumber(layoutGroup), layout.getCmsLayoutGroupId() != null ? layout
								.getCmsLayoutGroupId().getId() : null, "layoutgroup", getLayoutGroupPath(layoutGroup),
						"TODO");
			}
		}
		return null;
	}

	private Integer id;

	private String name;

	private Integer pagesnumber;

	private Integer groupid;

	private String itemtype;

	private String directory;

	// TODO to be added in the database
	private String description;

	public LayoutList(Integer id, String name, Integer pagesnumber, Integer groupid, String itemtype, String directory,
			String description) {
		this.id = id;
		this.name = name;
		this.pagesnumber = pagesnumber;
		this.groupid = groupid;
		this.itemtype = itemtype;
		this.directory = directory;
		this.description = description;
	}

	public LayoutList(CmsLayout layout) {
		this(layout.getId(), layout.getName(), layout.getCmsPages().size(), layout.getCmsLayoutGroupId().getId(),
				"layout", getLayoutPath(layout), "TO DO");
	}

	public LayoutList(CmsLayoutGroup layoutGroup) {
		this(layoutGroup.getId(), layoutGroup.getName(), getLayoutGroupPagesNumber(layoutGroup), layoutGroup
				.getParentId().getId(), "layoutgroup", getLayoutGroupPath(layoutGroup), "TO DO");

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPagesnumber() {
		return pagesnumber;
	}

	public void setPagesnumber(Integer pagesnumber) {
		this.pagesnumber = pagesnumber;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getItemtype() {
		return itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.include("id", "name", "pagesnumber", "groupid", "itemtype", "directory", "description");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
