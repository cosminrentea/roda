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

import ro.roda.domain.StudyDescr;
import ro.roda.domain.StudyDescrPK;
import ro.roda.service.LangService;
import ro.roda.service.StudyDescrService;
import ro.roda.service.StudyService;

@RequestMapping("/studydescrs")
@Controller
public class StudyDescrController {

	private ConversionService conversionService;

	@Autowired
	StudyDescrService studyDescrService;

	@Autowired
	LangService langService;

	@Autowired
	StudyService studyService;

	@Autowired
	public StudyDescrController(ConversionService conversionService) {
		super();
		this.conversionService = conversionService;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid StudyDescr studyDescr, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, studyDescr);
			return "studydescrs/create";
		}
		uiModel.asMap().clear();
		studyDescrService.saveStudyDescr(studyDescr);
		return "redirect:/studydescrs/"
				+ encodeUrlPathSegment(conversionService.convert(studyDescr.getId(), String.class), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new StudyDescr());
		return "studydescrs/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") StudyDescrPK id, Model uiModel) {
		uiModel.addAttribute("studydescr", studyDescrService.findStudyDescr(id));
		uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
		return "studydescrs/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("studydescrs", studyDescrService.findStudyDescrEntries(firstResult, sizeNo));
			float nrOfPages = (float) studyDescrService.countAllStudyDescrs() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("studydescrs", studyDescrService.findAllStudyDescrs());
		}
		return "studydescrs/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid StudyDescr studyDescr, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, studyDescr);
			return "studydescrs/update";
		}
		uiModel.asMap().clear();
		studyDescrService.updateStudyDescr(studyDescr);
		return "redirect:/studydescrs/"
				+ encodeUrlPathSegment(conversionService.convert(studyDescr.getId(), String.class), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") StudyDescrPK id, Model uiModel) {
		populateEditForm(uiModel, studyDescrService.findStudyDescr(id));
		return "studydescrs/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") StudyDescrPK id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		StudyDescr studyDescr = studyDescrService.findStudyDescr(id);
		studyDescrService.deleteStudyDescr(studyDescr);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/studydescrs";
	}

	void populateEditForm(Model uiModel, StudyDescr studyDescr) {
		uiModel.addAttribute("studyDescr", studyDescr);
		uiModel.addAttribute("langs", langService.findAllLangs());
		uiModel.addAttribute("studys", studyService.findAllStudys());
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
	public ResponseEntity<String> showJson(@PathVariable("id") StudyDescrPK id) {
		StudyDescr studyDescr = studyDescrService.findStudyDescr(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (studyDescr == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(studyDescr.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<StudyDescr> result = studyDescrService.findAllStudyDescrs();
		return new ResponseEntity<String>(StudyDescr.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		StudyDescr studyDescr = StudyDescr.fromJsonToStudyDescr(json);
		studyDescrService.saveStudyDescr(studyDescr);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (StudyDescr studyDescr : StudyDescr.fromJsonArrayToStudyDescrs(json)) {
			studyDescrService.saveStudyDescr(studyDescr);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		StudyDescr studyDescr = StudyDescr.fromJsonToStudyDescr(json);
		if (studyDescrService.updateStudyDescr(studyDescr) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (StudyDescr studyDescr : StudyDescr.fromJsonArrayToStudyDescrs(json)) {
			if (studyDescrService.updateStudyDescr(studyDescr) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") StudyDescrPK id) {
		StudyDescr studyDescr = studyDescrService.findStudyDescr(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (studyDescr == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		studyDescrService.deleteStudyDescr(studyDescr);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
