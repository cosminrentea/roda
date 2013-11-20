package ro.roda.service;

import ro.roda.transformer.AdminJson;

public interface AdminJsonService {

	public abstract AdminJson findLogin(String username, String password);

	public abstract AdminJson layoutGroupSave(String groupname, Integer parentId, String description);

	public abstract AdminJson layoutGroupEmpty(Integer groupId);

	public abstract AdminJson layoutGroupDrop(Integer groupId);

	public abstract AdminJson layoutDrop(Integer layoutId);

	public abstract AdminJson layoutSave(Integer groupId, String content, String name, Integer layoutId);

	public abstract AdminJson snippetGroupSave(String groupname, Integer parentId, String description);

	public abstract AdminJson snippetGroupEmpty(Integer groupId);

	public abstract AdminJson snippetGroupDrop(Integer groupId);

	public abstract AdminJson snippetDrop(Integer snippetId);

	public abstract AdminJson snippetSave(Integer groupId, String content, String name, Integer snippetId);

	public abstract AdminJson folderSave(String foldername, Integer parentId, String description);

	public abstract AdminJson folderEmpty(Integer folderId);

	public abstract AdminJson folderDrop(Integer folderId);

	public abstract AdminJson fileDrop(Integer fileId);

	public abstract AdminJson fileSave(Integer folderId, String alias, Integer fileId);
}