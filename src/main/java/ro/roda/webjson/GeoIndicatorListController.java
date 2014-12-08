package ro.roda.webjson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.domainjson.GeoIndicatorList;
import ro.roda.service.GeoIndicatorListService;

@RequestMapping("/geoindicatorlist")
@Controller
public class GeoIndicatorListController {

	@Autowired
	GeoIndicatorListService geoIndicatorListService;

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		GeoIndicatorList geoIndicatorList = geoIndicatorListService.findGeoIndicatorList(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (geoIndicatorList == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(geoIndicatorList.toJson(), headers, HttpStatus.OK);
	}

}
