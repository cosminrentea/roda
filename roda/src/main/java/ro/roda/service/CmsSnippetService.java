package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.CmsSnippet;


public interface CmsSnippetService {

	public abstract long countAllCmsSnippets();


	public abstract void deleteCmsSnippet(CmsSnippet cmsSnippet);


	public abstract CmsSnippet findCmsSnippet(Integer id);


	public abstract List<CmsSnippet> findAllCmsSnippets();


	public abstract List<CmsSnippet> findCmsSnippetEntries(int firstResult, int maxResults);


	public abstract void saveCmsSnippet(CmsSnippet cmsSnippet);


	public abstract CmsSnippet updateCmsSnippet(CmsSnippet cmsSnippet);

}
