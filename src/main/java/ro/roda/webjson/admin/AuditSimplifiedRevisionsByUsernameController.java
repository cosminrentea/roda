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

import ro.roda.domainjson.AuditSimplifiedRevisionsByUsername;
import ro.roda.service.AuditRevisionsService;

@RequestMapping("/adminjson/simple-revisions-by-user")
@Controller
public class AuditSimplifiedRevisionsByUsernameController {

	@Autowired
	AuditRevisionsService revisionsService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<AuditSimplifiedRevisionsByUsername> result = revisionsService.findAllAuditSimplifiedRevisionsByUsername();
		return new ResponseEntity<String>(AuditSimplifiedRevisionsByUsername.toJsonArray(result), headers,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/{username}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("username") String username) {
		AuditSimplifiedRevisionsByUsername auditSimplifiedRevisionsByUsername = revisionsService
				.findAuditSimplifiedRevisionsByUsername(username);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (auditSimplifiedRevisionsByUsername == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(auditSimplifiedRevisionsByUsername.toJson(), headers, HttpStatus.OK);
	}

}
