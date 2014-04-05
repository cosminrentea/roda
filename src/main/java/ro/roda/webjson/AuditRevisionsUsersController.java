package ro.roda.webjson;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.service.AuditRevisionsService;
import ro.roda.transformer.AuditRevisionsUser;

@RequestMapping("/admin/revised-users")
@Controller
public class AuditRevisionsUsersController {

	@Autowired
	AuditRevisionsService revisionsService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Set<AuditRevisionsUser> result = revisionsService.findAllAuditRevisionsUsers();
		return new ResponseEntity<String>(AuditRevisionsUser.toJsonArray(result), headers, HttpStatus.OK);
	}
}
