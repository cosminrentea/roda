package ro.roda.web;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
import ro.roda.domain.StudyPersonAssoc;
import ro.roda.service.StudyPersonAssocService;
import ro.roda.service.StudyPersonService;

@RequestMapping("/studypersonassocs")
@Controller
public class StudyPersonAssocController {

	@Autowired
	StudyPersonAssocService studyPersonAssocService;

	@Autowired
	StudyPersonService studyPersonService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid StudyPersonAssoc studyPersonAssoc, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, studyPersonAssoc);
			return "studypersonassocs/create";
		}
		uiModel.asMap().clear();
		studyPersonAssocService.saveStudyPersonAssoc(studyPersonAssoc);
		return "redirect:/studypersonassocs/"
				+ encodeUrlPathSegment(studyPersonAssoc.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new StudyPersonAssoc());
		return "studypersonassocs/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		uiModel.addAttribute("studypersonassoc", studyPersonAssocService.findStudyPersonAssoc(id));
		uiModel.addAttribute("itemId", id);
		return "studypersonassocs/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("studypersonassocs",
					studyPersonAssocService.findStudyPersonAssocEntries(firstResult, sizeNo));
			float nrOfPages = (float) studyPersonAssocService.countAllStudyPersonAssocs() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("studypersonassocs", studyPersonAssocService.findAllStudyPersonAssocs());
		}
		return "studypersonassocs/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid StudyPersonAssoc studyPersonAssoc, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, studyPersonAssoc);
			return "studypersonassocs/update";
		}
		uiModel.asMap().clear();
		studyPersonAssocService.updateStudyPersonAssoc(studyPersonAssoc);
		return "redirect:/studypersonassocs/"
				+ encodeUrlPathSegment(studyPersonAssoc.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, studyPersonAssocService.findStudyPersonAssoc(id));
		return "studypersonassocs/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		StudyPersonAssoc studyPersonAssoc = studyPersonAssocService.findStudyPersonAssoc(id);
		studyPersonAssocService.deleteStudyPersonAssoc(studyPersonAssoc);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/studypersonassocs";
	}

	void populateEditForm(Model uiModel, StudyPersonAssoc studyPersonAssoc) {
		uiModel.addAttribute("studyPersonAssoc", studyPersonAssoc);
		uiModel.addAttribute("studypeople", studyPersonService.findAllStudypeople());
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
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		StudyPersonAssoc studyPersonAssoc = studyPersonAssocService.findStudyPersonAssoc(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (studyPersonAssoc == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(studyPersonAssoc.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<StudyPersonAssoc> result = studyPersonAssocService.findAllStudyPersonAssocs();
		return new ResponseEntity<String>(StudyPersonAssoc.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		StudyPersonAssoc studyPersonAssoc = StudyPersonAssoc.fromJsonToStudyPersonAssoc(json);
		studyPersonAssocService.saveStudyPersonAssoc(studyPersonAssoc);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (StudyPersonAssoc studyPersonAssoc : StudyPersonAssoc.fromJsonArrayToStudyPersonAssocs(json)) {
			studyPersonAssocService.saveStudyPersonAssoc(studyPersonAssoc);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		StudyPersonAssoc studyPersonAssoc = StudyPersonAssoc.fromJsonToStudyPersonAssoc(json);
		if (studyPersonAssocService.updateStudyPersonAssoc(studyPersonAssoc) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (StudyPersonAssoc studyPersonAssoc : StudyPersonAssoc.fromJsonArrayToStudyPersonAssocs(json)) {
			if (studyPersonAssocService.updateStudyPersonAssoc(studyPersonAssoc) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		StudyPersonAssoc studyPersonAssoc = studyPersonAssocService.findStudyPersonAssoc(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (studyPersonAssoc == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		studyPersonAssocService.deleteStudyPersonAssoc(studyPersonAssoc);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
