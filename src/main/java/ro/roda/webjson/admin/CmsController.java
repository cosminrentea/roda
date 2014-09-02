package ro.roda.webjson.admin;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import ro.roda.domainjson.AdminJson;
import ro.roda.service.AdminJsonService;
import ro.roda.service.filestore.CmsFileStoreService;

@RequestMapping("/admin")
@Controller
public class CmsController {

	@Autowired
	AdminJsonService adminJsonService;

	@Autowired
	CmsFileStoreService cmsFileStoreService;

	private final Log log = LogFactory.getLog(this.getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(value = "/layoutgroupsave", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String layoutGroupSave(@RequestParam(value = "groupname") String groupname,
			@RequestParam(value = "parent") Integer parentId, @RequestParam(value = "description") String description) {

		AdminJson layoutGroupSave = adminJsonService.layoutGroupSave(groupname, parentId, description);

		if (layoutGroupSave == null) {
			return null;
		}
		return layoutGroupSave.toJson();

	}

	@RequestMapping(value = "/layoutgroupempty", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String layoutGroupEmpty(@RequestParam(value = "groupid") Integer groupId) {

		AdminJson layoutGroupEmpty = adminJsonService.layoutGroupEmpty(groupId);

		if (layoutGroupEmpty == null) {
			return null;
		}
		return layoutGroupEmpty.toJson();

	}

	@RequestMapping(value = "/layoutgroupdrop", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String layoutGroupDrop(@RequestParam(value = "groupid") Integer groupId) {

		AdminJson layoutGroupDrop = adminJsonService.layoutGroupDrop(groupId);

		if (layoutGroupDrop == null) {
			return null;
		}
		return layoutGroupDrop.toJson();

	}

	@RequestMapping(value = "/layoutdrop", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String layoutDrop(@RequestParam(value = "layoutid") Integer layoutId) {

		AdminJson layoutDrop = adminJsonService.layoutDrop(layoutId);

		if (layoutDrop == null) {
			return null;
		}
		return layoutDrop.toJson();

	}

	@RequestMapping(value = "/layoutsave", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String layoutSave(@RequestParam(value = "group") Integer groupId,
			@RequestParam(value = "content") String content, @RequestParam(value = "name") String name,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "id", required = false) Integer layoutId) {

		AdminJson layoutSave = adminJsonService.layoutSave(groupId, content, name, description, layoutId);

		if (layoutSave == null) {
			return null;
		}
		return layoutSave.toJson();

	}

	@RequestMapping(value = "/layoutmove", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String layoutMove(@RequestParam(value = "group") Integer groupId,
			@RequestParam(value = "id") Integer layoutId) {

		AdminJson layoutMove = adminJsonService.layoutMove(groupId, layoutId);

		if (layoutMove == null) {
			return null;
		}
		return layoutMove.toJson();

	}

	@RequestMapping(value = "/layoutgroupmove", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String layoutGroupMove(@RequestParam(value = "parent") Integer parentGroupId,
			@RequestParam(value = "group") Integer groupId) {

		AdminJson layoutGroupMove = adminJsonService.layoutGroupMove(parentGroupId, groupId);

		if (layoutGroupMove == null) {
			return null;
		}
		return layoutGroupMove.toJson();

	}

	@RequestMapping(value = "/snippetgroupsave", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String snippetGroupSave(@RequestParam(value = "groupname") String groupname,
			@RequestParam(value = "parent") Integer parentId, @RequestParam(value = "description") String description) {

		AdminJson snippetGroupSave = adminJsonService.snippetGroupSave(groupname, parentId, description);

		if (snippetGroupSave == null) {
			return null;
		}
		return snippetGroupSave.toJson();

	}

	@RequestMapping(value = "/snippetgroupempty", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String snippetGroupEmpty(@RequestParam(value = "groupid") Integer groupId) {

		AdminJson snippetGroupEmpty = adminJsonService.snippetGroupEmpty(groupId);

		if (snippetGroupEmpty == null) {
			return null;
		}
		return snippetGroupEmpty.toJson();

	}

	@RequestMapping(value = "/snippetgroupdrop", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String snippetGroupDrop(@RequestParam(value = "groupid") Integer groupId) {

		AdminJson snippetGroupDrop = adminJsonService.snippetGroupDrop(groupId);

		if (snippetGroupDrop == null) {
			return null;
		}
		return snippetGroupDrop.toJson();

	}

	@RequestMapping(value = "/snippetdrop", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String snippetDrop(@RequestParam(value = "snippetid") Integer snippetId) {

		AdminJson snippetDrop = adminJsonService.snippetDrop(snippetId);

		if (snippetDrop == null) {
			return null;
		}
		return snippetDrop.toJson();

	}

	@RequestMapping(value = "/snippetsave", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String snippetSave(@RequestParam(value = "group") Integer groupId,
			@RequestParam(value = "content") String content, @RequestParam(value = "name") String name,
			@RequestParam(value = "id", required = false) Integer snippetId) {

		AdminJson snippetSave = adminJsonService.snippetSave(groupId, content, name, snippetId);

		if (snippetSave == null) {
			return null;
		}
		return snippetSave.toJson();

	}

	@RequestMapping(value = "/snippetmove", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String snippetMove(@RequestParam(value = "group") Integer groupId,
			@RequestParam(value = "id") Integer snippetId) {

		AdminJson snippetMove = adminJsonService.snippetMove(groupId, snippetId);

		if (snippetMove == null) {
			return null;
		}
		return snippetMove.toJson();

	}

	@RequestMapping(value = "/snippetgroupmove", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String snippetGroupMove(@RequestParam(value = "parent") Integer parentGroupId,
			@RequestParam(value = "group") Integer groupId) {

		AdminJson snippetGroupMove = adminJsonService.snippetGroupMove(parentGroupId, groupId);

		if (snippetGroupMove == null) {
			return null;
		}
		return snippetGroupMove.toJson();

	}

	@RequestMapping(value = "/cmsfoldersave", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String folderSave(@RequestParam(value = "foldername") String foldername,
			@RequestParam(value = "parent") Integer parentId, @RequestParam(value = "description") String description) {

		AdminJson folderSave = adminJsonService.folderSave(foldername, parentId, description);

		if (folderSave == null) {
			return null;
		}
		return folderSave.toJson();

	}

	@RequestMapping(value = "/cmsfolderempty", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String folderEmpty(@RequestParam(value = "folderid") Integer folderId) {

		AdminJson folderEmpty = adminJsonService.folderEmpty(folderId);

		if (folderEmpty == null) {
			return null;
		}
		return folderEmpty.toJson();

	}

	@RequestMapping(value = "/cmsfolderdrop", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String folderDrop(@RequestParam(value = "folderid") Integer folderId) {

		AdminJson folderDrop = adminJsonService.folderDrop(folderId);

		if (folderDrop == null) {
			return null;
		}
		return folderDrop.toJson();

	}

	@RequestMapping(value = "/cmsfiledrop", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String fileDrop(@RequestParam(value = "fileid") Integer fileId) {

		AdminJson fileDrop = adminJsonService.fileDrop(fileId);

		if (fileDrop == null) {
			return null;
		}
		return fileDrop.toJson();

	}

	@RequestMapping(value = "/cmsfilesave", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String fileSave(@RequestParam("folderid") Integer folderId, @RequestParam("content") MultipartFile content,
			@RequestParam(value = "alias", required = false) String alias,
			@RequestParam(value = "id", required = false) Integer fileId,
			@RequestParam(value = "url", required = false) String url, HttpServletRequest httpServletRequest) {
		log.trace("> fileSave controller: " + folderId + " ; " + fileId + " ; " + alias);
		AdminJson fileSave = adminJsonService.fileSave(folderId, content, fileId, alias, url);

		if (fileSave == null) {
			return null;
		}

		return fileSave.toJson();
	}

	@RequestMapping(value = "/cmsjsonsave", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String jsonSave(@RequestBody String jsonString, @RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "id", required = false) Integer id, HttpServletRequest httpServletRequest) {
		log.trace("> jsonSave controller: " + id + " ; " + name);
		AdminJson jsonSave = adminJsonService.jsonSave(jsonString, id, name);

		if (jsonSave == null) {
			return null;
		}

		return jsonSave.toJson();
	}

	@RequestMapping(value = "/cmsfilemove", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String fileMove(@RequestParam(value = "folder") Integer folderId, @RequestParam(value = "id") Integer fileId) {

		AdminJson fileMove = adminJsonService.fileMove(folderId, fileId);

		if (fileMove == null) {
			return null;
		}
		return fileMove.toJson();

	}

	@RequestMapping(value = "/cmsfoldermove", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String folderMove(@RequestParam(value = "parent") Integer parentGroupId,
			@RequestParam(value = "folder") Integer folderId) {
		AdminJson folderMove = adminJsonService.folderMove(parentGroupId, folderId);
		if (folderMove == null) {
			return null;
		}
		return folderMove.toJson();
	}

	@RequestMapping(value = "/cmspagesave", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String cmspageSave(@RequestParam(value = "title") String title,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "lang", required = false) String lang,
			@RequestParam(value = "menutitle", required = false) String menutitle,
			@RequestParam(value = "synopsis", required = false) String synopsis,
			@RequestParam(value = "target", required = false) String target,
			@RequestParam(value = "url", required = false) String url,
			@RequestParam(value = "default", required = false) boolean defaultPage,
			@RequestParam(value = "externalredirect", required = false) String externalredirect,
			@RequestParam(value = "internalredirect", required = false) String internalredirect,
			@RequestParam(value = "layout", required = false) Integer layoutId,
			@RequestParam(value = "cacheable", required = false) Integer cacheable,
			@RequestParam(value = "published", required = false) boolean published,
			@RequestParam(value = "navigable", required = false) boolean navigable,
			@RequestParam(value = "pagetype", required = false) String pageType,
			@RequestParam(value = "parentid", required = false) Integer parent,
			@RequestParam(value = "content", required = false) String content) {

		AdminJson cmspageSave = adminJsonService.cmsPageSave(true, parent, title, lang, menutitle, synopsis, target,
				url, defaultPage, externalredirect, internalredirect, layoutId, cacheable, published, navigable,
				pageType, id, content);

		if (cmspageSave == null) {
			return null;
		}
		return cmspageSave.toJson();

	}

	@RequestMapping(value = "/cmspagepreview", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String cmspagePreview(@RequestParam(value = "title") String title,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "lang", required = false) String lang,
			@RequestParam(value = "menutitle", required = false) String menutitle,
			@RequestParam(value = "synopsis", required = false) String synopsis,
			@RequestParam(value = "target", required = false) String target,
			@RequestParam(value = "url", required = false) String url,
			@RequestParam(value = "default", required = false) boolean defaultPage,
			@RequestParam(value = "externalredirect", required = false) String externalredirect,
			@RequestParam(value = "internalredirect", required = false) String internalredirect,
			@RequestParam(value = "layout", required = false) Integer layoutId,
			@RequestParam(value = "cacheable", required = false) Integer cacheable,
			@RequestParam(value = "published", required = false) boolean published,
			@RequestParam(value = "navigable", required = false) boolean navigable,
			@RequestParam(value = "pagetype", required = false) String pageType,
			@RequestParam(value = "parentid", required = false) Integer parent,
			@RequestParam(value = "content", required = false) String content) {

		AdminJson cmspageSave = adminJsonService.cmsPageSave(false, parent, title, lang, menutitle, synopsis, target,
				url, defaultPage, externalredirect, internalredirect, layoutId, cacheable, published, navigable,
				pageType, id, content);

		if (cmspageSave == null) {
			return null;
		}
		return cmspageSave.toJson();

	}

	@RequestMapping(value = "/cmspagemove", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String cmspageMove(@RequestParam(value = "group", required = true) Integer groupId,
			@RequestParam(value = "id", required = true) Integer cmspageId,
			@RequestParam(value = "mode", required = true) String mode) {

		AdminJson cmspageMove = adminJsonService.cmsPageMove(groupId, cmspageId, mode);

		if (cmspageMove == null) {
			return null;
		}
		return cmspageMove.toJson();

	}

	@RequestMapping(value = "/cmspagedrop", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String cmspageDrop(@RequestParam(value = "cmspageid") Integer cmspageId) {

		AdminJson cmspageDrop = adminJsonService.cmsPageDrop(cmspageId);

		if (cmspageDrop == null) {
			return null;
		}
		return cmspageDrop.toJson();

	}

	@RequestMapping(value = "/newssave", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String newsSave(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "langId", required = true) Integer langId,
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "content", required = true) String content) {

		AdminJson newsSave = adminJsonService.newsSave(id, langId, title, content, Calendar.getInstance().getTime());

		if (newsSave == null) {
			return null;
		}
		return newsSave.toJson();

	}

}
