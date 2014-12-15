package ro.roda.service;

import java.io.IOException;
import java.io.InputStream;
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
import ro.roda.service.importer.DdiImporterService;
import ro.roda.service.page.RodaPageService;

@Service
@Transactional
public class AdminJsonServiceImpl implements AdminJsonService {

	@Autowired
	CmsFileStoreService fileStoreService;

	@Autowired
	DdiImporterService ddiImporterService;

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

	public AdminJson newsDrop(Integer newsId) {
		return AdminJson.newsDrop(newsId);
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
		fileStoreService.folderSave(CmsFolder.findCmsFolder(result.getId()));
		return result;
	}

	public AdminJson folderEmpty(Integer folderId) {
		AdminJson result = AdminJson.folderEmpty(folderId);
		fileStoreService.folderEmpty(CmsFolder.findCmsFolder(folderId));
		return result;
	}

	public AdminJson folderDrop(Integer folderId) {
		AdminJson result = AdminJson.folderDrop(folderId);
		fileStoreService.folderDrop(CmsFolder.findCmsFolder(folderId));
		return result;
	}

	public AdminJson fileDrop(Integer fileId) {
		AdminJson result = AdminJson.fileDrop(fileId);
		fileStoreService.fileDrop(CmsFile.findCmsFile(fileId));
		return result;
	}

	public AdminJson fileSave(Integer folderId, MultipartFile content, Integer fileId, String alias, String url) {
		AdminJson result = AdminJson.fileSave(folderId, content, fileId, alias, url);
		fileStoreService.fileSave(content, CmsFolder.findCmsFolder(folderId));
		return result;
	}

	@Override
	public AdminJson jsonCreate(String jsonString, String name) {
		MockMultipartFile mmf = new MockMultipartFile(name, name, "application/json", jsonString.getBytes(Charset
				.forName("UTF-8")));
		// get or create the folder for JSONs
		CmsFolder folder = CmsFolder.checkCmsFolder(null, CmsFolder.jsonCmsFolderName, null, null);
		fileStoreService.folderSave(folder);

		AdminJson result = AdminJson.fileSave(folder.getId(), mmf, null, null, null);
		fileStoreService.fileSave(mmf, folder);
		return result;
	}

	public AdminJson jsonSave(String jsonString, Integer cmsFileId, String name) {
		MockMultipartFile mmf = new MockMultipartFile(name, name, "application/json", jsonString.getBytes(Charset
				.forName("UTF-8")));
		CmsFolder folder = CmsFolder.checkCmsFolder(null, CmsFolder.jsonCmsFolderName, null, null);
		fileStoreService.folderSave(folder);

		// first remove the old version of the JSON
		fileStoreService.fileDrop(CmsFile.findCmsFile(cmsFileId));

		AdminJson result = AdminJson.fileSave(folder.getId(), mmf, cmsFileId, null, null);
		fileStoreService.fileSave(mmf, folder);
		return result;
	}

	public AdminJson jsonImport(Integer cmsFileId) {
		try {
			CmsFile jsonFile = CmsFile.findCmsFile(cmsFileId);
			InputStream is = fileStoreService.fileLoad(jsonFile);
			MockMultipartFile mmf;
			mmf = new MockMultipartFile(jsonFile.getFilename(), is);
			ddiImporterService.importDdiTestFile(mmf);

			// remove the last version of the JSON
			fileDrop(cmsFileId);

			return new AdminJson(true, "Study was imported");
		} catch (IOException e) {
			return new AdminJson(true, "Exception when importing Study: " + e);
		}
	}

	public AdminJson fileMove(Integer folderId, Integer fileId) {
		AdminJson result = AdminJson.fileMove(folderId, fileId);
		fileStoreService.fileMove(CmsFolder.findCmsFolder(folderId), CmsFile.findCmsFile(fileId));
		return result;
	}

	public AdminJson folderMove(Integer parentFolderId, Integer folderId) {
		AdminJson result = AdminJson.fileMove(parentFolderId, folderId);
		fileStoreService.folderMove(CmsFolder.findCmsFolder(parentFolderId), CmsFolder.findCmsFolder(folderId));
		return result;
	}

	public AdminJson cmsPageNav(boolean navigable, Integer cmsPageId) {
		rodaPageService.evictAll();
		return AdminJson.cmsPageSetNavigable(navigable, cmsPageId);
	}

}
