package ro.roda.service;

import java.util.List;

import ro.roda.domain.CmsPageType;

public interface CmsPageTypeService {

	public abstract long countAllCmsPageTypes();

	public abstract void deleteCmsPageType(CmsPageType cmsPageType);

	public abstract CmsPageType findCmsPageType(Integer id);

	public abstract List<CmsPageType> findAllCmsPageTypes();

	public abstract List<CmsPageType> findCmsPageTypeEntries(int firstResult, int maxResults);

	public abstract void saveCmsPageType(CmsPageType cmsPageType);

	public abstract CmsPageType updateCmsPageType(CmsPageType cmsPageType);

}
