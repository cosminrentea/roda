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
import ro.roda.domain.StudyDocuments;
import ro.roda.domain.StudyDocumentsPK;
import ro.roda.service.StudyDocumentsService;

@RequestMapping("/studydocumentses")
@Controller
public class StudyDocumentsController {

	private ConversionService conversionService;

	@Autowired
	StudyDocumentsService studyDocumentsService;

	@Autowired
	public StudyDocumentsController(ConversionService conversionService) {
		super();
		this.conversionService = conversionService;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid StudyDocuments studyDocuments, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, studyDocuments);
			return "studydocumentses/create";
		}
		uiModel.asMap().clear();
		studyDocumentsService.saveStudyDocuments(studyDocuments);
		return "redirect:/studydocumentses/"
				+ encodeUrlPathSegment(conversionService.convert(studyDocuments.getId(), String.class),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new StudyDocuments());
		return "studydocumentses/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") StudyDocumentsPK id, Model uiModel) {
		uiModel.addAttribute("studydocuments", studyDocumentsService.findStudyDocuments(id));
		uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
		return "studydocumentses/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("studydocumentses",
					studyDocumentsService.findStudyDocumentsEntries(firstResult, sizeNo));
			float nrOfPages = (float) studyDocumentsService.countAllStudyDocumentses() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("studydocumentses", studyDocumentsService.findAllStudyDocumentses());
		}
		return "studydocumentses/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid StudyDocuments studyDocuments, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, studyDocuments);
			return "studydocumentses/update";
		}
		uiModel.asMap().clear();
		studyDocumentsService.updateStudyDocuments(studyDocuments);
		return "redirect:/studydocumentses/"
				+ encodeUrlPathSegment(conversionService.convert(studyDocuments.getId(), String.class),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") StudyDocumentsPK id, Model uiModel) {
		populateEditForm(uiModel, studyDocumentsService.findStudyDocuments(id));
		return "studydocumentses/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") StudyDocumentsPK id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		StudyDocuments studyDocuments = studyDocumentsService.findStudyDocuments(id);
		studyDocumentsService.deleteStudyDocuments(studyDocuments);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/studydocumentses";
	}

	void populateEditForm(Model uiModel, StudyDocuments studyDocuments) {
		uiModel.addAttribute("studyDocuments", studyDocuments);
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
	public ResponseEntity<String> showJson(@PathVariable("id") StudyDocumentsPK id) {
		StudyDocuments studyDocuments = studyDocumentsService.findStudyDocuments(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (studyDocuments == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(studyDocuments.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<StudyDocuments> result = studyDocumentsService.findAllStudyDocumentses();
		return new ResponseEntity<String>(StudyDocuments.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		StudyDocuments studyDocuments = StudyDocuments.fromJsonToStudyDocuments(json);
		studyDocumentsService.saveStudyDocuments(studyDocuments);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (StudyDocuments studyDocuments : StudyDocuments.fromJsonArrayToStudyDocumentses(json)) {
			studyDocumentsService.saveStudyDocuments(studyDocuments);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		StudyDocuments studyDocuments = StudyDocuments.fromJsonToStudyDocuments(json);
		if (studyDocumentsService.updateStudyDocuments(studyDocuments) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (StudyDocuments studyDocuments : StudyDocuments.fromJsonArrayToStudyDocumentses(json)) {
			if (studyDocumentsService.updateStudyDocuments(studyDocuments) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") StudyDocumentsPK id) {
		StudyDocuments studyDocuments = studyDocumentsService.findStudyDocuments(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (studyDocuments == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		studyDocumentsService.deleteStudyDocuments(studyDocuments);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
