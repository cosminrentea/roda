package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Study;
import ro.roda.domain.Topic;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class StudiesByTopic extends JsonInfo {

	public static String toJsonArray(Collection<StudiesByTopic> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.exclude("studies.leaf", "studies.variables", "studies.files", "studies.persons", "studies.orgs");

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

	public static List<StudiesByTopic> findAllStudiesByTopic() {
		List<StudiesByTopic> result = new ArrayList<StudiesByTopic>();
		List<Topic> topics = Topic.findAllTopics();

		if (topics != null && topics.size() > 0) {

			Iterator<Topic> topicsIterator = topics.iterator();
			while (topicsIterator.hasNext()) {
				Topic topic = (Topic) topicsIterator.next();
				result.add(findDirectStudiesByTopic(topic.getId()));
			}
		}

		return result;
	}

	public static List<StudiesByTopic> findAllDirectStudiesByTopic() {
		List<StudiesByTopic> result = new ArrayList<StudiesByTopic>();
		List<Topic> topics = Topic.findAllTopics();

		if (topics != null && topics.size() > 0) {
			Set<StudyInfo> studiesByTopicSet = null;
			Iterator<Topic> topicIterator = topics.iterator();

			while (topicIterator.hasNext()) {
				Topic topic = (Topic) topicIterator.next();
				Set<Study> topicStudies = topic.getStudies();

				if (topicStudies != null && topicStudies.size() > 0) {
					studiesByTopicSet = new HashSet<StudyInfo>();

					Iterator<Study> topicStudiesIterator = topicStudies.iterator();
					while (topicStudiesIterator.hasNext()) {
						studiesByTopicSet.add(new StudyInfo(topicStudiesIterator.next()));
					}

					// TODO uncomment & refactor !!!
					// result.add(new StudiesByTopic(topic.getId(),
					// topic.getName(), studiesByTopicSet.size(),
					// studiesByTopicSet));
				}

			}
		}
		return result;
	}

	public static StudiesByTopic findStudiesByTopic(Integer id) {
		Topic targetTopic = Topic.findTopic(id);
		Set<StudyInfo> topicSubstudies = new HashSet<StudyInfo>();

		Set<Topic> topics = new HashSet<Topic>();
		topics.add(targetTopic);

		while (topics.size() > 0) {
			Set<Topic> subtopics = new HashSet<Topic>();

			Iterator<Topic> topicsIterator = topics.iterator();
			while (topicsIterator.hasNext()) {
				Topic topic = topicsIterator.next();

				Set<Study> topicStudies = topic.getStudies();
				if (topicStudies != null && topicStudies.size() > 0) {

					Iterator<Study> topicStudiesIterator = topicStudies.iterator();
					while (topicStudiesIterator.hasNext()) {
						topicSubstudies.add(new StudyInfo(topicStudiesIterator.next()));
					}
				}

				Set<Topic> topicTopics = topic.getTopics();
				if (topicTopics != null && topicTopics.size() > 0) {
					subtopics.addAll(topicTopics);
				}
			}

			topics.clear();
			topics.addAll(subtopics);
		}

		// TODO uncomment & refactor !!!
		// return new StudiesByTopic(targetTopic.getId(), targetTopic.getName(),
		// topicSubstudies.size(), topicSubstudies);
		return null;
	}

	public static StudiesByTopic findDirectStudiesByTopic(Integer id) {
		StudiesByTopic result = null;
		Topic topic = Topic.findTopic(id);

		if (topic != null) {
			Set<StudyInfo> studiesByTopicSet = null;

			Set<Study> topicStudies = topic.getStudies();

			// direct studies under topic
			if (topicStudies != null && topicStudies.size() > 0) {
				studiesByTopicSet = new HashSet<StudyInfo>();

				Iterator<Study> topicStudiesIterator = topicStudies.iterator();
				while (topicStudiesIterator.hasNext()) {
					studiesByTopicSet.add(new StudyInfo(topicStudiesIterator.next()));
				}

				// TODO uncomment & refactor !!!
				// result = new StudiesByTopic(topic.getId(), topic.getName(),
				// studiesByTopicSet.size(), studiesByTopicSet);
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

	public StudiesByTopic(Integer id) {
		this.id = id;
		studies = new HashSet<StudyInfo>();
	}

	public StudiesByTopic(Integer id, Set<StudyInfo> studies) {
		this.id = id;
		this.studies = studies;
	}

	public StudiesByTopic(Integer id, String name, Integer studiesCount, Set<StudyInfo> studies) {
		this.id = id;
		this.name = name;
		this.studiesCount = studiesCount;
		this.studies = studies;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.exclude("studies.leaf", "studies.variables", "studies.files", "studies.persons", "studies.orgs");

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
