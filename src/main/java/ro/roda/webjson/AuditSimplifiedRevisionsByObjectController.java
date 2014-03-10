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

import ro.roda.service.AuditRevisionsService;
import ro.roda.transformer.AuditSimplifiedRevisionsByObject;

@RequestMapping("/admin/simple-revisions-by-object")
@Controller
public class AuditSimplifiedRevisionsByObjectController {

	@Autowired
	AuditRevisionsService revisionsService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<AuditSimplifiedRevisionsByObject> result = revisionsService.findAllAuditSimplifiedRevisionsByObject();
		return new ResponseEntity<String>(AuditSimplifiedRevisionsByObject.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{object}/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("object") String object, @PathVariable("id") Integer id) {
		AuditSimplifiedRevisionsByObject auditSimplifiedRevisionsByObject = revisionsService
				.findAuditSimplifiedRevisionsByObject(object, id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (auditSimplifiedRevisionsByObject == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(auditSimplifiedRevisionsByObject.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{object}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("object") String object) {
		List<AuditSimplifiedRevisionsByObject> auditSimplifiedRevisionsByObject = revisionsService
				.findAllAuditSimplifiedRevisionsByObject(object);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (auditSimplifiedRevisionsByObject == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(
				AuditSimplifiedRevisionsByObject.toJsonArray(auditSimplifiedRevisionsByObject), headers, HttpStatus.OK);
	}

}
