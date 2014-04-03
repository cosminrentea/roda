package ro.roda.webjson;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.service.AdminJsonService;
import ro.roda.transformer.AdminJson;

@RequestMapping("/admin")
@Controller
public class AdminUserManagementController {

	@Autowired
	AdminJsonService adminJsonService;

	private final Log log = LogFactory.getLog(this.getClass());

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
	public String userRemoveFromAdmin(@RequestParam(value = "userid") Integer userId) {
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

}
