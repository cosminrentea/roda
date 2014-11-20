package ro.roda.domainjson;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class StudiesByTopic extends JsonInfo {

	public static String toJsonArray(Collection<StudiesByTopic> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.include("id", "name", "studiesCount");
		serializer.include("studies.name", "studies.id", "studies.yearStart", "studies.description",
				"studies.geographicCoverage", "studies.unitAnalysis", "studies.universe");

		serializer.exclude("*.class");
		serializer.exclude("*.*");

		serializer.transform(new FieldNameTransformer("indice"), "id");
		serializer.transform(new FieldNameTransformer("nrStudies"), "studiesCount");
		serializer.transform(new FieldNameTransformer("geo_coverage"), "studies.geographicCoverage");
		serializer.transform(new FieldNameTransformer("unit_analysis"), "studies.unitAnalysis");
		serializer.transform(new FieldNameTransformer("univers"), "studies.universe");
		serializer.transform(new FieldNameTransformer("indice"), "studies.id");
		// TODO transform the fields name in variables and files

		return serializer.rootName("data").serialize(collection);
	}

	private Integer id;

	private String name;

	private Integer studiesCount;

	private Set<StudyInfo> studies;

	public StudiesByTopic(Integer id) {
		this.id = id;
		studies = new HashSet<StudyInfo>();
	}

	public StudiesByTopic(Integer id, Set<StudyInfo> studies) {
		this.id = id;
		this.studies = studies;
	}

	public StudiesByTopic(Integer id, String name, Set<StudyInfo> studies) {
		this.id = id;
		this.name = name;
		this.studies = studies;
		this.studiesCount = studies.size();
	}

	public StudiesByTopic(Integer id, String name, Integer studiesCount, Set<StudyInfo> studies) {
		this.id = id;
		this.name = name;
		this.studiesCount = studiesCount;
		this.studies = studies;
	}

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

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.include("id", "name", "studiesCount");
		serializer.include("studies.name", "studies.id", "studies.yearStart", "studies.description",
				"studies.geographicCoverage", "studies.unitAnalysis", "studies.universe");

		serializer.exclude("*.class");
		serializer.exclude("*.*");

		serializer.transform(new FieldNameTransformer("indice"), "id");
		serializer.transform(new FieldNameTransformer("nrStudies"), "studiesCount");
		serializer.transform(new FieldNameTransformer("geo_coverage"), "studies.geographicCoverage");
		serializer.transform(new FieldNameTransformer("unit_analysis"), "studies.unitAnalysis");
		serializer.transform(new FieldNameTransformer("univers"), "studies.universe");
		serializer.transform(new FieldNameTransformer("indice"), "studies.id");
		// TODO transform the fields name in variables and files

		return serializer.rootName("data").serialize(this);
	}
}
