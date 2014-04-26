package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CmsSnippetGroup;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class SnippetGroupInfo extends SnippetList {

	public static String toJsonArr(Collection<SnippetGroupInfo> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "expanded", "type");
		serializer.include("id", "name", "pagesnumber", "groupid", "itemtype", "directory", "content", "leaf",
				"itemtype", "iconCls");

		serializer.transform(new FieldNameTransformer("snippetsnumber"), "pagesnumber");
		serializer.transform(new FieldNameTransformer("description"), "content");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<SnippetGroupInfo> findAllSnippetGroupInfos() {
		List<SnippetGroupInfo> result = new ArrayList<SnippetGroupInfo>();

		List<CmsSnippetGroup> snippetGroups = CmsSnippetGroup.findAllCmsSnippetGroups();

		if (snippetGroups != null && snippetGroups.size() > 0) {

			Iterator<CmsSnippetGroup> snippetGroupsIterator = snippetGroups.iterator();
			while (snippetGroupsIterator.hasNext()) {
				CmsSnippetGroup snippetGroup = (CmsSnippetGroup) snippetGroupsIterator.next();

				Integer parentId = snippetGroup.getParentId() == null ? null : snippetGroup.getParentId().getId();

				result.add(new SnippetGroupInfo(snippetGroup.getId(), snippetGroup.getName(), snippetGroup
						.getCmsSnippets().size(), parentId, getSnippetGroupPath(snippetGroup), snippetGroup
						.getDescription(), (snippetGroup.getCmsSnippets().size() == 0 && snippetGroup
						.getCmsSnippetGroups().size() == 0)));
			}
		}

		return result;
	}

	public static SnippetGroupInfo findSnippetGroupInfo(Integer id) {

		CmsSnippetGroup snippetGroup = CmsSnippetGroup.findCmsSnippetGroup(id);
		boolean leaf;
		int snippetsNumber;

		if (snippetGroup != null) {
			snippetsNumber = snippetGroup.getCmsSnippets().size();
			leaf = (snippetsNumber == 0 && snippetGroup.getCmsSnippetGroups().size() == 0);

			return new SnippetGroupInfo(snippetGroup.getId(), snippetGroup.getName(), snippetsNumber,
					snippetGroup.getParentId() == null ? null : snippetGroup.getParentId().getId(),
					getSnippetGroupPath(snippetGroup), snippetGroup.getDescription(), leaf);
		}
		return null;
	}

	boolean leaf;

	Integer snippetsnumber;

	public SnippetGroupInfo(Integer id, String name, Integer snippetnumber, Integer groupid, String directory,
			String content, boolean leaf) {
		super(id, name, groupid, directory, null, "snippetgroup", "snippetgroup");
		setContent(content);
		setLeaf(leaf);
		setSnippetsnumber(snippetnumber);
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public Integer getSnippetsnumber() {
		return snippetsnumber;
	}

	public void setSnippetsnumber(Integer snippetsnumber) {
		this.snippetsnumber = snippetsnumber;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "expanded", "type");
		serializer.include("id", "name", "pagesnumber", "groupid", "itemtype", "directory", "content", "leaf",
				"itemtype", "iconCls");

		serializer.transform(new FieldNameTransformer("snippetsnumber"), "pagesnumber");
		serializer.transform(new FieldNameTransformer("description"), "content");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
