package ro.roda.service.page;

import java.util.Map;

import ro.roda.domain.CmsPage;

public interface RodaPageService {

	public abstract String[] generatePage(String url, Map<String, String> parameters);

	public abstract String[] generatePage(CmsPage cmsPage, String url, Map<String, String> parameters);

	public abstract String[] generatePreviewPage(CmsPage cmsPage, String layoutContent, String pageContent, String url);

	public abstract String generateDefaultPageUrl();

	public abstract String generateFullRelativeUrl(CmsPage cmsPage);

	public abstract void evict(String url);

	public abstract void evictAll();

}
