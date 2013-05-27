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

import ro.roda.domain.SelectionVariableItem;
import ro.roda.domain.SelectionVariableItemPK;
import ro.roda.service.FileService;
import ro.roda.service.FormSelectionVarService;
import ro.roda.service.ItemService;
import ro.roda.service.SelectionVariableItemService;
import ro.roda.service.SelectionVariableService;

@RequestMapping("/selectionvariableitems")
@Controller
public class SelectionVariableItemController {

	private ConversionService conversionService;

	@Autowired
	SelectionVariableItemService selectionVariableItemService;

	@Autowired
	FileService fileService;

	@Autowired
	FormSelectionVarService formSelectionVarService;

	@Autowired
	ItemService itemService;

	@Autowired
	SelectionVariableService selectionVariableService;

	@Autowired
	public SelectionVariableItemController(ConversionService conversionService) {
		super();
		this.conversionService = conversionService;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid SelectionVariableItem selectionVariableItem, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, selectionVariableItem);
			return "selectionvariableitems/create";
		}
		uiModel.asMap().clear();
		selectionVariableItemService.saveSelectionVariableItem(selectionVariableItem);
		return "redirect:/selectionvariableitems/"
				+ encodeUrlPathSegment(conversionService.convert(selectionVariableItem.getId(), String.class),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new SelectionVariableItem());
		return "selectionvariableitems/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") SelectionVariableItemPK id, Model uiModel) {
		uiModel.addAttribute("selectionvariableitem", selectionVariableItemService.findSelectionVariableItem(id));
		uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
		return "selectionvariableitems/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("selectionvariableitems",
					selectionVariableItemService.findSelectionVariableItemEntries(firstResult, sizeNo));
			float nrOfPages = (float) selectionVariableItemService.countAllSelectionVariableItems() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("selectionvariableitems", selectionVariableItemService.findAllSelectionVariableItems());
		}
		return "selectionvariableitems/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid SelectionVariableItem selectionVariableItem, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, selectionVariableItem);
			return "selectionvariableitems/update";
		}
		uiModel.asMap().clear();
		selectionVariableItemService.updateSelectionVariableItem(selectionVariableItem);
		return "redirect:/selectionvariableitems/"
				+ encodeUrlPathSegment(conversionService.convert(selectionVariableItem.getId(), String.class),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") SelectionVariableItemPK id, Model uiModel) {
		populateEditForm(uiModel, selectionVariableItemService.findSelectionVariableItem(id));
		return "selectionvariableitems/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") SelectionVariableItemPK id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		SelectionVariableItem selectionVariableItem = selectionVariableItemService.findSelectionVariableItem(id);
		selectionVariableItemService.deleteSelectionVariableItem(selectionVariableItem);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/selectionvariableitems";
	}

	void populateEditForm(Model uiModel, SelectionVariableItem selectionVariableItem) {
		uiModel.addAttribute("selectionVariableItem", selectionVariableItem);
		uiModel.addAttribute("files", fileService.findAllFiles());
		uiModel.addAttribute("formselectionvars", formSelectionVarService.findAllFormSelectionVars());
		uiModel.addAttribute("items", itemService.findAllItems());
		uiModel.addAttribute("selectionvariables", selectionVariableService.findAllSelectionVariables());
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
	public ResponseEntity<String> showJson(@PathVariable("id") SelectionVariableItemPK id) {
		SelectionVariableItem selectionVariableItem = selectionVariableItemService.findSelectionVariableItem(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (selectionVariableItem == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(selectionVariableItem.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<SelectionVariableItem> result = selectionVariableItemService.findAllSelectionVariableItems();
		return new ResponseEntity<String>(SelectionVariableItem.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		SelectionVariableItem selectionVariableItem = SelectionVariableItem.fromJsonToSelectionVariableItem(json);
		selectionVariableItemService.saveSelectionVariableItem(selectionVariableItem);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (SelectionVariableItem selectionVariableItem : SelectionVariableItem
				.fromJsonArrayToSelectionVariableItems(json)) {
			selectionVariableItemService.saveSelectionVariableItem(selectionVariableItem);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		SelectionVariableItem selectionVariableItem = SelectionVariableItem.fromJsonToSelectionVariableItem(json);
		if (selectionVariableItemService.updateSelectionVariableItem(selectionVariableItem) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (SelectionVariableItem selectionVariableItem : SelectionVariableItem
				.fromJsonArrayToSelectionVariableItems(json)) {
			if (selectionVariableItemService.updateSelectionVariableItem(selectionVariableItem) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") SelectionVariableItemPK id) {
		SelectionVariableItem selectionVariableItem = selectionVariableItemService.findSelectionVariableItem(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (selectionVariableItem == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		selectionVariableItemService.deleteSelectionVariableItem(selectionVariableItem);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
