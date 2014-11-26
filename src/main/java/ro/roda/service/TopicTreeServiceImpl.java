package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.Topic;
import ro.roda.domainjson.TopicTree;

@Service
@Transactional
public class TopicTreeServiceImpl implements TopicTreeService {

	public List<TopicTree> findAll(String language) {
		return TopicTree.findAll(language);
	}

	public TopicTree find(Integer id, String language) {
		Topic topic = Topic.findTopic(id);
		if (topic == null) {
			return null;
		}
		return TopicTree.find(topic, language);
	}
}
