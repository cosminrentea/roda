package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CmsFile;
import ro.roda.domain.CmsFolder;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class FileTree extends FileList {

	public static String toJsonArr(Collection<FileTree> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth", "type");
		serializer.include("id", "name", "expanded", "iconCls");

		int maxDepth = 0;
		for (FileTree fileTree : collection) {
			if (maxDepth < fileTree.getDepth()) {
				maxDepth = fileTree.getDepth();
			}
		}

		String includeData = "";
		for (int i = 0; i < maxDepth + 1; i++) {
			if (i > 0) {
				includeData += ".";
			}
			includeData += "children";
			serializer.exclude(includeData + ".depth", includeData + ".folderid", includeData + ".directory",
					includeData + ".type");
			serializer.include(includeData + ".name", includeData + ".id", includeData + ".alias", includeData
					+ ".filesize", includeData + ".filetype", includeData + ".leaf", includeData + ".iconCls");
			serializer.transform(new FieldNameTransformer("indice"), includeData + ".id");
		}

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"children\":[{\"name\":\"Root\",\"indice\":\"0\",\"expanded\":\"true\",\"children\":"
				+ serializer.serialize(collection) + "}]}";
	}

	public static List<FileTree> findAllFileTrees() {
		List<FileTree> result = new ArrayList<FileTree>();

		List<CmsFolder> folders = CmsFolder.entityManager()
				.createQuery("SELECT o FROM CmsFolder o WHERE o.parentId IS NULL", CmsFolder.class).getResultList();

		if (folders != null && folders.size() > 0) {
			Iterator<CmsFolder> foldersIterator = folders.iterator();

			while (foldersIterator.hasNext()) {
				CmsFolder folder = (CmsFolder) foldersIterator.next();

				result.add(findFolderTree(folder.getId()));
			}
		}
		return result;
	}

	public static FileTree findFolderTree(Integer id) {
		FileTree result = null;
		CmsFolder folder = CmsFolder.findCmsFolder(id);

		if (folder != null) {
			result = new FileTree(folder);
			result.setName(folder.getName());

			Set<FileList> dataByFolderSet = null;

			Set<CmsFolder> children = folder.getCmsFolders();
			Set<CmsFile> files = folder.getCmsFiles();

			int maxDepth = 0;
			if (children != null && children.size() > 0) {
				dataByFolderSet = new HashSet<FileList>();

				Iterator<CmsFolder> childrenIterator = children.iterator();
				while (childrenIterator.hasNext()) {
					CmsFolder childCmsFolder = childrenIterator.next();
					FileTree fileTree = findFolderTree(childCmsFolder.getId());
					dataByFolderSet.add(fileTree);
					if (maxDepth < fileTree.getDepth()) {
						maxDepth = fileTree.getDepth();
					}
				}
				result.setDepth(maxDepth + 1);
			}

			if (files != null && files.size() > 0) {
				if (dataByFolderSet == null) {
					dataByFolderSet = new HashSet<FileList>();
				}

				Iterator<CmsFile> filesIterator = files.iterator();
				while (filesIterator.hasNext()) {
					dataByFolderSet.add(new FileList(filesIterator.next()));
				}
			}
			result.setChildren(dataByFolderSet);
		}
		return result;

	}

	private Set<FileList> children;

	private int depth;

	private boolean expanded = true;

	public Set<FileList> getChildren() {
		return children;
	}

	public void setChildren(Set<FileList> children) {
		this.children = children;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public FileTree(CmsFolder folder) {
		super(folder.getId(), folder.getName(), null, null, "folder", null, null, "folder");

		this.children = new HashSet<FileList>();
		// TODO set leaf
		setLeaf(false);
	}

	public FileTree(CmsFolder folder, Set<FileList> children) {
		this(folder);
		this.children = children;
	}

	public FileTree(CmsFolder folder, String name, Set<FileList> children) {
		this(folder, children);
		this.setName(name);
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth", "type");
		serializer.include("id", "name", "expanded", "iconCls");

		String includeData = "";
		for (int i = 0; i < getDepth() + 1; i++) {
			if (i > 0) {
				includeData += ".";
			}
			includeData += "children";
			serializer.exclude(includeData + ".depth");
			serializer.exclude(includeData + ".depth", includeData + ".folderid", includeData + ".directory",
					includeData + ".type");
			serializer.include(includeData + ".name", includeData + ".id", includeData + ".alias", includeData
					+ ".filesize", includeData + ".filetype", includeData + ".leaf", includeData + ".iconCls");
		}

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"children\":[{\"name\":\"Root\",\"indice\":\"0\",\"expanded\":\"true\",\"children\":"
				+ serializer.serialize(this) + "}]}";
	}
}
