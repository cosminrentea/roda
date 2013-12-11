package ro.roda.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.service.AdminJsonService;
import ro.roda.transformer.AdminJson;

@RequestMapping("/admin")
@Controller
public class AdminJsonController {

	@Autowired
	AdminJsonService adminJsonService;
	
	private final Log log = LogFactory.getLog(this.getClass());

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

	@RequestMapping(value = "/foldersave", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String folderSave(@RequestParam(value = "foldername") String foldername,
			@RequestParam(value = "parent") Integer parentId, @RequestParam(value = "description") String description) {

		AdminJson folderSave = adminJsonService.folderSave(foldername, parentId, description);

		if (folderSave == null) {
			return null;
		}
		return folderSave.toJson();

	}

	@RequestMapping(value = "/folderempty", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String folderEmpty(@RequestParam(value = "folderid") Integer folderId) {

		AdminJson folderEmpty = adminJsonService.folderEmpty(folderId);

		if (folderEmpty == null) {
			return null;
		}
		return folderEmpty.toJson();

	}

	@RequestMapping(value = "/folderdrop", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String folderDrop(@RequestParam(value = "folderid") Integer folderId) {

		AdminJson folderDrop = adminJsonService.folderDrop(folderId);

		if (folderDrop == null) {
			return null;
		}
		return folderDrop.toJson();

	}

	@RequestMapping(value = "/filedrop", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String fileDrop(@RequestParam(value = "fileid") Integer fileId) {

		AdminJson fileDrop = adminJsonService.fileDrop(fileId);

		if (fileDrop == null) {
			return null;
		}
		return fileDrop.toJson();

	}

	@RequestMapping(value = "/filesave", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String fileSave(@RequestParam(value = "parent") Integer folderId,
			@RequestParam(value = "alias") String alias, @RequestParam(value = "id", required = false) Integer fileId) {

		AdminJson fileSave = adminJsonService.fileSave(folderId, alias, fileId);

		if (fileSave == null) {
			return null;
		}
		return fileSave.toJson();

	}

	@RequestMapping(value = "/filemove", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String fileMove(@RequestParam(value = "folder") Integer folderId, @RequestParam(value = "id") Integer fileId) {

		AdminJson fileMove = adminJsonService.fileMove(folderId, fileId);

		if (fileMove == null) {
			return null;
		}
		return fileMove.toJson();

	}

	@RequestMapping(value = "/foldermove", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String folderMove(@RequestParam(value = "parent") Integer parentGroupId,
			@RequestParam(value = "folder") Integer folderId) {

		AdminJson folderMove = adminJsonService.folderMove(parentGroupId, folderId);

		if (folderMove == null) {
			return null;
		}
		return folderMove.toJson();

	}
	
	@RequestMapping(value = "/usersave", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String userSave(@RequestParam(value = "id") Integer id,
			@RequestParam(value = "username") String username, @RequestParam(value = "email") String email,
			@RequestParam(value = "enabled") Boolean enabled) {


		//TODO Cosmin
		return null;

	}

	@RequestMapping(value = "/addusertogroup", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String addUserToGroup(@RequestParam(value = "userid") Integer userId,
			@RequestParam(value = "groupid") Integer groupId) {
		
		log.trace("addusertogroup: " + userId + " / " + groupId);
		
		AdminJson response = adminJsonService.addUserToGroup(userId, groupId);
		if (response == null) {
			return null;
		}
		return response.toJson();
	}
}
