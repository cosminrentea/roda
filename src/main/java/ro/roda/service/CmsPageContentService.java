package ro.roda.service;

import java.util.List;

import ro.roda.domain.CmsPageContent;

public interface CmsPageContentService {

	public abstract long countAllCmsPageContents();

	public abstract void deleteCmsPageContent(CmsPageContent cmsPageContent);

	public abstract CmsPageContent findCmsPageContent(Integer id);

	public abstract List<CmsPageContent> findAllCmsPageContents();

	public abstract List<CmsPageContent> findCmsPageContentEntries(int firstResult, int maxResults);

	public abstract void saveCmsPageContent(CmsPageContent cmsPageContent);

	public abstract CmsPageContent updateCmsPageContent(CmsPageContent cmsPageContent);

}
