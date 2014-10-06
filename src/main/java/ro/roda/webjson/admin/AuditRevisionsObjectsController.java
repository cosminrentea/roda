package ro.roda.webjson.admin;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.domainjson.AuditRevisionsObject;
import ro.roda.service.AuditRevisionsService;

@RequestMapping("/adminjson/revised-objects")
@Controller
public class AuditRevisionsObjectsController {

	@Autowired
	AuditRevisionsService revisionsService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Set<AuditRevisionsObject> result = revisionsService.findAllAuditRevisionsObjects();
		return new ResponseEntity<String>(AuditRevisionsObject.toJsonArray(result), headers, HttpStatus.OK);
	}
}
