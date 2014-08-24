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

import ro.roda.domainjson.DdiEditorCityList;
import ro.roda.service.DdiEditorCityListService;

@RequestMapping("/ddieditorcitylist")
@Controller
public class DdiEditorCityListController {

	@Autowired
	DdiEditorCityListService ddiEditorCityListService;

	// @Autowired
	// CatalogStudyService catalogStudyService;

	// @Autowired
	// SeriesService seriesService;

	// @Autowired
	// UsersService usersService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<DdiEditorCityList> result = ddiEditorCityListService.findAllCities();
		return new ResponseEntity<String>(DdiEditorCityList.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		DdiEditorCityList ddiEditorCityList = ddiEditorCityListService.findCity(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (ddiEditorCityList == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(ddiEditorCityList.toJson(), headers, HttpStatus.OK);
	}

}
