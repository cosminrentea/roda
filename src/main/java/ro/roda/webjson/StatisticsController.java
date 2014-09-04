package ro.roda.webjson;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.service.StatisticsService;

@RequestMapping("/statistics")
@Controller
public class StatisticsController {

	@Autowired
	StatisticsService statisticsService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@RequestParam(value = "variable1") Long id1,
			@RequestParam(value = "variable2", required = false) Long id2,
			@RequestParam(value = "operation", required = false) String operation) {

		List<Long> varList = new ArrayList<Long>();
		varList.add(id1);
		if (id2 != null) {
			varList.add(id2);
		}
		String json = statisticsService.getStatisticsJson(operation, varList);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (json == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(json, headers, HttpStatus.OK);
	}

}
