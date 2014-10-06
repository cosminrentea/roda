package ro.roda.webjson.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.domainjson.UserInfo;
import ro.roda.service.UserInfoService;

@RequestMapping("/adminjson/userinfo")
@Controller
public class UserInfoController {

	@Autowired
	UserInfoService userInfoService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<UserInfo> result = userInfoService.findAllUserInfos();
		return new ResponseEntity<String>(UserInfo.toJsonArr(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		UserInfo userInfo = userInfoService.findUserInfo(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (userInfo == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(userInfo.toJson(), headers, HttpStatus.OK);
	}

}
