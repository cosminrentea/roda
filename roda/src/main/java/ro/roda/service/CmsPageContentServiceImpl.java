package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.CmsPageContent;

@Service
@Transactional
public class CmsPageContentServiceImpl implements CmsPageContentService {

	public long countAllCmsPageContents() {
		return CmsPageContent.countCmsPageContents();
	}

	public void deleteCmsPageContent(CmsPageContent cmsPageContent) {
		cmsPageContent.remove();
	}

	public CmsPageContent findCmsPageContent(Integer id) {
		return CmsPageContent.findCmsPageContent(id);
	}

	public List<CmsPageContent> findAllCmsPageContents() {
		return CmsPageContent.findAllCmsPageContents();
	}

	public List<CmsPageContent> findCmsPageContentEntries(int firstResult, int maxResults) {
		return CmsPageContent.findCmsPageContentEntries(firstResult, maxResults);
	}

	public void saveCmsPageContent(CmsPageContent cmsPageContent) {
		cmsPageContent.persist();
	}

	public CmsPageContent updateCmsPageContent(CmsPageContent cmsPageContent) {
		return cmsPageContent.merge();
	}
}
