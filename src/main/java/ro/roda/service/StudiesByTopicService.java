package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.StudiesByTopic;

public interface StudiesByTopicService {

	public abstract List<StudiesByTopic> findAllStudiesByTopic();

	public abstract StudiesByTopic findStudiesByTopic(Integer id);

}
