package ro.roda.webjson.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.domainjson.GeographyByVersion;
import ro.roda.service.GeographyByVersionService;

@RequestMapping("/adminjson/geographybyversion")
@Controller
public class GeographyByVersionController {

	@Autowired
	GeographyByVersionService geographyByVersionService;

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		GeographyByVersion geographyByVersion = geographyByVersionService.findGeographiesByVersion(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (geographyByVersion == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(geographyByVersion.toJson(), headers, HttpStatus.OK);
	}

}
