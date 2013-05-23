package ro.roda.web;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
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
import ro.roda.domain.TranslatedTopic;
import ro.roda.domain.TranslatedTopicPK;
import ro.roda.service.LangService;
import ro.roda.service.TopicService;
import ro.roda.service.TranslatedTopicService;

@RequestMapping("/translatedtopics")
@Controller
public class TranslatedTopicController {

	private ConversionService conversionService;

	@Autowired
	TranslatedTopicService translatedTopicService;

	@Autowired
	LangService langService;

	@Autowired
	TopicService topicService;

	@Autowired
	public TranslatedTopicController(ConversionService conversionService) {
		super();
		this.conversionService = conversionService;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid TranslatedTopic translatedTopic, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, translatedTopic);
			return "translatedtopics/create";
		}
		uiModel.asMap().clear();
		translatedTopicService.saveTranslatedTopic(translatedTopic);
		return "redirect:/translatedtopics/"
				+ encodeUrlPathSegment(conversionService.convert(translatedTopic.getId(), String.class),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new TranslatedTopic());
		return "translatedtopics/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") TranslatedTopicPK id, Model uiModel) {
		uiModel.addAttribute("translatedtopic", translatedTopicService.findTranslatedTopic(id));
		uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
		return "translatedtopics/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("translatedtopics",
					translatedTopicService.findTranslatedTopicEntries(firstResult, sizeNo));
			float nrOfPages = (float) translatedTopicService.countAllTranslatedTopics() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("translatedtopics", translatedTopicService.findAllTranslatedTopics());
		}
		return "translatedtopics/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid TranslatedTopic translatedTopic, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, translatedTopic);
			return "translatedtopics/update";
		}
		uiModel.asMap().clear();
		translatedTopicService.updateTranslatedTopic(translatedTopic);
		return "redirect:/translatedtopics/"
				+ encodeUrlPathSegment(conversionService.convert(translatedTopic.getId(), String.class),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") TranslatedTopicPK id, Model uiModel) {
		populateEditForm(uiModel, translatedTopicService.findTranslatedTopic(id));
		return "translatedtopics/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") TranslatedTopicPK id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		TranslatedTopic translatedTopic = translatedTopicService.findTranslatedTopic(id);
		translatedTopicService.deleteTranslatedTopic(translatedTopic);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/translatedtopics";
	}

	void populateEditForm(Model uiModel, TranslatedTopic translatedTopic) {
		uiModel.addAttribute("translatedTopic", translatedTopic);
		uiModel.addAttribute("langs", langService.findAllLangs());
		uiModel.addAttribute("topics", topicService.findAllTopics());
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

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") TranslatedTopicPK id) {
		TranslatedTopic translatedTopic = translatedTopicService.findTranslatedTopic(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (translatedTopic == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(translatedTopic.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<TranslatedTopic> result = translatedTopicService.findAllTranslatedTopics();
		return new ResponseEntity<String>(TranslatedTopic.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		TranslatedTopic translatedTopic = TranslatedTopic.fromJsonToTranslatedTopic(json);
		translatedTopicService.saveTranslatedTopic(translatedTopic);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (TranslatedTopic translatedTopic : TranslatedTopic.fromJsonArrayToTranslatedTopics(json)) {
			translatedTopicService.saveTranslatedTopic(translatedTopic);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		TranslatedTopic translatedTopic = TranslatedTopic.fromJsonToTranslatedTopic(json);
		if (translatedTopicService.updateTranslatedTopic(translatedTopic) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (TranslatedTopic translatedTopic : TranslatedTopic.fromJsonArrayToTranslatedTopics(json)) {
			if (translatedTopicService.updateTranslatedTopic(translatedTopic) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") TranslatedTopicPK id) {
		TranslatedTopic translatedTopic = translatedTopicService.findTranslatedTopic(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (translatedTopic == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		translatedTopicService.deleteTranslatedTopic(translatedTopic);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
