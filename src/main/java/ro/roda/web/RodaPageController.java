package ro.roda.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;

import ro.fortsoft.pf4j.DefaultPluginManager;
import ro.fortsoft.pf4j.PluginManager;
import ro.roda.plugin.RodaPagePlugin;
import ro.roda.service.RodaPageService;

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
		log.error(url);

		// "url" now is the full URL with a trailing slash "/..."

		String pageBody = rodaPageService.generatePage(url);
		// TODO
		String pageHead = "Test head";

		// uiModel.addAttribute("rodapage", page);
		// uiModel.addAttribute("pageUrl", url);

		uiModel.addAttribute("pageBody", pageBody);
		uiModel.addAttribute("pageHead", pageHead);

		return "rodapage/show";
	}
	/*
	 * @RequestMapping(value = "/{url}", produces = "text/html") public String
	 * show(@PathVariable("url") String url, Model uiModel) {
	 * 
	 * String pageBody = rodaPageService.generatePage(url); // TODO String
	 * pageHead = "Test head";
	 * 
	 * // uiModel.addAttribute("rodapage", page); //
	 * uiModel.addAttribute("pageUrl", url);
	 * 
	 * uiModel.addAttribute("pageBody", pageBody);
	 * uiModel.addAttribute("pageHead", pageHead);
	 * 
	 * return "rodapage/show"; }
	 * 
	 * // version of the previous function using the plugin:
	 * 
	 * @RequestMapping(value = "/plugin/{url}", produces = "text/html") public
	 * String showFromPlugin(@PathVariable("url") String url, Model uiModel) {
	 * // load RODA plugins // create the plugin manager final PluginManager
	 * pluginManager = new DefaultPluginManager();
	 * 
	 * // load and start (active/resolved) the plugins
	 * pluginManager.loadPlugins(); pluginManager.startPlugins();
	 * 
	 * // retrieves the extensions for extension point List<RodaPagePlugin>
	 * pagePlugins = pluginManager.getExtensions(RodaPagePlugin.class); for
	 * (RodaPagePlugin pagePlugin : pagePlugins) { // TODO //
	 * System.out.println(">>> " + pagePlugin.getPageContent(url)); }
	 * 
	 * // stop the plugins pluginManager.stopPlugins();
	 * 
	 * // TODO return ""; }
	 */
}
