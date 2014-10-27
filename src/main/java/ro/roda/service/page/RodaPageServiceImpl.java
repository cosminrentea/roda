package ro.roda.service.page;

import static ro.roda.service.page.RodaPageConstants.ADMIN_URL;
import static ro.roda.service.page.RodaPageConstants.CMS_FILE_CONTENT_URL;
import static ro.roda.service.page.RodaPageConstants.DEFAULT_ERROR_PAGE_LANGUAGE;
import static ro.roda.service.page.RodaPageConstants.DEFAULT_LANGUAGE_URL;
import static ro.roda.service.page.RodaPageConstants.FILE_URL_LINK_CODE;
import static ro.roda.service.page.RodaPageConstants.GETNEWSID_CODE;
import static ro.roda.service.page.RodaPageConstants.GETNEWS_CODE;
import static ro.roda.service.page.RodaPageConstants.IMG_LINK_CODE;
import static ro.roda.service.page.RodaPageConstants.OFFSET_RELATIVE_URL;
import static ro.roda.service.page.RodaPageConstants.PAGE_BREADCRUMBS_CODE;
import static ro.roda.service.page.RodaPageConstants.PAGE_CONTENT_CODE;
import static ro.roda.service.page.RodaPageConstants.PAGE_CONTEXT_PATH;
import static ro.roda.service.page.RodaPageConstants.PAGE_LINK_BY_URL_CODE;
import static ro.roda.service.page.RodaPageConstants.PAGE_MAPPING;
import static ro.roda.service.page.RodaPageConstants.PAGE_TITLE_CODE;
import static ro.roda.service.page.RodaPageConstants.PAGE_TREE_BY_URL_CODE;
import static ro.roda.service.page.RodaPageConstants.PAGE_URL_LINK_CODE;
import static ro.roda.service.page.RodaPageConstants.SNIPPET_CODE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import ro.roda.domain.CmsFile;
import ro.roda.domain.CmsLayout;
import ro.roda.domain.CmsPage;
import ro.roda.domain.CmsPageType;
import ro.roda.domain.CmsSnippet;
import ro.roda.domain.Lang;
import ro.roda.domain.News;

@Service
@Transactional
public class RodaPageServiceImpl implements RodaPageService, ServletContextAware {

	private ServletContext servletContext;

	private final Log log = LogFactory.getLog(this.getClass());

	@Value("${roda.cms.news.count}")
	private Integer newsCount = 3;

	@Value("${roda.cms.news.maxtitle}")
	private Integer newsMaxTitle = 100;

	@Value("${roda.cms.news.maxtext}")
	private Integer newsMaxText = 200;

	@CacheEvict(value = "pages")
	public void evict(String url) {

	}

	@CacheEvict(value = "pages", allEntries = true)
	public void evictAll() {

	}

	/**
	 * Returns an array of 4 Strings, as follows: if the page is redirected, the
	 * array will contain [null, null, redirectExternalUrl, null] or [null,
	 * null, null, redirectInternalUrl]; otherwise, the array will contain
	 * [pageContent, pageTitle, null, null].
	 * 
	 * @param cmsPage
	 * @param url
	 * @return
	 * @throws NoSuchRequestHandlingMethodException
	 */

	@Cacheable(value = "pages", key = "#cmsPageUrl")
	public String[] generatePage(String cmsPageUrl, HttpServletRequest request) throws Exception {

		log.trace("cmsPageUrl: " + cmsPageUrl);

		// Request Parameters can be obtained here: request.getParameterMap()

		CmsPage cmsPage = CmsPage.findCmsPageByFullUrl(cmsPageUrl);
		String[] response = new String[4];
		String pageTitle = "";
		StringBuilder sb = new StringBuilder();

		if (cmsPage != null) {

			if (cmsPage.getExternalRedirect() != null && !cmsPage.getExternalRedirect().trim().equals("")) {
				response[2] = cmsPage.getExternalRedirect();
				return response;
			}
			if (cmsPage.getInternalRedirect() != null && !cmsPage.getInternalRedirect().trim().equals("")) {
				response[3] = cmsPage.getInternalRedirect();
				return response;
			}

			String pageContent = replacePageContent(getLayout(cmsPage, cmsPageUrl), cmsPage);

			// pageContent = StringUtils.replace(pageContent, "\\", "\\\\");
			// pageContent = StringUtils.replace(pageContent, "\"", "\\\"");

			sb.append(pageContent);
			pageTitle = cmsPage.getName();
		} else {
			throw new NoSuchRequestHandlingMethodException(request);

			// below: the old way of handling an "ERROR PAGE" (page not found)

			// String requestLang = null;
			//
			// // the first fragment of the url might be the requested language
			// if (cmsPageUrl.indexOf("/", 1) > 0) {
			// requestLang = cmsPageUrl.substring(1, cmsPageUrl.indexOf("/",
			// 1));
			// }
			//
			// Lang lang = Lang.findLang(requestLang);
			// if (lang == null) {
			// lang = Lang.findLang(DEFAULT_ERROR_PAGE_LANGUAGE);
			// }
			//
			// List<CmsPage> errorPages = CmsPage.findCmsPageByLangAndType(lang,
			// CmsPageType.checkCmsPageType(null, "error404", null));
			//
			// if (errorPages != null && errorPages.size() > 0) {
			// String pageContent =
			// replacePageContent(getLayout(errorPages.get(0), cmsPageUrl),
			// errorPages.get(0));
			//
			// sb.append(pageContent);
			// pageTitle = errorPages.get(0).getName();
			// }

		}
		response[0] = sb.toString();
		response[1] = pageTitle;

		return response;
	}

	public String[] generatePreviewPage(CmsPage cmsPage, String layoutContent, String pageContent, String url) {
		String pageTitle = "";
		String[] pageContentAndTitle = new String[4];
		StringBuilder sb = new StringBuilder();

		if (cmsPage != null) {
			String resultPageContent = replacePageContent(getLayout(cmsPage, layoutContent, url), pageContent, cmsPage);

			sb.append(resultPageContent);
			pageTitle = cmsPage.getName();
		} else {
			String requestLang = null;

			// the first fragment of the url might be the requested language
			if (url.indexOf("/", 1) > 0) {
				requestLang = url.substring(1, url.indexOf("/", 1));
			}

			Lang lang = Lang.findLang(requestLang);
			if (lang == null) {
				lang = Lang.findLang(DEFAULT_ERROR_PAGE_LANGUAGE);
			}

			List<CmsPage> errorPages = CmsPage.findCmsPageByLangAndType(lang,
					CmsPageType.checkCmsPageType(null, "error404", null));

			if (errorPages != null && errorPages.size() > 0) {
				String resultPageContent = replacePageContent(getLayout(errorPages.get(0), url), pageContent,
						errorPages.get(0));

				sb.append(resultPageContent);
				pageTitle = errorPages.get(0).getName();
			}
		}
		pageContentAndTitle[0] = sb.toString();
		pageContentAndTitle[1] = pageTitle;

		return pageContentAndTitle;
	}

	public String generateDefaultPageUrl() {
		CmsPage defaultPage = CmsPage.findCmsPageDefault();
		String relativeUrl = generateFullRelativeUrl(defaultPage);
		return (relativeUrl != null && relativeUrl.length() > 1) ? relativeUrl : DEFAULT_LANGUAGE_URL;
	}

	public String generateFullRelativeUrl(CmsPage cmsPage) {
		String result = "";
		if (cmsPage != null) {
			result = cmsPage.getUrl();
			CmsPage parentPage = cmsPage.getCmsPageId();
			while (parentPage != null) {
				result = parentPage.getUrl() + "/" + result;
				parentPage = parentPage.getCmsPageId();
			}

			result = "/" + result;
		}

		return result;
	}

	private String getLayout(CmsPage cmsPage, String url) {
		CmsLayout pageLayout = cmsPage.getCmsLayoutId();
		String layoutContent = pageLayout.getLayoutContent();
		layoutContent = replaceGetNews(layoutContent, cmsPage, newsCount, newsMaxTitle, newsMaxText);

		layoutContent = replacePageTitle(layoutContent, cmsPage.getMenuTitle());
		layoutContent = replaceContextPath(layoutContent);
		layoutContent = replaceSnippets(layoutContent);

		layoutContent = replacePageLinkByUrl(layoutContent, cmsPage);
		layoutContent = replacePageBreadcrumbs(layoutContent, cmsPage);
		layoutContent = replacePageTreeByUrl(layoutContent, cmsPage);
		layoutContent = replacePageUrlLink(layoutContent, cmsPage);
		layoutContent = replaceFileUrl(layoutContent, url);
		layoutContent = replaceImgLink(layoutContent, url);

		return layoutContent;
	}

	private String getLayout(CmsPage cmsPage, String layoutContent, String url) {
		// This method is to be invoked mainly from the page preview generator.

		String resultLayoutContent = layoutContent;
		resultLayoutContent = replaceSnippets(resultLayoutContent);
		resultLayoutContent = replacePageTitle(resultLayoutContent, cmsPage.getMenuTitle());
		resultLayoutContent = replaceContextPath(resultLayoutContent);
		resultLayoutContent = replacePageLinkByUrl(resultLayoutContent, cmsPage);
		resultLayoutContent = replacePageBreadcrumbs(resultLayoutContent, cmsPage);
		resultLayoutContent = replacePageTreeByUrl(resultLayoutContent, cmsPage);
		resultLayoutContent = replacePageUrlLink(resultLayoutContent, cmsPage);

		resultLayoutContent = replaceFileUrl(resultLayoutContent, ADMIN_URL);
		resultLayoutContent = replaceImgLink(resultLayoutContent, ADMIN_URL);
		resultLayoutContent = replaceGetNews(resultLayoutContent, cmsPage, newsCount, newsMaxTitle, newsMaxText);
		return resultLayoutContent;
	}

	private String replacePageTitle(String content, String pageTitle) {
		return content.replace(PAGE_TITLE_CODE, pageTitle != null ? pageTitle : "");
	}

	private String replaceContextPath(String content) {
		return content.replace(PAGE_CONTEXT_PATH, servletContext.getContextPath() + PAGE_MAPPING);
	}

	private String replacePageLinkByUrl(String content, CmsPage cmsPage) {
		int fromIndex = content.indexOf(PAGE_LINK_BY_URL_CODE, 0);
		String result = content;
		while (fromIndex > -1) {
			String url = result.substring(fromIndex + PAGE_LINK_BY_URL_CODE.length(),
					result.indexOf("')]]", fromIndex + PAGE_LINK_BY_URL_CODE.length()));

			result = StringUtils.replace(result, PAGE_LINK_BY_URL_CODE + url + "')]]", servletContext.getContextPath()
					+ PAGE_MAPPING + generateFullRelativeUrl(url, cmsPage));
			fromIndex = result.indexOf(PAGE_LINK_BY_URL_CODE, fromIndex + PAGE_LINK_BY_URL_CODE.length());
		}

		return result;
	}

	private String replacePageTreeByUrl(String content, CmsPage cmsPage) {
		int fromIndex = content.indexOf(PAGE_TREE_BY_URL_CODE, 0);
		String result = content;
		while (fromIndex > -1) {
			String arg = result.substring(fromIndex + PAGE_TREE_BY_URL_CODE.length(),
					result.indexOf(")]]", fromIndex + PAGE_TREE_BY_URL_CODE.length()));

			String args[] = arg.split("',");
			String url = args[0].trim();
			Integer depth = null;
			if (args.length > 1) {
				depth = Integer.parseInt(args[1].trim());
			}

			log.debug("URL =" + url);
			log.debug("Depth = " + depth);

			CmsPage rootPage = CmsPage.findCmsPage(url).get(0);

			log.debug("Replaced code is: " + PAGE_TREE_BY_URL_CODE + url + "'," + depth + ")]]");

			result = StringUtils.replace(result, PAGE_TREE_BY_URL_CODE + url + "'," + depth + ")]]",
					generatePageTreeMenu(rootPage, depth));
			fromIndex = result.indexOf(PAGE_TREE_BY_URL_CODE, fromIndex + PAGE_TREE_BY_URL_CODE.length());
		}

		return result;
	}

	private String replaceGetNews(String content, CmsPage cmsPage, Integer count, Integer maxTitle, Integer maxText) {
		int fromIndex = content.indexOf(GETNEWS_CODE, 0);
		if (cmsPage != null) {
			int pageLangId = cmsPage.getLangId().getId();
			while (fromIndex > -1) {
				content = StringUtils.replace(content, GETNEWS_CODE + "]]",
						generateNewsList(pageLangId, count, maxTitle, maxText));
				fromIndex = content.indexOf(GETNEWS_CODE, fromIndex + GETNEWS_CODE.length());
			}
		} else {
			log.debug("CmsPage is null");
		}
		return content;
	}

	private String replaceGetNewsId(String content, CmsPage cmsPage, Map<String, String> parameters) {
		int fromIndex = content.indexOf(GETNEWSID_CODE, 0);
		if (cmsPage != null) {
			int pageLangId = cmsPage.getLangId().getId();
			while (fromIndex > -1) {
				content = StringUtils.replace(content, GETNEWSID_CODE + "]]",
						generateNewsId(pageLangId, Integer.valueOf(parameters.get("id"))));
				fromIndex = content.indexOf(GETNEWSID_CODE, fromIndex + GETNEWSID_CODE.length());
			}
		} else {
			log.debug("CmsPage is null");
		}
		return content;
	}

	private String replacePageBreadcrumbs(String content, CmsPage cmsPage) {
		int fromIndex = content.indexOf(PAGE_BREADCRUMBS_CODE, 0);
		while (fromIndex > -1) {
			String sep = content.substring(fromIndex + PAGE_BREADCRUMBS_CODE.length(),
					content.indexOf("')]]", fromIndex + PAGE_BREADCRUMBS_CODE.length()));

			log.trace("Breadcrumbs separator = " + sep);

			content = StringUtils.replace(content, PAGE_BREADCRUMBS_CODE + sep + "')]]",
					generatePageBreadcrumbs(cmsPage, sep));

			fromIndex = content.indexOf(PAGE_BREADCRUMBS_CODE, fromIndex + PAGE_BREADCRUMBS_CODE.length());
		}

		return content;
	}

	private String replacePageUrlLink(String content, CmsPage cmsPage) {
		int fromIndex = content.indexOf(PAGE_URL_LINK_CODE, 0);
		String result = content;
		while (fromIndex > -1) {
			String url = result.substring(fromIndex + PAGE_URL_LINK_CODE.length(),
					result.indexOf("]]", fromIndex + PAGE_URL_LINK_CODE.length()));

			// The following code has issues due to the interference with
			// regular expressions syntax:
			// result = result.replaceAll("[[PageURLLink:" + url + "]]",
			// "<a href=\"" + url + "\">" + CmsPage.findCmsPage(url).getName() +
			// "</a>");
			// CmsPage cmsPage = CmsPage.findCmsPage(url);

			CmsPage page = findRelativePage(url, cmsPage);

			result = result.substring(0, fromIndex) + "<a href=\"" + servletContext.getContextPath() + PAGE_MAPPING
					+ generateFullRelativeUrl(page) + "\">" + (page != null ? page.getMenuTitle() : url) + "</a>"
					+ result.substring(result.indexOf("]]", fromIndex + PAGE_URL_LINK_CODE.length()) + "]]".length());

			fromIndex = result.indexOf(PAGE_URL_LINK_CODE, fromIndex + PAGE_URL_LINK_CODE.length());
		}

		return result;
	}

	private String replaceFileUrl(String content, String url) {
		int fromIndex = content.indexOf(FILE_URL_LINK_CODE, 0);
		String result = content;
		while (fromIndex > -1) {
			String alias = result.substring(fromIndex + FILE_URL_LINK_CODE.length(),
					result.indexOf("]]", fromIndex + FILE_URL_LINK_CODE.length()));
			CmsFile cmsFile = CmsFile.findCmsFile(alias);

			StringBuilder relativePath = new StringBuilder();
			if (url != null) {
				for (int i = 0; i < StringUtils.countMatches(url, "/") - OFFSET_RELATIVE_URL; i++) {
					relativePath.append("../");
				}
			}

			result = result.substring(0, fromIndex)
					+ (cmsFile != null ? relativePath.toString() + CMS_FILE_CONTENT_URL + cmsFile.getId() : "")
					+ result.substring(result.indexOf("]]", fromIndex + FILE_URL_LINK_CODE.length()) + "]]".length());
			fromIndex = result.indexOf(FILE_URL_LINK_CODE, fromIndex + FILE_URL_LINK_CODE.length());
		}
		return result;
	}

	private String replaceImgLink(String content, String url) {

		int fromIndex = content.indexOf(IMG_LINK_CODE, 0);
		String result = content;
		while (fromIndex > -1) {
			String alias = result.substring(fromIndex + IMG_LINK_CODE.length(),
					result.indexOf("]]", fromIndex + IMG_LINK_CODE.length()));
			CmsFile cmsFile = CmsFile.findCmsFile(alias);

			log.trace("The URL in replaceImgLink is: " + url);

			StringBuilder relativePath = new StringBuilder();

			if (url != null) {
				for (int i = 0; i < StringUtils.countMatches(url, "/") - OFFSET_RELATIVE_URL; i++) {
					relativePath.append("../");
				}
			}

			result = result.substring(0, fromIndex) + "<img src=\""
					+ (cmsFile != null ? relativePath.toString() + CMS_FILE_CONTENT_URL + cmsFile.getId() : "")
					+ "\" />"
					+ result.substring(result.indexOf("]]", fromIndex + IMG_LINK_CODE.length()) + "]]".length());
			fromIndex = result.indexOf(IMG_LINK_CODE, fromIndex + IMG_LINK_CODE.length());
		}
		return result;
	}

	private String replaceSnippets(String content) {
		String snippetsReplaced = content;

		int index = 0;
		while ((index = snippetsReplaced.indexOf(SNIPPET_CODE, index)) > -1) {
			int snippetNameIndex = index + SNIPPET_CODE.length();
			String snippetName = snippetsReplaced.substring(snippetNameIndex,
					snippetsReplaced.indexOf("]]", snippetNameIndex));

			String snippetContent = replaceSnippets(CmsSnippet.findCmsSnippet(snippetName).getSnippetContent());
			snippetsReplaced = snippetsReplaced.replace(SNIPPET_CODE + snippetName + "]]", snippetContent);
		}

		return snippetsReplaced;
	}

	private String replacePageContent(String content, CmsPage page) {
		String result = content;
		if (result.indexOf(PAGE_CONTENT_CODE) > -1) {
			// We assume that a CmsPage has a single CmsPageContent
			String pageContent = page.getCmsPageContents().iterator().next().getContentText();
			pageContent = replacePageTitle(pageContent, page.getMenuTitle());
			pageContent = replaceContextPath(pageContent);
			pageContent = replacePageLinkByUrl(pageContent, page);
			pageContent = replacePageBreadcrumbs(pageContent, page);
			pageContent = replaceSnippets(pageContent);
			pageContent = replacePageTreeByUrl(pageContent, page);
			pageContent = replacePageUrlLink(pageContent, page);
			pageContent = replaceFileUrl(pageContent, generateFullRelativeUrl(page));
			pageContent = replaceImgLink(pageContent, generateFullRelativeUrl(page));
			pageContent = replaceGetNews(pageContent, page, newsCount, newsMaxTitle, newsMaxText);
			result = result.replace(PAGE_CONTENT_CODE, pageContent);
		}
		return result;
	}

	private String replacePageContent(String content, String pageContent, CmsPage page) {
		// This method is to be invoked mainly from the page preview generator.

		String result = content;
		if (result.indexOf(PAGE_CONTENT_CODE) > -1) {
			// We assume that a CmsPage has a single CmsPageContent
			String resultContent = pageContent;
			resultContent = replacePageTitle(pageContent, page.getMenuTitle());
			resultContent = replaceContextPath(pageContent);
			resultContent = replacePageLinkByUrl(pageContent, page);
			resultContent = replacePageBreadcrumbs(pageContent, page);
			resultContent = replaceSnippets(pageContent);
			resultContent = replacePageTreeByUrl(pageContent, page);
			resultContent = replacePageUrlLink(pageContent, page);

			resultContent = replaceFileUrl(pageContent, ADMIN_URL);
			resultContent = replaceImgLink(pageContent, ADMIN_URL);
			resultContent = replaceGetNews(pageContent, page, newsCount, newsMaxTitle, newsMaxText);
			result = result.replace(PAGE_CONTENT_CODE, resultContent);
		}
		return result;
	}

	private String generateFullRelativeUrl(String url, CmsPage cmsPage) {

		// returns the full relative url of the closest page with the given url
		// fragment

		String result = "";

		CmsPage tempPage = cmsPage;
		CmsPage resultPage = null;

		// search above
		if (cmsPage != null) {
			while (tempPage != null) {
				resultPage = CmsPage.findCmsPageByParent(url, tempPage);
				if (resultPage != null) {
					return generateFullRelativeUrl(resultPage);
				} else {
					tempPage = tempPage.getCmsPageId();
				}

			}
		}

		// search underneath
		if (cmsPage != null) {

			Set<CmsPage> cmsPagesUnderneath = cmsPage.getCmsPages();

			if (cmsPagesUnderneath != null) {

				Iterator<CmsPage> itCmsPagesUnderneath = cmsPagesUnderneath.iterator();
				while (itCmsPagesUnderneath.hasNext()) {
					tempPage = itCmsPagesUnderneath.next();
					resultPage = CmsPage.findCmsPageByParent(url, tempPage);
					if (resultPage != null) {
						return generateFullRelativeUrl(resultPage);
					}
				}
			}
		}

		return result;
	}

	private CmsPage findRelativePage(String url, CmsPage cmsPage) {

		// returns the closest page
		// with the given url fragment

		CmsPage resultPage = null;

		CmsPage previousPage = null;
		CmsPage tempPage = cmsPage;

		if (cmsPage != null) {
			while (tempPage != null) {
				resultPage = findClosestRelativePage(url, tempPage, previousPage);
				if (resultPage != null) {
					return resultPage;
				} else {
					previousPage = tempPage;
					tempPage = tempPage.getCmsPageId();
				}
			}
		}
		return resultPage;
	}

	private CmsPage findClosestRelativePage(String url, CmsPage cmsPage, CmsPage previousPage) {
		// find the closest relative page searching top-down from the current
		// page

		if (cmsPage.getUrl().equals(url)) {
			return cmsPage;
		}

		Set<CmsPage> children = cmsPage.getCmsPages();
		List<CmsPage> unvisitedChildren = new ArrayList<CmsPage>();
		unvisitedChildren.addAll(children);
		if (previousPage != null) {
			unvisitedChildren.remove(previousPage);
		}

		while (unvisitedChildren.size() > 0) {
			List<CmsPage> added = new ArrayList<CmsPage>();
			List<CmsPage> removed = new ArrayList<CmsPage>();
			for (CmsPage child : unvisitedChildren) {
				if (child.getUrl().equals(url)) {
					return child;
				}
				if (child.getCmsPages() != null && child.getCmsPages().size() > 0) {
					added.addAll(child.getCmsPages());
				}
				removed.add(child);
			}
			unvisitedChildren.removeAll(removed);
			unvisitedChildren.addAll(added);
		}
		return null;
	}

	private String generatePageBreadcrumbs(CmsPage cmsPage, String separator) {
		StringBuilder result = new StringBuilder();
		// current page title - as text
		result.append(cmsPage.getMenuTitle());
		// the hierarchy of parents - as links
		cmsPage = cmsPage.getCmsPageId();
		while (cmsPage != null) {
			result = result.insert(0, "<a href=\"" + servletContext.getContextPath() + PAGE_MAPPING
					+ generateFullRelativeUrl(cmsPage) + "\">" + cmsPage.getMenuTitle() + "</a>" + separator);
			cmsPage = cmsPage.getCmsPageId();
		}
		// log.trace("Breadcrumbs = " + result);
		return result.toString();
	}

	private String generatePageTreeMenu(CmsPage cmsPage, Integer depth) {

		StringBuilder result = new StringBuilder();
		// TODO what is the id of the menu? maybe another parameter of the
		// function?
		result.append("<ul id=\"navmenu\">");

		if (cmsPage != null && cmsPage.getCmsPages() != null && cmsPage.getCmsPages().size() > 0) {
			// cmsPage.reorderCmsPages();

			SortedSet<CmsPage> sortedChildren = new TreeSet<CmsPage>();
			sortedChildren.addAll(cmsPage.getCmsPages());
			for (CmsPage child : sortedChildren) {
				result.append(generatePageTreeMenuRec(child, depth));
			}
		}

		result.append("</ul>");

		log.debug("The menu is:" + result);

		return result.toString();
	}

	private String generateNewsList(Integer langId, Integer count, Integer maxTitle, Integer maxText) {

		StringBuilder result = new StringBuilder();
		List<News> news = News.findNumberedNewspieces(count, langId);
		if (news != null && news.size() > 0) {
			result.append("<div class='news'>");
			Iterator<News> newsIterator = news.iterator();
			while (newsIterator.hasNext()) {
				News newsitem = (News) newsIterator.next();
				result.append("<div class='newsitem'>");
				result.append("<div class='newstitle'>" + newsitem.getTruncatedTitle(maxTitle) + "</div>");
				result.append("<div class=\"newsdate\">" + newsitem.getAdded().toString() + "</div>");
				result.append("<div class='newscontent'>" + newsitem.getTruncatedContent(maxText) + "</div>");
				result.append("</div>");
			}
			result.append("</div>");
		}
		return result.toString();
	}

	private String generateNewsId(Integer langId, Integer id) {

		StringBuilder result = new StringBuilder();

		// TODO implement

		return result.toString();
	}

	private String generatePageTreeMenuRec(CmsPage cmsPage, Integer depth) {
		// TODO validate depth
		StringBuilder result = new StringBuilder();

		if (cmsPage != null && depth > 0) {

			if (cmsPage.getCmsPages() != null && cmsPage.getCmsPages().size() > 0) {
				// cmsPage.reorderCmsPages();

				result.append("<li class=\"more\">");
				result.append(PAGE_URL_LINK_CODE + cmsPage.getUrl() + "]]");
				result.append("<ul>");

				SortedSet<CmsPage> sortedChildren = new TreeSet<CmsPage>();
				sortedChildren.addAll(cmsPage.getCmsPages());

				for (CmsPage child : sortedChildren) {
					result.append(generatePageTreeMenuRec(child, depth - 1).toString());
				}

				result.append("</ul>");
				// result.append("</li>");
			} else if (cmsPage.isNavigable()) {
				result.append("<li>");
				result.append(PAGE_URL_LINK_CODE + cmsPage.getUrl() + "]]");
				result.append("</li>");
			}
		}

		return result.toString();
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		log.trace("ContextPath: " + servletContext.getContextPath());
		this.servletContext = servletContext;
	}

}
