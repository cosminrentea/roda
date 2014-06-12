package ro.roda.webjson.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.domainjson.UserList;
import ro.roda.service.UserManagementService;

@RequestMapping("/admin/userslist")
@Controller
public class UsersListController {

	@Autowired
	UserManagementService userManagementService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listUserListJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<UserList> result = userManagementService.findAllUserLists();
		return new ResponseEntity<String>(UserList.toJsonArray(result), headers, HttpStatus.OK);
	}
}
