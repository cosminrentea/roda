package ro.roda.webjson.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.domain.UserSettingValue;
import ro.roda.domain.Users;
import ro.roda.service.UserSettingValueService;
import ro.roda.service.UsersService;

@RequestMapping("/user/settings")
@Controller
public class UserSettingsController {

	@Autowired
	UserSettingValueService userSettingValueService;

	@Autowired
	UsersService usersService;

	private final Log log = LogFactory.getLog(this.getClass());

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> getAllUserSettings() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		List<UserSettingValue> results = null;

		String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		log.trace(username);

		// find the user (ID) and then the user's settings
		List<Users> users = usersService.findUsersByUsernameAndEnabled(username);
		if ((users != null) && (users.size() > 0)) {
			results = userSettingValueService.findAllUserSettingValuesByUser(users.get(0));
		}

		return new ResponseEntity<String>(UserSettingValue.toJsonArraySettingsOfUser(results), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> getSessionAttribute(@RequestParam(value = "name") String settingName) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		// find the user (ID) and then the user's setting
		String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		log.trace(username);

		return new ResponseEntity<String>(UserSettingValue.toJsonArraySettingsOfUser(userSettingValueService
				.findUserSettingValueByUserAndSettingName(username, settingName)), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/set", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> setUserSetting(@RequestParam(value = "name") String settingName,
			@RequestParam(value = "value") String settingValue) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		log.trace(username);
		userSettingValueService.setUserSettingValue(username, settingName);

		return new ResponseEntity<String>("{\"success\": true}", headers, HttpStatus.OK);

		// return new
		// ResponseEntity<String>(UserSettingValue.toJsonArraySettingsOfUser(results),
		// headers, HttpStatus.OK);

		// TODO !!!

		// log.trace(attributeName + " : " + attributeValue);
		// session.setAttribute(attributeName, attributeValue);

	}

}
