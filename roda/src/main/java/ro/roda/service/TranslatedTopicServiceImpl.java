package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.TranslatedTopic;
import ro.roda.domain.TranslatedTopicPK;

@Service
@Transactional
public class TranslatedTopicServiceImpl implements TranslatedTopicService {

	public long countAllTranslatedTopics() {
		return TranslatedTopic.countTranslatedTopics();
	}

	public void deleteTranslatedTopic(TranslatedTopic translatedTopic) {
		translatedTopic.remove();
	}

	public TranslatedTopic findTranslatedTopic(TranslatedTopicPK id) {
		return TranslatedTopic.findTranslatedTopic(id);
	}

	public List<TranslatedTopic> findAllTranslatedTopics() {
		return TranslatedTopic.findAllTranslatedTopics();
	}

	public List<TranslatedTopic> findTranslatedTopicEntries(int firstResult, int maxResults) {
		return TranslatedTopic.findTranslatedTopicEntries(firstResult, maxResults);
	}

	public void saveTranslatedTopic(TranslatedTopic translatedTopic) {
		translatedTopic.persist();
	}

	public TranslatedTopic updateTranslatedTopic(TranslatedTopic translatedTopic) {
		return translatedTopic.merge();
	}
}
