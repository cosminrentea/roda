package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Catalog;
import ro.roda.domain.CatalogStudy;
import flexjson.JSONSerializer;

@Configurable
public class CatalogTree extends JsonInfo {

	public static String toJsonArray(Collection<CatalogTree> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth");
		serializer.include("id", "name", "type");

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

		return "{\"data\":[{\"name\":\"RODA\",\"type\":\"M\",\"data\":" + serializer.serialize(collection) + "}]}";
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
			result = new CatalogTree(catalog.getId());
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

	private String type;

	private Set<JsonInfo> data;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public CatalogTree(Integer id) {
		this.id = id;
		this.type = "C";
		data = new HashSet<JsonInfo>();
	}

	public CatalogTree(Catalog catalog) {
		this.id = catalog.getId();
		this.type = "C";
	}

	public CatalogTree(Integer id, Set<JsonInfo> data) {
		this.id = id;
		this.type = "C";
		this.data = data;
	}

	public CatalogTree(Integer id, String name, Set<JsonInfo> data) {
		this.id = id;
		this.name = name;
		this.type = "C";
		this.data = data;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth");
		serializer.include("id", "name", "type");

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

		return "{\"data\":[{\"name\":\"RODA\",\"type\":\"M\",\"data\":" + serializer.serialize(this) + "}]}";
	}
}
