package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Catalog;
import ro.roda.domain.CatalogStudy;
import ro.roda.domain.Study;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class StudiesTree extends StudyList {

	public static String toJsonArr(Collection<StudiesTree> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth", "type");
		serializer.exclude("studies.leaf", "studies.variables", "studies.files", "studies.persons", "studies.orgs",
				"studies.keywords");

		serializer.include("id", "name", "studiesCount");
		serializer.include("studies.name", "studies.id", "studies.yearStart", "studies.description",
				"studies.geographicCoverage", "studies.unitAnalysis", "studies.universe");

		// serializer.include("id", "name", "itemtype", "expanded", "iconCls");

		int maxDepth = 0;
		for (StudiesTree studiesTree : collection) {
			if (maxDepth < studiesTree.getDepth()) {
				maxDepth = studiesTree.getDepth();
			}
		}

		String includeData = "";
		for (int i = 0; i < maxDepth + 1; i++) {
			if (i > 0) {
				includeData += ".";
			}
			includeData += "children";
			serializer.exclude(includeData + ".depth", includeData + ".type");
			serializer.exclude(includeData + "studies.leaf", includeData + "studies.variables", includeData
					+ "studies.files", includeData + "studies.persons", includeData + "studies.orgs", includeData
					+ "studies.keywords");

			serializer.include(includeData + "id", includeData + "name", includeData + "studiesCount");
			serializer.include(includeData + "studies.name", includeData + "studies.id", includeData
					+ "studies.yearStart", includeData + "studies.description", includeData
					+ "studies.geographicCoverage", includeData + "studies.unitAnalysis", includeData
					+ "studies.universe");

			serializer.transform(new FieldNameTransformer("indice"), includeData + ".id");
			// serializer.transform(new FieldNameTransformer("data"),
			// includeData + ".children");
		}

		serializer.transform(new FieldNameTransformer("indice"), "id");
		// serializer.transform(new FieldNameTransformer("data"), "children");

		return "{\"children\":" + serializer.serialize(collection) + "}";
		// return
		// "{\"children\":[{\"name\":\"Root\",\"indice\":\"0\",\"expanded\":\"true\",\"children\":"
		// + serializer.serialize(collection) + "}]}";
	}

	public static List<StudiesTree> findAllStudiesTrees() {
		List<StudiesTree> result = new ArrayList<StudiesTree>();

		List<Catalog> catalogs = Catalog.entityManager()
				.createQuery("SELECT o FROM Catalog o WHERE o.parentId IS NULL", Catalog.class).getResultList();

		if (catalogs != null && catalogs.size() > 0) {
			Iterator<Catalog> catalogsIterator = catalogs.iterator();

			while (catalogsIterator.hasNext()) {
				Catalog catalog = (Catalog) catalogsIterator.next();

				result.add(findStudiesTree(catalog.getId()));
			}
		}
		return result;
	}

	public static StudiesTree findStudiesTree(Integer id) {
		StudiesTree result = null;
		Catalog catalog = Catalog.findCatalog(id);

		if (catalog != null) {
			result = new StudiesTree(catalog);
			result.setName(catalog.getName());

			Set<StudyList> dataByCatalogSet = null;

			Set<Catalog> children = catalog.getCatalogs();
			Set<Study> studies = new HashSet<Study>();
			for (CatalogStudy cs : catalog.getCatalogStudies()) {
				studies.add(cs.getStudyId());
			}

			int maxDepth = 0;
			if (children != null && children.size() > 0) {
				dataByCatalogSet = new TreeSet<StudyList>();

				Iterator<Catalog> childrenIterator = children.iterator();
				while (childrenIterator.hasNext()) {
					Catalog childCatalog = childrenIterator.next();
					StudiesTree studiesTree = findStudiesTree(childCatalog.getId());
					dataByCatalogSet.add(studiesTree);
					if (maxDepth < studiesTree.getDepth()) {
						maxDepth = studiesTree.getDepth();
					}
				}
				result.setDepth(maxDepth + 1);
			}

			if (studies != null && studies.size() > 0) {
				if (dataByCatalogSet == null) {
					dataByCatalogSet = new TreeSet<StudyList>();
				}

				Iterator<Study> studiesIterator = studies.iterator();
				while (studiesIterator.hasNext()) {
					StudyList sl = new StudyList(studiesIterator.next());
					sl.setGroupid(catalog.getId());
					dataByCatalogSet.add(sl);
				}
			}
			result.setChildren(dataByCatalogSet);
		}
		return result;

	}

	private Set<StudyList> children;

	private boolean leaf;

	private int depth;

	private boolean expanded = true;

	public Set<StudyList> getChildren() {
		return children;
	}

	public void setChildren(Set<StudyList> children) {
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

	public StudiesTree(Catalog catalog) {
		super(catalog);
		if ((catalog.getCatalogs() == null || catalog.getCatalogs().size() == 0)
				&& (catalog.getCatalogStudies() == null || catalog.getCatalogStudies().size() == 0)) {
			this.leaf = true;
		} else {
			this.leaf = false;
		}
		this.children = new HashSet<StudyList>();
	}

	public StudiesTree(Catalog catalog, Set<StudyList> children) {
		this(catalog);
		this.children = children;
	}

	public StudiesTree(Catalog catalog, String name, Set<StudyList> children) {
		this(catalog, children);
		this.setName(name);
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth", "type");
		serializer.exclude("studies.leaf", "studies.variables", "studies.files", "studies.persons", "studies.orgs",
				"studies.keywords");

		serializer.include("id", "name", "studiesCount");
		serializer.include("studies.name", "studies.id", "studies.yearStart", "studies.description",
				"studies.geographicCoverage", "studies.unitAnalysis", "studies.universe");

		// serializer.include("id", "name", "itemtype", "expanded", "iconCls");

		String includeData = "";
		for (int i = 0; i < getDepth() + 1; i++) {
			if (i > 0) {
				includeData += ".";
			}
			includeData += "children";
			serializer.exclude(includeData + ".depth", includeData + ".type");
			serializer.exclude(includeData + "studies.leaf", includeData + "studies.variables", includeData
					+ "studies.files", includeData + "studies.persons", includeData + "studies.orgs", includeData
					+ "studies.keywords");

			serializer.include(includeData + "id", includeData + "name", includeData + "studiesCount");
			serializer.include(includeData + "studies.name", includeData + "studies.id", includeData
					+ "studies.yearStart", includeData + "studies.description", includeData
					+ "studies.geographicCoverage", includeData + "studies.unitAnalysis", includeData
					+ "studies.universe");

			serializer.transform(new FieldNameTransformer("indice"), includeData + ".id");
			// serializer.transform(new FieldNameTransformer("data"),
			// includeData + ".children");
		}

		serializer.transform(new FieldNameTransformer("indice"), "id");
		// serializer.transform(new FieldNameTransformer("data"), "children");

		return "{\"children\":" + serializer.serialize(this) + "}";
		// return
		// "{\"children\":[{\"name\":\"Root\",\"indice\":\"0\",\"expanded\":\"true\",\"children\":"
		// + serializer.serialize(this) + "}]}";
	}
}
