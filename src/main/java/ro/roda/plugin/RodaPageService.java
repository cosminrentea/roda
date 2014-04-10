package ro.roda.plugin;

import ro.roda.domain.CmsPage;

public interface RodaPageService {

	public abstract String[] generatePage(String url);

	public abstract String generateDefaultPageUrl();
	
	public abstract String generateFullRelativeUrl(CmsPage cmsPage);

	public abstract void evict(String url);

	public abstract void evictAll();

}
