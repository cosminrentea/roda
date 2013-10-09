package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CmsFolder;
import flexjson.JSONSerializer;

@Configurable
public class FolderTree extends FileList {

	public static String toJsonArray(Collection<FolderTree> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth");
		serializer.include("id", "name", "expanded");

		int maxDepth = 0;
		for (FolderTree fileTree : collection) {
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
			serializer.exclude(includeData + ".depth", includeData + ".alias", includeData + ".directory", includeData
					+ ".filesize", includeData + ".folderid");
			serializer.include(includeData + ".name", includeData + ".id", includeData + ".expanded", includeData
					+ ".filetype");
			serializer.transform(new FieldNameTransformer("indice"), includeData + ".id");
		}

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"children\":[{\"name\":\"Root\",\"indice\":\"0\",\"expanded\":\"true\",\"children\":"
				+ serializer.serialize(collection) + "}]}";
	}

	public static List<FolderTree> findAllFolderTrees() {
		List<FolderTree> result = new ArrayList<FolderTree>();

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

	public static FolderTree findFolderTree(Integer id) {
		FolderTree result = null;
		CmsFolder folder = CmsFolder.findCmsFolder(id);

		if (folder != null) {
			result = new FolderTree(folder);
			result.setName(folder.getName());

			Set<FileList> dataByFolderSet = null;

			Set<CmsFolder> children = folder.getCmsFolders();

			int maxDepth = 0;
			if (children != null && children.size() > 0) {
				dataByFolderSet = new HashSet<FileList>();

				Iterator<CmsFolder> childrenIterator = children.iterator();
				while (childrenIterator.hasNext()) {
					CmsFolder childCmsFolder = childrenIterator.next();
					FolderTree folderTree = findFolderTree(childCmsFolder.getId());
					dataByFolderSet.add(folderTree);
					if (maxDepth < folderTree.getDepth()) {
						maxDepth = folderTree.getDepth();
					}
				}
				result.setDepth(maxDepth + 1);
			}

			result.setChildren(dataByFolderSet);
		}
		return result;

	}

	private Set<FileList> children;

	private boolean expanded = true;

	private int depth;

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

	public FolderTree(CmsFolder folder) {
		super(folder.getId(), folder.getName(), null, null, "folder", null, null);

		this.children = new HashSet<FileList>();
	}

	public FolderTree(CmsFolder folder, Set<FileList> children) {
		this(folder);
		this.children = children;
	}

	public FolderTree(CmsFolder folder, String name, Set<FileList> children) {
		this(folder, children);
		this.setName(name);
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "depth");
		serializer.include("id", "name", "expanded");

		String includeData = "";
		for (int i = 0; i < getDepth() + 1; i++) {
			if (i > 0) {
				includeData += ".";
			}
			includeData += "children";
			serializer.exclude(includeData + ".depth", includeData + ".alias", includeData + ".directory", includeData
					+ ".filesize", includeData + ".folderid");
			serializer.include(includeData + ".name", includeData + ".id", includeData + ".expanded", includeData
					+ ".filetype");
			serializer.transform(new FieldNameTransformer("indice"), includeData + ".id");
		}

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"children\":[{\"name\":\"Root\",\"indice\":\"0\",\"expanded\":\"true\",\"children\":"
				+ serializer.serialize(this) + "}]}";
	}
}
