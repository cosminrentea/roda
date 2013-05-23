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
import ro.roda.domain.FormEditedTextVar;
import ro.roda.domain.FormEditedTextVarPK;
import ro.roda.service.FormEditedTextVarService;
import ro.roda.service.FormService;
import ro.roda.service.VariableService;

@RequestMapping("/formeditedtextvars")
@Controller
public class FormEditedTextVarController {

	private ConversionService conversionService;

	@Autowired
	FormEditedTextVarService formEditedTextVarService;

	@Autowired
	FormService formService;

	@Autowired
	VariableService variableService;

	@Autowired
	public FormEditedTextVarController(ConversionService conversionService) {
		super();
		this.conversionService = conversionService;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid FormEditedTextVar formEditedTextVar, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, formEditedTextVar);
			return "formeditedtextvars/create";
		}
		uiModel.asMap().clear();
		formEditedTextVarService.saveFormEditedTextVar(formEditedTextVar);
		return "redirect:/formeditedtextvars/"
				+ encodeUrlPathSegment(conversionService.convert(formEditedTextVar.getId(), String.class),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new FormEditedTextVar());
		return "formeditedtextvars/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") FormEditedTextVarPK id, Model uiModel) {
		uiModel.addAttribute("formeditedtextvar", formEditedTextVarService.findFormEditedTextVar(id));
		uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
		return "formeditedtextvars/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("formeditedtextvars",
					formEditedTextVarService.findFormEditedTextVarEntries(firstResult, sizeNo));
			float nrOfPages = (float) formEditedTextVarService.countAllFormEditedTextVars() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("formeditedtextvars", formEditedTextVarService.findAllFormEditedTextVars());
		}
		return "formeditedtextvars/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid FormEditedTextVar formEditedTextVar, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, formEditedTextVar);
			return "formeditedtextvars/update";
		}
		uiModel.asMap().clear();
		formEditedTextVarService.updateFormEditedTextVar(formEditedTextVar);
		return "redirect:/formeditedtextvars/"
				+ encodeUrlPathSegment(conversionService.convert(formEditedTextVar.getId(), String.class),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") FormEditedTextVarPK id, Model uiModel) {
		populateEditForm(uiModel, formEditedTextVarService.findFormEditedTextVar(id));
		return "formeditedtextvars/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") FormEditedTextVarPK id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		FormEditedTextVar formEditedTextVar = formEditedTextVarService.findFormEditedTextVar(id);
		formEditedTextVarService.deleteFormEditedTextVar(formEditedTextVar);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/formeditedtextvars";
	}

	void populateEditForm(Model uiModel, FormEditedTextVar formEditedTextVar) {
		uiModel.addAttribute("formEditedTextVar", formEditedTextVar);
		uiModel.addAttribute("forms", formService.findAllForms());
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
	public ResponseEntity<String> showJson(@PathVariable("id") FormEditedTextVarPK id) {
		FormEditedTextVar formEditedTextVar = formEditedTextVarService.findFormEditedTextVar(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (formEditedTextVar == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(formEditedTextVar.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<FormEditedTextVar> result = formEditedTextVarService.findAllFormEditedTextVars();
		return new ResponseEntity<String>(FormEditedTextVar.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		FormEditedTextVar formEditedTextVar = FormEditedTextVar.fromJsonToFormEditedTextVar(json);
		formEditedTextVarService.saveFormEditedTextVar(formEditedTextVar);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (FormEditedTextVar formEditedTextVar : FormEditedTextVar.fromJsonArrayToFormEditedTextVars(json)) {
			formEditedTextVarService.saveFormEditedTextVar(formEditedTextVar);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		FormEditedTextVar formEditedTextVar = FormEditedTextVar.fromJsonToFormEditedTextVar(json);
		if (formEditedTextVarService.updateFormEditedTextVar(formEditedTextVar) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (FormEditedTextVar formEditedTextVar : FormEditedTextVar.fromJsonArrayToFormEditedTextVars(json)) {
			if (formEditedTextVarService.updateFormEditedTextVar(formEditedTextVar) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") FormEditedTextVarPK id) {
		FormEditedTextVar formEditedTextVar = formEditedTextVarService.findFormEditedTextVar(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (formEditedTextVar == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		formEditedTextVarService.deleteFormEditedTextVar(formEditedTextVar);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
