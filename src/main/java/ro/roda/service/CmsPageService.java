package ro.roda.service;

import java.util.List;

import ro.roda.domain.CmsPage;

public interface CmsPageService {

	public abstract long countAllCmsPages();

	public abstract void deleteCmsPage(CmsPage cmsPage);

	public abstract CmsPage findCmsPage(Integer id);

	public abstract CmsPage findCmsPage(String url);

	public abstract List<CmsPage> findAllCmsPages();

	public abstract List<CmsPage> findCmsPageEntries(int firstResult, int maxResults);

	public abstract void saveCmsPage(CmsPage cmsPage);

	public abstract CmsPage updateCmsPage(CmsPage cmsPage);

}
