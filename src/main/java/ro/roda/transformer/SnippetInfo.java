package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CmsLayout;
import ro.roda.domain.CmsPage;
import ro.roda.domain.CmsPageContent;
import ro.roda.domain.CmsSnippet;
import flexjson.JSONSerializer;

@Configurable
public class SnippetInfo extends SnippetList {

	public static String toJsonArr(Collection<SnippetInfo> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "leaf", "type");
		serializer.include("id", "name", "groupid", "directory", "content");

		serializer.include("snippetUsage.id", "snippetUsage.name", "snippetUsage.type");

		// serializer.transform(new FlatCmsPageTypeTransformer("pagetype"),
		// "snippetUsage.cmsPageTypeId");

		// serializer.include("snippetUsage.id", "snippetUsage.name",
		// "snippetUsage.url", "snippetUsage.visible",
		// "snippetUsage.cmsPageTypeId.id", "snippetUsage.cmsPageTypeId.name");

		// serializer.transform(new FieldNameTransformer("snippetusage"),
		// "pages");
		// serializer.transform(new FieldNameTransformer("pagetypeid"),
		// "snippetUsage.cmsPageTypeId.id");
		// serializer.transform(new FieldNameTransformer("pagetypename"),
		// "snippetUsage.cmsPageTypeId.name");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<SnippetInfo> findAllSnippetInfos() {
		List<SnippetInfo> result = new ArrayList<SnippetInfo>();

		List<CmsSnippet> snippets = CmsSnippet.findAllCmsSnippets();

		if (snippets != null && snippets.size() > 0) {

			Iterator<CmsSnippet> snippetsIterator = snippets.iterator();
			while (snippetsIterator.hasNext()) {
				CmsSnippet snippet = (CmsSnippet) snippetsIterator.next();

				result.add(new SnippetInfo(snippet));
			}
		}
		return result;
	}

	public static SnippetInfo findSnippetInfo(Integer id) {

		CmsSnippet snippet = CmsSnippet.findCmsSnippet(id);

		if (snippet != null) {
			return new SnippetInfo(snippet);
		}

		return null;
	}

	private Set<JsonInfo> snippetUsage;

	public SnippetInfo(CmsSnippet snippet) {
		super(snippet);
		this.snippetUsage = new HashSet<JsonInfo>();

		String snippetContent = snippet.getSnippetContent();

		// search for the snippet content in all cms pages
		Iterator<CmsPage> pageIterator = CmsPage.findAllCmsPages().iterator();
		while (pageIterator.hasNext()) {
			CmsPage page = (CmsPage) pageIterator.next();
			Iterator<CmsPageContent> pageContentsIterator = page.getCmsPageContents().iterator();
			while (pageContentsIterator.hasNext()) {
				CmsPageContent pageContent = (CmsPageContent) pageContentsIterator.next();
				if (StringUtils.containsIgnoreCase(pageContent.getContentText(), "[[Snippet: " + snippet.getName())
						|| StringUtils.containsIgnoreCase(pageContent.getContentText(), snippetContent)) {
					System.out.println("Adding usage");
					this.snippetUsage.add(new JsonInfo(page.getId(), page.getName(), "page"));
				}
			}
		}

		// search for the snippet content in all cms layouts
		Iterator<CmsLayout> layoutIterator = CmsLayout.findAllCmsLayouts().iterator();
		while (layoutIterator.hasNext()) {
			CmsLayout layout = (CmsLayout) layoutIterator.next();
			if (StringUtils.containsIgnoreCase(layout.getLayoutContent(), "[[Snippet: " + snippet.getName())
					|| StringUtils.containsIgnoreCase(layout.getLayoutContent(), snippetContent)) {
				this.snippetUsage.add(new JsonInfo(layout.getId(), layout.getName(), "layout"));
			}
		}

		// search for the snippet content in all cms snippets
		Iterator<CmsSnippet> snippetIterator = CmsSnippet.findAllCmsSnippets().iterator();
		while (snippetIterator.hasNext()) {
			CmsSnippet otherSnippet = (CmsSnippet) snippetIterator.next();
			if (otherSnippet.getId() != snippet.getId()
					&& (StringUtils.containsIgnoreCase(otherSnippet.getSnippetContent(),
							"[[Snippet: " + snippet.getName()) || StringUtils.containsIgnoreCase(
							otherSnippet.getSnippetContent(), snippetContent.toLowerCase()))) {
				this.snippetUsage.add(new JsonInfo(otherSnippet.getId(), otherSnippet.getName(), "snippet"));
			}
		}
	}

	public Set<JsonInfo> getSnippetUsage() {
		return snippetUsage;
	}

	public void setSnippetUsage(Set<JsonInfo> usage) {
		this.snippetUsage = usage;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "leaf", "type");
		serializer.include("id", "name", "groupid", "directory", "content");

		serializer.include("snippetUsage.id", "snippetUsage.name", "snippetUsage.type");

		// serializer.transform(new FieldNameTransformer("snippetusage"),
		// "pages");
		// serializer.transform(new FieldNameTransformer("pagetypeid"),
		// "snippetUsage.cmsPageTypeId.id");
		// serializer.transform(new FieldNameTransformer("pagetypename"),
		// "snippetUsage.cmsPageTypeId.name");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
