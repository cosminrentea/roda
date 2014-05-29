package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Catalog;
import flexjson.JSONSerializer;

@Configurable
public class CatalogInfo extends StudyList {

	public static String toJsonArr(Collection<CatalogInfo> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "expanded", "leaf");
		serializer.include("id", "name", "groupid", "itemtype", "description");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<CatalogInfo> findAllGroupInfos() {
		List<CatalogInfo> result = new ArrayList<CatalogInfo>();

		List<Catalog> catalogs = Catalog.findAllCatalogs();

		if (catalogs != null && catalogs.size() > 0) {

			Iterator<Catalog> catalogsIterator = catalogs.iterator();
			while (catalogsIterator.hasNext()) {
				Catalog catalog = (Catalog) catalogsIterator.next();

				Integer parentId = catalog.getParentId() == null ? null : catalog.getParentId().getId();

				result.add(new CatalogInfo(catalog.getId(), catalog.getName(), catalog.getCatalogStudies().size(),
						parentId, "studygroup", getCatalogPath(catalog), catalog.getDescription(), (catalog
								.getCatalogStudies().size() == 0 && catalog.getCatalogs().size() == 0)));
			}
		}

		return result;
	}

	public static CatalogInfo findGroupInfo(Integer id) {

		// StudyList studyList = findStudyList(id);
		/*
		 * boolean leaf; int studysNumber;
		 * 
		 * if (studyList != null) { if
		 * (studyList.getItemtype().equals("studygroup")) { Catalog catalog =
		 * Catalog.findCatalog(studyList.getId()); studysNumber =
		 * catalog.getStudys().size(); leaf = (studysNumber == 0 &&
		 * catalog.getCatalogs().size() == 0);
		 * 
		 * return new CatalogInfo(studyList.getId(), studyList.getName(),
		 * studysNumber, studyList.getGroupid(), studyList.getItemtype(),
		 * studyList.getDirectory(), studyList.getDescription(), leaf); }
		 * 
		 * } return null;
		 */

		Catalog catalog = Catalog.findCatalog(id);

		boolean leaf;
		int studysNumber;

		if (catalog != null) {
			studysNumber = catalog.getCatalogStudies().size();
			leaf = (studysNumber == 0 && catalog.getCatalogs().size() == 0);
			return new CatalogInfo(catalog.getId(), catalog.getName(), studysNumber,
					catalog.getParentId() != null ? catalog.getParentId().getId() : null, "catalog",
					getCatalogPath(catalog), catalog.getDescription(), leaf);
		}

		return null;
	}

	private boolean expanded = true;

	public CatalogInfo(Integer id, String name, Integer studynumber, Integer groupid, String itemtype,
			String directory, String description, boolean leaf) {
		super(id, name, groupid, itemtype, description);
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
		serializer.include("id", "name", "groupid", "itemtype", "description");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
