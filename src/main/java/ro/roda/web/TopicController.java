package ro.roda.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import ro.roda.domain.Topic;
import ro.roda.service.SeriesService;
import ro.roda.service.StudyService;
import ro.roda.service.TopicService;
import ro.roda.service.TranslatedTopicService;

@RequestMapping("/topics")
@Controller
public class TopicController {

	// @RequestMapping(value = "/tree", headers = "Accept=application/json")
	// public ResponseEntity<String> createFromJsonArray() {
	// HttpHeaders headers = new HttpHeaders();
	// headers.add("Content-Type", "application/json");
	// return new ResponseEntity<String>(Topic.toJsonByParent(null), headers,
	// HttpStatus.OK);
	// }

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		Topic topic = topicService.findTopic(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (topic == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(topic.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<Topic> result = topicService.findAllTopics();
		return new ResponseEntity<String>(Topic.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		Topic topic = Topic.fromJsonToTopic(json);
		topicService.saveTopic(topic);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (Topic topic : Topic.fromJsonArrayToTopics(json)) {
			topicService.saveTopic(topic);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		Topic topic = Topic.fromJsonToTopic(json);
		if (topicService.updateTopic(topic) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (Topic topic : Topic.fromJsonArrayToTopics(json)) {
			if (topicService.updateTopic(topic) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		Topic topic = topicService.findTopic(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (topic == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		topicService.deleteTopic(topic);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@Autowired
	TopicService topicService;

	@Autowired
	SeriesService seriesService;

	@Autowired
	StudyService studyService;

	@Autowired
	TranslatedTopicService translatedTopicService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Topic topic, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, topic);
			return "topics/create";
		}
		uiModel.asMap().clear();
		topicService.saveTopic(topic);
		return "redirect:/topics/" + encodeUrlPathSegment(topic.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new Topic());
		return "topics/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		uiModel.addAttribute("topic", topicService.findTopic(id));
		uiModel.addAttribute("itemId", id);
		return "topics/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("topics", topicService.findTopicEntries(firstResult, sizeNo));
			float nrOfPages = (float) topicService.countAllTopics() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("topics", topicService.findAllTopics());
		}
		return "topics/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Topic topic, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, topic);
			return "topics/update";
		}
		uiModel.asMap().clear();
		topicService.updateTopic(topic);
		return "redirect:/topics/" + encodeUrlPathSegment(topic.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, topicService.findTopic(id));
		return "topics/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Topic topic = topicService.findTopic(id);
		topicService.deleteTopic(topic);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/topics";
	}

	void populateEditForm(Model uiModel, Topic topic) {
		uiModel.addAttribute("topic", topic);
		uiModel.addAttribute("serieses", seriesService.findAllSerieses());
		uiModel.addAttribute("studys", studyService.findAllStudys());
		uiModel.addAttribute("topics", topicService.findAllTopics());
		uiModel.addAttribute("translatedtopics", translatedTopicService.findAllTranslatedTopics());
	}

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
		String enc = httpServletRequest.getCharacterEncoding();
		if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		try {
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
		} catch (UnsupportedEncodingException uee) {
		}
		return pathSegment;
	}
}
