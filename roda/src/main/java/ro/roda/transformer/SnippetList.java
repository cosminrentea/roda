package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CmsSnippet;
import ro.roda.domain.CmsSnippetGroup;
import flexjson.JSONSerializer;

@Configurable
public class SnippetList extends JsonInfo {

	public static String toJsonArray(Collection<SnippetList> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.include("id", "name", "groupid", "content", "directory");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<SnippetList> findAllSnippetLists() {
		List<SnippetList> result = new ArrayList<SnippetList>();

		List<CmsSnippet> snippets = CmsSnippet.findAllCmsSnippets();

		if (snippets != null && snippets.size() > 0) {

			Iterator<CmsSnippet> snippetsIterator = snippets.iterator();
			while (snippetsIterator.hasNext()) {
				CmsSnippet snippet = (CmsSnippet) snippetsIterator.next();

				Integer groupId = snippet.getCmsSnippetGroupId() == null ? null : snippet.getCmsSnippetGroupId()
						.getId();

				result.add(new SnippetList(snippet.getId(), snippet.getName(), groupId, snippet.getSnippetContent(),
						getSnippetPath(snippet), null));
			}
		}

		return result;
	}

	public static String getSnippetPath(CmsSnippet snippet) {
		if (snippet.getCmsSnippetGroupId() != null) {
			return getSnippetGroupPath(snippet.getCmsSnippetGroupId()) + "/" + snippet.getName();
		} else {
			return snippet.getName();
		}
	}

	public static String getSnippetGroupPath(CmsSnippetGroup snippetGroup) {
		if (snippetGroup.getParentId() != null) {
			return getSnippetGroupPath(snippetGroup.getParentId()) + "/" + snippetGroup.getName();
		} else {
			return snippetGroup.getName();
		}
	}

	public static SnippetList findSnippetList(Integer id) {
		CmsSnippet snippet = CmsSnippet.findCmsSnippet(id);

		if (snippet != null) {
			return new SnippetList(snippet.getId(), snippet.getName(), snippet.getCmsSnippetGroupId() != null ? snippet
					.getCmsSnippetGroupId().getId() : null, snippet.getSnippetContent(), getSnippetPath(snippet), null);
		}
		return null;
	}

	private Integer id;

	private String name;

	private Integer groupid;

	private String content;

	private String directory;

	// TODO: configure
	private Integer n;

	public SnippetList(Integer id, String name, Integer groupid, String directory, String content, Integer n) {
		this.id = id;
		this.name = name;
		this.groupid = groupid;
		this.directory = directory;
		this.content = content;
		this.n = n;
	}

	public SnippetList(CmsSnippet snippet) {
		this(snippet.getId(), snippet.getName(), snippet.getCmsSnippetGroupId().getId(), getSnippetPath(snippet),
				snippet.getSnippetContent(), null);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.include("id", "name", "groupid", "content", "directory");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
