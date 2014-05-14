package ro.roda.webjson;

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

import ro.roda.domain.Setting;
import ro.roda.service.SettingService;

@RequestMapping("/admin/settings")
@Controller
public class SettingsController {

	@Autowired
	SettingService settingService;

	private final Log log = LogFactory.getLog(this.getClass());

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> getAllSettings() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<Setting> result = settingService.findAllSettings();
		return new ResponseEntity<String>(Setting.toJsonArray(result), headers, HttpStatus.OK);
	}
}
