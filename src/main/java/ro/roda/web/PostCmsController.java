package ro.roda.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.service.PostCmsService;
import ro.roda.transformer.GroupSave;

@RequestMapping("/admin/cmslayoutgroupsave")
@Controller
public class PostCmsController {

	@Autowired
	PostCmsService postCmsService;

	@RequestMapping(value = "/{name}/{parent}/{description}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("name") String name, @PathVariable("parent") Integer parent,
			@PathVariable("description") String description) {
		GroupSave groupSave = postCmsService.findGroupSave(name, parent, description);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (groupSave == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(groupSave.toJson(), headers, HttpStatus.OK);
	}

}
