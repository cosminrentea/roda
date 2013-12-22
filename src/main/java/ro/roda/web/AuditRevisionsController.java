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

import ro.roda.service.AuditRevisionsService;
import ro.roda.transformer.AuditRevisions;

@RequestMapping("/admin/revisions")
@Controller
public class AuditRevisionsController {

	@Autowired
	AuditRevisionsService revisionsService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<AuditRevisions> result = revisionsService.findAllRevisions();
		return new ResponseEntity<String>(AuditRevisions.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		AuditRevisions revisions = revisionsService.findRevisions(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (revisions == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(revisions.toJson(), headers, HttpStatus.OK);
	}

}
