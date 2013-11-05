package ro.roda.service;

import java.util.List;

import ro.roda.domain.Topic;

public interface TopicService {

	public abstract long countAllTopics();

	public abstract void deleteTopic(Topic topic);

	public abstract Topic findTopic(Integer id);

	public abstract List<Topic> findAllTopics();

	public abstract List<Topic> findTopicEntries(int firstResult, int maxResults);

	public abstract void saveTopic(Topic topic);

	public abstract Topic updateTopic(Topic topic);

}
