package ro.roda.service;

import org.springframework.web.multipart.MultipartFile;
import java.util.Date;

import ro.roda.domainjson.AdminJson;

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

	public abstract AdminJson jsonCreate(String jsonString, String name);

	public abstract AdminJson jsonSave(String jsonString, Integer cmsFileId, String name);

	public abstract AdminJson jsonImport(Integer id);

	public abstract AdminJson fileMove(Integer folderId, Integer fileId);

	public abstract AdminJson folderMove(Integer parentFolderId, Integer folderId);

	// CMS PAGE
	public abstract AdminJson cmsPageSave(boolean save, Integer cmsPageParentId, String name, String lang,
			String menutitle, String synopsis, String target, String url, boolean defaultPage, String externalredirect,
			String internalredirect, Integer layoutId, Integer cacheable, boolean published, boolean navigable,
			String pagetype, Integer cmsPageId, String pageContent);

	public abstract AdminJson cmsPageMove(Integer cmsPageParentId, Integer cmsPageId, String mode);

	public abstract AdminJson cmsPageDrop(Integer cmsPageId);

	public abstract AdminJson cmsPageNav(boolean navigable, Integer cmsPageId);

	public abstract AdminJson newsSave(Integer id, Integer langId, String title, String content, Date added);

	public abstract AdminJson newsDrop(Integer layoutId);

	public abstract AdminJson studyImport(Integer ddiId, Integer csvId, Integer[] fileIds);

}
