package ro.roda.webjson;

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
import ro.roda.transformer.UserMessages;

@RequestMapping("/admin/usermessages")
@Controller
public class UserMessagesController {

	@Autowired
	UserManagementService userManagementService;

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listUserMessagesJson(@PathVariable("id") Integer id) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<UserMessages> result = userManagementService.findUserMessages(id);
		return new ResponseEntity<String>(UserMessages.toJsonArray(result), headers, HttpStatus.OK);
	}

}
