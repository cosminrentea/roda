package ro.roda.webjson;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;

import ro.roda.service.page.RodaPageService;

@RequestMapping("/page")
@Controller
public class RodaPageController {

	private final Log log = LogFactory.getLog(this.getClass());

	private final static String requestMapping = "/page";

	@Autowired
	RodaPageService rodaPageService;

	@RequestMapping(produces = "text/html")
	public void showDefaultPage(HttpServletRequest request, HttpServletResponse response, Model uiModel) {
		log.trace("Computing default page");
		try {
			response.sendRedirect(request.getContextPath() + requestMapping + rodaPageService.generateDefaultPageUrl());
		} catch (IOException ioe) {
			log.error("Default page exception : ", ioe);
		}

	}

	@RequestMapping(value = "/**", produces = "text/html")
	public String show(HttpServletRequest request, HttpServletResponse response, Model uiModel) {

		String url = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

		url = url.substring(requestMapping.length());
		log.trace("Computing page: " + url);

		// "url" now is the full URL with a trailing slash "/..."

		String[] generatedPage = rodaPageService.generatePage(url);

		// if the fields externalredirect and internalredirect are
		// specified, then externalredirect has greater priority
		if (generatedPage[2] != null) {
			// the page should be redirected externally
			try {
				// external redirect
				response.sendRedirect(generatedPage[2]);
			} catch (IOException ioeExternalRedirect) {
				// TODO log
			}

		} else if (generatedPage[3] != null) {
			// the page should be redirected internally
			try {
				// internal redirect
				response.sendRedirect(request.getContextPath() + requestMapping + generatedPage[3]);

			} catch (Exception internalRedirectException) {
				// TODO log
			}
		} else {
			return show(generatedPage, uiModel);
		}
		return null;
	}

	private String show(String[] generatedPage, Model uiModel) {
		String pageBody = generatedPage[0];
		String pageTitle = generatedPage[1];

		// uiModel.addAttribute("rodapage", page);
		// uiModel.addAttribute("pageUrl", url);

		uiModel.addAttribute("pageBody", pageBody);
		uiModel.addAttribute("pageTitle", pageTitle);

		return "rodapage/show";
	}
}
