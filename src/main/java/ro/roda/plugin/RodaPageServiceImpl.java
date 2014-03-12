package ro.roda.plugin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.CmsFile;
import ro.roda.domain.CmsLayout;
import ro.roda.domain.CmsPage;
import ro.roda.domain.CmsSnippet;

@Service
@Transactional
public class RodaPageServiceImpl implements RodaPageService {

	public String generatePage(String url) {
		CmsPage page = CmsPage.findCmsPage(url);
		StringBuilder sb = new StringBuilder();

		String pageContent = replacePageContent(getLayout(page), page);
		pageContent = pageContent.replaceAll("\\", "\\\\").replaceAll("\"", "\\\"");
		System.out.println("Escaped quotes:\n" + pageContent);

		sb.append(pageContent);

		return sb.toString();
	}

	private String getLayout(CmsPage cmsPage) {
		CmsLayout pageLayout = cmsPage.getCmsLayoutId();
		String layoutContent = pageLayout.getLayoutContent();

		layoutContent = replacePageTitle(layoutContent, cmsPage.getMenuTitle());
		layoutContent = replacePageLinkByUrl(layoutContent);

		layoutContent = replaceSnippets(layoutContent);
		layoutContent = replacePageUrlLink(layoutContent);
		layoutContent = replaceFileUrl(layoutContent);
		layoutContent = replaceImgLink(layoutContent);

		return layoutContent;
	}

	private String replacePageTitle(String content, String pageTitle) {
		return content.replace("[[Code: PageTitle]]", pageTitle != null ? pageTitle : "");
	}

	private String replacePageLinkByUrl(String content) {
		int fromIndex = content.indexOf("[[Code: PageLinkbyUrl('", 0);
		String result = content;
		while (fromIndex > -1) {
			String name = result.substring(fromIndex + "[[Code: PageLinkbyUrl('".length(),
					result.indexOf("']]", fromIndex + "[[Code: PageLinkbyUrl('".length()));
			result = result.replaceAll("[[Code: PageLinkbyUrl('" + name + "']]", CmsFile.findCmsFile(name).getUrl());
			fromIndex = result.indexOf("[[Code: PageLinkbyUrl('", fromIndex);
		}
		return result;
	}

	private String replacePageUrlLink(String content) {
		int fromIndex = content.indexOf("[[PageURLLink:", 0);
		String result = content;
		while (fromIndex > -1) {
			String url = result.substring(fromIndex + "[[PageURLLink:".length(),
					result.indexOf("]]", fromIndex + "[[PageURLLink:".length()));
			System.out.println("URL = " + url);
			// The following code has issues due to the interference with
			// regular expressions syntax:
			// result = result.replaceAll("[[PageURLLink:" + url + "]]",
			// "<a href=\"" + url + "\">" + CmsPage.findCmsPage(url).getName() +
			// "</a>");
			result = result.substring(0, fromIndex) + "<a href=\"" + url + "\">" + CmsPage.findCmsPage(url).getName()
					+ "</a>"
					+ result.substring(result.indexOf("]]", fromIndex + "[[PageURLLink:".length()) + "]]".length());
			fromIndex = result.indexOf("[[PageURLLink:", fromIndex + "[[PageURLLink:".length());
		}
		return result;
	}

	private String replaceFileUrl(String content) {
		int fromIndex = content.indexOf("[[FileURL:", 0);
		String result = content;
		while (fromIndex > -1) {
			String alias = result.substring(fromIndex + "[[FileURL:".length(),
					result.indexOf("]]", fromIndex + "[[FileURL:".length()));
			CmsFile cmsFile = CmsFile.findCmsFile(alias);

			result = result.substring(0, fromIndex) + (cmsFile != null ? cmsFile.getUrl() : "")
					+ result.substring(result.indexOf("]]", fromIndex + "[[FileURL:".length()) + "]]".length());
			fromIndex = result.indexOf("[[FileURL:", fromIndex + "[[FileURL:".length());
		}
		return result;
	}

	private String replaceImgLink(String content) {
		int fromIndex = content.indexOf("[[ImgLink: ", 0);
		String result = content;
		while (fromIndex > -1) {
			String alias = result.substring(fromIndex + "[[ImgLink: ".length(),
					result.indexOf("]]", fromIndex + "[[ImgLink: ".length()));
			CmsFile cmsFile = CmsFile.findCmsFile(alias);

			result = result.substring(0, fromIndex) + "<img src=\"" + (cmsFile != null ? cmsFile.getUrl() : "")
					+ "\" />"
					+ result.substring(result.indexOf("]]", fromIndex + "[[ImgLink: ".length()) + "]]".length());
			fromIndex = result.indexOf("[[ImgLink: ", fromIndex + "[[ImgLink: ".length());
		}
		return result;
	}

	private String replaceSnippets(String content) {
		String snippetsReplaced = content;

		int index = 0;
		while ((index = snippetsReplaced.indexOf("[[Snippet: ", index)) > -1) {
			int snippetNameIndex = index + "[[Snippet: ".length();
			String snippetName = snippetsReplaced.substring(snippetNameIndex,
					snippetsReplaced.indexOf("]]", snippetNameIndex));

			String snippetContent = replaceSnippets(CmsSnippet.findCmsSnippet(snippetName).getSnippetContent());
			snippetsReplaced = snippetsReplaced.replace("[[Snippet: " + snippetName + "]]", snippetContent);
		}

		return snippetsReplaced;
	}

	private String replacePageContent(String content, CmsPage page) {
		String result = content;
		if (result.indexOf("[[Code: PageContent]]") > -1) {
			// We suppose that a CmsPage has a single CmsPageContent
			String pageContent = page.getCmsPageContents().iterator().next().getContentText();
			pageContent = replacePageTitle(pageContent, page.getMenuTitle());
			pageContent = replacePageLinkByUrl(pageContent);
			pageContent = replaceFileUrl(pageContent);
			pageContent = replaceSnippets(pageContent);

			result = result.replace("[[Code: PageContent]]", pageContent);
		}
		return result;
	}

	// private String mergeLayoutAndContents(CmsPage cmsPage) {
	//
	// if (cmsPage.getCmsPageContents() != null) {
	//
	// // TODO: multiple page contents
	// List<CmsPageContent> unusedContents = new
	// ArrayList<CmsPageContent>(cmsPage.getCmsPageContents());
	//
	// CmsLayout pageLayout = cmsPage.getCmsLayoutId();
	// String pageLayoutContent = "";
	//
	// if (pageLayout != null) {
	// pageLayoutContent = pageLayout.getLayoutContent();
	// pageLayoutContent = pageLayoutContent.replaceAll("<html>", "");
	// pageLayoutContent = pageLayoutContent.replaceAll("</html>", "");
	// for (int i = 0; i < unusedContents.size(); i++) {
	// CmsPageContent unusedContent = unusedContents.get(i);
	// String searchIdString = "[[" + unusedContent.getId() + "]]";
	// if (pageLayoutContent.indexOf(searchIdString) > -1) {
	// pageLayoutContent.replaceAll(searchIdString,
	// unusedContent.getContentText());
	// unusedContents.remove(i);
	// i--;
	// }
	// }
	// }
	//
	// // for the contents that were not referred in layout
	// for (int i = 0; i < unusedContents.size(); i++) {
	// CmsPageContent unusedContent = unusedContents.get(i);
	// pageLayoutContent += unusedContent.getContentText();
	// }
	//
	// return pageLayoutContent;
	// }
	// return "";
	// }

}
