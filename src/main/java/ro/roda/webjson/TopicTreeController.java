package ro.roda.webjson;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.domain.TranslatedTopic;
import ro.roda.domainjson.TopicTree;
import ro.roda.service.TopicTreeService;

@RequestMapping("/topictree")
@Controller
public class TopicTreeController {

	@Autowired
	TopicTreeService topicTreeService;

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson(Locale locale) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<TopicTree> result = topicTreeService.findAll(locale.getLanguage());
		return new ResponseEntity<String>(TopicTree.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id, Locale locale) {
		TopicTree result = topicTreeService.find(id, locale.getLanguage());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (result == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(result.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/all", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> treeAll(@RequestParam(value = "node", required = false) String id, Locale locale) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(TranslatedTopic.toJsonByParent(id, locale.getLanguage()), headers,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/relevant", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> treeRelevant(@RequestParam(value = "node", required = false) String parentTopicId,
			Locale locale) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(TranslatedTopic.toJsonRelevantTree(parentTopicId, locale.getLanguage()),
				headers, HttpStatus.OK);
	}

}
