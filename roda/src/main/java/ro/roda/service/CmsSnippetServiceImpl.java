package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.CmsSnippet;


@Service
@Transactional
public class CmsSnippetServiceImpl implements CmsSnippetService {

	public long countAllCmsSnippets() {
        return CmsSnippet.countCmsSnippets();
    }

	public void deleteCmsSnippet(CmsSnippet cmsSnippet) {
        cmsSnippet.remove();
    }

	public CmsSnippet findCmsSnippet(Integer id) {
        return CmsSnippet.findCmsSnippet(id);
    }

	public List<CmsSnippet> findAllCmsSnippets() {
        return CmsSnippet.findAllCmsSnippets();
    }

	public List<CmsSnippet> findCmsSnippetEntries(int firstResult, int maxResults) {
        return CmsSnippet.findCmsSnippetEntries(firstResult, maxResults);
    }

	public void saveCmsSnippet(CmsSnippet cmsSnippet) {
        cmsSnippet.persist();
    }

	public CmsSnippet updateCmsSnippet(CmsSnippet cmsSnippet) {
        return cmsSnippet.merge();
    }
}
