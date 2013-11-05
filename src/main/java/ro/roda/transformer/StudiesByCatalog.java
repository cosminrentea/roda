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
public class StudiesByCatalog extends JsonInfo {

	public static String toJsonArray(Collection<StudiesByCatalog> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.exclude("studies.leaf", "studies.variables", "studies.files", "studies.persons", "studies.orgs",
				"studies.keywords");

		serializer.include("id", "name", "studiesCount");
		serializer.include("studies.name", "studies.id", "studies.yearStart", "studies.description",
				"studies.geographicCoverage", "studies.unitAnalysis", "studies.universe");

		// return "{\"data\":[{\"name\":\"RODA\",\"level\":0,\"data\":"
		// + serializer.serialize(collection) + "}]}";

		serializer.transform(new FieldNameTransformer("indice"), "id");
		serializer.transform(new FieldNameTransformer("nrStudies"), "studiesCount");
		serializer.transform(new FieldNameTransformer("geo_coverage"), "studies.geographicCoverage");
		serializer.transform(new FieldNameTransformer("unit_analysis"), "studies.unitAnalysis");
		serializer.transform(new FieldNameTransformer("univers"), "studies.universe");
		serializer.transform(new FieldNameTransformer("indice"), "studies.id");
		// TODO transform the fields name in variables and files

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<StudiesByCatalog> findAllStudiesByCatalog() {
		List<StudiesByCatalog> result = new ArrayList<StudiesByCatalog>();
		List<Catalog> catalogs = Catalog.findAllCatalogs();

		if (catalogs != null && catalogs.size() > 0) {

			Iterator<Catalog> catalogsIterator = catalogs.iterator();
			while (catalogsIterator.hasNext()) {
				Catalog catalog = (Catalog) catalogsIterator.next();
				result.add(findDirectStudiesByCatalog(catalog.getId()));
			}
		}

		return result;
	}

	public static List<StudiesByCatalog> findAllDirectStudiesByCatalog() {
		List<StudiesByCatalog> result = new ArrayList<StudiesByCatalog>();
		List<Catalog> catalogs = Catalog.findAllCatalogs();

		if (catalogs != null && catalogs.size() > 0) {
			Set<StudyInfo> studiesByCatalogSet = null;
			Iterator<Catalog> catalogIterator = catalogs.iterator();

			while (catalogIterator.hasNext()) {
				Catalog catalog = (Catalog) catalogIterator.next();
				Set<CatalogStudy> catalogStudies = catalog.getCatalogStudies();

				if (catalogStudies != null && catalogStudies.size() > 0) {
					studiesByCatalogSet = new HashSet<StudyInfo>();

					Iterator<CatalogStudy> catalogStudiesIterator = catalogStudies.iterator();
					while (catalogStudiesIterator.hasNext()) {
						studiesByCatalogSet.add(new StudyInfo(catalogStudiesIterator.next().getStudyId()));
					}

					result.add(new StudiesByCatalog(catalog.getId(), catalog.getName(), studiesByCatalogSet.size(),
							studiesByCatalogSet));
				}

			}
		}
		return result;
	}

	public static StudiesByCatalog findStudiesByCatalog(Integer id) {
		Catalog targetCatalog = Catalog.findCatalog(id);
		Set<StudyInfo> catalogSubstudies = new HashSet<StudyInfo>();

		Set<Catalog> catalogs = new HashSet<Catalog>();
		catalogs.add(targetCatalog);

		while (catalogs.size() > 0) {
			Set<Catalog> subcatalogs = new HashSet<Catalog>();

			Iterator<Catalog> catalogsIterator = catalogs.iterator();
			while (catalogsIterator.hasNext()) {
				Catalog catalog = catalogsIterator.next();

				Set<CatalogStudy> catalogStudies = catalog.getCatalogStudies();
				if (catalogStudies != null && catalogStudies.size() > 0) {

					Iterator<CatalogStudy> catalogStudiesIterator = catalogStudies.iterator();
					while (catalogStudiesIterator.hasNext()) {
						catalogSubstudies.add(new StudyInfo(catalogStudiesIterator.next().getStudyId()));
					}
				}

				Set<Catalog> catalogCatalogs = catalog.getCatalogs();
				if (catalogCatalogs != null && catalogCatalogs.size() > 0) {
					subcatalogs.addAll(catalogCatalogs);
				}
			}

			catalogs.clear();
			catalogs.addAll(subcatalogs);
		}

		return new StudiesByCatalog(targetCatalog.getId(), targetCatalog.getName(), catalogSubstudies.size(),
				catalogSubstudies);
	}

	public static StudiesByCatalog findDirectStudiesByCatalog(Integer id) {
		StudiesByCatalog result = null;
		Catalog catalog = Catalog.findCatalog(id);

		if (catalog != null) {
			Set<StudyInfo> studiesByCatalogSet = null;

			Set<CatalogStudy> catalogStudies = catalog.getCatalogStudies();

			// direct studies under catalog
			if (catalogStudies != null && catalogStudies.size() > 0) {
				studiesByCatalogSet = new HashSet<StudyInfo>();

				Iterator<CatalogStudy> catalogStudiesIterator = catalogStudies.iterator();
				while (catalogStudiesIterator.hasNext()) {
					studiesByCatalogSet.add(new StudyInfo(catalogStudiesIterator.next().getStudyId()));
				}

				result = new StudiesByCatalog(catalog.getId(), catalog.getName(), studiesByCatalogSet.size(),
						studiesByCatalogSet);
			}
		}
		return result;
	}

	private Integer id;

	private String name;

	private Integer studiesCount;

	private Set<StudyInfo> studies;

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

	public Integer getStudiesCount() {
		return studiesCount;
	}

	public void setStudiesCount(Integer studiesCount) {
		this.studiesCount = studiesCount;
	}

	public Set<StudyInfo> getStudies() {
		return studies;
	}

	public void setStudies(Set<StudyInfo> studies) {
		this.studies = studies;
	}

	public StudiesByCatalog(Integer id) {
		this.id = id;
		studies = new HashSet<StudyInfo>();
	}

	public StudiesByCatalog(Integer id, Set<StudyInfo> studies) {
		this.id = id;
		this.studies = studies;
	}

	public StudiesByCatalog(Integer id, String name, Integer studiesCount, Set<StudyInfo> studies) {
		this.id = id;
		this.name = name;
		this.studiesCount = studiesCount;
		this.studies = studies;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.exclude("studies.leaf", "studies.variables", "studies.files", "studies.persons", "studies.orgs",
				"studies.keywords");

		serializer.include("id", "name", "studiesCount");
		serializer.include("studies.name", "studies.id", "studies.yearStart", "studies.description",
				"studies.geographicCoverage", "studies.unitAnalysis", "studies.universe");

		// return "{\"data\":[{\"name\":\"RODA\",\"level\":0,\"data\":"
		// + serializer.serialize(collection) + "}]}";

		serializer.transform(new FieldNameTransformer("indice"), "id");
		serializer.transform(new FieldNameTransformer("nrStudies"), "studiesCount");
		serializer.transform(new FieldNameTransformer("geo_coverage"), "studies.geographicCoverage");
		serializer.transform(new FieldNameTransformer("unit_analysis"), "studies.unitAnalysis");
		serializer.transform(new FieldNameTransformer("univers"), "studies.universe");
		serializer.transform(new FieldNameTransformer("indice"), "studies.id");
		// TODO transform the fields name in variables and files

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
