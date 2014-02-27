package ro.roda.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ro.roda.service.RodaPageService;

@RequestMapping("/page")
@Controller
public class RodaPageController {

	@Autowired
	RodaPageService rodaPageService;

	@RequestMapping(value = "/{url}", produces = "text/html")
	public String show(@PathVariable("url") String url, Model uiModel) {

		String pageBody = rodaPageService.generatePage(url);
		// TODO
		String pageHead = "Test head";

		// uiModel.addAttribute("rodapage", page);
		// uiModel.addAttribute("pageUrl", url);

		uiModel.addAttribute("pageBody", pageBody);
		uiModel.addAttribute("pageHead", pageHead);

		return "rodapage/show";
	}
}
