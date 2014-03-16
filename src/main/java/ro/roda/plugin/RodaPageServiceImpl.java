package ro.roda.plugin;

import java.util.StringTokenizer;

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

		if (checkFullRelativeUrl(url)) {
			String dbUrl = url.substring((url.lastIndexOf("/") + 1));

			CmsPage page = CmsPage.findCmsPage(dbUrl);

			if (page != null) {
				String pageContent = replacePageContent(getLayout(page, url), page);

				// pageContent = StringUtils.replace(pageContent, "\\", "\\\\");
				// pageContent = StringUtils.replace(pageContent, "\"", "\\\"");

				sb.append(pageContent);
			}
		} else {
			sb.append("<html><div> The page you requested does not exist. (url: " + url + ")</div></html>");
		}
		return sb.toString();
	}

	private String getLayout(CmsPage cmsPage, String url) {
		CmsLayout pageLayout = cmsPage.getCmsLayoutId();
		String layoutContent = pageLayout.getLayoutContent();

		layoutContent = replacePageTitle(layoutContent, cmsPage.getMenuTitle());
		layoutContent = replacePageLinkByUrl(layoutContent);

		layoutContent = replaceSnippets(layoutContent);
		layoutContent = replacePageUrlLink(layoutContent);
		layoutContent = replaceFileUrl(layoutContent, url);
		layoutContent = replaceImgLink(layoutContent, url);

		return layoutContent;
	}

	private String replacePageTitle(String content, String pageTitle) {
		return content.replace(PAGE_TITLE_CODE, pageTitle != null ? pageTitle : "");
	}

	private String replacePageLinkByUrl(String content) {
		int fromIndex = content.indexOf(PAGE_LINK_BY_URL_CODE, 0);
		String result = content;
		while (fromIndex > -1) {
			String url = result.substring(fromIndex + PAGE_LINK_BY_URL_CODE.length(),
					result.indexOf("')]]", fromIndex + PAGE_LINK_BY_URL_CODE.length()));

			result = StringUtils.replace(result, PAGE_LINK_BY_URL_CODE + url + "')]]", RODA_PAGE_URL
					+ generateFullRelativeUrl(CmsPage.findCmsPage(url)));
			fromIndex = result.indexOf(PAGE_LINK_BY_URL_CODE, fromIndex + PAGE_LINK_BY_URL_CODE.length());
		}

		return result;
	}

	private String replacePageUrlLink(String content) {
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
			CmsPage cmsPage = CmsPage.findCmsPage(url);

			result = result.substring(0, fromIndex) + "<a href=\"" + RODA_PAGE_URL + generateFullRelativeUrl(cmsPage)
					+ "\">" + (cmsPage != null ? cmsPage.getName() : url) + "</a>"
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
			pageContent = replacePageLinkByUrl(pageContent);
			pageContent = replaceSnippets(pageContent);

			pageContent = replacePageUrlLink(pageContent);
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

	private boolean checkFullRelativeUrl(String url) {

		// checks if the components of the url and the parent-child relationship
		// exist in the database
		if (url != null) {
			StringTokenizer tokenizer = new StringTokenizer(url, "/");
			CmsPage prevPage = null;
			while (tokenizer.hasMoreTokens()) {
				String pathUrl = tokenizer.nextToken();

				CmsPage pathPage = CmsPage.findCmsPage(pathUrl);

				if (pathPage == null) {
					return false;
				} else {
					if (pathPage.getCmsPageId() != prevPage) {
						return false;
					}
					prevPage = pathPage;
				}
			}
			return true;
		}
		return false;
	}

}
