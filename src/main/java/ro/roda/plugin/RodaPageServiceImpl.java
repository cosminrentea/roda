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

		sb.append(replacePageContent(getLayout(page), page));

		return sb.toString();
	}

	private String getLayout(CmsPage cmsPage) {
		CmsLayout pageLayout = cmsPage.getCmsLayoutId();
		String layoutContent = pageLayout.getLayoutContent();

		layoutContent = replacePageTitle(layoutContent, cmsPage.getMenuTitle());
		layoutContent = replacePageLinkByUrl(layoutContent);
		layoutContent = replaceFileUrl(layoutContent);

		layoutContent = replaceSnippets(layoutContent);

		return layoutContent;
	}

	private String replacePageTitle(String content, String pageTitle) {
		return content.replace("[[Code:PageTitle]]", pageTitle);
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

	private String replaceFileUrl(String content) {
		int fromIndex = content.indexOf("[[FileURL:", 0);
		String result = content;
		while (fromIndex > -1) {
			String faviconpng = result.substring(fromIndex + "[[FileURL:".length(),
					result.indexOf("]]", fromIndex + "[[FileURL:".length()));
			result = result.replaceAll("[[FileURL:" + faviconpng + "]]", CmsFile.findCmsFile(faviconpng).getUrl());
			fromIndex = result.indexOf("[[FileURL:", fromIndex);
		}
		return result;
	}

	private String replaceSnippets(String content) {
		String snippetsReplaced = content;

		int index = 0;
		while ((index = snippetsReplaced.indexOf("[[Snippet:", index)) > -1) {
			int snippetNameIndex = index + "[[Snippet:".length();
			String snippetName = snippetsReplaced.substring(snippetNameIndex,
					snippetsReplaced.indexOf("]]", snippetNameIndex));

			String snippetContent = replaceSnippets(CmsSnippet.findCmsSnippet(snippetName).getSnippetContent());
			snippetsReplaced = snippetsReplaced.replace("[[Snippet:" + snippetName + "]]", snippetContent);
		}

		return snippetsReplaced;
	}

	private String replacePageContent(String content, CmsPage page) {
		String result = content;
		if (result.indexOf("[[PageContent]]") > -1) {
			// We suppose that a CmsPage has a single CmsPageContent
			String pageContent = page.getCmsPageContents().iterator().next().getContentText();
			pageContent = replacePageTitle(pageContent, page.getMenuTitle());
			pageContent = replacePageLinkByUrl(pageContent);
			pageContent = replaceFileUrl(pageContent);
			pageContent = replaceSnippets(pageContent);

			result.replace("[[PageContent]]", pageContent);
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
