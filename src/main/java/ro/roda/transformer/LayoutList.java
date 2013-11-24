package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CmsLayout;
import ro.roda.domain.CmsLayoutGroup;
import flexjson.JSONSerializer;

@Configurable
public class LayoutList extends JsonInfo implements Comparable<LayoutList> {

	public static String toJsonArray(Collection<LayoutList> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "leaf");
		serializer.include("id", "name", "pagesnumber", "groupid", "itemtype", "directory", "description");

		serializer.transform(new FieldNameTransformer("indice"), "id");

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
						"layout", getLayoutPath(layout), layout.getDescription()));
			}
		}

		// the below code is commented due to the fact that the Layout List
		// should contain only layouts, not layout groups
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
		 * result.add(new LayoutList(layoutGroup.getId(), layoutGroup.getName(),
		 * getLayoutGroupPagesNumber(layoutGroup), parentId, "layoutgroup",
		 * getLayoutGroupPath(layoutGroup), layoutGroup.getDescription())); } }
		 */

		return result;
	}

	public static String getLayoutPath(CmsLayout layout) {
		if (layout.getCmsLayoutGroupId() != null) {
			return getLayoutGroupPath(layout.getCmsLayoutGroupId());
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
					getLayoutPath(layout), layout.getDescription());
		}
		// the below code is commented due to the fact that the Layout List
		// should contain only layouts, not layout groups
		/*
		 * else { CmsLayoutGroup layoutGroup =
		 * CmsLayoutGroup.findCmsLayoutGroup(id);
		 * 
		 * if (layoutGroup != null) { return new LayoutList(layoutGroup.getId(),
		 * layoutGroup.getName(), getLayoutGroupPagesNumber(layoutGroup),
		 * layout.getCmsLayoutGroupId() != null ? layout
		 * .getCmsLayoutGroupId().getId() : null, "layoutgroup",
		 * getLayoutGroupPath(layoutGroup), layoutGroup.getDescription()); } }
		 */
		return null;
	}

	private Integer id;

	private String name;

	private Integer pagesnumber;

	private Integer groupid;

	private String itemtype;

	private String directory;

	private String description;

	private boolean leaf = true;

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
		this(layout.getId(), layout.getName(), layout.getCmsPages().size(), layout.getCmsLayoutGroupId() == null ? null
				: layout.getCmsLayoutGroupId().getId(), "layout", getLayoutPath(layout), layout.getDescription());
	}

	public LayoutList(CmsLayoutGroup layoutGroup) {
		this(layoutGroup.getId(), layoutGroup.getName(), getLayoutGroupPagesNumber(layoutGroup), layoutGroup
				.getParentId() == null ? null : layoutGroup.getParentId().getId(), "layoutgroup",
				getLayoutGroupPath(layoutGroup), layoutGroup.getDescription());

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

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "leaf");
		serializer.include("id", "name", "pagesnumber", "groupid", "itemtype", "directory", "description");

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

	@Override
	public int compareTo(LayoutList layoutList) {
		System.out.println("Compare " + ((itemtype.equals("layout") ? "2" : "1") + " " + name + " " + groupid)
				+ (layoutList.getItemtype().equals("layout") ? "2" : "1") + " " + layoutList.getName() + " "
				+ layoutList.getGroupid());
		return ((itemtype.equals("layout") ? "2" : "1") + " " + name + " " + groupid).compareTo((layoutList
				.getItemtype().equals("layout") ? "2" : "1")
				+ " "
				+ layoutList.getName()
				+ " "
				+ layoutList.getGroupid());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(itemtype == null ? 0 : (itemtype.equals("layoutgroup") ? 1 : 2))
				.append(groupid == null ? 0 : groupid.intValue()).append(name).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other != null && other instanceof LayoutList) {
			return new EqualsBuilder().append(this.getItemtype(), ((LayoutList) other).getItemtype())
					.append(this.getGroupid(), ((LayoutList) other).getGroupid())
					.append(this.getName(), ((LayoutList) other).getName()).isEquals();
		} else {
			return false;
		}
	}

}
