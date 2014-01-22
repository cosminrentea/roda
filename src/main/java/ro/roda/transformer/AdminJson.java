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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.multipart.MultipartFile;

import ro.roda.domain.CmsFile;
import ro.roda.domain.CmsFolder;
import ro.roda.domain.CmsLayout;
import ro.roda.domain.CmsLayoutGroup;
import ro.roda.domain.CmsSnippet;
import ro.roda.domain.CmsSnippetGroup;
import ro.roda.domain.UserGroup;
import ro.roda.domain.Users;
import ro.roda.filestore.CmsFileStoreService;
import flexjson.JSON;
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

	public static AdminJson layoutSave(Integer groupId, String content, String name, String description,
			Integer layoutId) {

		CmsLayout layout = null;
		if (layoutId != null) {
			layout = CmsLayout.findCmsLayout(layoutId);
		}

		if (layout == null) {
			layout = new CmsLayout();
		}

		layout.setName(name);
		layout.setLayoutContent(content);
		layout.setDescription(description);

		try {
			CmsLayoutGroup parentGroup = CmsLayoutGroup.findCmsLayoutGroup(groupId);
			if (parentGroup != null) {
				// layout.setCmsLayoutGroupId(parentGroup);
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

			CmsLayout.entityManager().persist(layout);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Layout not created " + e.getMessage());
		}

		return new AdminJson(true, "CMS Layout created successfully");
	}

	public static AdminJson layoutMove(Integer groupId, Integer layoutId) {

		CmsLayout layout = null;
		if (layoutId != null) {
			layout = CmsLayout.findCmsLayout(layoutId);
		}

		if (layout == null) {
			return new AdminJson(false, "CMS Layout to be moved should exist");
		}

		CmsLayoutGroup parentGroup = CmsLayoutGroup.findCmsLayoutGroup(groupId);

		if (groupId != null && parentGroup == null) {
			return new AdminJson(false, "The CMS Layout group should exist");
		}

		if (groupId == (layout.getCmsLayoutGroupId() == null ? null : layout.getCmsLayoutGroupId().getId())) {
			return new AdminJson(false, "The  group of the CMS Layout doesn't change");
		}

		try {
			if (parentGroup != null) {
				if (parentGroup.getCmsLayouts() == null) {
					parentGroup.setCmsLayouts(new HashSet<CmsLayout>());
					parentGroup.getCmsLayouts().add(layout);
				} else {
					parentGroup.getCmsLayouts().add(layout);
				}
				CmsLayoutGroup.entityManager().persist(parentGroup);
			}
			layout.setCmsLayoutGroupId(parentGroup);

			CmsLayout.entityManager().persist(layout);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Layout not moved " + e.getMessage());
		}

		return new AdminJson(true, "CMS Layout moved successfully");
	}

	public static AdminJson layoutGroupMove(Integer parentGroupId, Integer groupId) {

		CmsLayoutGroup layoutGroup = null;
		if (groupId != null) {
			layoutGroup = CmsLayoutGroup.findCmsLayoutGroup(groupId);
		}

		if (layoutGroup == null) {
			return new AdminJson(false, "CMS Layout Group to be moved should exist");
		}

		CmsLayoutGroup parentGroup = CmsLayoutGroup.findCmsLayoutGroup(parentGroupId);

		if (parentGroupId != null && parentGroup == null) {
			return new AdminJson(false, "The CMS Layout group should exist");
		}

		if (parentGroupId == (layoutGroup.getParentId() == null ? null : layoutGroup.getParentId().getId())) {
			return new AdminJson(false, "The parent of the CMS Layout group doesn't change");
		}

		try {
			if (parentGroup != null) {
				if (parentGroup.getCmsLayoutGroups() == null) {
					parentGroup.setCmsLayoutGroups(new HashSet<CmsLayoutGroup>());
					parentGroup.getCmsLayoutGroups().add(layoutGroup);
				} else {
					parentGroup.getCmsLayoutGroups().add(layoutGroup);
				}
				CmsLayoutGroup.entityManager().persist(parentGroup);
			}
			layoutGroup.setParentId(parentGroup);

			CmsLayoutGroup.entityManager().persist(layoutGroup);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "CMS Layout group not moved " + e.getMessage());
		}

		return new AdminJson(true, "CMS Layout group moved successfully");
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

	public static AdminJson snippetMove(Integer groupId, Integer snippetId) {

		CmsSnippet snippet = null;
		if (snippetId != null) {
			snippet = CmsSnippet.findCmsSnippet(snippetId);
		}

		if (snippet == null) {
			return new AdminJson(false, "CMS Snippet to be moved should exist");
		}

		CmsSnippetGroup parentGroup = CmsSnippetGroup.findCmsSnippetGroup(groupId);

		if (groupId != null && parentGroup == null) {
			return new AdminJson(false, "The CMS Snippet group should exist");
		}

		if (groupId == (snippet.getCmsSnippetGroupId() == null ? null : snippet.getCmsSnippetGroupId().getId())) {
			return new AdminJson(false, "The  group of the CMS Snippet doesn't change");
		}

		try {
			if (parentGroup != null) {
				if (parentGroup.getCmsSnippets() == null) {
					parentGroup.setCmsSnippets(new HashSet<CmsSnippet>());
					parentGroup.getCmsSnippets().add(snippet);
				} else {
					parentGroup.getCmsSnippets().add(snippet);
				}
				CmsSnippetGroup.entityManager().persist(parentGroup);
			}
			snippet.setCmsSnippetGroupId(parentGroup);

			CmsSnippet.entityManager().persist(snippet);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Snippet not moved " + e.getMessage());
		}

		return new AdminJson(true, "CMS Snippet moved successfully");
	}

	public static AdminJson snippetGroupMove(Integer parentGroupId, Integer groupId) {

		CmsSnippetGroup snippetGroup = null;
		if (groupId != null) {
			snippetGroup = CmsSnippetGroup.findCmsSnippetGroup(groupId);
		}

		if (snippetGroup == null) {
			return new AdminJson(false, "CMS Snippet Group to be moved should exist");
		}

		CmsSnippetGroup parentGroup = CmsSnippetGroup.findCmsSnippetGroup(parentGroupId);

		if (parentGroupId != null && parentGroup == null) {
			return new AdminJson(false, "The CMS Snippet group should exist");
		}

		if (parentGroupId == (snippetGroup.getParentId() == null ? null : snippetGroup.getParentId().getId())) {
			return new AdminJson(false, "The parent of the CMS Snippet group doesn't change");
		}

		try {
			if (parentGroup != null) {
				if (parentGroup.getCmsSnippetGroups() == null) {
					parentGroup.setCmsSnippetGroups(new HashSet<CmsSnippetGroup>());
					parentGroup.getCmsSnippetGroups().add(snippetGroup);
				} else {
					parentGroup.getCmsSnippetGroups().add(snippetGroup);
				}
				CmsSnippetGroup.entityManager().persist(parentGroup);
			}
			snippetGroup.setParentId(parentGroup);

			CmsSnippetGroup.entityManager().persist(snippetGroup);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "CMS Snippet group not moved " + e.getMessage());
		}

		return new AdminJson(true, "CMS Snippet group moved successfully");
	}

	// Methods for CMS files and folders.
	public static AdminJson folderSave(String foldername, Integer parentId, String description) {
		if (foldername == null)
			return new AdminJson(false, "The folder must have a name.");

		CmsFolder folder = new CmsFolder();
		folder.setName(foldername);
		folder.setDescription(description);

		CmsFolder parentFolder = null;
		if (parentId != null) {
			parentFolder = CmsFolder.findCmsFolder(parentId);
			if (parentFolder != null) {
				folder.setParentId(parentFolder);
				Set<CmsFolder> children = parentFolder.getCmsFolders();
				children.add(folder);
				parentFolder.setCmsFolders(children);
			}
		}

		try {
			CmsFolder.entityManager().persist(folder);
			if (parentFolder != null) {
				CmsFolder.entityManager().merge(parentFolder);
			}
			CmsFolder.entityManager().flush();
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Folder not created; " + e.getMessage());
		}

		return new AdminJson(true, "Folder created", folder.getId());
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

		try {
			CmsFolder.entityManager().remove(folder);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Folder not dropped " + e.getMessage());
		}

		return new AdminJson(true, "CMS Folder dropped successfully");
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

	public static AdminJson fileSave(Integer folderId, MultipartFile content, Integer fileId, String alias) {

		CmsFile file = null;
		if (fileId != null) {
			file = CmsFile.findCmsFile(fileId);
		}

		if (file == null) {
			file = new CmsFile();
		}

		file.setLabel(alias);
		file.setFilename(content.getOriginalFilename());
		file.setContentType(content.getContentType());
		file.setFilesize(content.getSize());

		//TODO Cosmin: check the use-case: file with the same name is added to the same folder
		// should overwrite the existing row in DB, OR throw ERROR
		
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

	public static AdminJson fileMove(Integer folderId, Integer fileId) {

		CmsFile file = null;
		if (fileId != null) {
			file = CmsFile.findCmsFile(fileId);
		}

		if (file == null) {
			return new AdminJson(false, "CMS File to be moved should exist");
		}

		CmsFolder folder = CmsFolder.findCmsFolder(folderId);

		if (folderId == null || folder == null) {
			return new AdminJson(false, "The CMS Folder should exist");
		}

		if (folderId == (file.getCmsFolderId() == null ? null : file.getCmsFolderId().getId())) {
			return new AdminJson(false, "The folder of the CMS File doesn't change");
		}

		try {
			if (folder != null) {
				if (folder.getCmsFiles() == null) {
					folder.setCmsFiles(new HashSet<CmsFile>());
					folder.getCmsFiles().add(file);
				} else {
					folder.getCmsFiles().add(file);
				}
				CmsFolder.entityManager().persist(folder);
			}
			file.setCmsFolderId(folder);

			CmsFile.entityManager().persist(file);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "File not moved " + e.getMessage());
		}

		return new AdminJson(true, "CMS File moved successfully");
	}

	public static AdminJson folderMove(Integer parentFolderId, Integer folderId) {

		CmsFolder folder = null;
		if (folderId != null) {
			folder = CmsFolder.findCmsFolder(folderId);
		}

		if (folder == null) {
			return new AdminJson(false, "CMS Folder to be moved should exist");
		}

		CmsFolder parentFolder = CmsFolder.findCmsFolder(parentFolderId);

		if (parentFolderId == null || parentFolder == null) {
			return new AdminJson(false, "The CMS Folder should exist");
		}

		if (parentFolderId == (folder.getParentId() == null ? null : folder.getParentId().getId())) {
			return new AdminJson(false, "The parent of the CMS Folder doesn't change");
		}

		try {
			if (parentFolder != null) {
				if (parentFolder.getCmsFolders() == null) {
					parentFolder.setCmsFolders(new HashSet<CmsFolder>());
					parentFolder.getCmsFolders().add(folder);
				} else {
					parentFolder.getCmsFolders().add(folder);
				}
				CmsFolder.entityManager().persist(parentFolder);
			}
			folder.setParentId(parentFolder);

			CmsFolder.entityManager().persist(folder);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "CMS Folder not moved " + e.getMessage());
		}

		return new AdminJson(true, "CMS Folder moved successfully");
	}

	public static AdminJson userSave(Integer id, String username, String password, String passwordCheck, String email, Boolean enabled) {
		if (username == null) {
			return new AdminJson(false, "The User must have a name!");
		}
		if (password == null || !password.equals(passwordCheck)) {
			return new AdminJson(false, "The User password is not correct!");
		}
		try {
			Users u = Users.checkUsers(id, username, password, enabled);
		} catch (Exception e) {
			return new AdminJson(false, "Exception saving User: " + e.getMessage());
		}
		return new AdminJson(true, "User created/saved");
	}

	public static AdminJson groupSave(Integer id, String name, String description, Boolean enabled) {
		if (name == null) {
			return new AdminJson(false, "The User Group must have a name!");
		}
		try {
			UserGroup ug = UserGroup.checkUserGroup(id, name, description, enabled);
		} catch (Exception e) {
			return new AdminJson(false, "Exception saving User Group: " + e.getMessage());
		}
		return new AdminJson(true, "User Group created/saved");
	}

	public static AdminJson addUserToGroup(Integer userId, Integer groupId) {
		// TODO Cosmin
		return new AdminJson(true, "");
	}

	public static AdminJson deleteUserFromGroup(Integer userId, Integer groupId) {
		// TODO Cosmin
		return new AdminJson(true, "");
	}

	public static AdminJson enableUser(Integer userId) {
		// TODO Cosmin
		return new AdminJson(true, "");
	}

	public static AdminJson disableUser(Integer userId) {
		// TODO Cosmin
		return new AdminJson(true, "");
	}

	public static AdminJson dropUser(Integer userId) {
		// TODO Cosmin
		return new AdminJson(true, "");
	}

	public static AdminJson changePasswordUser(Integer userId, String password, String controlPassword) {
		// TODO Cosmin
		return new AdminJson(true, "");
	}

	public static AdminJson messageUser(Integer userId, String subject, String message) {
		// TODO Cosmin
		return new AdminJson(true, "");
	}

	public static AdminJson messageGroup(Integer groupId, String subject, String message) {
		// TODO Cosmin
		return new AdminJson(true, "");
	}

	private final Log log = LogFactory.getLog(this.getClass());

	private boolean success;

	private String message;

	@JSON(include = false)
	private Integer id;

	public AdminJson(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public AdminJson(boolean success, String message, Integer id) {
		this.success = success;
		this.message = message;
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
