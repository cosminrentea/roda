package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CmsMenu;
import flexjson.JSONSerializer;

@Configurable
public class CmsMenuTree extends JsonInfo {

	public static String toJsonArray(Collection<CmsMenuTree> collection) {

		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth", "name", "type", "depth");
		serializer.include("id", "text", "parentId", "className", "iconCls", "leaf", "items");

		int maxDepth = 0;
		for (CmsMenuTree cmsMenuTree : collection) {
			if (maxDepth < cmsMenuTree.getDepth()) {
				maxDepth = cmsMenuTree.getDepth();
			}
		}

		String includeData = "";
		for (int i = 0; i < maxDepth + 1; i++) {
			if (i > 0) {
				includeData += ".";
			}
			includeData += "items";
			serializer.exclude(includeData + ".depth", includeData + ".type", includeData + ".name");
			serializer.include(includeData + ".text", includeData + ".id", includeData + ".parentId", includeData
					+ ".className", includeData + ".iconCls", includeData + ".leaf");
			serializer.transform(new FieldNameTransformer("indice"), includeData + ".id");
		}

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"items\":" + serializer.serialize(collection) + "}";
	}

	public static List<CmsMenuTree> findAllCmsMenuTree() {
		List<CmsMenuTree> result = new ArrayList<CmsMenuTree>();

		List<CmsMenu> cmsMenus = CmsMenu.entityManager()
				.createQuery("SELECT o FROM CmsMenu o WHERE o.parentId IS NULL", CmsMenu.class).getResultList();

		if (cmsMenus != null && cmsMenus.size() > 0) {
			Iterator<CmsMenu> cmsMenuIterator = cmsMenus.iterator();

			while (cmsMenuIterator.hasNext()) {
				CmsMenu cmsMenu = (CmsMenu) cmsMenuIterator.next();

				result.add(findCmsMenuTree(cmsMenu.getId()));
			}
		}
		return result;
	}

	public static CmsMenuTree findCmsMenuTree(Integer id) {
		CmsMenuTree result = null;
		CmsMenu cmsMenu = CmsMenu.findCmsMenu(id);

		if (cmsMenu != null) {
			result = new CmsMenuTree(cmsMenu);
			result.setText(cmsMenu.getText());

			Set<CmsMenu> items = cmsMenu.getCmsMenus();
			Set<CmsMenuTree> dataByCmsMenuSet = null;
			int maxDepth = 0;
			if (items != null && items.size() > 0) {
				dataByCmsMenuSet = new HashSet<CmsMenuTree>();

				Iterator<CmsMenu> childrenIterator = items.iterator();
				while (childrenIterator.hasNext()) {
					CmsMenu childCmsMenu = childrenIterator.next();
					CmsMenuTree cmsMenuTree = findCmsMenuTree(childCmsMenu.getId());
					dataByCmsMenuSet.add(cmsMenuTree);
					if (maxDepth < cmsMenuTree.getDepth()) {
						maxDepth = cmsMenuTree.getDepth();
					}
				}
				result.setDepth(maxDepth + 1);
			}

			result.setItems(dataByCmsMenuSet);
		}
		return result;

	}

	private Integer id;

	private String text;

	private String iconCls;

	private Integer parentId;

	private String className;

	private Set<CmsMenuTree> items;

	private boolean leaf;

	private int depth;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Set<CmsMenuTree> getItems() {
		return items;
	}

	public void setItems(Set<CmsMenuTree> items) {
		this.items = items;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public CmsMenuTree(CmsMenu cmsMenu) {
		this.id = cmsMenu.getId();
		this.text = cmsMenu.getText();
		this.className = cmsMenu.getClassName();
		this.iconCls = cmsMenu.getIconCls();
		if (cmsMenu.getParentId() == null) {
			this.parentId = 0;
		} else {
			this.parentId = cmsMenu.getParentId().getId();
		}
		if (cmsMenu.getCmsMenus() != null && cmsMenu.getCmsMenus().size() > 0) {
			this.leaf = false;
		} else {
			this.leaf = true;
		}
		this.items = new HashSet<CmsMenuTree>();
	}

	public String toJson() {

		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth", "name", "type");
		serializer.include("id", "text", "parentId", "className", "iconCls", "leaf", "items");

		String includeData = "";
		for (int i = 0; i < getDepth() + 1; i++) {
			if (i > 0) {
				includeData += ".";
			}
			includeData += "items";
			serializer.exclude(includeData + ".depth", includeData + ".type", includeData + ".name");
			serializer.include(includeData + ".text", includeData + ".id", includeData + ".parentId", includeData
					+ ".className", includeData + ".iconCls", includeData + ".leaf");
			serializer.transform(new FieldNameTransformer("indice"), includeData + ".id");
		}

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"items\":" + serializer.serialize(this) + "}";
	}
}
