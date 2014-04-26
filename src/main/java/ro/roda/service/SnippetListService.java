package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.SnippetList;

public interface SnippetListService {

	public abstract List<SnippetList> findAllSnippetLists();

	public abstract SnippetList findSnippetList(Integer id);

}
