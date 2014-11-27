package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.TopicTree;

public interface TopicTreeService {

	public abstract List<TopicTree> findAll(String language);

	public abstract TopicTree find(Integer topicId, String language);

}
