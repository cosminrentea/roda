package ro.roda.webjson.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.domainjson.UserGroupInfo;
import ro.roda.service.UserManagementService;

@RequestMapping("/adminjson/groupinfo")
@Controller
public class UserGroupInfoController {

	@Autowired
	UserManagementService userManagementService;

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showUserGroupInfoJson(@PathVariable("id") Integer id) {

		UserGroupInfo userGroupInfo = userManagementService.findUserGroupInfo(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (userGroupInfo == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(userGroupInfo.toJson(), headers, HttpStatus.OK);
	}
}
