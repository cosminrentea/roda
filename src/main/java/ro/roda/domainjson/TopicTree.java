package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.i18n.LocaleContextHolder;

import ro.roda.domain.Lang;
import ro.roda.domain.Study;
import ro.roda.domain.Topic;
import ro.roda.domain.TranslatedTopic;
import ro.roda.domain.TranslatedTopicPK;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class TopicTree extends JsonInfo {

	public static String toJsonArray(Collection<TopicTree> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth");
		serializer.include("id", "name", "type", "leaf");

		int maxDepth = 0;
		for (TopicTree topicTree : collection) {
			if (maxDepth < topicTree.getDepth()) {
				maxDepth = topicTree.getDepth();
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

		return serializer.transform(new FieldNameTransformer("indice"), "id").serialize(collection);
	}

	public static List<TopicTree> findAll(String language) {
		List<TranslatedTopic> usedTranslatedTopics = TranslatedTopic.usedTranslatedTopics(null, language);

		if (usedTranslatedTopics == null || usedTranslatedTopics.size() == 0)
			return null;

		List<TopicTree> results = new ArrayList<TopicTree>();
		for (TranslatedTopic tt : usedTranslatedTopics) {
			results.add(find(tt.getTopicId(), language));
		}
		return results;
	}

	public static TopicTree find(Topic topic, String language) {
		TopicTree result = new TopicTree(topic);
		TranslatedTopic tt = TranslatedTopic.findTranslatedTopic(new TranslatedTopicPK(Lang.findLang(language).getId(),
				topic.getId()));
		result.setName((tt != null) ? tt.getTranslation() : "");

		Set<JsonInfo> dataByTopicSet = new HashSet<JsonInfo>();
		Set<Study> topicStudies = topic.getStudies();
		Iterator<Study> topicStudiesIterator = topicStudies.iterator();
		while (topicStudiesIterator.hasNext()) {
			dataByTopicSet.add(new StudyInfo(topicStudiesIterator.next()));
		}
		result.setData(dataByTopicSet);
		return result;
	}

	// public static List<TopicTree> findAll() {
	// List<TopicTree> result = new ArrayList<TopicTree>();
	// List<Topic> topics = Topic.entityManager()
	// .createQuery("SELECT t FROM Topic t WHERE t.parentId IS NULL",
	// Topic.class).getResultList();
	// if (topics != null && topics.size() > 0) {
	// Iterator<Topic> topicIterator = topics.iterator();
	// while (topicIterator.hasNext()) {
	// result.add(find(topicIterator.next()));
	// }
	// }
	// return result;
	// }

	// public static TopicTree find(Topic topic) {
	// TopicTree result = new TopicTree(topic);
	// Integer currentLanguageId =
	// Lang.findLang(LocaleContextHolder.getLocale().getLanguage()).getId();
	// TranslatedTopic tt = TranslatedTopic
	// .findTranslatedTopic(new TranslatedTopicPK(currentLanguageId,
	// topic.getId()));
	// result.setName((tt != null) ? tt.getTranslation() : "");
	// Set<JsonInfo> dataByTopicSet = null;
	// Set<Topic> children = topic.getTopics();
	// int maxDepth = 0;
	// if (children != null && children.size() > 0) {
	// dataByTopicSet = new HashSet<JsonInfo>();
	//
	// Iterator<Topic> childrenIterator = children.iterator();
	// while (childrenIterator.hasNext()) {
	// Topic childTopic = childrenIterator.next();
	// TopicTree topicTree = find(childTopic);
	// dataByTopicSet.add(topicTree);
	// if (maxDepth < topicTree.getDepth()) {
	// maxDepth = topicTree.getDepth();
	// }
	// }
	// result.setDepth(maxDepth + 1);
	// }
	//
	// Set<Study> topicStudies = topic.getStudies();
	// if (topicStudies != null && topicStudies.size() > 0) {
	// if (dataByTopicSet == null) {
	// dataByTopicSet = new HashSet<JsonInfo>();
	// }
	//
	// Iterator<Study> topicStudiesIterator = topicStudies.iterator();
	// while (topicStudiesIterator.hasNext()) {
	// dataByTopicSet.add(new StudyInfo(topicStudiesIterator.next()));
	// }
	// }
	// result.setData(dataByTopicSet);
	// return result;
	//
	// }

	private Integer id;

	private String name;

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

	public TopicTree(Topic topic) {
		this.id = topic.getId();
		if ((topic.getTopics() == null || topic.getTopics().size() == 0)
				&& (topic.getStudies() == null || topic.getStudies().size() == 0)) {
			this.leaf = true;
		} else {
			this.leaf = false;
		}

		if (topic.getSeries() == null) {
			setType(JsonInfo.CATALOG_TYPE);
		} else {
			setType(JsonInfo.SERIES_TYPE);
		}
		this.data = new HashSet<JsonInfo>();
	}

	public TopicTree(Topic topic, Set<JsonInfo> data) {
		this(topic);
		this.data = data;
	}

	public TopicTree(Topic topic, String name, Set<JsonInfo> data) {
		this(topic, data);
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
