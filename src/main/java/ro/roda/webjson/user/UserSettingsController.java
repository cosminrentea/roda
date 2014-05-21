package ro.roda.webjson.user;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.domain.UserSetting;
import ro.roda.service.UserSettingService;

@RequestMapping("/user/settings")
@Controller
public class UserSettingsController {

	@Autowired
	UserSettingService userSettingService;

	private final Log log = LogFactory.getLog(this.getClass());

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> getAllSettings() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<UserSetting> result = userSettingService.findAllUserSettings();
		return new ResponseEntity<String>(UserSetting.toJsonArray(result), headers, HttpStatus.OK);
	}
}
