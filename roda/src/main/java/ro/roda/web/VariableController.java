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
import ro.roda.domain.Variable;
import ro.roda.service.ConceptService;
import ro.roda.service.FileService;
import ro.roda.service.FormEditedNumberVarService;
import ro.roda.service.FormEditedTextVarService;
import ro.roda.service.InstanceVariableService;
import ro.roda.service.OtherStatisticService;
import ro.roda.service.SelectionVariableService;
import ro.roda.service.SkipService;
import ro.roda.service.VargroupService;
import ro.roda.service.VariableService;

@RequestMapping("/variables")
@Controller
public class VariableController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
		Variable variable = variableService.findVariable(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (variable == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(variable.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<Variable> result = variableService.findAllVariables();
		return new ResponseEntity<String>(Variable.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		Variable variable = Variable.fromJsonToVariable(json);
		variableService.saveVariable(variable);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (Variable variable : Variable.fromJsonArrayToVariables(json)) {
			variableService.saveVariable(variable);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		Variable variable = Variable.fromJsonToVariable(json);
		if (variableService.updateVariable(variable) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (Variable variable : Variable.fromJsonArrayToVariables(json)) {
			if (variableService.updateVariable(variable) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
		Variable variable = variableService.findVariable(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (variable == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		variableService.deleteVariable(variable);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@Autowired
	VariableService variableService;

	@Autowired
	ConceptService conceptService;

	@Autowired
	FileService fileService;

	@Autowired
	FormEditedNumberVarService formEditedNumberVarService;

	@Autowired
	FormEditedTextVarService formEditedTextVarService;

	@Autowired
	InstanceVariableService instanceVariableService;

	@Autowired
	OtherStatisticService otherStatisticService;

	@Autowired
	SelectionVariableService selectionVariableService;

	@Autowired
	SkipService skipService;

	@Autowired
	VargroupService vargroupService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Variable variable, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, variable);
			return "variables/create";
		}
		uiModel.asMap().clear();
		variableService.saveVariable(variable);
		return "redirect:/variables/" + encodeUrlPathSegment(variable.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new Variable());
		return "variables/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		uiModel.addAttribute("variable", variableService.findVariable(id));
		uiModel.addAttribute("itemId", id);
		return "variables/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("variables", variableService.findVariableEntries(firstResult, sizeNo));
			float nrOfPages = (float) variableService.countAllVariables() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("variables", variableService.findAllVariables());
		}
		return "variables/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Variable variable, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, variable);
			return "variables/update";
		}
		uiModel.asMap().clear();
		variableService.updateVariable(variable);
		return "redirect:/variables/" + encodeUrlPathSegment(variable.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, variableService.findVariable(id));
		return "variables/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Variable variable = variableService.findVariable(id);
		variableService.deleteVariable(variable);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/variables";
	}

	void populateEditForm(Model uiModel, Variable variable) {
		uiModel.addAttribute("variable", variable);
		uiModel.addAttribute("concepts", conceptService.findAllConcepts());
		uiModel.addAttribute("files", fileService.findAllFiles());
		uiModel.addAttribute("formeditednumbervars", formEditedNumberVarService.findAllFormEditedNumberVars());
		uiModel.addAttribute("formeditedtextvars", formEditedTextVarService.findAllFormEditedTextVars());
		uiModel.addAttribute("instancevariables", instanceVariableService.findAllInstanceVariables());
		uiModel.addAttribute("otherstatistics", otherStatisticService.findAllOtherStatistics());
		uiModel.addAttribute("selectionvariables", selectionVariableService.findAllSelectionVariables());
		uiModel.addAttribute("skips", skipService.findAllSkips());
		uiModel.addAttribute("vargroups", vargroupService.findAllVargroups());
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
