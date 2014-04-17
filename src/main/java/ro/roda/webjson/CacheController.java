package ro.roda.webjson;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import ro.roda.domain.CmsPage;
import ro.roda.service.page.RodaPageService;
import ro.roda.service.thumbnails.ThumbnailsService;
import ro.roda.transformer.AdminJson;

@RequestMapping("/admin/cache")
@Controller
public class CacheController {

	@Autowired
	RodaPageService rodaPageService;

	@Autowired
	ThumbnailsService thumbnailsService;

	private final static String evictPageMapping = "/admin/cache/evict-page";

	private final Log log = LogFactory.getLog(this.getClass());

	@RequestMapping(value = "/evict-thumbnails", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String evictThumbnails() {
		thumbnailsService.evictAll();
		return new AdminJson(true, "Cache evicted - Thumbnails: ALL").toJson();
	}

	@RequestMapping(value = "/evict-pages", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String evictPages() {
		rodaPageService.evictAll();
		return new AdminJson(true, "Cache evicted - Pages: ALL").toJson();
	}

	@RequestMapping(value = "/evict-page-url/**", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String evictPageUrl(HttpServletRequest request, Model uiModel) {
		String url = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		url = url.substring(evictPageMapping.length());
		log.trace("Evict from pages cache: " + url);
		rodaPageService.evict(url);
		return new AdminJson(true, "Cache evicted - Pages: URL = " + url).toJson();
	}

	@RequestMapping(value = "/evict-page-id/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String evictPageId(@PathVariable("id") Integer pageId) {
		String url = rodaPageService.generateFullRelativeUrl(CmsPage.findCmsPage(pageId));
		log.trace("Evict from pages cache: ID =" + pageId + " , URL = " + url);
		rodaPageService.evict(url);
		return new AdminJson(true, "Cache evicted - Pages: ID = " + pageId + " , URL = " + url).toJson();
	}

}
