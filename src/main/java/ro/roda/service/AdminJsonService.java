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

	public abstract AdminJson folderSave(String foldername, Integer parentId, String description);

	public abstract AdminJson folderEmpty(Integer folderId);

	public abstract AdminJson folderDrop(Integer folderId);

	public abstract AdminJson fileDrop(Integer fileId);

	public abstract AdminJson fileSave(Integer folderId, MultipartFile content, Integer fileId, String alias);

	public abstract AdminJson fileMove(Integer folderId, Integer fileId);

	public abstract AdminJson folderMove(Integer parentFolderId, Integer folderId);

	public abstract AdminJson addUserToGroup(Integer userId, Integer groupId);
}
