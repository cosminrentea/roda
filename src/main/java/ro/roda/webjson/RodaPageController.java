package ro.roda.webjson;

import static ro.roda.service.page.RodaPageConstants.PAGE_MAPPING;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;

import ro.roda.service.page.RodaPageConstants;
import ro.roda.service.page.RodaPageService;

@RequestMapping(RodaPageConstants.PAGE_MAPPING)
// @RequestMapping({ "ro", "en" })
@Controller
public class RodaPageController {

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	RodaPageService rodaPageService;

	@RequestMapping(produces = "text/html")
	public void showDefaultPage(HttpServletRequest request, HttpServletResponse response, Model uiModel)
			throws Exception {
		log.trace("showDefaultPage");
		response.sendRedirect(request.getContextPath() + RodaPageConstants.PAGE_MAPPING
				+ rodaPageService.generateDefaultPageUrl());
	}

	@RequestMapping(value = "/**", produces = "text/html")
	public String show(HttpServletRequest request, HttpServletResponse response, Model uiModel) throws Exception {

		String url = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		// get the part containing the URL of the CMS Page
		String cmsPageUrl = url.substring(RodaPageConstants.PAGE_MAPPING_LENGTH);

		log.trace("url: " + url);
		log.trace("cmsPageUrl: " + cmsPageUrl);

		// cmsPageUrl is used also as a key for the cache
		// holding the Rendered Pages !
		// request is used for paths and exception-throwing !
		String[] generatedPage = rodaPageService.generatePage(cmsPageUrl, request);

		// if the fields externalredirect and internalredirect are
		// specified, then externalredirect has greater priority
		if (generatedPage[2] != null) {
			// the page should be redirected externally
			response.sendRedirect(generatedPage[2]);
		} else if (generatedPage[3] != null) {
			// the page should be redirected internally
			response.sendRedirect(request.getContextPath() + RodaPageConstants.PAGE_MAPPING + generatedPage[3]);
		} else {
			return show(generatedPage, uiModel);
		}
		return null;
	}

	private String show(String[] generatedPage, Model uiModel) {
		uiModel.addAttribute("pageBody", generatedPage[0]);
		uiModel.addAttribute("pageTitle", generatedPage[1]);
		return "rodapagerender";
	}
}
