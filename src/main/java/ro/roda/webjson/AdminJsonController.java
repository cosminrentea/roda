package ro.roda.webjson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import ro.roda.domain.CmsFile;
import ro.roda.filestore.CmsFileStoreService;
import ro.roda.service.AdminJsonService;
import ro.roda.transformer.AdminJson;

@RequestMapping("/admin")
@Controller
public class AdminJsonController {

	@Autowired
	AdminJsonService adminJsonService;

	@Autowired
	CmsFileStoreService cmsFileStoreService;

	private final Log log = LogFactory.getLog(this.getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String login(@ModelAttribute("username") String username, @ModelAttribute("password") String password,
			BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		// if (bindingResult.hasErrors()) {
		// populateEditForm(uiModel, cmsFolder);
		// return "admin/login";
		// }
		// uiModel.asMap().clear();

		// only for testing:
		// username = "admin";
		// password = "e10adc3949ba59abbe56e057f20f883e";
		AdminJson login = adminJsonService.findLogin(username, password);
		// HttpHeaders headers = new HttpHeaders();
		// headers.add("Content-Type", "application/json; charset=utf-8");
		if (login == null) {
			return null;
		}
		return login.toJson();
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

	@RequestMapping(value = "/cmsfilecontent/{id}", method = RequestMethod.GET)
	public String fileContent(@PathVariable("id") Integer id, HttpServletResponse response) {
		CmsFile cmsFile = CmsFile.findCmsFile(id);
		InputStream is = cmsFileStoreService.fileLoad(cmsFile);
		try {
			if (is != null) {
				response.setHeader("Content-Disposition", "inline;filename=\"" + cmsFile.getFilename() + "\"");
				OutputStream out = response.getOutputStream();
				response.setContentType(cmsFile.getContentType());
				IOUtils.copy(is, out);
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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

	// User management - POST methods

	@RequestMapping(value = "/usersave", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String userSave(@RequestParam(value = "id") Integer id, @RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password, @RequestParam(value = "password2") String passwordCheck,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "enabled", defaultValue = "true") Boolean enabled) {
		AdminJson response = adminJsonService.userSave(id, username, password, passwordCheck, email, enabled);
		if (response == null) {
			return null;
		}
		return response.toJson();
	}

	@RequestMapping(value = "/groupsave", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String groupSave(@RequestParam(value = "id") Integer id, @RequestParam(value = "name") String name,
			@RequestParam(value = "description", defaultValue = "") String description,
			@RequestParam(value = "enabled") Boolean enabled) {
		AdminJson response = adminJsonService.groupSave(id, name, description, enabled);
		if (response == null) {
			return null;
		}
		return response.toJson();
	}

	@RequestMapping(value = "/useraddtogroup", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String userAddToGroup(@RequestParam(value = "userid") Integer userId,
			@RequestParam(value = "groupid") Integer groupId) {
		AdminJson response = adminJsonService.userAddToGroup(userId, groupId);
		if (response == null) {
			return null;
		}
		return response.toJson();
	}

	@RequestMapping(value = "/userremovefromgroup", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String userRemoveFromGroup(@RequestParam(value = "userid") Integer userId,
			@RequestParam(value = "groupid") Integer groupId) {
		AdminJson response = adminJsonService.userRemoveFromGroup(userId, groupId);
		if (response == null) {
			return null;
		}
		return response.toJson();
	}

	@RequestMapping(value = "/useraddtoadmin", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String userAddToAdmin(@RequestParam(value = "userid") Integer userId,
			@RequestParam(value = "groupid") Integer groupId) {
		// TODO make sure admin group has ID 1
		AdminJson response = adminJsonService.userAddToGroup(userId, 1);
		if (response == null) {
			return null;
		}
		return response.toJson();
	}

	@RequestMapping(value = "/userremovefromadmin", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String userRemoveFromGroup(@RequestParam(value = "userid") Integer userId) {
		// TODO make sure admin group has ID 1
		AdminJson response = adminJsonService.userRemoveFromGroup(userId, 1);
		if (response == null) {
			return null;
		}
		return response.toJson();
	}

	@RequestMapping(value = "/userenable", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String userEnable(@RequestParam(value = "userid") Integer userId) {
		AdminJson response = adminJsonService.userEnable(userId);
		if (response == null) {
			return null;
		}
		return response.toJson();
	}

	@RequestMapping(value = "/userdisable", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String userDisable(@RequestParam(value = "userid") Integer userId) {
		AdminJson response = adminJsonService.userDisable(userId);
		if (response == null) {
			return null;
		}
		return response.toJson();
	}

	@RequestMapping(value = "/userdrop", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String userDrop(@RequestParam(value = "userid") Integer userId) {
		AdminJson response = adminJsonService.userDrop(userId);
		if (response == null) {
			return null;
		}
		return response.toJson();
	}

	@RequestMapping(value = "/userchangepassword", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String userChangePassword(@RequestParam(value = "userid") Integer userId,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "controlpassword") String controlPassword) {
		AdminJson response = adminJsonService.userChangePassword(userId, password, controlPassword);
		if (response == null) {
			return null;
		}
		return response.toJson();
	}

	@RequestMapping(value = "/usermessage", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String userMessage(@RequestParam(value = "userid") Integer userId,
			@RequestParam(value = "subject") String subject, @RequestParam(value = "message") String message) {
		AdminJson response = adminJsonService.userMessage(userId, subject, message);
		if (response == null) {
			return null;
		}
		return response.toJson();
	}

	@RequestMapping(value = "/groupmessage", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String groupMessage(@RequestParam(value = "groupid") Integer groupId,
			@RequestParam(value = "subject") String subject, @RequestParam(value = "message") String message) {
		AdminJson response = adminJsonService.groupMessage(groupId, subject, message);
		if (response == null) {
			return null;
		}
		return response.toJson();
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
			@RequestParam(value = "pagetype", required = false) String pageType,
			@RequestParam(value = "parentid", required = false) Integer parent,
			@RequestParam(value = "content", required = false) String content) {

		AdminJson cmspageSave = adminJsonService.cmsPageSave(parent, title, lang, menutitle, synopsis, target, url,
				defaultPage, externalredirect, internalredirect, layoutId, cacheable, published, pageType, id, content);

		if (cmspageSave == null) {
			return null;
		}
		return cmspageSave.toJson();

	}

	@RequestMapping(value = "/cmspagemove", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String cmspageMove(@RequestParam(value = "group") Integer groupId,
			@RequestParam(value = "id") Integer cmspageId) {

		AdminJson cmspageMove = adminJsonService.cmsPageMove(groupId, cmspageId);

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

}
