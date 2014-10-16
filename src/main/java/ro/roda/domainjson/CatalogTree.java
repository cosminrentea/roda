package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Catalog;
import ro.roda.domain.CatalogStudy;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class CatalogTree extends JsonInfo {

	public static String toJsonArray(Collection<CatalogTree> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth");
		serializer.include("id", "name", "type", "leaf");

		int maxDepth = 0;
		for (CatalogTree catalogTree : collection) {
			if (maxDepth < catalogTree.getDepth()) {
				maxDepth = catalogTree.getDepth();
			}
		}

		String includeData = "";
		for (int i = 0; i < maxDepth + 1; i++) {
			if (i > 0) {
				includeData += ".";
			}
			includeData += "data";
			serializer.exclude(includeData + ".depth", includeData + ".variables", includeData + ".files", includeData
					+ ".geographicCoverage", includeData + ".unitAnalysis", includeData + ".universe");
			serializer.include(includeData + ".name", includeData + ".id", includeData + ".yearStart", includeData
					+ ".type", includeData + ".leaf");
			serializer.transform(new FieldNameTransformer("indice"), includeData + ".id");
		}

		serializer.transform(new FieldNameTransformer("indice"), "id");

//		return "{\"data\":[{\"name\":\"RODA\",\"type\":\"" + JsonInfo.MAIN_TYPE + "\",\"data\":"
//				+ serializer.serialize(collection) + "}]}";
		return serializer.serialize(collection);

	}

	public static List<CatalogTree> findAllCatalogTree() {
		List<CatalogTree> result = new ArrayList<CatalogTree>();

		List<Catalog> catalogs = Catalog.entityManager()
				.createQuery("SELECT o FROM Catalog o WHERE o.parentId IS NULL", Catalog.class).getResultList();

		if (catalogs != null && catalogs.size() > 0) {
			Iterator<Catalog> catalogIterator = catalogs.iterator();

			while (catalogIterator.hasNext()) {
				Catalog catalog = (Catalog) catalogIterator.next();

				result.add(findCatalogTree(catalog.getId()));
			}
		}
		return result;
	}

	public static CatalogTree findCatalogTree(Integer id) {
		CatalogTree result = null;
		Catalog catalog = Catalog.findCatalog(id);

		if (catalog != null) {
			result = new CatalogTree(catalog);
			result.setName(catalog.getName());

			Set<JsonInfo> dataByCatalogSet = null;

			Set<Catalog> children = catalog.getCatalogs();
			Set<CatalogStudy> catalogStudies = catalog.getCatalogStudies();

			int maxDepth = 0;
			if (children != null && children.size() > 0) {
				dataByCatalogSet = new HashSet<JsonInfo>();

				Iterator<Catalog> childrenIterator = children.iterator();
				while (childrenIterator.hasNext()) {
					Catalog childCatalog = childrenIterator.next();
					CatalogTree catalogTree = findCatalogTree(childCatalog.getId());
					dataByCatalogSet.add(catalogTree);
					if (maxDepth < catalogTree.getDepth()) {
						maxDepth = catalogTree.getDepth();
					}
				}
				result.setDepth(maxDepth + 1);
			}

			if (catalogStudies != null && catalogStudies.size() > 0) {
				if (dataByCatalogSet == null) {
					dataByCatalogSet = new HashSet<JsonInfo>();
				}

				Iterator<CatalogStudy> catalogStudiesIterator = catalogStudies.iterator();
				while (catalogStudiesIterator.hasNext()) {
					dataByCatalogSet.add(new StudyInfo(catalogStudiesIterator.next().getStudyId()));
				}
			}
			result.setData(dataByCatalogSet);
		}
		return result;

	}

	private Integer id;

	private String name;

	// private String type;

	private Set<JsonInfo> data;

	private boolean leaf;

	private int depth;

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

	// public String getType() {
	// return type;
	// }
	//
	// public void setType(String type) {
	// this.type = type;
	// }

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public Set<JsonInfo> getData() {
		return data;
	}

	public void setData(Set<JsonInfo> data) {
		this.data = data;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public CatalogTree(Catalog catalog) {
		this.id = catalog.getId();
		if ((catalog.getCatalogs() == null || catalog.getCatalogs().size() == 0)
				&& (catalog.getCatalogStudies() == null || catalog.getCatalogStudies().size() == 0)) {
			this.leaf = true;
		} else {
			this.leaf = false;
		}

		if (catalog.getSeries() == null) {
			setType(JsonInfo.CATALOG_TYPE);
		} else {
			setType(JsonInfo.SERIES_TYPE);
		}
		this.data = new HashSet<JsonInfo>();
	}

	public CatalogTree(Catalog catalog, Set<JsonInfo> data) {
		this(catalog);
		this.data = data;
	}

	public CatalogTree(Catalog catalog, String name, Set<JsonInfo> data) {
		this(catalog, data);
		this.name = name;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth");
		serializer.include("id", "name", "type", "leaf");

		String includeData = "";
		for (int i = 0; i < getDepth() + 1; i++) {
			if (i > 0) {
				includeData += ".";
			}
			includeData += "data";
			serializer.exclude(includeData + ".depth", includeData + ".variables", includeData + ".files", includeData
					+ ".geographicCoverage", includeData + ".unitAnalysis", includeData + ".universe");
			serializer.include(includeData + ".name", includeData + ".id", includeData + ".yearStart", includeData
					+ ".type", includeData + ".leaf");
			serializer.transform(new FieldNameTransformer("indice"), includeData + ".id");
		}

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"data\":[{\"name\":\"RODA\",\"type\":\"" + JsonInfo.MAIN_TYPE + "\",\"data\":"
				+ serializer.serialize(this) + "}]}";
	}
}
