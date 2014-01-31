package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.CmsMenu;

@Service
@Transactional
public class CmsMenuServiceImpl implements CmsMenuService {

	public long countAllCmsMenus() {
		return CmsMenu.countCmsMenus();
	}

	public void deleteCmsMenu(CmsMenu cmsMenu) {
		cmsMenu.remove();
	}

	public CmsMenu findCmsMenu(Integer id) {
		return CmsMenu.findCmsMenu(id);
	}

	public List<CmsMenu> findAllCmsMenus() {
		return CmsMenu.findAllCmsMenus();
	}

	public List<CmsMenu> findCmsMenuEntries(int firstResult, int maxResults) {
		return CmsMenu.findCmsMenuEntries(firstResult, maxResults);
	}

	public void saveCmsMenu(CmsMenu cmsMenu) {
		cmsMenu.persist();
	}

	public CmsMenu updateCmsMenu(CmsMenu cmsMenu) {
		return cmsMenu.merge();
	}
}
