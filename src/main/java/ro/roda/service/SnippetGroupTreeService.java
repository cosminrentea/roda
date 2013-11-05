package ro.roda.service;

import java.util.List;

import ro.roda.transformer.SnippetGroupTree;

public interface SnippetGroupTreeService {

	public abstract List<SnippetGroupTree> findAllSnippetGroupTrees();

	public abstract SnippetGroupTree findSnippetGroupTree(Integer id);

}
