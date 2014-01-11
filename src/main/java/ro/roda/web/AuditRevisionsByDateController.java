package ro.roda.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import ro.roda.transformer.AuditRevisionsByDate;

@RequestMapping("/admin/revisions-by-date")
@Controller
public class AuditRevisionsByDateController {

	@Autowired
	AuditRevisionsService revisionsService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<AuditRevisionsByDate> result = revisionsService.findAllAuditRevisionsByDate();
		return new ResponseEntity<String>(AuditRevisionsByDate.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{date}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("date") String dateString) {

		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;

		try {
			date = inputFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		AuditRevisionsByDate auditRevisionsByDate = revisionsService.findAuditRevisionsByDate(date);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (auditRevisionsByDate == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(auditRevisionsByDate.toJson(), headers, HttpStatus.OK);
	}

}
