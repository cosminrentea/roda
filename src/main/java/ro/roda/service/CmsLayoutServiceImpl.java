package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.CmsLayout;

@Service
@Transactional
public class CmsLayoutServiceImpl implements CmsLayoutService {

	public long countAllCmsLayouts() {
		return CmsLayout.countCmsLayouts();
	}

	public void deleteCmsLayout(CmsLayout cmsLayout) {
		cmsLayout.remove();
	}

	public CmsLayout findCmsLayout(Integer id) {
		return CmsLayout.findCmsLayout(id);
	}

	public List<CmsLayout> findAllCmsLayouts() {
		return CmsLayout.findAllCmsLayouts();
	}

	public List<CmsLayout> findCmsLayoutEntries(int firstResult, int maxResults) {
		return CmsLayout.findCmsLayoutEntries(firstResult, maxResults);
	}

	public void saveCmsLayout(CmsLayout cmsLayout) {
		cmsLayout.persist();
	}

	public CmsLayout updateCmsLayout(CmsLayout cmsLayout) {
		return cmsLayout.merge();
	}
}
