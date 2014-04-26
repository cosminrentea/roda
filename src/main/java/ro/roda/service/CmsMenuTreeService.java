package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.CmsMenuTree;

public interface CmsMenuTreeService {

	public abstract List<CmsMenuTree> findAllCmsMenuTrees();

	public abstract CmsMenuTree findCmsMenuTree(Integer id);

}
