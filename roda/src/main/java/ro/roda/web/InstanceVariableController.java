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

import ro.roda.domain.InstanceVariable;
import ro.roda.domain.InstanceVariablePK;
import ro.roda.service.InstanceService;
import ro.roda.service.InstanceVariableService;
import ro.roda.service.VariableService;

@RequestMapping("/instancevariables")
@Controller
public class InstanceVariableController {

	private ConversionService conversionService;

	@Autowired
	InstanceVariableService instanceVariableService;

	@Autowired
	InstanceService instanceService;

	@Autowired
	VariableService variableService;

	@Autowired
	public InstanceVariableController(ConversionService conversionService) {
		super();
		this.conversionService = conversionService;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid InstanceVariable instanceVariable, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, instanceVariable);
			return "instancevariables/create";
		}
		uiModel.asMap().clear();
		instanceVariableService.saveInstanceVariable(instanceVariable);
		return "redirect:/instancevariables/"
				+ encodeUrlPathSegment(conversionService.convert(instanceVariable.getId(), String.class),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new InstanceVariable());
		return "instancevariables/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") InstanceVariablePK id, Model uiModel) {
		uiModel.addAttribute("instancevariable", instanceVariableService.findInstanceVariable(id));
		uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
		return "instancevariables/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("instancevariables",
					instanceVariableService.findInstanceVariableEntries(firstResult, sizeNo));
			float nrOfPages = (float) instanceVariableService.countAllInstanceVariables() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("instancevariables", instanceVariableService.findAllInstanceVariables());
		}
		return "instancevariables/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid InstanceVariable instanceVariable, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, instanceVariable);
			return "instancevariables/update";
		}
		uiModel.asMap().clear();
		instanceVariableService.updateInstanceVariable(instanceVariable);
		return "redirect:/instancevariables/"
				+ encodeUrlPathSegment(conversionService.convert(instanceVariable.getId(), String.class),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") InstanceVariablePK id, Model uiModel) {
		populateEditForm(uiModel, instanceVariableService.findInstanceVariable(id));
		return "instancevariables/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") InstanceVariablePK id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		InstanceVariable instanceVariable = instanceVariableService.findInstanceVariable(id);
		instanceVariableService.deleteInstanceVariable(instanceVariable);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/instancevariables";
	}

	void populateEditForm(Model uiModel, InstanceVariable instanceVariable) {
		uiModel.addAttribute("instanceVariable", instanceVariable);
		uiModel.addAttribute("instances", instanceService.findAllInstances());
		uiModel.addAttribute("variables", variableService.findAllVariables());
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
	public ResponseEntity<String> showJson(@PathVariable("id") InstanceVariablePK id) {
		InstanceVariable instanceVariable = instanceVariableService.findInstanceVariable(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (instanceVariable == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(instanceVariable.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<InstanceVariable> result = instanceVariableService.findAllInstanceVariables();
		return new ResponseEntity<String>(InstanceVariable.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		InstanceVariable instanceVariable = InstanceVariable.fromJsonToInstanceVariable(json);
		instanceVariableService.saveInstanceVariable(instanceVariable);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (InstanceVariable instanceVariable : InstanceVariable.fromJsonArrayToInstanceVariables(json)) {
			instanceVariableService.saveInstanceVariable(instanceVariable);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		InstanceVariable instanceVariable = InstanceVariable.fromJsonToInstanceVariable(json);
		if (instanceVariableService.updateInstanceVariable(instanceVariable) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (InstanceVariable instanceVariable : InstanceVariable.fromJsonArrayToInstanceVariables(json)) {
			if (instanceVariableService.updateInstanceVariable(instanceVariable) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") InstanceVariablePK id) {
		InstanceVariable instanceVariable = instanceVariableService.findInstanceVariable(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (instanceVariable == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		instanceVariableService.deleteInstanceVariable(instanceVariable);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
