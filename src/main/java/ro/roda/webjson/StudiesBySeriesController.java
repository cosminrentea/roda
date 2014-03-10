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

import ro.roda.service.StudiesBySeriesService;
import ro.roda.transformer.StudiesBySeries;

@RequestMapping("/studiesbyseries")
@Controller
public class StudiesBySeriesController {

	@Autowired
	StudiesBySeriesService studiesBySeriesService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<StudiesBySeries> result = studiesBySeriesService.findAllStudiesBySeries();
		return new ResponseEntity<String>(StudiesBySeries.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		StudiesBySeries studiesBySeries = studiesBySeriesService.findStudiesBySeries(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (studiesBySeries == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(studiesBySeries.toJson(), headers, HttpStatus.OK);
	}

}
