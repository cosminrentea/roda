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
import ro.roda.domain.StudyOrg;
import ro.roda.domain.StudyOrgPK;
import ro.roda.service.OrgService;
import ro.roda.service.StudyOrgAssocService;
import ro.roda.service.StudyOrgService;
import ro.roda.service.StudyService;

@RequestMapping("/studyorgs")
@Controller
public class StudyOrgController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") StudyOrgPK id) {
		StudyOrg studyOrg = studyOrgService.findStudyOrg(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (studyOrg == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(studyOrg.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<StudyOrg> result = studyOrgService.findAllStudyOrgs();
		return new ResponseEntity<String>(StudyOrg.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		StudyOrg studyOrg = StudyOrg.fromJsonToStudyOrg(json);
		studyOrgService.saveStudyOrg(studyOrg);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (StudyOrg studyOrg : StudyOrg.fromJsonArrayToStudyOrgs(json)) {
			studyOrgService.saveStudyOrg(studyOrg);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		StudyOrg studyOrg = StudyOrg.fromJsonToStudyOrg(json);
		if (studyOrgService.updateStudyOrg(studyOrg) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (StudyOrg studyOrg : StudyOrg.fromJsonArrayToStudyOrgs(json)) {
			if (studyOrgService.updateStudyOrg(studyOrg) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") StudyOrgPK id) {
		StudyOrg studyOrg = studyOrgService.findStudyOrg(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (studyOrg == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		studyOrgService.deleteStudyOrg(studyOrg);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	private ConversionService conversionService;

	@Autowired
	StudyOrgService studyOrgService;

	@Autowired
	OrgService orgService;

	@Autowired
	StudyService studyService;

	@Autowired
	StudyOrgAssocService studyOrgAssocService;

	@Autowired
	public StudyOrgController(ConversionService conversionService) {
		super();
		this.conversionService = conversionService;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid StudyOrg studyOrg, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, studyOrg);
			return "studyorgs/create";
		}
		uiModel.asMap().clear();
		studyOrgService.saveStudyOrg(studyOrg);
		return "redirect:/studyorgs/"
				+ encodeUrlPathSegment(conversionService.convert(studyOrg.getId(), String.class), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new StudyOrg());
		return "studyorgs/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") StudyOrgPK id, Model uiModel) {
		uiModel.addAttribute("studyorg", studyOrgService.findStudyOrg(id));
		uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
		return "studyorgs/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("studyorgs", studyOrgService.findStudyOrgEntries(firstResult, sizeNo));
			float nrOfPages = (float) studyOrgService.countAllStudyOrgs() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("studyorgs", studyOrgService.findAllStudyOrgs());
		}
		return "studyorgs/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid StudyOrg studyOrg, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, studyOrg);
			return "studyorgs/update";
		}
		uiModel.asMap().clear();
		studyOrgService.updateStudyOrg(studyOrg);
		return "redirect:/studyorgs/"
				+ encodeUrlPathSegment(conversionService.convert(studyOrg.getId(), String.class), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") StudyOrgPK id, Model uiModel) {
		populateEditForm(uiModel, studyOrgService.findStudyOrg(id));
		return "studyorgs/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") StudyOrgPK id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		StudyOrg studyOrg = studyOrgService.findStudyOrg(id);
		studyOrgService.deleteStudyOrg(studyOrg);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/studyorgs";
	}

	void populateEditForm(Model uiModel, StudyOrg studyOrg) {
		uiModel.addAttribute("studyOrg", studyOrg);
		uiModel.addAttribute("orgs", orgService.findAllOrgs());
		uiModel.addAttribute("studys", studyService.findAllStudys());
		uiModel.addAttribute("studyorgassocs", studyOrgAssocService.findAllStudyOrgAssocs());
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
