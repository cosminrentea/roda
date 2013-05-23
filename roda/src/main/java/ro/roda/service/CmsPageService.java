package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.CmsPage;

public interface CmsPageService {

	public abstract long countAllCmsPages();

	public abstract void deleteCmsPage(CmsPage cmsPage);

	public abstract CmsPage findCmsPage(Integer id);

	public abstract List<CmsPage> findAllCmsPages();

	public abstract List<CmsPage> findCmsPageEntries(int firstResult, int maxResults);

	public abstract void saveCmsPage(CmsPage cmsPage);

	public abstract CmsPage updateCmsPage(CmsPage cmsPage);

}
