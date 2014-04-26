package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CmsFolder;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class FolderInfo extends FileList {

	public static String toJsonArr(Collection<FolderInfo> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "expanded", "leaf", "alias", "filesize", "filetype");
		serializer.include("id", "name", "filesnumber", "folderid", "type", "directory");

		serializer.transform(new FieldNameTransformer("itemtype"), "type");
		serializer.transform(new FieldNameTransformer("indice"), "id");
		serializer.transform(new FieldNameTransformer("groupid"), "folderid");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<FolderInfo> findAllFolderInfos() {
		List<FolderInfo> result = new ArrayList<FolderInfo>();

		List<CmsFolder> folders = CmsFolder.findAllCmsFolders();

		if (folders != null && folders.size() > 0) {

			Iterator<CmsFolder> foldersIterator = folders.iterator();
			while (foldersIterator.hasNext()) {
				CmsFolder folder = (CmsFolder) foldersIterator.next();

				Integer parentId = folder.getParentId() == null ? null : folder.getParentId().getId();

				result.add(new FolderInfo(folder.getId(), folder.getName(), folder.getCmsFiles().size(), parentId,
						"folder", getFolderPath(folder)));
			}
		}

		return result;
	}

	public static FolderInfo findFolderInfo(Integer id) {
		CmsFolder folder = CmsFolder.findCmsFolder(id);
		if (folder != null) {
			int filesNumber = folder.getCmsFiles().size();

			return new FolderInfo(id, folder.getName(), filesNumber, folder.getParentId() == null ? null : folder
					.getParentId().getId(), "folder", getFolderPath(folder));
		}
		return null;
	}

	private boolean expanded = true;

	private Integer filesnumber;

	public FolderInfo(Integer id, String name, Integer filenumber, Integer groupid, String itemtype, String directory) {
		super(id, name, null, null, null, groupid, directory, "folder");
		setFilesnumber(filenumber);
		setType(itemtype);
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public Integer getFilesnumber() {
		return filesnumber;
	}

	public void setFilesnumber(Integer filesnumber) {
		this.filesnumber = filesnumber;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "expanded", "leaf", "alias", "filesize", "filetype");
		serializer.include("id", "name", "filesnumber", "folderid", "type", "directory");

		serializer.transform(new FieldNameTransformer("itemtype"), "type");
		serializer.transform(new FieldNameTransformer("indice"), "id");
		serializer.transform(new FieldNameTransformer("groupid"), "folderid");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
