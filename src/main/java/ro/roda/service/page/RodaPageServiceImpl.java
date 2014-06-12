package ro.roda.service.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.CmsFile;
import ro.roda.domain.CmsLayout;
import ro.roda.domain.CmsPage;
import ro.roda.domain.CmsPageType;
import ro.roda.domain.CmsSnippet;
import ro.roda.domain.Lang;

@Service
@Transactional
public class RodaPageServiceImpl implements RodaPageService {

	private static String RODA_PAGE_URL = "/roda/page";
	private static String PAGE_TITLE_CODE = "[[Code: PageTitle]]";
	private static String PAGE_LINK_BY_URL_CODE = "[[Code: PageLinkbyUrl('";
	private static String PAGE_URL_LINK_CODE = "[[PageURLLink:";
	private static String FILE_URL_LINK_CODE = "[[FileURL:";
	private static String IMG_LINK_CODE = "[[ImgLink: ";
	private static String SNIPPET_CODE = "[[Snippet: ";
	private static String PAGE_CONTENT_CODE = "[[Code: PageContent]]";
	private static String PAGE_TREE_BY_URL_CODE = "[[Code: PageTreeByUrl('";
	private static String PAGE_BREADCRUMBS = "[[Code: PageBreadcrumbs('";

	private static String DEFAULT_ERROR_PAGE_LANG = "en";
	private static String ADMIN_URL = "admin/index.html";
	private static String CMS_FILE_CONTENT_URL = "cmsfilecontent/";
	private static String defaultUrlWhenNoLanguage = "/en";

	private final Log log = LogFactory.getLog(this.getClass());

	@CacheEvict(value = "pages")
	public void evict(String url) {

	}

	@CacheEvict(value = "pages", allEntries = true)
	public void evictAll() {

	}

	@Cacheable(value = "pages")
	public String[] generatePage(String url) {
		// if (checkFullRelativeUrl(url)) {

		// the URL fragment is no longer unique, but the full URL is
		// TODO remove the commented code below if definitive
		// String dbUrl = url.substring((url.lastIndexOf("/") + 1));
		// CmsPage page = CmsPage.findCmsPage(dbUrl);

		CmsPage page = CmsPage.findCmsPageByFullUrl(url);

		return generatePage(page, url);
	}

	public String generateDefaultPageUrl() {
		CmsPage defaultPage = CmsPage.findCmsPageDefault();
		String relativeUrl = generateFullRelativeUrl(defaultPage);
		return (relativeUrl != null && relativeUrl.length() > 1) ? relativeUrl : defaultUrlWhenNoLanguage;
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
	 */
	public String[] generatePage(CmsPage cmsPage, String url) {
		String pageTitle = "";
		String[] pageContentAndTitle = new String[4];
		StringBuilder sb = new StringBuilder();

		if (cmsPage != null) {

			if (cmsPage.getExternalRedirect() != null && !cmsPage.getExternalRedirect().trim().equals("")) {
				pageContentAndTitle[2] = cmsPage.getExternalRedirect();
				return pageContentAndTitle;
			}
			if (cmsPage.getInternalRedirect() != null && !cmsPage.getInternalRedirect().trim().equals("")) {
				pageContentAndTitle[3] = cmsPage.getInternalRedirect();
				return pageContentAndTitle;
			}

			String pageContent = replacePageContent(getLayout(cmsPage, url), cmsPage);

			// pageContent = StringUtils.replace(pageContent, "\\", "\\\\");
			// pageContent = StringUtils.replace(pageContent, "\"", "\\\"");

			sb.append(pageContent);
			pageTitle = cmsPage.getName();
		} else {
			String requestLang = null;

			// the first fragment of the url might be the requested language
			if (url.indexOf("/", 1) > 0) {
				requestLang = url.substring(1, url.indexOf("/", 1));
			}

			Lang lang = Lang.findLang(requestLang);
			if (lang == null) {
				lang = Lang.findLang(DEFAULT_ERROR_PAGE_LANG);
			}

			List<CmsPage> errorPages = CmsPage.findCmsPageByLangAndType(lang,
					CmsPageType.checkCmsPageType(null, "error404", null));

			if (errorPages != null && errorPages.size() > 0) {
				String pageContent = replacePageContent(getLayout(errorPages.get(0), url), errorPages.get(0));

				sb.append(pageContent);
				pageTitle = errorPages.get(0).getName();
			}

			// if there is no error page defined, then:
			// sb.append("<html><div> The page you requested does not exist. (url: "
			// + url + ")</div></html>");
			// pageTitle = "Error";
		}
		pageContentAndTitle[0] = sb.toString();
		pageContentAndTitle[1] = pageTitle;

		return pageContentAndTitle;
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
				lang = Lang.findLang(DEFAULT_ERROR_PAGE_LANG);
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

		layoutContent = replacePageTitle(layoutContent, cmsPage.getMenuTitle());
		layoutContent = replacePageLinkByUrl(layoutContent, cmsPage);
		layoutContent = replacePageBreadcrumbs(layoutContent, cmsPage);
		layoutContent = replaceSnippets(layoutContent);
		layoutContent = replacePageTreeByUrl(layoutContent, cmsPage);
		layoutContent = replacePageUrlLink(layoutContent, cmsPage);
		layoutContent = replaceFileUrl(layoutContent, url);
		layoutContent = replaceImgLink(layoutContent, url);

		return layoutContent;
	}

	private String getLayout(CmsPage cmsPage, String layoutContent, String url) {
		// This method is to be invoked mainly from the page preview generator.

		String resultLayoutContent = layoutContent;

		resultLayoutContent = replacePageTitle(resultLayoutContent, cmsPage.getMenuTitle());
		resultLayoutContent = replacePageLinkByUrl(resultLayoutContent, cmsPage);
		resultLayoutContent = replacePageBreadcrumbs(resultLayoutContent, cmsPage);
		resultLayoutContent = replaceSnippets(resultLayoutContent);
		resultLayoutContent = replacePageTreeByUrl(resultLayoutContent, cmsPage);
		resultLayoutContent = replacePageUrlLink(resultLayoutContent, cmsPage);

		resultLayoutContent = replaceFileUrl(resultLayoutContent, ADMIN_URL);
		resultLayoutContent = replaceImgLink(resultLayoutContent, ADMIN_URL);

		return resultLayoutContent;
	}

	private String replacePageTitle(String content, String pageTitle) {
		return content.replace(PAGE_TITLE_CODE, pageTitle != null ? pageTitle : "");
	}

	private String replacePageLinkByUrl(String content, CmsPage cmsPage) {
		int fromIndex = content.indexOf(PAGE_LINK_BY_URL_CODE, 0);
		String result = content;
		while (fromIndex > -1) {
			String url = result.substring(fromIndex + PAGE_LINK_BY_URL_CODE.length(),
					result.indexOf("')]]", fromIndex + PAGE_LINK_BY_URL_CODE.length()));

			result = StringUtils.replace(result, PAGE_LINK_BY_URL_CODE + url + "')]]", RODA_PAGE_URL
					+ generateFullRelativeUrl(url, cmsPage));
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

	private String replacePageBreadcrumbs(String content, CmsPage cmsPage) {
		int fromIndex = content.indexOf(PAGE_BREADCRUMBS, 0);
		while (fromIndex > -1) {
			String sep = content.substring(fromIndex + PAGE_BREADCRUMBS.length(),
					content.indexOf("')]]", fromIndex + PAGE_BREADCRUMBS.length()));

			log.debug("Breadcrumbs separator = " + sep);

			content = StringUtils.replace(content, PAGE_BREADCRUMBS + sep + "')]]",
					generatePageBreadcrumbs(cmsPage, sep));

			fromIndex = content.indexOf(PAGE_BREADCRUMBS, fromIndex + PAGE_BREADCRUMBS.length());
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

			result = result.substring(0, fromIndex) + "<a href=\"" + RODA_PAGE_URL + generateFullRelativeUrl(page)
					+ "\">" + (page != null ? page.getName() : url) + "</a>"
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
				for (int i = 0; i < StringUtils.countMatches(url, "/"); i++) {
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

			System.out.println("The URL in replaceImgLink is " + url);

			StringBuilder relativePath = new StringBuilder();

			if (url != null) {
				for (int i = 0; i < StringUtils.countMatches(url, "/"); i++) {
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
			pageContent = replacePageLinkByUrl(pageContent, page);
			pageContent = replacePageBreadcrumbs(pageContent, page);
			pageContent = replaceSnippets(pageContent);
			pageContent = replacePageTreeByUrl(pageContent, page);
			pageContent = replacePageUrlLink(pageContent, page);
			pageContent = replaceFileUrl(pageContent, generateFullRelativeUrl(page));
			pageContent = replaceImgLink(pageContent, generateFullRelativeUrl(page));

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
			resultContent = replacePageLinkByUrl(pageContent, page);
			resultContent = replacePageBreadcrumbs(pageContent, page);
			resultContent = replaceSnippets(pageContent);
			resultContent = replacePageTreeByUrl(pageContent, page);
			resultContent = replacePageUrlLink(pageContent, page);

			resultContent = replaceFileUrl(pageContent, ADMIN_URL);
			resultContent = replaceImgLink(pageContent, ADMIN_URL);

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

		return result;
	}

	private CmsPage findRelativePage(String url, CmsPage cmsPage) {

		// returns the closest page with the given url
		// fragment

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
			result = result.insert(0,
					"<a href=\"" + RODA_PAGE_URL + generateFullRelativeUrl(cmsPage) + "\">" + cmsPage.getMenuTitle()
							+ "</a>" + separator);
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
			} else if (cmsPage.isNavigable()) {
				result.append("<li>");
				result.append(PAGE_URL_LINK_CODE + cmsPage.getUrl() + "]]");
				result.append("</li>");
			}
		}

		return result.toString();
	}

}
