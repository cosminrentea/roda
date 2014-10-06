package ro.roda.webjson.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import ro.roda.domain.CmsPage;
import ro.roda.domain.UserSettingValue;
import ro.roda.domainjson.AdminJson;
import ro.roda.service.page.RodaPageService;
import ro.roda.service.thumbnails.ThumbnailsService;

@RequestMapping("/adminjson/cache")
@Controller
public class CacheController {

	@Autowired
	RodaPageService rodaPageService;

	@Autowired
	ThumbnailsService thumbnailsService;

	private final static String evictPageMapping = "/adminjson/cache/evict-page";

	private final Log log = LogFactory.getLog(this.getClass());

	@RequestMapping(value = "/evict-thumbnails", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> evictThumbnails() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		thumbnailsService.evictAll();

		return new ResponseEntity<String>(new AdminJson(true, "Cache evicted - Thumbnails: ALL").toJson(), headers,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/evict-pages", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> evictPages() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		rodaPageService.evictAll();

		return new ResponseEntity<String>(new AdminJson(true, "Cache evicted - Pages: ALL").toJson(), headers,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/evict-page-url/**", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> evictPageUrl(HttpServletRequest request, Model uiModel) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		String url = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		url = url.substring(evictPageMapping.length());
		log.trace("Evict from pages cache: " + url);
		rodaPageService.evict(url);

		return new ResponseEntity<String>(new AdminJson(true, "Cache evicted - Pages: URL = " + url).toJson(), headers,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/evict-page-id/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> evictPageId(@PathVariable("id") Integer pageId) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		String url = rodaPageService.generateFullRelativeUrl(CmsPage.findCmsPage(pageId));
		log.trace("Evict from pages cache: ID =" + pageId + " , URL = " + url);
		rodaPageService.evict(url);

		return new ResponseEntity<String>(new AdminJson(true, "Cache evicted - Pages: ID = " + pageId + " , URL = "
				+ url).toJson(), headers, HttpStatus.OK);
	}

}
