package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Study;
import flexjson.JSONSerializer;

@Configurable
public class StudiesByYear {

	public static String toJsonArray(Collection<StudiesByYear> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.exclude("studies.variables", "studies.files");
		serializer.include("year", "studiesCount");
		serializer.include("studies.name", "studies.id", "studies.description", "studies.geographicCoverage",
				"studies.unitAnalysis", "studies.universe");

		// return "{\"data\":[{\"name\":\"RODA\",\"level\":0,\"data\":"
		// + serializer.serialize(collection) + "}]}";

		serializer.transform(new FieldNameTransformer("geo_coverage"), "studies.geographicCoverage");
		serializer.transform(new FieldNameTransformer("unit_analysis"), "studies.unitAnalysis");
		serializer.transform(new FieldNameTransformer("univers"), "studies.universe");
		serializer.transform(new FieldNameTransformer("indice"), "studies.id");
		// TODO transform the fields name in variables and files

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<StudiesByYear> findAllStudiesByYear() {
		List<StudiesByYear> result = null;
		List<Study> studies = Study.entityManager()
				.createQuery("SELECT o FROM Study o ORDER BY o.yearStart ASC", Study.class).getResultList();
		Set<StudyInfo> studiesByYearSet = null;
		Iterator<Study> it = studies.iterator();
		int yearPrev = 0;

		result = new ArrayList<StudiesByYear>();

		while (it.hasNext()) {
			Study study = (Study) it.next();
			int yearTmp = study.getYearStart();
			if (yearTmp != yearPrev || !it.hasNext()) {
				if (!it.hasNext()) {
					studiesByYearSet.add(new StudyInfo(study));
				}
				if (studiesByYearSet != null) {
					result.add(new StudiesByYear(yearPrev, studiesByYearSet.size(), studiesByYearSet));
				}
				studiesByYearSet = new HashSet<StudyInfo>();
				yearPrev = yearTmp;
			} else {
				studiesByYearSet.add(new StudyInfo(study));
			}

		}
		return result;
	}

	public static StudiesByYear findStudiesByYear(Integer year) {
		if (year == null)
			return null;
		// TODO
		return new StudiesByYear(year);
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
	}

	public StudiesByYear(Integer year, Integer studiesCount, Set<StudyInfo> studies) {
		this.year = year;
		this.studiesCount = studiesCount;
		this.studies = studies;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}
}
