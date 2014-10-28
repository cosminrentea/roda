package ro.roda.service;

import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;
import ro.roda.domain.CmsFile;
import ro.roda.domain.CmsFolder;
import ro.roda.domain.CmsLayout;
import ro.roda.domain.CmsPage;
import ro.roda.domainjson.AdminJson;
import ro.roda.service.filestore.CmsFileStoreService;
import ro.roda.service.page.RodaPageService;

@Service
@Transactional
public class AdminJsonServiceImpl implements AdminJsonService {

	@Autowired
	CmsFileStoreService fileStore;

	@Autowired
	RodaPageService rodaPageService;

	public AdminJson findLogin(String username, String password) {
		return AdminJson.findLogin(username, password);
	}

	public AdminJson layoutGroupSave(String groupname, Integer parentId, String description) {
		rodaPageService.evictAll();
		return AdminJson.layoutGroupSave(groupname, parentId, description);
	}

	public AdminJson layoutGroupEmpty(Integer groupId) {
		rodaPageService.evictAll();
		return AdminJson.layoutGroupEmpty(groupId);
	}

	public AdminJson layoutGroupDrop(Integer groupId) {
		rodaPageService.evictAll();
		return AdminJson.layoutGroupDrop(groupId);
	}

	public AdminJson layoutDrop(Integer layoutId) {
		rodaPageService.evictAll();
		return AdminJson.layoutDrop(layoutId);
	}

	public AdminJson layoutSave(Integer groupId, String content, String name, String description, Integer layoutId) {
		rodaPageService.evictAll();

		return AdminJson.layoutSave(groupId, content, name, description, layoutId);
	}

	public AdminJson layoutMove(Integer groupId, Integer layoutId) {
		rodaPageService.evictAll();
		return AdminJson.layoutMove(groupId, layoutId);
	}

	public AdminJson layoutGroupMove(Integer parentGroupId, Integer groupId) {
		rodaPageService.evictAll();
		return AdminJson.layoutGroupMove(parentGroupId, groupId);
	}

	public AdminJson snippetGroupSave(String groupname, Integer parentId, String description) {
		rodaPageService.evictAll();
		return AdminJson.snippetGroupSave(groupname, parentId, description);
	}

	public AdminJson snippetGroupEmpty(Integer groupId) {
		rodaPageService.evictAll();
		return AdminJson.snippetGroupEmpty(groupId);
	}

	public AdminJson snippetGroupDrop(Integer groupId) {
		rodaPageService.evictAll();
		return AdminJson.snippetGroupDrop(groupId);
	}

	public AdminJson snippetDrop(Integer snippetId) {
		rodaPageService.evictAll();
		return AdminJson.snippetDrop(snippetId);
	}

	public AdminJson snippetSave(Integer groupId, String content, String name, Integer snippetId) {
		rodaPageService.evictAll();
		return AdminJson.snippetSave(groupId, content, name, snippetId);
	}

	public AdminJson snippetMove(Integer groupId, Integer snippetId) {
		rodaPageService.evictAll();
		return AdminJson.snippetMove(groupId, snippetId);
	}

	public AdminJson snippetGroupMove(Integer parentGroupId, Integer groupId) {
		rodaPageService.evictAll();
		return AdminJson.snippetGroupMove(parentGroupId, groupId);
	}

	public AdminJson newsSave(Integer id, Integer langId, String title, String content, Date added) {
		rodaPageService.evictAll();
		return AdminJson.newsSave(id, langId, title, content, added);
	}

	// CMS PAGE
	public AdminJson cmsPageSave(boolean save, Integer cmsPageParentId, String name, String lang, String menutitle,
			String synopsis, String target, String url, boolean defaultPage, String externalredirect,
			String internalredirect, Integer layoutId, Integer cacheable, boolean published, boolean navigable,
			String pagetype, Integer cmsPageId, String pageContent) {
		AtomicReference<CmsPage> cmsPageRef = new AtomicReference<CmsPage>();
		AdminJson adminJson = AdminJson.cmsPageSave(save, cmsPageParentId, name, lang, menutitle, synopsis, target,
				url, defaultPage, externalredirect, internalredirect, layoutId, cacheable, published, navigable,
				pagetype, cmsPageId, pageContent, cmsPageRef);
		CmsPage cmsPage = cmsPageRef.get();

		if (save) {
			// evict page from cache (because it was just saved/updated)
			rodaPageService.evictAll();
		} else {
			// generate preview page
			adminJson.setMessage(rodaPageService.generatePreviewPage(cmsPage, CmsLayout.findCmsLayout(layoutId)
					.getLayoutContent(), pageContent,
					rodaPageService.generateFullRelativeUrl(cmsPage != null ? cmsPage.getCmsPageId() : null)
							+ (url != null ? "/" + url : "/preview"))[0]);
		}

		return adminJson;
	}

	public AdminJson cmsPageMove(Integer cmsPageParentId, Integer cmsPageId, String mode) {
		rodaPageService.evictAll();
		return AdminJson.cmsPageMove(cmsPageParentId, cmsPageId, mode);
	}

	public AdminJson cmsPageDrop(Integer cmsPageId) {
		rodaPageService.evictAll();
		return AdminJson.cmsPageDrop(cmsPageId);
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

	@Override
	public AdminJson jsonCreate(String jsonString, String name) {
		MockMultipartFile mmf = new MockMultipartFile(name, name, "application/json", jsonString.getBytes(Charset
				.forName("UTF-8")));
		// get or create the folder for JSONs
		CmsFolder folder = CmsFolder.checkCmsFolder(null, CmsFolder.jsonCmsFolderName, null, null);
		fileStore.folderSave(folder);

		AdminJson result = AdminJson.fileSave(folder.getId(), mmf, null, null, null);
		fileStore.fileSave(mmf, folder);
		return result;
	}

	public AdminJson jsonSave(String jsonString, Integer cmsFileId, String name) {
		MockMultipartFile mmf = new MockMultipartFile(name, name, "application/json", jsonString.getBytes(Charset
				.forName("UTF-8")));
		CmsFolder folder = CmsFolder.checkCmsFolder(null, CmsFolder.jsonCmsFolderName, null, null);
		fileStore.folderSave(folder);

		// remove the old version of the JSON
		fileStore.fileDrop(CmsFile.findCmsFile(cmsFileId));

		AdminJson result = AdminJson.fileSave(folder.getId(), mmf, cmsFileId, null, null);
		fileStore.fileSave(mmf, folder);

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

	public AdminJson cmsPageNav(boolean navigable, Integer cmsPageId) {
		rodaPageService.evictAll();
		return AdminJson.cmsPageSetNavigable(navigable, cmsPageId);
	}
	
}
