package ro.roda.transformer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CmsFile;
import ro.roda.domain.CmsFolder;
import ro.roda.domain.CmsLayout;
import ro.roda.domain.CmsLayoutGroup;
import ro.roda.domain.CmsSnippet;
import ro.roda.domain.CmsSnippetGroup;
import ro.roda.domain.Users;
import flexjson.JSONSerializer;

@Configurable
public class AdminJson {

	public static String toJsonArray(Collection<AdminJson> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");

		return serializer.serialize(collection);
	}

	public static AdminJson findLogin(String username, String password) {
		if (username == null)
			return null;

		EntityManager em = Users.entityManager();
		TypedQuery<Users> q = em
				.createQuery(
						"SELECT o FROM Users AS o WHERE LOWER(o.username) LIKE LOWER(:username)  AND LOWER(o.password) LIKE LOWER(:password)  AND o.enabled = true",
						Users.class);
		q.setParameter("username", username);
		q.setParameter("password", password);

		List<Users> queryResult = q.getResultList();
		if (queryResult.size() > 0) {
			return new AdminJson(true, "Auth ok");
		} else {
			return new AdminJson(false, "Auth failed");
		}
	}

	public static AdminJson layoutGroupSave(String groupname, Integer parentId, String description) {
		if (groupname == null)
			return new AdminJson(false, "The layout group must gave a name.");
		;

		CmsLayoutGroup layoutGroup = new CmsLayoutGroup();
		layoutGroup.setName(groupname);
		layoutGroup.setDescription(description);

		CmsLayoutGroup parentGroup = null;
		if (parentId != null) {
			parentGroup = CmsLayoutGroup.findCmsLayoutGroup(parentId);
			if (parentGroup != null) {
				layoutGroup.setParentId(parentGroup);
				parentGroup.getCmsLayoutGroups().add(layoutGroup);
			}
		}
		try {
			CmsLayoutGroup.entityManager().persist(layoutGroup);
			if (parentGroup != null) {
				CmsLayoutGroup.entityManager().persist(parentGroup);
			}
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Layout group not created " + e.getMessage());
		}

		return new AdminJson(true, "Layout group created successfully");
	}

	public static AdminJson layoutGroupEmpty(Integer groupId) {
		CmsLayoutGroup layoutGroup = CmsLayoutGroup.findCmsLayoutGroup(groupId);
		if (layoutGroup == null)
			return new AdminJson(false, "The layout group does not exist.");

		Set<CmsLayoutGroup> childGroups = layoutGroup.getCmsLayoutGroups();
		Iterator<CmsLayoutGroup> childGroupsIterator = childGroups.iterator();
		while (childGroupsIterator.hasNext()) {
			CmsLayoutGroup childGroup = childGroupsIterator.next();
			childGroup.setParentId(null);
			CmsLayoutGroup.entityManager().persist(childGroup);
		}

		Set<CmsLayout> childLayouts = layoutGroup.getCmsLayouts();
		Iterator<CmsLayout> childLayoutsIterator = childLayouts.iterator();
		while (childLayoutsIterator.hasNext()) {
			CmsLayout childLayout = childLayoutsIterator.next();
			childLayout.setCmsLayoutGroupId(null);
			CmsLayout.entityManager().persist(childLayout);
		}

		layoutGroup.setCmsLayoutGroups(new HashSet<CmsLayoutGroup>());
		layoutGroup.setCmsLayouts(new HashSet<CmsLayout>());

		try {
			CmsLayoutGroup.entityManager().persist(layoutGroup);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Layout group not emptied " + e.getMessage());
		}

		return new AdminJson(true, "CMS Layout group emptied successfully");
	}

	public static AdminJson layoutGroupDrop(Integer groupId) {
		CmsLayoutGroup layoutGroup = CmsLayoutGroup.findCmsLayoutGroup(groupId);
		if (layoutGroup == null)
			return new AdminJson(false, "The layout group does not exist.");

		Set<CmsLayoutGroup> childGroups = layoutGroup.getCmsLayoutGroups();
		Iterator<CmsLayoutGroup> childGroupsIterator = childGroups.iterator();
		while (childGroupsIterator.hasNext()) {
			CmsLayoutGroup childGroup = childGroupsIterator.next();
			childGroup.setParentId(null);
			CmsLayoutGroup.entityManager().persist(childGroup);
		}

		Set<CmsLayout> childLayouts = layoutGroup.getCmsLayouts();
		Iterator<CmsLayout> childLayoutsIterator = childLayouts.iterator();
		while (childLayoutsIterator.hasNext()) {
			CmsLayout childLayout = childLayoutsIterator.next();
			childLayout.setCmsLayoutGroupId(null);
			CmsLayout.entityManager().persist(childLayout);
		}

		try {
			CmsLayoutGroup.entityManager().remove(layoutGroup);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Layout group not dropped " + e.getMessage());
		}

		return new AdminJson(true, "CMS Layout group dropped successfully");
	}

	public static AdminJson layoutDrop(Integer layoutId) {
		CmsLayout layout = CmsLayout.findCmsLayout(layoutId);
		if (layout == null)
			return new AdminJson(false, "The layout does not exist.");
		try {
			CmsLayoutGroup parentGroup = layout.getCmsLayoutGroupId();
			if (parentGroup.getCmsLayouts() != null && parentGroup.getCmsLayouts().contains(layout)) {
				parentGroup.getCmsLayouts().remove(layout);
			}

			CmsLayout.entityManager().remove(layout);
		} catch (Exception e) {
			return new AdminJson(false, "CMS Layout not dropped" + e.getMessage());
		}

		return new AdminJson(true, "CMS Layout dropped successfully");
	}

	public static AdminJson layoutSave(Integer groupId, String content, String name, Integer layoutId) {

		CmsLayout layout = null;
		if (layoutId != null) {
			layout = CmsLayout.findCmsLayout(layoutId);
		}

		if (layout == null) {
			layout = new CmsLayout();
		}

		layout.setName(name);
		layout.setLayoutContent(content);

		CmsLayoutGroup parentGroup = CmsLayoutGroup.findCmsLayoutGroup(groupId);
		if (parentGroup != null) {
			layout.setCmsLayoutGroupId(parentGroup);
			if (parentGroup.getCmsLayouts() != null && parentGroup.getCmsLayouts().contains(layout)) {
				// do nothing
			} else {
				if (parentGroup.getCmsLayouts() == null) {
					parentGroup.setCmsLayouts(new HashSet<CmsLayout>());
					parentGroup.getCmsLayouts().add(layout);
				} else {
					parentGroup.getCmsLayouts().add(layout);
				}
				CmsLayoutGroup.entityManager().persist(parentGroup);
			}
			layout.setCmsLayoutGroupId(parentGroup);
		}

		try {
			CmsLayoutGroup.entityManager().persist(layout);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Layout not created " + e.getMessage());
		}

		return new AdminJson(true, "CMS Layout created successfully");
	}

	// Methods for Snippets
	public static AdminJson snippetGroupSave(String groupname, Integer parentId, String description) {
		if (groupname == null)
			return new AdminJson(false, "The snippet group must gave a name.");
		;

		CmsSnippetGroup snippetGroup = new CmsSnippetGroup();
		snippetGroup.setName(groupname);
		snippetGroup.setDescription(description);

		CmsSnippetGroup parentGroup = null;
		if (parentId != null) {
			parentGroup = CmsSnippetGroup.findCmsSnippetGroup(parentId);
			if (parentGroup != null) {
				snippetGroup.setParentId(parentGroup);
				parentGroup.getCmsSnippetGroups().add(snippetGroup);
			}
		}
		try {
			CmsSnippetGroup.entityManager().persist(snippetGroup);
			if (parentGroup != null) {
				CmsSnippetGroup.entityManager().persist(parentGroup);
			}
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Snippet group not created " + e.getMessage());
		}

		return new AdminJson(true, "Snippet group created successfully");
	}

	public static AdminJson snippetGroupEmpty(Integer groupId) {
		CmsSnippetGroup snippetGroup = CmsSnippetGroup.findCmsSnippetGroup(groupId);
		if (snippetGroup == null)
			return new AdminJson(false, "The snippet group does not exist.");

		Set<CmsSnippetGroup> childGroups = snippetGroup.getCmsSnippetGroups();
		Iterator<CmsSnippetGroup> childGroupsIterator = childGroups.iterator();
		while (childGroupsIterator.hasNext()) {
			CmsSnippetGroup childGroup = childGroupsIterator.next();
			childGroup.setParentId(null);
			CmsSnippetGroup.entityManager().persist(childGroup);
		}

		Set<CmsSnippet> childSnippets = snippetGroup.getCmsSnippets();
		Iterator<CmsSnippet> childSnippetsIterator = childSnippets.iterator();
		while (childSnippetsIterator.hasNext()) {
			CmsSnippet childSnippet = childSnippetsIterator.next();
			childSnippet.setCmsSnippetGroupId(null);
			CmsSnippet.entityManager().persist(childSnippet);
		}

		snippetGroup.setCmsSnippetGroups(new HashSet<CmsSnippetGroup>());
		snippetGroup.setCmsSnippets(new HashSet<CmsSnippet>());

		try {
			CmsSnippetGroup.entityManager().persist(snippetGroup);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Snippet group not emptied " + e.getMessage());
		}

		return new AdminJson(true, "CMS Snippet group emptied successfully");
	}

	public static AdminJson snippetGroupDrop(Integer groupId) {
		CmsSnippetGroup snippetGroup = CmsSnippetGroup.findCmsSnippetGroup(groupId);
		if (snippetGroup == null)
			return new AdminJson(false, "The snippet group does not exist.");

		Set<CmsSnippetGroup> childGroups = snippetGroup.getCmsSnippetGroups();
		Iterator<CmsSnippetGroup> childGroupsIterator = childGroups.iterator();
		while (childGroupsIterator.hasNext()) {
			CmsSnippetGroup childGroup = childGroupsIterator.next();
			childGroup.setParentId(null);
			CmsSnippetGroup.entityManager().persist(childGroup);
		}

		Set<CmsSnippet> childSnippets = snippetGroup.getCmsSnippets();
		Iterator<CmsSnippet> childSnippetsIterator = childSnippets.iterator();
		while (childSnippetsIterator.hasNext()) {
			CmsSnippet childSnippet = childSnippetsIterator.next();
			childSnippet.setCmsSnippetGroupId(null);
			CmsSnippet.entityManager().persist(childSnippet);
		}

		try {
			CmsSnippetGroup.entityManager().remove(snippetGroup);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Snippet group not dropped " + e.getMessage());
		}

		return new AdminJson(true, "CMS Snippet group dropped successfully");
	}

	public static AdminJson snippetDrop(Integer snippetId) {
		CmsSnippet snippet = CmsSnippet.findCmsSnippet(snippetId);
		if (snippet == null)
			return new AdminJson(false, "The snippet does not exist.");
		try {
			CmsSnippetGroup parentGroup = snippet.getCmsSnippetGroupId();
			if (parentGroup.getCmsSnippets() != null && parentGroup.getCmsSnippets().contains(snippet)) {
				parentGroup.getCmsSnippets().remove(snippet);
			}

			CmsSnippet.entityManager().remove(snippet);
		} catch (Exception e) {
			return new AdminJson(false, "CMS Snippet not dropped" + e.getMessage());
		}

		return new AdminJson(true, "CMS Snippet dropped successfully");
	}

	public static AdminJson snippetSave(Integer groupId, String name, String content, Integer snippetId) {

		CmsSnippet snippet = null;
		if (snippetId != null) {
			snippet = CmsSnippet.findCmsSnippet(snippetId);
		}

		if (snippet == null) {
			snippet = new CmsSnippet();
		}

		snippet.setName(name);
		snippet.setSnippetContent(content);

		CmsSnippetGroup parentGroup = CmsSnippetGroup.findCmsSnippetGroup(groupId);
		if (parentGroup != null) {
			snippet.setCmsSnippetGroupId(parentGroup);
			if (parentGroup.getCmsSnippets() != null && parentGroup.getCmsSnippets().contains(snippet)) {
				// do nothing
			} else {
				if (parentGroup.getCmsSnippets() == null) {
					parentGroup.setCmsSnippets(new HashSet<CmsSnippet>());
					parentGroup.getCmsSnippets().add(snippet);
				} else {
					parentGroup.getCmsSnippets().add(snippet);
				}
				CmsSnippetGroup.entityManager().persist(parentGroup);
			}
			snippet.setCmsSnippetGroupId(parentGroup);
		}

		try {
			CmsSnippetGroup.entityManager().persist(snippet);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Snippet not created " + e.getMessage());
		}

		return new AdminJson(true, "CMS Snippet created successfully");
	}

	// Methods for CMS files and folders.
	public static AdminJson folderSave(String foldername, Integer parentId, String description) {
		if (foldername == null)
			return new AdminJson(false, "The folder must gave a name.");
		;

		CmsFolder folder = new CmsFolder();
		folder.setName(foldername);
		folder.setDescription(description);

		CmsFolder parentFolder = null;
		if (parentId != null) {
			parentFolder = CmsFolder.findCmsFolder(parentId);
			if (parentFolder != null) {
				folder.setParentId(parentFolder);
				parentFolder.getCmsFolders().add(folder);
			}
		}
		try {
			CmsFolder.entityManager().persist(folder);
			if (parentFolder != null) {
				CmsFolder.entityManager().persist(parentFolder);
			}
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Folder not created " + e.getMessage());
		}

		return new AdminJson(true, "Folder created successfully");
	}

	public static AdminJson folderEmpty(Integer folderId) {
		CmsFolder folder = CmsFolder.findCmsFolder(folderId);
		if (folder == null)
			return new AdminJson(false, "The folder does not exist.");

		Set<CmsFolder> childGroups = folder.getCmsFolders();
		Iterator<CmsFolder> childGroupsIterator = childGroups.iterator();
		while (childGroupsIterator.hasNext()) {
			CmsFolder childGroup = childGroupsIterator.next();
			childGroup.setParentId(null);
			CmsFolder.entityManager().persist(childGroup);
		}

		Set<CmsFile> childFiles = folder.getCmsFiles();
		Iterator<CmsFile> childFilesIterator = childFiles.iterator();
		while (childFilesIterator.hasNext()) {
			CmsFile childFile = childFilesIterator.next();
			childFile.setCmsFolderId(null);
			CmsFile.entityManager().persist(childFile);
		}

		folder.setCmsFolders(new HashSet<CmsFolder>());
		folder.setCmsFiles(new HashSet<CmsFile>());

		try {
			CmsFolder.entityManager().persist(folder);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Folder not emptied " + e.getMessage());
		}

		return new AdminJson(true, "CMS Folder emptied successfully");
	}

	public static AdminJson folderDrop(Integer folderId) {
		CmsFolder folder = CmsFolder.findCmsFolder(folderId);
		if (folder == null)
			return new AdminJson(false, "The folder group does not exist.");

		Set<CmsFolder> childGroups = folder.getCmsFolders();
		Iterator<CmsFolder> childGroupsIterator = childGroups.iterator();
		while (childGroupsIterator.hasNext()) {
			CmsFolder childGroup = childGroupsIterator.next();
			childGroup.setParentId(null);
			CmsFolder.entityManager().persist(childGroup);
		}

		Set<CmsFile> childFiles = folder.getCmsFiles();
		Iterator<CmsFile> childFilesIterator = childFiles.iterator();
		while (childFilesIterator.hasNext()) {
			CmsFile childFile = childFilesIterator.next();
			childFile.setCmsFolderId(null);
			CmsFile.entityManager().persist(childFile);
		}

		try {
			CmsFolder.entityManager().remove(folder);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Folder not dropped " + e.getMessage());
		}

		return new AdminJson(true, "CMS Folder group dropped successfully");
	}

	public static AdminJson fileDrop(Integer fileId) {
		CmsFile file = CmsFile.findCmsFile(fileId);
		if (file == null)
			return new AdminJson(false, "The file does not exist.");

		CmsFolder parentGroup = file.getCmsFolderId();
		if (parentGroup.getCmsFiles() != null && parentGroup.getCmsFiles().contains(file)) {
			parentGroup.getCmsFiles().remove(file);
			CmsFolder.entityManager().persist(parentGroup);
		}

		try {
			CmsFile.entityManager().remove(file);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "CMS File not dropped" + e.getMessage());
		}

		return new AdminJson(true, "CMS File dropped successfully");
	}

	// TODO: file multipart
	// TODO (also for the above methods): manage the files and folders in the
	// repository
	public static AdminJson fileSave(Integer folderId, String alias, Integer fileId) {

		CmsFile file = null;
		if (fileId != null) {
			file = CmsFile.findCmsFile(fileId);
		}

		if (file == null) {
			file = new CmsFile();
		}

		file.setLabel(alias);

		CmsFolder parentFolder = CmsFolder.findCmsFolder(folderId);
		if (parentFolder != null) {
			file.setCmsFolderId(parentFolder);
			if (parentFolder.getCmsFiles() != null && parentFolder.getCmsFiles().contains(file)) {
				// do nothing
			} else {
				if (parentFolder.getCmsFiles() == null) {
					parentFolder.setCmsFiles(new HashSet<CmsFile>());
					parentFolder.getCmsFiles().add(file);
				} else {
					parentFolder.getCmsFiles().add(file);
				}
				CmsFolder.entityManager().persist(parentFolder);
			}
			file.setCmsFolderId(parentFolder);
		}

		try {
			CmsFolder.entityManager().persist(file);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "File not created " + e.getMessage());
		}

		return new AdminJson(true, "CMS File created successfully");
	}

	private final Log log = LogFactory.getLog(this.getClass());

	private boolean success;

	private String message;

	public AdminJson(boolean success, String message) {
		this.success = success;
		this.message = message;

	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String toJson() {

		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");

		return serializer.serialize(this);
	}
}
