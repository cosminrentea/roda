package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.CmsPage;

@Service
@Transactional
public class CmsPageServiceImpl implements CmsPageService {

	public long countAllCmsPages() {
		return CmsPage.countCmsPages();
	}

	public void deleteCmsPage(CmsPage cmsPage) {
		cmsPage.remove();
	}

	public CmsPage findCmsPage(Integer id) {
		return CmsPage.findCmsPage(id);
	}

	public List<CmsPage> findCmsPage(String url) {
		return CmsPage.findCmsPage(url);
	}

	public CmsPage findCmsPageByParent(String url, CmsPage parent) {
		return CmsPage.findCmsPageByParent(url, parent);
	}

	public CmsPage findCmsPageByFullUrl(String url) {
		return CmsPage.findCmsPageByFullUrl(url);
	}

	public List<CmsPage> findAllCmsPages() {
		return CmsPage.findAllCmsPages();
	}

	public List<CmsPage> findCmsPageEntries(int firstResult, int maxResults) {
		return CmsPage.findCmsPageEntries(firstResult, maxResults);
	}

	public void saveCmsPage(CmsPage cmsPage) {
		cmsPage.persist();
	}

	public CmsPage updateCmsPage(CmsPage cmsPage) {
		return cmsPage.merge();
	}

}
