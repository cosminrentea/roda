package ro.roda.service;

import java.util.List;

import ro.roda.domain.CmsLayout;

public interface CmsLayoutService {

	public abstract long countAllCmsLayouts();

	public abstract void deleteCmsLayout(CmsLayout cmsLayout);

	public abstract CmsLayout findCmsLayout(Integer id);

	public abstract List<CmsLayout> findAllCmsLayouts();

	public abstract List<CmsLayout> findCmsLayoutEntries(int firstResult, int maxResults);

	public abstract void saveCmsLayout(CmsLayout cmsLayout);

	public abstract CmsLayout updateCmsLayout(CmsLayout cmsLayout);

}
