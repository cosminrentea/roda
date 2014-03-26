package ro.roda.plugin;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.CmsFile;
import ro.roda.domain.CmsLayout;
import ro.roda.domain.CmsPage;
import ro.roda.domain.CmsSnippet;

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

	public String generatePage(String url) {
		StringBuilder sb = new StringBuilder();

		// if (checkFullRelativeUrl(url)) {

		// the URL fragment is no longer unique, but the full URL is
		// TODO remove the commented code below if definitive
		// String dbUrl = url.substring((url.lastIndexOf("/") + 1));
		// CmsPage page = CmsPage.findCmsPage(dbUrl);

		CmsPage page = CmsPage.findCmsPageByFullUrl(url);

		if (page != null) {
			String pageContent = replacePageContent(getLayout(page, url), page);

			// pageContent = StringUtils.replace(pageContent, "\\", "\\\\");
			// pageContent = StringUtils.replace(pageContent, "\"", "\\\"");

			sb.append(pageContent);
		} else {
			sb.append("<html><div> The page you requested does not exist. (url: " + url + ")</div></html>");
		}
		return sb.toString();
	}

	private String getLayout(CmsPage cmsPage, String url) {
		CmsLayout pageLayout = cmsPage.getCmsLayoutId();
		String layoutContent = pageLayout.getLayoutContent();

		layoutContent = replacePageTitle(layoutContent, cmsPage.getMenuTitle());
		layoutContent = replacePageLinkByUrl(layoutContent, cmsPage);

		layoutContent = replaceSnippets(layoutContent);
		layoutContent = replacePageUrlLink(layoutContent, cmsPage);
		layoutContent = replaceFileUrl(layoutContent, url);
		layoutContent = replaceImgLink(layoutContent, url);

		return layoutContent;
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
					+ (cmsFile != null ? relativePath.toString() + cmsFile.getUrl() : "")
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

			StringBuilder relativePath = new StringBuilder();

			if (url != null) {
				for (int i = 0; i < StringUtils.countMatches(url, "/"); i++) {
					relativePath.append("../");
				}
			}

			result = result.substring(0, fromIndex) + "<img src=\""
					+ (cmsFile != null ? relativePath.toString() + cmsFile.getUrl() : "") + "\" />"
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
			// We suppose that a CmsPage has a single CmsPageContent
			String pageContent = page.getCmsPageContents().iterator().next().getContentText();
			pageContent = replacePageTitle(pageContent, page.getMenuTitle());
			pageContent = replacePageLinkByUrl(pageContent, page);
			pageContent = replaceSnippets(pageContent);

			pageContent = replacePageUrlLink(pageContent, page);
			pageContent = replaceFileUrl(pageContent, generateFullRelativeUrl(page));
			pageContent = replaceImgLink(pageContent, generateFullRelativeUrl(page));

			result = result.replace(PAGE_CONTENT_CODE, pageContent);
		}
		return result;
	}

	private String generateFullRelativeUrl(CmsPage cmsPage) {
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
		CmsPage tempPage = cmsPage;
		CmsPage resultPage = null;

		if (cmsPage != null) {
			while (tempPage != null) {
				resultPage = CmsPage.findCmsPageByParent(url, tempPage);
				if (resultPage != null) {
					return resultPage;
				} else {
					tempPage = tempPage.getCmsPageId();
				}

			}
		}
		return resultPage;
	}

	// private boolean checkFullRelativeUrl(String url) {
	//
	// // checks if the components of the url and the parent-child relationship
	// // exist in the database
	// if (url != null) {
	// StringTokenizer tokenizer = new StringTokenizer(url, "/");
	// CmsPage prevPage = null;
	// while (tokenizer.hasMoreTokens()) {
	// String pathUrl = tokenizer.nextToken();
	//
	// CmsPage pathPage = CmsPage.findCmsPage(pathUrl);
	//
	// if (pathPage == null) {
	// return false;
	// } else {
	// if (pathPage.getCmsPageId() != prevPage) {
	// return false;
	// }
	// prevPage = pathPage;
	// }
	// }
	// return true;
	// }
	// return false;
	// }

}
