package ro.roda.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.transformer.AdminJson;

@Service
@Transactional
public class AdminJsonServiceImpl implements AdminJsonService {

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

	public AdminJson layoutSave(Integer groupId, String content, String name, Integer layoutId) {
		return AdminJson.layoutSave(groupId, content, name, layoutId);
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
		return AdminJson.snippetSave(groupId, content, name, snippetId);
	}

	public AdminJson folderSave(String foldername, Integer parentId, String description) {
		return AdminJson.folderSave(foldername, parentId, description);
	}

	public AdminJson folderEmpty(Integer groupId) {
		return AdminJson.folderEmpty(groupId);
	}

	public AdminJson folderDrop(Integer groupId) {
		return AdminJson.folderDrop(groupId);
	}

	public AdminJson fileDrop(Integer fileId) {
		return AdminJson.fileDrop(fileId);
	}

	public AdminJson fileSave(Integer folderId, String alias, Integer fileId) {
		return AdminJson.fileSave(folderId, alias, fileId);
	}
}
