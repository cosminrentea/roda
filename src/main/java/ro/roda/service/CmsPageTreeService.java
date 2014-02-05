package ro.roda.service;

import java.util.List;

import ro.roda.transformer.CmsPageTree;

public interface CmsPageTreeService {

	public abstract List<CmsPageTree> findAllCmsPageTrees();

	public abstract CmsPageTree findCmsPageTree(Integer id);

}
