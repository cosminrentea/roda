package ro.roda.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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

	public abstract AdminJson fileGrid();

	public abstract AdminJson fileTree();

	public abstract AdminJson folderTree();

	public abstract AdminJson fileInfo(Integer id);

	public abstract AdminJson folderInfo(Integer id);

	public abstract AdminJson folderSave(String foldername, Integer parentId, String description);

	public abstract AdminJson folderEmpty(Integer folderId);

	public abstract AdminJson folderDrop(Integer folderId);

	public abstract AdminJson fileDrop(Integer fileId);

	public abstract AdminJson fileSave(Integer folderId, MultipartFile content, Integer fileId, String alias);

	public abstract AdminJson fileMove(Integer folderId, Integer fileId);

	public abstract AdminJson folderMove(Integer parentFolderId, Integer folderId);

	// USER MANAGEMENT

	public abstract AdminJson userSave(Integer id, String username, String email, Boolean enabled);

	public abstract AdminJson groupSave(Integer id, String name, String description);

	public abstract AdminJson addUserToGroup(Integer userId, Integer groupId);

	public abstract AdminJson deleteUserFromGroup(Integer userId, Integer groupId);

	public abstract AdminJson enableUser(Integer userId);

	public abstract AdminJson disableUser(Integer userId);

	public abstract AdminJson dropUser(Integer userId);

	public abstract AdminJson changePasswordUser(Integer userId, String password, String controlPassword);

	public abstract AdminJson messageUser(Integer userId, String subject, String message);

	public abstract AdminJson messageGroup(Integer groupId, String subject, String message);

}
