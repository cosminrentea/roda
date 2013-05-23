package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.TranslatedTopic;
import ro.roda.domain.TranslatedTopicPK;


public interface TranslatedTopicService {

	public abstract long countAllTranslatedTopics();


	public abstract void deleteTranslatedTopic(TranslatedTopic translatedTopic);


	public abstract TranslatedTopic findTranslatedTopic(TranslatedTopicPK id);


	public abstract List<TranslatedTopic> findAllTranslatedTopics();


	public abstract List<TranslatedTopic> findTranslatedTopicEntries(int firstResult, int maxResults);


	public abstract void saveTranslatedTopic(TranslatedTopic translatedTopic);


	public abstract TranslatedTopic updateTranslatedTopic(TranslatedTopic translatedTopic);

}
