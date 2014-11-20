package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.StudiesByTopic;

public interface StudiesByTopicService {

	public abstract List<StudiesByTopic> findAllDirectStudiesByTopic();

	public abstract StudiesByTopic findDirectStudiesByTopic(Integer topicId);

	public abstract List<StudiesByTopic> findAllStudiesByTopic();

	public abstract StudiesByTopic findStudiesByTopic(Integer topicId);

}
