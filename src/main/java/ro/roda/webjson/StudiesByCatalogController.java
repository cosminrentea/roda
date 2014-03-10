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

import ro.roda.service.StudiesByCatalogService;
import ro.roda.transformer.StudiesByCatalog;

@RequestMapping("/studiesbycatalog")
@Controller
public class StudiesByCatalogController {

	@Autowired
	StudiesByCatalogService studiesByCatalogService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<StudiesByCatalog> result = studiesByCatalogService.findAllStudiesByCatalog();
		return new ResponseEntity<String>(StudiesByCatalog.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		StudiesByCatalog studiesByCatalog = studiesByCatalogService.findStudiesByCatalog(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (studiesByCatalog == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(studiesByCatalog.toJson(), headers, HttpStatus.OK);
	}

}
