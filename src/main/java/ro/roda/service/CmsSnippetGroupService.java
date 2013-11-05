package ro.roda.service;

import java.util.List;

import ro.roda.domain.CmsSnippetGroup;

public interface CmsSnippetGroupService {

	public abstract long countAllCmsSnippetGroups();

	public abstract void deleteCmsSnippetGroup(CmsSnippetGroup cmsSnippetGroup);

	public abstract CmsSnippetGroup findCmsSnippetGroup(Integer id);

	public abstract List<CmsSnippetGroup> findAllCmsSnippetGroups();

	public abstract List<CmsSnippetGroup> findCmsSnippetGroupEntries(int firstResult, int maxResults);

	public abstract void saveCmsSnippetGroup(CmsSnippetGroup cmsSnippetGroup);

	public abstract CmsSnippetGroup updateCmsSnippetGroup(CmsSnippetGroup cmsSnippetGroup);

}
