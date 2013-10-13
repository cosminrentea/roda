package ro.roda.service;

import java.util.List;

import ro.roda.transformer.LayoutGroupTree;

public interface LayoutGroupTreeService {

	public abstract List<LayoutGroupTree> findAllLayoutGroupTrees();

	public abstract LayoutGroupTree findLayoutGroupTree(Integer id);

}
