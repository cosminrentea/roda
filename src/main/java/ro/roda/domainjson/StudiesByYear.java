package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Study;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class StudiesByYear extends JsonInfo {

	public static String toJsonArray(Collection<StudiesByYear> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "id", "name", "type");
		serializer.exclude("studies.variables", "studies.files", "studies.leaf");
		serializer.include("year", "studiesCount");
		serializer.include("studies.name", "studies.id", "studies.description", "studies.geographicCoverage",
				"studies.unitAnalysis", "studies.universe", "studies.geographicUnit", "studies.researchInstrument",
				"studies.weighting");

		// return "{\"data\":[{\"name\":\"RODA\",\"level\":0,\"data\":"
		// + serializer.serialize(collection) + "}]}";

		serializer.transform(new FieldNameTransformer("geo_coverage"), "studies.geographicCoverage");
		serializer.transform(new FieldNameTransformer("unit_analysis"), "studies.unitAnalysis");
		serializer.transform(new FieldNameTransformer("research_instrument"), "studies.researchInstrument");
		serializer.transform(new FieldNameTransformer("geo_unit"), "studies.geographicUnit");
		serializer.transform(new FieldNameTransformer("indice"), "studies.id");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<StudiesByYear> findAllStudiesByYear() {
		List<StudiesByYear> result = new ArrayList<StudiesByYear>();
		List<Study> studies = Study.entityManager()
				.createQuery("SELECT o FROM Study o ORDER BY o.yearStart ASC", Study.class).getResultList();

		if (studies != null && studies.size() > 0) {
			Set<StudyInfo> studiesByYearSet = null;
			Integer oldYear = null;
			Iterator<Study> studiesIterator = studies.iterator();

			while (studiesIterator.hasNext()) {
				Study study = (Study) studiesIterator.next();

				if (!study.getYearStart().equals(oldYear)) {
					if (studiesByYearSet != null) {
						result.add(new StudiesByYear(oldYear, studiesByYearSet));
					}
					studiesByYearSet = new HashSet<StudyInfo>();
					oldYear = study.getYearStart();
				}
				studiesByYearSet.add(new StudyInfo(study));
			}

			if (studiesByYearSet != null) {
				result.add(new StudiesByYear(oldYear, studiesByYearSet));
			}
		}
		return result;
	}

	public static StudiesByYear findStudiesByYear(Integer year) {
		StudiesByYear result = null;
		TypedQuery<Study> query = Study.entityManager().createQuery("SELECT o FROM Study o WHERE o.yearStart = :year",
				Study.class);
		query.setParameter("year", year);

		List<Study> studies = query.getResultList();

		if (studies != null && studies.size() > 0) {
			Set<StudyInfo> studiesByYearSet = new HashSet<StudyInfo>();
			Iterator<Study> studiesIterator = studies.iterator();

			while (studiesIterator.hasNext()) {
				studiesByYearSet.add(new StudyInfo(studiesIterator.next()));
			}

			if (studiesByYearSet.size() > 0) {
				result = new StudiesByYear(year, studiesByYearSet);
			}
		}
		return result;
	}

	private Integer year;

	private Integer studiesCount;

	private Set<StudyInfo> studies;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
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

	public StudiesByYear(Integer year) {
		this.year = year;
		studies = new HashSet<StudyInfo>();
	}

	public StudiesByYear(Integer year, Set<StudyInfo> studies) {
		this.year = year;
		this.studies = studies;
		this.studiesCount = (studies != null ? studies.size() : null);
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "id", "name", "type");
		serializer.exclude("studies.variables", "studies.files", "studies.leaf");
		serializer.include("year", "studiesCount");

		serializer.include("studies.name", "studies.id", "studies.description", "studies.geographicCoverage",
				"studies.unitAnalysis", "studies.universe", "studies.geographicUnit", "studies.researchInstrument",
				"studies.weighting");

		// return "{\"data\":[{\"name\":\"RODA\",\"level\":0,\"data\":"
		// + serializer.serialize(collection) + "}]}";

		serializer.transform(new FieldNameTransformer("geo_coverage"), "studies.geographicCoverage");
		serializer.transform(new FieldNameTransformer("unit_analysis"), "studies.unitAnalysis");
		serializer.transform(new FieldNameTransformer("research_instrument"), "studies.researchInstrument");
		serializer.transform(new FieldNameTransformer("geo_unit"), "studies.geographicUnit");
		serializer.transform(new FieldNameTransformer("indice"), "studies.id");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
