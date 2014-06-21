package ro.roda.webjson;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ro.roda.domain.Topic;

@RequestMapping("/topics")
@Controller
public class TopicTreeController {

	@RequestMapping(value = "/tree", headers = "Accept=application/json")
	public ResponseEntity<String> tree() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(Topic.toJsonTree(), headers, HttpStatus.OK);
	}
}
