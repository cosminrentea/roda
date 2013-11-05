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

import ro.roda.domain.StudyPerson;
import ro.roda.domain.StudyPersonPK;
import ro.roda.service.PersonService;
import ro.roda.service.StudyPersonAssocService;
import ro.roda.service.StudyPersonService;
import ro.roda.service.StudyService;

@RequestMapping("/studypeople")
@Controller
public class StudyPersonController {

	private ConversionService conversionService;

	@Autowired
	StudyPersonService studyPersonService;

	@Autowired
	PersonService personService;

	@Autowired
	StudyService studyService;

	@Autowired
	StudyPersonAssocService studyPersonAssocService;

	@Autowired
	public StudyPersonController(ConversionService conversionService) {
		super();
		this.conversionService = conversionService;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid StudyPerson studyPerson, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, studyPerson);
			return "studypeople/create";
		}
		uiModel.asMap().clear();
		studyPersonService.saveStudyPerson(studyPerson);
		return "redirect:/studypeople/"
				+ encodeUrlPathSegment(conversionService.convert(studyPerson.getId(), String.class), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new StudyPerson());
		return "studypeople/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") StudyPersonPK id, Model uiModel) {
		uiModel.addAttribute("studyperson", studyPersonService.findStudyPerson(id));
		uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
		return "studypeople/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("studypeople", studyPersonService.findStudyPersonEntries(firstResult, sizeNo));
			float nrOfPages = (float) studyPersonService.countAllStudypeople() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("studypeople", studyPersonService.findAllStudypeople());
		}
		return "studypeople/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid StudyPerson studyPerson, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, studyPerson);
			return "studypeople/update";
		}
		uiModel.asMap().clear();
		studyPersonService.updateStudyPerson(studyPerson);
		return "redirect:/studypeople/"
				+ encodeUrlPathSegment(conversionService.convert(studyPerson.getId(), String.class), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") StudyPersonPK id, Model uiModel) {
		populateEditForm(uiModel, studyPersonService.findStudyPerson(id));
		return "studypeople/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") StudyPersonPK id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		StudyPerson studyPerson = studyPersonService.findStudyPerson(id);
		studyPersonService.deleteStudyPerson(studyPerson);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/studypeople";
	}

	void populateEditForm(Model uiModel, StudyPerson studyPerson) {
		uiModel.addAttribute("studyPerson", studyPerson);
		uiModel.addAttribute("people", personService.findAllPeople());
		uiModel.addAttribute("studys", studyService.findAllStudys());
		uiModel.addAttribute("studypersonassocs", studyPersonAssocService.findAllStudyPersonAssocs());
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
	public ResponseEntity<String> showJson(@PathVariable("id") StudyPersonPK id) {
		StudyPerson studyPerson = studyPersonService.findStudyPerson(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (studyPerson == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(studyPerson.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<StudyPerson> result = studyPersonService.findAllStudypeople();
		return new ResponseEntity<String>(StudyPerson.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		StudyPerson studyPerson = StudyPerson.fromJsonToStudyPerson(json);
		studyPersonService.saveStudyPerson(studyPerson);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (StudyPerson studyPerson : StudyPerson.fromJsonArrayToStudypeople(json)) {
			studyPersonService.saveStudyPerson(studyPerson);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		StudyPerson studyPerson = StudyPerson.fromJsonToStudyPerson(json);
		if (studyPersonService.updateStudyPerson(studyPerson) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (StudyPerson studyPerson : StudyPerson.fromJsonArrayToStudypeople(json)) {
			if (studyPersonService.updateStudyPerson(studyPerson) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") StudyPersonPK id) {
		StudyPerson studyPerson = studyPersonService.findStudyPerson(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (studyPerson == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		studyPersonService.deleteStudyPerson(studyPerson);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
