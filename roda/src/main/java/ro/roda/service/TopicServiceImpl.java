package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.Topic;

@Service
@Transactional
public class TopicServiceImpl implements TopicService {

	public long countAllTopics() {
		return Topic.countTopics();
	}

	public void deleteTopic(Topic topic) {
		topic.remove();
	}

	public Topic findTopic(Integer id) {
		return Topic.findTopic(id);
	}

	public List<Topic> findAllTopics() {
		return Topic.findAllTopics();
	}

	public List<Topic> findTopicEntries(int firstResult, int maxResults) {
		return Topic.findTopicEntries(firstResult, maxResults);
	}

	public void saveTopic(Topic topic) {
		topic.persist();
	}

	public Topic updateTopic(Topic topic) {
		return topic.merge();
	}
}
