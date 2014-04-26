package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.LayoutTree;

public interface LayoutTreeService {

	public abstract List<LayoutTree> findAllLayoutTrees();

	public abstract LayoutTree findLayoutTree(Integer id);

}
