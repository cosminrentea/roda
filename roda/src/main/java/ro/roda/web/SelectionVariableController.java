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
import ro.roda.domain.SelectionVariable;
import ro.roda.service.SelectionVariableItemService;
import ro.roda.service.SelectionVariableService;
import ro.roda.service.VariableService;

@RequestMapping("/selectionvariables")
@Controller
public class SelectionVariableController {

	@Autowired
	SelectionVariableService selectionVariableService;

	@Autowired
	SelectionVariableItemService selectionVariableItemService;

	@Autowired
	VariableService variableService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid SelectionVariable selectionVariable, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, selectionVariable);
			return "selectionvariables/create";
		}
		uiModel.asMap().clear();
		selectionVariableService.saveSelectionVariable(selectionVariable);
		return "redirect:/selectionvariables/"
				+ encodeUrlPathSegment(selectionVariable.getVariableId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new SelectionVariable());
		return "selectionvariables/create";
	}

	@RequestMapping(value = "/{variableId}", produces = "text/html")
	public String show(@PathVariable("variableId") Long variableId, Model uiModel) {
		uiModel.addAttribute("selectionvariable", selectionVariableService.findSelectionVariable(variableId));
		uiModel.addAttribute("itemId", variableId);
		return "selectionvariables/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("selectionvariables",
					selectionVariableService.findSelectionVariableEntries(firstResult, sizeNo));
			float nrOfPages = (float) selectionVariableService.countAllSelectionVariables() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("selectionvariables", selectionVariableService.findAllSelectionVariables());
		}
		return "selectionvariables/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid SelectionVariable selectionVariable, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, selectionVariable);
			return "selectionvariables/update";
		}
		uiModel.asMap().clear();
		selectionVariableService.updateSelectionVariable(selectionVariable);
		return "redirect:/selectionvariables/"
				+ encodeUrlPathSegment(selectionVariable.getVariableId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{variableId}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("variableId") Long variableId, Model uiModel) {
		populateEditForm(uiModel, selectionVariableService.findSelectionVariable(variableId));
		return "selectionvariables/update";
	}

	@RequestMapping(value = "/{variableId}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("variableId") Long variableId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		SelectionVariable selectionVariable = selectionVariableService.findSelectionVariable(variableId);
		selectionVariableService.deleteSelectionVariable(selectionVariable);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/selectionvariables";
	}

	void populateEditForm(Model uiModel, SelectionVariable selectionVariable) {
		uiModel.addAttribute("selectionVariable", selectionVariable);
		uiModel.addAttribute("selectionvariableitems", selectionVariableItemService.findAllSelectionVariableItems());
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

	@RequestMapping(value = "/{variableId}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("variableId") Long variableId) {
		SelectionVariable selectionVariable = selectionVariableService.findSelectionVariable(variableId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (selectionVariable == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(selectionVariable.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<SelectionVariable> result = selectionVariableService.findAllSelectionVariables();
		return new ResponseEntity<String>(SelectionVariable.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		SelectionVariable selectionVariable = SelectionVariable.fromJsonToSelectionVariable(json);
		selectionVariableService.saveSelectionVariable(selectionVariable);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (SelectionVariable selectionVariable : SelectionVariable.fromJsonArrayToSelectionVariables(json)) {
			selectionVariableService.saveSelectionVariable(selectionVariable);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		SelectionVariable selectionVariable = SelectionVariable.fromJsonToSelectionVariable(json);
		if (selectionVariableService.updateSelectionVariable(selectionVariable) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (SelectionVariable selectionVariable : SelectionVariable.fromJsonArrayToSelectionVariables(json)) {
			if (selectionVariableService.updateSelectionVariable(selectionVariable) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{variableId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("variableId") Long variableId) {
		SelectionVariable selectionVariable = selectionVariableService.findSelectionVariable(variableId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (selectionVariable == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		selectionVariableService.deleteSelectionVariable(selectionVariable);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
