package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Study;
import flexjson.JSONSerializer;

@Configurable
public class YearsTree extends JsonInfo {

	public static String toJsonArray(Collection<YearsTree> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth", "id");
		serializer.include("name", "year", "type");

		serializer.exclude("data.depth", "data.variables", "data.files", "data.geographicCoverage",
				"data.unitAnalysis", "data.universe");
		serializer.include("data.name", "data.id", "data.yearStart", "data.type", "data.leaf");
		serializer.transform(new FieldNameTransformer("indice"), "data.id");

		return "{\"data\":[{\"name\":\"RODA\",\"type\":\"M\",\"data\":" + serializer.serialize(collection) + "}]}";
	}

	public static List<YearsTree> findAllYearsTree() {
		List<YearsTree> result = new ArrayList<YearsTree>();

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
						result.add(new YearsTree(oldYear, oldYear.toString(), new HashSet<JsonInfo>(studiesByYearSet)));
					}
					studiesByYearSet = new HashSet<StudyInfo>();
					oldYear = study.getYearStart();
				}
				studiesByYearSet.add(new StudyInfo(study));
			}

			if (studiesByYearSet != null) {
				result.add(new YearsTree(oldYear, oldYear.toString(), new HashSet<JsonInfo>(studiesByYearSet)));
			}
		}
		return result;
	}

	public static YearsTree findYearsTree(Integer year) {
		YearsTree result = null;

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
				result = new YearsTree(year, year.toString(), new HashSet<JsonInfo>(studiesByYearSet));
			}
		}
		return result;
	}

	private String name;

	private Integer year;

	private String type;

	private Set<JsonInfo> data;

	private int depth;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
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

	public YearsTree(Integer year) {
		this.year = year;
		this.type = JsonInfo.YEAR_TYPE;
		data = new HashSet<JsonInfo>();
	}

	public YearsTree(Integer year, Set<JsonInfo> data) {
		this.year = year;
		this.type = JsonInfo.YEAR_TYPE;
		this.data = data;
	}

	public YearsTree(Integer year, String name, Set<JsonInfo> data) {
		this.year = year;
		this.name = name;
		this.type = JsonInfo.YEAR_TYPE;
		this.data = data;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth", "id");
		serializer.include("id", "name", "type");

		serializer.exclude("data.depth", "data.variables", "data.files", "data.geographicCoverage",
				"data.unitAnalysis", "data.universe");
		serializer.include("data.name", "data.id", "data.yearStart", "data.type", "data.leaf");
		serializer.transform(new FieldNameTransformer("indice"), "data.id");

		return "{\"data\":[{\"name\":\"RODA\",\"type\":\"M\",\"data\":" + serializer.serialize(this) + "}]}";
	}
}
