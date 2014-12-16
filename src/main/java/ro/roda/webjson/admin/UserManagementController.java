package ro.roda.webjson.admin;

import java.sql.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.domainjson.AdminJson;
import ro.roda.service.UserManagementService;

@RequestMapping("/adminjson")
@Controller
public class UserManagementController {

	@Autowired
	UserManagementService umService;

	private final Log log = LogFactory.getLog(this.getClass());

	// User management - POST methods

	@RequestMapping(value = "/usercreate", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String userCreate(@RequestParam(value = "authority") String authorityName,
			@RequestParam(value = "username") String username, @RequestParam(value = "password") String password,
			@RequestParam(value = "passwordcheck") String passwordCheck,
			@RequestParam(value = "enabled", defaultValue = "true") Boolean enabled,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "firstname", required = false) String firstname,
			@RequestParam(value = "middlename", required = false) String middlename,
			@RequestParam(value = "lastname", required = false) String lastname,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "sex", required = false) String sex,
			@RequestParam(value = "salutation", required = false) String salutation,
			@RequestParam(value = "birthdate", required = false) Date birthdate,
			@RequestParam(value = "address1", required = false) String address1,
			@RequestParam(value = "address2", required = false) String address2) {
		return umService.userCreate(authorityName, username, password, passwordCheck, enabled, email, firstname,
				middlename, lastname, title, sex, salutation, birthdate, address1, address2).toJsonWithId();
	}

	@RequestMapping(value = "/usersave", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String userSave(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "firstname", required = false) String firstname,
			@RequestParam(value = "middlename", required = false) String middlename,
			@RequestParam(value = "lastname", required = false) String lastname,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "sex", required = false) String sex,
			@RequestParam(value = "salutation", required = false) String salutation,
			@RequestParam(value = "birthdate", required = false) Date birthdate,
			@RequestParam(value = "address1", required = false) String address1,
			@RequestParam(value = "address2", required = false) String address2) {
		return umService.userSave(id, username, email, firstname, middlename, lastname, title, sex, salutation,
				birthdate, address1, address2).toJsonWithId();
	}

	@RequestMapping(value = "/userdrop", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String userDrop(@RequestParam(value = "userid") Integer userId) {
		AdminJson response = umService.userDrop(userId);
		if (response == null) {
			return null;
		}
		return response.toJson();
	}

	@RequestMapping(value = "/useraddauthority", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String userAddAuthority(@RequestParam(value = "userid") Integer userId,
			@RequestParam(value = "authority") String authorityName) {
		return umService.userAddAuthority(userId, authorityName).toJson();
	}

	@RequestMapping(value = "/userremoveauthority", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String userRemoveAuthority(@RequestParam(value = "userid") Integer userId,
			@RequestParam(value = "authority") String authorityName) {
		return umService.userRemoveAuthority(userId, authorityName).toJson();
	}

	@RequestMapping(value = "/userenable", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String userEnable(@RequestParam(value = "userid") Integer userId) {
		AdminJson response = umService.userEnable(userId);
		if (response == null) {
			return null;
		}
		return response.toJson();
	}

	@RequestMapping(value = "/userdisable", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String userDisable(@RequestParam(value = "userid") Integer userId) {
		AdminJson response = umService.userDisable(userId);
		if (response == null) {
			return null;
		}
		return response.toJson();
	}

	@RequestMapping(value = "/userchangepassword", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String userChangePassword(@RequestParam(value = "userid") Integer userId,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "passwordcheck") String passwordCheck) {
		AdminJson response = umService.userChangePassword(userId, password, passwordCheck);
		if (response == null) {
			return null;
		}
		return response.toJson();
	}

	@RequestMapping(value = "/usermessage", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String userMessage(@RequestParam(value = "userid") Integer userId,
			@RequestParam(value = "subject") String subject, @RequestParam(value = "message") String message) {
		AdminJson response = umService.userMessage(userId, subject, message);
		if (response == null) {
			return null;
		}
		return response.toJson();
	}

	@RequestMapping(value = "/groupmessage", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String groupMessage(@RequestParam(value = "authority") String authorityName,
			@RequestParam(value = "subject") String subject, @RequestParam(value = "message") String message) {
		AdminJson response = umService.groupMessage(authorityName, subject, message);
		if (response == null) {
			return null;
		}
		return response.toJson();
	}

}
