package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.SnippetGroupTree;

public interface SnippetGroupTreeService {

	public abstract List<SnippetGroupTree> findAllSnippetGroupTrees();

	public abstract SnippetGroupTree findSnippetGroupTree(Integer id);

}
