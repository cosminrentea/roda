package ro.roda.service;

import java.util.List;

import ro.roda.domain.CmsMenu;

public interface CmsMenuService {

	public abstract long countAllCmsMenus();

	public abstract void deleteCmsMenu(CmsMenu cmsMenu);

	public abstract CmsMenu findCmsMenu(Integer id);

	public abstract List<CmsMenu> findAllCmsMenus();

	public abstract List<CmsMenu> findCmsMenuEntries(int firstResult, int maxResults);

	public abstract void saveCmsMenu(CmsMenu cmsMenu);

	public abstract CmsMenu updateCmsMenu(CmsMenu cmsMenu);

}
