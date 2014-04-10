package ro.roda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ro.roda.domain.CmsFile;
import ro.roda.domain.CmsFolder;
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
		AdminJson result = AdminJson.fileDrop(fileId);
		fileStore.fileDrop(CmsFile.findCmsFile(fileId));
		return result;
	}

	public AdminJson fileSave(Integer folderId, MultipartFile content, Integer fileId, String alias, String url) {
		AdminJson result = AdminJson.fileSave(folderId, content, fileId, alias, url);
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
	public AdminJson userSave(Integer id, String username, String password, String passwordCheck, String email,
			Boolean enabled) {
		return AdminJson.userSave(id, username, password, passwordCheck, email, enabled);
	}

	@Override
	public AdminJson groupSave(Integer id, String name, String description, Boolean enabled) {
		return AdminJson.groupSave(id, name, description, enabled);
	}

	public AdminJson userAddToGroup(Integer userId, Integer groupId) {
		return AdminJson.addUserToGroup(userId, groupId);
	}

	@Override
	public AdminJson userRemoveFromGroup(Integer userId, Integer groupId) {
		return AdminJson.deleteUserFromGroup(userId, groupId);
	}

	@Override
	public AdminJson userEnable(Integer userId) {
		return AdminJson.enableUser(userId);
	}

	@Override
	public AdminJson userDisable(Integer userId) {
		return AdminJson.disableUser(userId);
	}

	@Override
	public AdminJson userDrop(Integer userId) {
		return AdminJson.dropUser(userId);
	}

	@Override
	public AdminJson userChangePassword(Integer userId, String password, String controlPassword) {
		return AdminJson.changePasswordUser(userId, password, controlPassword);
	}

	@Override
	public AdminJson userMessage(Integer userId, String subject, String message) {
		return AdminJson.messageUser(userId, subject, message);
	}

	@Override
	public AdminJson groupMessage(Integer groupId, String subject, String message) {
		return AdminJson.messageGroup(groupId, subject, message);
	}

	// CMS PAGE
	public AdminJson cmsPageSave(boolean preview, Integer cmsPageParentId, String name, String lang, String menutitle, String synopsis,
			String target, String url, boolean defaultPage, String externalredirect, String internalredirect,
			Integer layoutId, Integer cacheable, boolean published, boolean navigable, String pagetype, Integer cmsPageId,
			String pageContent) {
		return AdminJson.cmsPageSave(preview, cmsPageParentId, name, lang, menutitle, synopsis, target, url, defaultPage,
				externalredirect, internalredirect, layoutId, cacheable, published, navigable, pagetype, cmsPageId, pageContent);
	}

	public AdminJson cmsPageMove(Integer cmsPageParentId, Integer cmsPageId) {
		return AdminJson.cmsPageMove(cmsPageParentId, cmsPageId);
	}

	public AdminJson cmsPageDrop(Integer cmsPageId) {
		return AdminJson.cmsPageDrop(cmsPageId);
	}

}
