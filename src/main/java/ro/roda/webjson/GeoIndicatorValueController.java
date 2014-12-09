package ro.roda.webjson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.domainjson.GeoIndicatorValue;
import ro.roda.service.GeoIndicatorValueService;

@RequestMapping("/geoindicatorvalue")
@Controller
public class GeoIndicatorValueController {

	@Autowired
	GeoIndicatorValueService geoIndicatorValueService;

	@RequestMapping(value = "/{geographyId}/{indicatorId}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("geographyId") Integer geographyId,
			@PathVariable("indicatorId") Integer indicatorId) {
		GeoIndicatorValue geoIndicatorValue = geoIndicatorValueService.findGeoIndicatorValue(geographyId, indicatorId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (geoIndicatorValue == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(geoIndicatorValue.toJson(), headers, HttpStatus.OK);
	}

}
