package ro.roda.service;

import org.springframework.web.multipart.MultipartFile;

import ro.roda.transformer.AdminJson;

public interface AdminJsonService {

	public abstract AdminJson findLogin(String username, String password);

	public abstract AdminJson layoutGroupSave(String groupname, Integer parentId, String description);

	public abstract AdminJson layoutGroupEmpty(Integer groupId);

	public abstract AdminJson layoutGroupDrop(Integer groupId);

	public abstract AdminJson layoutDrop(Integer layoutId);

	public abstract AdminJson layoutSave(Integer groupId, String content, String name, String description,
			Integer layoutId);

	public abstract AdminJson layoutMove(Integer groupId, Integer layoutId);

	public abstract AdminJson layoutGroupMove(Integer parentGroupId, Integer groupId);

	public abstract AdminJson snippetGroupSave(String groupname, Integer parentId, String description);

	public abstract AdminJson snippetGroupEmpty(Integer groupId);

	public abstract AdminJson snippetGroupDrop(Integer groupId);

	public abstract AdminJson snippetDrop(Integer snippetId);

	public abstract AdminJson snippetSave(Integer groupId, String content, String name, Integer snippetId);

	public abstract AdminJson snippetMove(Integer groupId, Integer snippetId);

	public abstract AdminJson snippetGroupMove(Integer parentGroupId, Integer groupId);

	// CMS FILES

	public abstract AdminJson folderSave(String foldername, Integer parentId, String description);

	public abstract AdminJson folderEmpty(Integer folderId);

	public abstract AdminJson folderDrop(Integer folderId);

	public abstract AdminJson fileDrop(Integer fileId);

	public abstract AdminJson fileSave(Integer folderId, MultipartFile content, Integer fileId, String alias, String url);

	public abstract AdminJson fileMove(Integer folderId, Integer fileId);

	public abstract AdminJson folderMove(Integer parentFolderId, Integer folderId);

	// USER MANAGEMENT

	public abstract AdminJson userSave(Integer id, String username, String password, String passwordCheck,
			String email, Boolean enabled);

	public abstract AdminJson groupSave(Integer id, String name, String description, Boolean enabled);

	public abstract AdminJson userAddToGroup(Integer userId, Integer groupId);

	public abstract AdminJson userRemoveFromGroup(Integer userId, Integer groupId);

	public abstract AdminJson userEnable(Integer userId);

	public abstract AdminJson userDisable(Integer userId);

	public abstract AdminJson userDrop(Integer userId);

	public abstract AdminJson userChangePassword(Integer userId, String password, String controlPassword);

	public abstract AdminJson userMessage(Integer userId, String subject, String message);

	public abstract AdminJson groupMessage(Integer groupId, String subject, String message);

	// CMS PAGE
	public abstract AdminJson cmsPageSave(boolean preview, Integer cmsPageParentId, String name, String lang, String menutitle,
			String synopsis, String target, String url, boolean defaultPage, String externalredirect,
			String internalredirect, Integer layoutId, Integer cacheable, boolean published, boolean navigable,
			String pagetype, Integer cmsPageId, String pageContent);

	public abstract AdminJson cmsPageMove(Integer cmsPageParentId, Integer cmsPageId);

	public abstract AdminJson cmsPageDrop(Integer cmsPageId);

}
