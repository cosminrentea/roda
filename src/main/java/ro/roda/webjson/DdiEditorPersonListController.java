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

import ro.roda.domainjson.DdiEditorPersonList;
import ro.roda.service.DdiEditorPersonListService;

@RequestMapping("/ddieditorpersonlist")
@Controller
public class DdiEditorPersonListController {

	@Autowired
	DdiEditorPersonListService ddiEditorPersonListService;

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
		List<DdiEditorPersonList> result = ddiEditorPersonListService.findAllPersons();
		return new ResponseEntity<String>(DdiEditorPersonList.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		DdiEditorPersonList ddiEditorPersonList = ddiEditorPersonListService.findPerson(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (ddiEditorPersonList == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(ddiEditorPersonList.toJson(), headers, HttpStatus.OK);
	}

}
