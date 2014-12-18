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

import ro.roda.domainjson.DdiEditorCountryList;
import ro.roda.service.DdiEditorCountryListService;

@RequestMapping("/ddieditorcountrylist")
@Controller
public class DdiEditorCountryListController {

	@Autowired
	DdiEditorCountryListService ddiEditorCountryListService;

	// @Autowired
	// CatalogStudyService catalogStudyService;

	// @Autowired
	// SeriesService seriesService;

	// @Autowired
	// UsersService usersService;

	// TODO manage the language of the country name
	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<DdiEditorCountryList> result = ddiEditorCountryListService.findAllCountries();
		return new ResponseEntity<String>(DdiEditorCountryList.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		DdiEditorCountryList ddiEditorCountryList = ddiEditorCountryListService.findCountry(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (ddiEditorCountryList == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(ddiEditorCountryList.toJson(), headers, HttpStatus.OK);
	}

}
