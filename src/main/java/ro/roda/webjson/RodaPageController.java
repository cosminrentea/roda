package ro.roda.webjson;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;

import ro.roda.plugin.RodaPageService;

@RequestMapping("/page")
@Controller
public class RodaPageController {

	private final Log log = LogFactory.getLog(this.getClass());

	private final static String requestMapping = "/page";

	@Autowired
	RodaPageService rodaPageService;

	@RequestMapping(value = "/**", produces = "text/html")
	public String show(HttpServletRequest request, Model uiModel) {

		String url = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

		url = url.substring(requestMapping.length());
		log.debug(url);

		// "url" now is the full URL with a trailing slash "/..."

		String pageBody = rodaPageService.generatePage(url)[0];
		String pageTitle = rodaPageService.generatePage(url)[1];

		// uiModel.addAttribute("rodapage", page);
		// uiModel.addAttribute("pageUrl", url);

		uiModel.addAttribute("pageBody", pageBody);
		uiModel.addAttribute("pageTitle", pageTitle);

		return "rodapage/show";
	}
}
