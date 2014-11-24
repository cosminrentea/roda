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

	// public List<StudiesByTopic> findAllDirectStudiesByTopic() {
	// List<StudiesByTopic> results = new ArrayList<StudiesByTopic>();
	// String language = LocaleContextHolder.getLocale().getLanguage();
	// List<TranslatedTopic> ttl =
	// TranslatedTopic.usedTranslatedTopics(language);
	// if (ttl != null && ttl.size() > 0) {
	// Set<StudyInfo> studiesByTopicSet = null;
	// Iterator<TranslatedTopic> topicIterator = ttl.iterator();
	// while (topicIterator.hasNext()) {
	// TranslatedTopic tt = (TranslatedTopic) topicIterator.next();
	// Topic t = tt.getTopicId();
	// Set<Study> topicStudies = t.getStudies();
	// if (topicStudies != null && topicStudies.size() > 0) {
	// studiesByTopicSet = new HashSet<StudyInfo>();
	// Iterator<Study> topicStudiesIterator = topicStudies.iterator();
	// while (topicStudiesIterator.hasNext()) {
	// studiesByTopicSet.add(new StudyInfo(topicStudiesIterator.next()));
	// }
	// results.add(new StudiesByTopic(t.getId(), tt.getTranslation(),
	// studiesByTopicSet.size(),
	// studiesByTopicSet));
	// }
	// }
	// }
	// return results;
	// }

	/**
	 * Return direct studies "having" the given Topic ID.
	 */
	public StudiesByTopic findDirectStudiesByTopic(Integer topicId) {
		StudiesByTopic result = null;
		Topic topic = Topic.findTopic(topicId);
		if (topic != null) {
			Set<Study> topicStudies = topic.getStudies();
			if (topicStudies != null && topicStudies.size() > 0) {
				Set<StudyInfo> studyInfoSet = new HashSet<StudyInfo>();
				Iterator<Study> topicStudiesIterator = topicStudies.iterator();
				while (topicStudiesIterator.hasNext()) {
					studyInfoSet.add(new StudyInfo(topicStudiesIterator.next()));
				}
				result = new StudiesByTopic(topicId, TranslatedTopic.findTranslatedTopic(
						new TranslatedTopicPK(Lang.findLang(LocaleContextHolder.getLocale().getLanguage()).getId(),
								topicId)).getTranslation(), studyInfoSet);
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
		return new StudiesByTopic(topic.getId(), TranslatedTopic.findTranslatedTopic(
				new TranslatedTopicPK(Lang.findLang(LocaleContextHolder.getLocale().getLanguage()).getId(), topicId))
				.getTranslation(), topicSubstudies);
	}

}
