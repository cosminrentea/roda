package ro.roda.service;

import java.util.List;

import ro.roda.transformer.YearsTree;

public interface YearsTreeService {

	public abstract List<YearsTree> findAllYearsTree();

	public abstract YearsTree findYearsTree(Integer id);

}
