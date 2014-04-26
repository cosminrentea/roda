package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.SnippetTree;

public interface SnippetTreeService {

	public abstract List<SnippetTree> findAllSnippetTrees();

	public abstract SnippetTree findSnippetTree(Integer id);

}
