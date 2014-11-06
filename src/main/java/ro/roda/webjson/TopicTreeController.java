package ro.roda.webjson;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.domain.TranslatedTopic;

@RequestMapping("/topics")
@Controller
public class TopicTreeController {

	@RequestMapping(value = "/tree", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> tree(@RequestParam(value = "node", required = false) String id) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(TranslatedTopic.toJsonByParent(id), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/tree-relevant", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> treeRelevant(@RequestParam(value = "node", required = false) String id) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(TranslatedTopic.toJsonRelevantTree(), headers, HttpStatus.OK);
	}

}
