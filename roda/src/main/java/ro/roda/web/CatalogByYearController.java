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

import ro.roda.domain.Catalog;
import ro.roda.service.CatalogByYearService;
import ro.roda.transformer.CatalogByYear;

@RequestMapping("/catalogsbyyear")
@Controller
public class CatalogByYearController {

	@Autowired
	CatalogByYearService catalogByYearService;

	//@Autowired
	//CatalogStudyService catalogStudyService;

	//@Autowired
	//SeriesService seriesService;

	//@Autowired
	//UsersService usersService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<CatalogByYear> result = catalogByYearService.findAllCatalogsByYear();
		return new ResponseEntity<String>(CatalogByYear.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		CatalogByYear catalogByYear = catalogByYearService.findCatalogByYear(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (catalogByYear == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(catalogByYear.toJson(), headers, HttpStatus.OK);
	}
	
}
