package ro.roda.service;

import java.util.List;

import ro.roda.domain.CmsPage;

public interface CmsPageService {

	public abstract long countAllCmsPages();

	public abstract void deleteCmsPage(CmsPage cmsPage);

	public abstract CmsPage findCmsPage(Integer id);

	public abstract List<CmsPage> findCmsPage(String url);

	public abstract CmsPage findCmsPageByParent(String url, CmsPage parent);

	public abstract CmsPage findCmsPageByFullUrl(String url);

	public abstract List<CmsPage> findAllCmsPages();

	public abstract List<CmsPage> findCmsPageEntries(int firstResult, int maxResults);

	public abstract void reorderAllCmsPages();

	public abstract void saveCmsPage(CmsPage cmsPage);

	public abstract CmsPage updateCmsPage(CmsPage cmsPage);

}
