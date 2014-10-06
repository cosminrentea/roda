package ro.roda.webjson.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.domainjson.UserGroupList;
import ro.roda.service.UserManagementService;

@RequestMapping("/adminjson/grouplist")
@Controller
public class UserGroupListController {

	@Autowired
	UserManagementService userManagementService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listUserGroupListJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<UserGroupList> result = userManagementService.findAllUserGroupLists();
		return new ResponseEntity<String>(UserGroupList.toJsonArr(result), headers, HttpStatus.OK);
	}

}
