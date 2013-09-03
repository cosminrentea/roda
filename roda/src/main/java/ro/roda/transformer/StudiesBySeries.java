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
import ro.roda.domain.Series;
import ro.roda.domain.SeriesDescr;
import flexjson.JSONSerializer;

@Configurable
public class StudiesBySeries extends JsonInfo {

	public static String toJsonArray(Collection<StudiesBySeries> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.exclude("studies.leaf", "studies.universe", "studies.variables.concepts",
				"studies.variables.fileId", "studies.variables.formEditedNumberVars",
				"studies.variables.instanceVariables", "studies.variables.operatorInstructions",
				"studies.variables.otherStatistics", "studies.variables.selectionVariable", "studies.variables.skips",
				"studies.variables.skips1", "studies.variables.type", "studies.variables.vargroups",
				"studies.variables.variableType");
		serializer.exclude("studies.files");

		serializer.include("id", "name", "studiesCount");
		serializer.include("studies.name", "studies.id", "studies.yearStart", "studies.description",
				"studies.geographicCoverage", "studies.unitAnalysis");
		serializer.include("studies.variables.id", "studies.variables.name", "studies.variables.label");

		// return "{\"data\":[{\"name\":\"RODA\",\"level\":0,\"data\":"
		// + serializer.serialize(collection) + "}]}";

		serializer.transform(new FieldNameTransformer("indice"), "id");
		serializer.transform(new FieldNameTransformer("nrStudies"), "studiesCount");
		serializer.transform(new FieldNameTransformer("geo_coverage"), "studies.geographicCoverage");
		serializer.transform(new FieldNameTransformer("unit_analysis"), "studies.unitAnalysis");
		serializer.transform(new FieldNameTransformer("univers"), "studies.universe");
		serializer.transform(new FieldNameTransformer("indice"), "studies.id");
		serializer.transform(new FieldNameTransformer("indice"), "studies.variables.id");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<StudiesBySeries> findAllStudiesBySeries() {
		List<StudiesBySeries> result = new ArrayList<StudiesBySeries>();
		List<Series> series = Series.findAllSerieses();

		if (series != null && series.size() > 0) {
			Set<StudyInfo> studiesBySeriesSet = null;
			Iterator<Series> seriesIterator = series.iterator();

			while (seriesIterator.hasNext()) {
				Series series1 = (Series) seriesIterator.next();
				Catalog seriesCatalog = series1.getCatalog();

				Set<SeriesDescr> seriesDescrs = series1.getSeriesDescrs();
				String seriesGeoCoverage = null;

				if (seriesDescrs != null && seriesDescrs.size() > 0) {
					SeriesDescr seriesDescr = seriesDescrs.iterator().next();
					seriesGeoCoverage = seriesDescr.getGeographicCoverage();
				}

				if (seriesCatalog != null) {

					Set<CatalogStudy> seriesStudies = seriesCatalog.getCatalogStudies();

					if (seriesStudies != null && seriesStudies.size() > 0) {
						studiesBySeriesSet = new HashSet<StudyInfo>();

						Iterator<CatalogStudy> seriesStudyIterator = seriesStudies.iterator();
						while (seriesStudyIterator.hasNext()) {
							studiesBySeriesSet.add(new StudyInfo(seriesStudyIterator.next().getStudyId()));
						}

						// TODO What is the study's name? For now, it's the
						// title found in its first description.
						result.add(new StudiesBySeries(series1.getCatalogId(), seriesCatalog.getName(),
								studiesBySeriesSet.size(), studiesBySeriesSet, seriesGeoCoverage, seriesCatalog
										.getDescription()));
					}
				}
			}
		}
		return result;
	}

	public static StudiesBySeries findStudiesBySeries(Integer id) {
		StudiesBySeries result = null;
		Series series = Series.findSeries(id);

		if (series != null && series.getCatalog() != null) {
			Set<StudyInfo> studiesBySeriesSet = null;

			Set<CatalogStudy> catalogStudies = series.getCatalog().getCatalogStudies();

			String seriesGeoCoverage = null;

			if (series.getSeriesDescrs() != null && series.getSeriesDescrs().size() > 0) {
				SeriesDescr seriesDescr = series.getSeriesDescrs().iterator().next();
				seriesGeoCoverage = seriesDescr.getGeographicCoverage();
			}

			if (catalogStudies != null && catalogStudies.size() > 0) {
				studiesBySeriesSet = new HashSet<StudyInfo>();

				Iterator<CatalogStudy> catalogStudiesIterator = catalogStudies.iterator();
				while (catalogStudiesIterator.hasNext()) {
					studiesBySeriesSet.add(new StudyInfo(catalogStudiesIterator.next().getStudyId()));
				}

				result = new StudiesBySeries(series.getCatalogId(), series.getCatalog().getName(),
						studiesBySeriesSet.size(), studiesBySeriesSet, seriesGeoCoverage, series.getCatalog()
								.getDescription());
			}
		}
		return result;
	}

	private Integer id;

	private String name;

	private String geoCoverage;

	private String universe;

	private String description;

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

	public String getGeoCoverage() {
		return geoCoverage;
	}

	public void setGeoCoverage(String geoCoverage) {
		this.geoCoverage = geoCoverage;
	}

	public String getUniverse() {
		return universe;
	}

	public void setUniverse(String universe) {
		this.universe = universe;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public StudiesBySeries(Integer id) {
		this.id = id;
		studies = new HashSet<StudyInfo>();
	}

	public StudiesBySeries(Integer id, Set<StudyInfo> studies) {
		this.id = id;
		this.studies = studies;
	}

	public StudiesBySeries(Integer id, String name, Integer studiesCount, Set<StudyInfo> studies, String geoCoverage,
			String description) {
		this.id = id;
		this.name = name;
		this.studiesCount = studiesCount;
		this.studies = studies;
		this.geoCoverage = geoCoverage;
		this.description = description;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.exclude("studies.leaf", "studies.universe", "studies.variables.concepts",
				"studies.variables.fileId", "studies.variables.formEditedNumberVars",
				"studies.variables.instanceVariables", "studies.variables.operatorInstructions",
				"studies.variables.otherStatistics", "studies.variables.selectionVariable", "studies.variables.skips",
				"studies.variables.skips1", "studies.variables.type", "studies.variables.vargroups",
				"studies.variables.variableType");
		serializer.exclude("studies.files.content", "studies.files.fullPath", "studies.files.id",
				"studies.files.instances", "studies.files.selectionVariableItems", "studies.files.size",
				"studies.files.studies1", "studies.files.title", "studies.files.variables");

		serializer.include("id", "name", "studiesCount");
		serializer.include("studies.name", "studies.id", "studies.yearStart", "studies.description",
				"studies.geographicCoverage", "studies.unitAnalysis");
		serializer.include("studies.variables.id", "studies.variables.name", "studies.variables.label");

		// return "{\"data\":[{\"name\":\"RODA\",\"level\":0,\"data\":"
		// + serializer.serialize(collection) + "}]}";

		serializer.transform(new FieldNameTransformer("indice"), "id");
		serializer.transform(new FieldNameTransformer("nrStudies"), "studiesCount");
		serializer.transform(new FieldNameTransformer("geo_coverage"), "studies.geographicCoverage");
		serializer.transform(new FieldNameTransformer("unit_analysis"), "studies.unitAnalysis");
		serializer.transform(new FieldNameTransformer("univers"), "studies.universe");
		serializer.transform(new FieldNameTransformer("indice"), "studies.id");
		serializer.transform(new FieldNameTransformer("indice"), "studies.variables.id");
		// TODO transform the fields name in variables and files

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
