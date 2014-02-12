package ro.roda.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ro.roda.domain.CmsLayout;
import ro.roda.domain.CmsPage;
import ro.roda.domain.CmsPageContent;
import ro.roda.service.CmsLayoutService;
import ro.roda.service.CmsPageContentService;
import ro.roda.service.CmsPageService;
import ro.roda.service.CmsPageTypeService;

@RequestMapping("/page")
@Controller
public class RodaPageController {

	@Autowired
	CmsPageService cmsPageService;

	@Autowired
	CmsLayoutService cmsLayoutService;

	@Autowired
	CmsPageContentService cmsPageContentService;

	@Autowired
	CmsPageTypeService cmsPageTypeService;

	@RequestMapping(value = "/{url}", produces = "text/html")
	public String show(@PathVariable("url") String url, Model uiModel) {

		String pageBody = generatePage(url);
		// TODO
		String pageHead = "Test head";

		// uiModel.addAttribute("rodapage", page);
		// uiModel.addAttribute("pageUrl", url);

		uiModel.addAttribute("pageBody", pageBody);
		uiModel.addAttribute("pageHead", pageHead);

		return "rodapage/show";
	}

	private String generatePage(String url) {
		CmsPage page = cmsPageService.findCmsPage(url);
		StringBuilder sb = new StringBuilder();

		// TODO: remove later (it is only for testing purpose)
		sb.append("<label>" + page.getName() + "</label><br>");
		sb.append("<label>" + page.getUrl() + "</label><br>");
		sb.append("<label>" + page.getExternalRedirect() + "</label><br>");
		sb.append("<label>" + page.getInternalRedirect() + "</label><br>");
		sb.append("<label>" + page.getSynopsis() + "</label><br>");
		sb.append("<label>" + page.getTarget() + "</label><br>");
		sb.append("<label>" + page.isDefaultPage() + "</label><br>");
		sb.append("<label>" + page.isNavigable() + "</label><br>");
		sb.append("<label>" + page.isVisible() + "</label><br>");
		sb.append("<label>" + page.getCmsPageTypeId().getName() + "</label><br>");
		sb.append("<label>" + page.getCacheable() + "</label><br>");
		sb.append("<label>" + page.getCmsPageId().getName() + "</label><br>");

		sb.append("Page Content in the database:");
		sb.append(mergeLayoutAndContents(page));

		return sb.toString();
	}

	private String mergeLayoutAndContents(CmsPage cmsPage) {

		if (cmsPage.getCmsPageContents() != null) {

			// TODO: multiple page contents
			List<CmsPageContent> unusedContents = new ArrayList<CmsPageContent>(cmsPage.getCmsPageContents());

			CmsLayout pageLayout = cmsPage.getCmsLayoutId();
			String pageLayoutContent = "";

			if (pageLayout != null) {
				pageLayoutContent = pageLayout.getLayoutContent();
				pageLayoutContent = pageLayoutContent.replaceAll("<html>", "");
				pageLayoutContent = pageLayoutContent.replaceAll("</html>", "");
				for (int i = 0; i < unusedContents.size(); i++) {
					CmsPageContent unusedContent = unusedContents.get(i);
					String searchIdString = "[[" + unusedContent.getId() + "]]";
					if (pageLayoutContent.indexOf(searchIdString) > -1) {
						pageLayoutContent.replaceAll(searchIdString, unusedContent.getContentText());
						unusedContents.remove(i);
						i--;
					}
				}
			}

			// for the contents that were not referred in layout
			for (int i = 0; i < unusedContents.size(); i++) {
				CmsPageContent unusedContent = unusedContents.get(i);
				pageLayoutContent += unusedContent.getContentText();
			}

			return pageLayoutContent;
		}
		return "";
	}
}
