package ro.roda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ro.roda.domain.CmsFolder;
import ro.roda.domain.CmsFile;
import ro.roda.filestore.CmsFileStoreService;
import ro.roda.transformer.AdminJson;

@Service
@Transactional
public class AdminJsonServiceImpl implements AdminJsonService {

	@Autowired
	CmsFileStoreService fileStore;

	public AdminJson findLogin(String username, String password) {
		return AdminJson.findLogin(username, password);
	}

	public AdminJson layoutGroupSave(String groupname, Integer parentId, String description) {
		return AdminJson.layoutGroupSave(groupname, parentId, description);
	}

	public AdminJson layoutGroupEmpty(Integer groupId) {
		return AdminJson.layoutGroupEmpty(groupId);
	}

	public AdminJson layoutGroupDrop(Integer groupId) {
		return AdminJson.layoutGroupDrop(groupId);
	}

	public AdminJson layoutDrop(Integer layoutId) {
		return AdminJson.layoutDrop(layoutId);
	}

	public AdminJson layoutSave(Integer groupId, String content, String name, String description, Integer layoutId) {
		return AdminJson.layoutSave(groupId, content, name, description, layoutId);
	}

	public AdminJson layoutMove(Integer groupId, Integer layoutId) {
		return AdminJson.layoutMove(groupId, layoutId);
	}

	public AdminJson layoutGroupMove(Integer parentGroupId, Integer groupId) {
		return AdminJson.layoutGroupMove(parentGroupId, groupId);
	}

	public AdminJson snippetGroupSave(String groupname, Integer parentId, String description) {
		return AdminJson.snippetGroupSave(groupname, parentId, description);
	}

	public AdminJson snippetGroupEmpty(Integer groupId) {
		return AdminJson.snippetGroupEmpty(groupId);
	}

	public AdminJson snippetGroupDrop(Integer groupId) {
		return AdminJson.snippetGroupDrop(groupId);
	}

	public AdminJson snippetDrop(Integer snippetId) {
		return AdminJson.snippetDrop(snippetId);
	}

	public AdminJson snippetSave(Integer groupId, String content, String name, Integer snippetId) {
		return AdminJson.snippetSave(groupId, name, content, snippetId);
	}

	public AdminJson snippetMove(Integer groupId, Integer snippetId) {
		return AdminJson.snippetMove(groupId, snippetId);
	}

	public AdminJson snippetGroupMove(Integer parentGroupId, Integer groupId) {
		return AdminJson.snippetGroupMove(parentGroupId, groupId);
	}

	// CMS FILE

	public AdminJson fileGrid() {
		// TODO Cosmin fileStore.fileGrid();
		// AdminJson result = AdminJson.fileGrid();
		return new AdminJson(true, "");
	}

	public AdminJson fileTree() {
		// TODO Cosmin fileStore.fileTree();
		// AdminJson result = AdminJson.fileTree();
		return new AdminJson(true, "");
	}

	public AdminJson folderTree() {
		// TODO Cosmin fileStore.folderTree();
		// AdminJson result = AdminJson.folderTree();
		return new AdminJson(true, "");
	}

	public AdminJson fileInfo(Integer id) {
		// TODO Cosmin fileStore.fileInfo();
		// AdminJson result = AdminJson.fileInfo();
		return new AdminJson(true, "");
	}

	public AdminJson folderInfo(Integer id) {
		// TODO Cosmin
		// fileStore.folderInfo();
		// AdminJson result = AdminJson.folderInfo();
		return new AdminJson(true, "");
	}

	public AdminJson folderSave(String foldername, Integer parentId, String description) {
		AdminJson result = AdminJson.folderSave(foldername, parentId, description);
		fileStore.folderSave(CmsFolder.findCmsFolder(result.getId()));
		return result;
	}

	public AdminJson folderEmpty(Integer folderId) {
		AdminJson result = AdminJson.folderEmpty(folderId);
		fileStore.folderEmpty(CmsFolder.findCmsFolder(folderId));
		return result;
	}

	public AdminJson folderDrop(Integer folderId) {
		AdminJson result = AdminJson.folderDrop(folderId);
		fileStore.folderDrop(CmsFolder.findCmsFolder(folderId));
		return result;
	}

	public AdminJson fileDrop(Integer fileId) {
		AdminJson result = AdminJson.folderDrop(fileId);
		fileStore.fileDrop(CmsFile.findCmsFile(fileId));
		return result;
	}

	public AdminJson fileSave(Integer folderId, MultipartFile content, Integer fileId, String alias) {
		AdminJson result = AdminJson.fileSave(folderId, content, alias, fileId);
		fileStore.fileSave(content, CmsFolder.findCmsFolder(folderId));
		return result;
	}

	public AdminJson fileMove(Integer folderId, Integer fileId) {
		AdminJson result = AdminJson.fileMove(folderId, fileId);
		fileStore.fileMove(CmsFolder.findCmsFolder(folderId), CmsFile.findCmsFile(fileId));
		return result;
	}

	public AdminJson folderMove(Integer parentFolderId, Integer folderId) {
		AdminJson result = AdminJson.fileMove(parentFolderId, folderId);
		fileStore.folderMove(CmsFolder.findCmsFolder(parentFolderId), CmsFolder.findCmsFolder(folderId));
		return result;
	}

	// USER MANAGEMENT

	@Override
	public AdminJson userSave(Integer id, String username, String email, Boolean enabled) {
		return AdminJson.userSave(id, username, email, enabled);
	}

	@Override
	public AdminJson groupSave(Integer id, String name, String description) {
		return AdminJson.groupSave(id, name, description);
	}

	public AdminJson addUserToGroup(Integer userId, Integer groupId) {
		return AdminJson.addUserToGroup(userId, groupId);
	}

	@Override
	public AdminJson deleteUserFromGroup(Integer userId, Integer groupId) {
		return AdminJson.deleteUserFromGroup(userId, groupId);
	}

	@Override
	public AdminJson enableUser(Integer userId) {
		return AdminJson.enableUser(userId);
	}

	@Override
	public AdminJson disableUser(Integer userId) {
		return AdminJson.disableUser(userId);
	}

	@Override
	public AdminJson dropUser(Integer userId) {
		return AdminJson.dropUser(userId);
	}

	@Override
	public AdminJson changePasswordUser(Integer userId, String password, String controlPassword) {
		return AdminJson.changePasswordUser(userId, password, controlPassword);
	}

	@Override
	public AdminJson messageUser(Integer userId, String subject, String message) {
		return AdminJson.messageUser(userId, subject, message);
	}

	@Override
	public AdminJson messageGroup(Integer groupId, String subject, String message) {
		return AdminJson.messageGroup(groupId, subject, message);
	}

}
