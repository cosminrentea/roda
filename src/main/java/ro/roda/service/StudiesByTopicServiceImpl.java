package ro.roda.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.Lang;
import ro.roda.domain.Study;
import ro.roda.domain.Topic;
import ro.roda.domain.TranslatedTopic;
import ro.roda.domain.TranslatedTopicPK;
import ro.roda.domainjson.StudiesByTopic;
import ro.roda.domainjson.StudyInfo;

@Service
@Transactional
public class StudiesByTopicServiceImpl implements StudiesByTopicService {

	public StudiesByTopic findStudiesByTopic(Integer topicId) {
		Topic topic = Topic.findTopic(topicId);
		Set<StudyInfo> topicSubstudies = new HashSet<StudyInfo>();

		Set<Topic> topics = new HashSet<Topic>();
		topics.add(topic);

		while (topics.size() > 0) {
			Set<Topic> subtopics = new HashSet<Topic>();

			Iterator<Topic> topicsIterator = topics.iterator();
			while (topicsIterator.hasNext()) {
				Topic currentTopic = topicsIterator.next();

				Set<Study> topicStudies = currentTopic.getStudies();
				if (topicStudies != null && topicStudies.size() > 0) {

					Iterator<Study> topicStudiesIterator = topicStudies.iterator();
					while (topicStudiesIterator.hasNext()) {
						topicSubstudies.add(new StudyInfo(topicStudiesIterator.next()));
					}
				}

				Set<Topic> topicTopics = currentTopic.getTopics();
				if (topicTopics != null && topicTopics.size() > 0) {
					subtopics.addAll(topicTopics);
				}
			}

			topics.clear();
			topics.addAll(subtopics);
		}

		// TODO refactor !!!
		String topicgetName = "foo";
		// topicgetName = topic.getName();
		return new StudiesByTopic(topic.getId(), topicgetName, topicSubstudies.size(), topicSubstudies);
	}

	public StudiesByTopic findDirectStudiesByTopic(Integer topicId) {
		StudiesByTopic result = null;
		Topic topic = Topic.findTopic(topicId);

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

				// TODO refactor !!!
				String topicgetName = "foo";
				// topicgetName = topic.getName();
				result = new StudiesByTopic(topic.getId(), topicgetName, studiesByTopicSet.size(), studiesByTopicSet);
			}
		}
		return result;
	}

	public List<StudiesByTopic> findAllStudiesByTopic() {
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

	public List<StudiesByTopic> findAllDirectStudiesByTopic() {
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

					result.add(new StudiesByTopic(topic.getId(), TranslatedTopic.findTranslatedTopic(
							new TranslatedTopicPK(Lang.findLang(LocaleContextHolder.getLocale().getLanguage()).getId(),
									topic.getId())).getTranslation(), studiesByTopicSet.size(), studiesByTopicSet));
				}
			}
		}
		return result;
	}

}
