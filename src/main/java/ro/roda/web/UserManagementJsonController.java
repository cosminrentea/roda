package ro.roda.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.service.UserManagementService;
import ro.roda.transformer.UserGroupInfo;
import ro.roda.transformer.UserGroupList;
import ro.roda.transformer.UserList;
import ro.roda.transformer.UsersByGroup;

@RequestMapping("/admin")
@Controller
public class UserManagementJsonController {

	@Autowired
	UserManagementService userManagementService;

	@RequestMapping(value = "/grouplist", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listUserGroupListJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<UserGroupList> result = userManagementService.findAllUserGroupLists();
		return new ResponseEntity<String>(UserGroupList.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/groupinfo/{id}", headers = "Accept=application/json")
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

	@RequestMapping(value = "/userslist", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listUserListJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<UserList> result = userManagementService.findAllUserLists();
		return new ResponseEntity<String>(UserList.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/usersbygroup/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listUsersByGroupJson(@PathVariable("id") Integer id) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<UsersByGroup> result = userManagementService.findUsersByGroup(id);
		return new ResponseEntity<String>(UsersByGroup.toJsonArray(result), headers, HttpStatus.OK);
	}

}
