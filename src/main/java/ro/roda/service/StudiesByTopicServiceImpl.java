package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.StudiesByTopic;

@Service
@Transactional
public class StudiesByTopicServiceImpl implements StudiesByTopicService {

	public List<StudiesByTopic> findAllStudiesByTopic() {
		return StudiesByTopic.findAllStudiesByTopic();
	}

	public StudiesByTopic findStudiesByTopic(Integer id) {
		return StudiesByTopic.findStudiesByTopic(id);
	}
}
