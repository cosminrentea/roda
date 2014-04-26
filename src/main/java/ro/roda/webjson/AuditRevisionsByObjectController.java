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

import ro.roda.domainjson.AuditRevisionsByObject;
import ro.roda.service.AuditRevisionsService;

@RequestMapping("/admin/revisions-by-object")
@Controller
public class AuditRevisionsByObjectController {

	@Autowired
	AuditRevisionsService revisionsService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<AuditRevisionsByObject> result = revisionsService.findAllAuditRevisionsByObject();
		return new ResponseEntity<String>(AuditRevisionsByObject.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{object}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("object") String object) {
		AuditRevisionsByObject auditRevisionsByObject = revisionsService.findAuditRevisionsByObject(object);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (auditRevisionsByObject == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(auditRevisionsByObject.toJson(), headers, HttpStatus.OK);
	}

}
