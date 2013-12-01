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

		serializer.exclude("*.class", "type");
		serializer.include("id", "name", "groupid", "content", "directory");

		serializer.transform(new FieldNameTransformer("indice"), "id");

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

				// SnippetList newSnippetList = new SnippetList(snippet.getId(),
				// snippet.getName(), groupId,
				// getSnippetPath(snippet), null, "snippet");
				// newSnippetList.setContent(snippet.getSnippetContent());
				result.add(new SnippetList(snippet));
			}
		}

		return result;
	}

	public static String getSnippetPath(CmsSnippet snippet) {
		if (snippet.getCmsSnippetGroupId() != null) {
			return getSnippetGroupPath(snippet.getCmsSnippetGroupId());
		} else {
			return null; // snippet.getName();
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
			// SnippetList newSnippetList = new SnippetList(snippet.getId(),
			// snippet.getName(),
			// snippet.getCmsSnippetGroupId() != null ?
			// snippet.getCmsSnippetGroupId().getId() : null,
			// getSnippetPath(snippet), null, "snippet");
			// newSnippetList.setContent(snippet.getSnippetContent());
			return new SnippetList(snippet);
		}
		return null;
	}

	private Integer id;

	private String name;

	private Integer groupid;

	private String content;

	private String directory;

	private String itemtype;

	private boolean leaf;

	// TODO: configure
	private Integer n;

	private String iconCls;

	public SnippetList(Integer id, String name, Integer groupid, String directory, Integer n, String itemtype,
			String iconCls) {
		this.id = id;
		this.name = name;
		this.groupid = groupid;
		this.directory = directory;
		this.n = n;
		this.itemtype = itemtype;
		this.iconCls = iconCls;
	}

	public SnippetList(CmsSnippet snippet) {
		this(snippet.getId(), snippet.getName(), snippet.getCmsSnippetGroupId() == null ? null : snippet
				.getCmsSnippetGroupId().getId(), getSnippetPath(snippet), 200, "snippet", "snippet");
		this.content = snippet.getSnippetContent();
		this.leaf = true;
	}

	public SnippetList(CmsSnippetGroup snippetGroup) {
		this(snippetGroup.getId(), snippetGroup.getName(), snippetGroup.getParentId() == null ? null : snippetGroup
				.getParentId().getId(), getSnippetGroupPath(snippetGroup), 200, "snippetgroup", "snippetgroup");
		if ((snippetGroup.getCmsSnippetGroups() != null && snippetGroup.getCmsSnippetGroups().size() > 0)
				|| (snippetGroup.getCmsSnippets() != null && snippetGroup.getCmsSnippets().size() > 0)) {
			this.leaf = false;
		} else {
			this.leaf = true;
		}
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

	public String getItemtype() {
		return itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name", "groupid", "content", "directory");

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
