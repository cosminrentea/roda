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
import ro.roda.domain.UnitAnalysis;
import ro.roda.service.StudyService;
import ro.roda.service.UnitAnalysisService;

@RequestMapping("/unitanalyses")
@Controller
public class UnitAnalysisController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		UnitAnalysis unitAnalysis = unitAnalysisService.findUnitAnalysis(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (unitAnalysis == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(unitAnalysis.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<UnitAnalysis> result = unitAnalysisService.findAllUnitAnalyses();
		return new ResponseEntity<String>(UnitAnalysis.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		UnitAnalysis unitAnalysis = UnitAnalysis.fromJsonToUnitAnalysis(json);
		unitAnalysisService.saveUnitAnalysis(unitAnalysis);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (UnitAnalysis unitAnalysis : UnitAnalysis.fromJsonArrayToUnitAnalyses(json)) {
			unitAnalysisService.saveUnitAnalysis(unitAnalysis);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		UnitAnalysis unitAnalysis = UnitAnalysis.fromJsonToUnitAnalysis(json);
		if (unitAnalysisService.updateUnitAnalysis(unitAnalysis) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (UnitAnalysis unitAnalysis : UnitAnalysis.fromJsonArrayToUnitAnalyses(json)) {
			if (unitAnalysisService.updateUnitAnalysis(unitAnalysis) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		UnitAnalysis unitAnalysis = unitAnalysisService.findUnitAnalysis(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (unitAnalysis == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		unitAnalysisService.deleteUnitAnalysis(unitAnalysis);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@Autowired
	UnitAnalysisService unitAnalysisService;

	@Autowired
	StudyService studyService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid UnitAnalysis unitAnalysis, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, unitAnalysis);
			return "unitanalyses/create";
		}
		uiModel.asMap().clear();
		unitAnalysisService.saveUnitAnalysis(unitAnalysis);
		return "redirect:/unitanalyses/" + encodeUrlPathSegment(unitAnalysis.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new UnitAnalysis());
		return "unitanalyses/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		uiModel.addAttribute("unitanalysis", unitAnalysisService.findUnitAnalysis(id));
		uiModel.addAttribute("itemId", id);
		return "unitanalyses/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("unitanalyses", unitAnalysisService.findUnitAnalysisEntries(firstResult, sizeNo));
			float nrOfPages = (float) unitAnalysisService.countAllUnitAnalyses() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("unitanalyses", unitAnalysisService.findAllUnitAnalyses());
		}
		return "unitanalyses/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid UnitAnalysis unitAnalysis, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, unitAnalysis);
			return "unitanalyses/update";
		}
		uiModel.asMap().clear();
		unitAnalysisService.updateUnitAnalysis(unitAnalysis);
		return "redirect:/unitanalyses/" + encodeUrlPathSegment(unitAnalysis.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, unitAnalysisService.findUnitAnalysis(id));
		return "unitanalyses/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		UnitAnalysis unitAnalysis = unitAnalysisService.findUnitAnalysis(id);
		unitAnalysisService.deleteUnitAnalysis(unitAnalysis);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/unitanalyses";
	}

	void populateEditForm(Model uiModel, UnitAnalysis unitAnalysis) {
		uiModel.addAttribute("unitAnalysis", unitAnalysis);
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
}
