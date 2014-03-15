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

	private static String PAGE_TITLE_CODE = "[[Code: PageTitle]]";
	private static String PAGE_LINK_BY_URL_CODE = "[[Code: PageLinkbyUrl('";
	private static String PAGE_URL_LINK_CODE = "[[PageURLLink:";
	private static String FILE_URL_LINK_CODE = "[[FileURL:";
	private static String IMG_LINK_CODE = "[[ImgLink: ";
	private static String SNIPPET_CODE = "[[Snippet: ";
	private static String PAGE_CONTENT_CODE = "[[Code: PageContent]]";

	public String generatePage(String url) {
		CmsPage page = CmsPage.findCmsPage(url);
		StringBuilder sb = new StringBuilder();

		String pageContent = replacePageContent(getLayout(page, url), page);

		// pageContent = StringUtils.replace(pageContent, "\\", "\\\\");
		// pageContent = StringUtils.replace(pageContent, "\"", "\\\"");

		sb.append(pageContent);

		return sb.toString();
	}

	private String getLayout(CmsPage cmsPage, String url) {
		CmsLayout pageLayout = cmsPage.getCmsLayoutId();
		String layoutContent = pageLayout.getLayoutContent();

		layoutContent = replacePageTitle(layoutContent, cmsPage.getMenuTitle());
		layoutContent = replacePageLinkByUrl(layoutContent, url);

		layoutContent = replaceSnippets(layoutContent);
		layoutContent = replacePageUrlLink(layoutContent);
		layoutContent = replaceFileUrl(layoutContent, url);
		layoutContent = replaceImgLink(layoutContent, url);

		return layoutContent;
	}

	private String replacePageTitle(String content, String pageTitle) {
		return content.replace(PAGE_TITLE_CODE, pageTitle != null ? pageTitle : "");
	}

	private String replacePageLinkByUrl(String content, String pageUrl) {
		int fromIndex = content.indexOf(PAGE_LINK_BY_URL_CODE, 0);
		String result = content;
		while (fromIndex > -1) {
			String url = result.substring(fromIndex + PAGE_LINK_BY_URL_CODE.length(),
					result.indexOf("')]]", fromIndex + PAGE_LINK_BY_URL_CODE.length()));
			CmsPage cmsPage = CmsPage.findCmsPage(url);

			String modifiedUrl = "";
			String relativeUrl = pageUrl;
			int depth = 0;
			while (cmsPage == null && depth < StringUtils.countMatches(pageUrl, "/")) {
				// try to build a URL relative to the one of the page
				modifiedUrl = relativeUrl + "/" + url;
				cmsPage = CmsPage.findCmsPage(modifiedUrl);

				if (cmsPage == null) {
					relativeUrl = relativeUrl.substring(0, relativeUrl.lastIndexOf("/"));
				}
				depth++;
			}

			result = StringUtils.replace(result, PAGE_LINK_BY_URL_CODE + url + "')]]", "/roda/page" + modifiedUrl);
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
			result = result.substring(0, fromIndex) + "<a href=\"" + "/roda/page" + url + "\">" + cmsPage.getName()
					+ "</a>"
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
			for (int i = 0; i < StringUtils.countMatches(url, "/"); i++) {
				relativePath.append("../");
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
			for (int i = 0; i < StringUtils.countMatches(url, "/"); i++) {
				relativePath.append("../");
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
			pageContent = replacePageLinkByUrl(pageContent, page.getUrl());
			pageContent = replaceSnippets(pageContent);

			pageContent = replacePageUrlLink(pageContent);
			pageContent = replaceFileUrl(pageContent, page.getUrl());
			pageContent = replaceImgLink(pageContent, page.getUrl());

			result = result.replace(PAGE_CONTENT_CODE, pageContent);
		}
		return result;
	}

}
