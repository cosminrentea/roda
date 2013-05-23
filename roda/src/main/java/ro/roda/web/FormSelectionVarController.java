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
import ro.roda.domain.FormSelectionVar;
import ro.roda.domain.FormSelectionVarPK;
import ro.roda.service.FormSelectionVarService;
import ro.roda.service.FormService;
import ro.roda.service.SelectionVariableItemService;

@RequestMapping("/formselectionvars")
@Controller


public class FormSelectionVarController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") FormSelectionVarPK id) {
        FormSelectionVar formSelectionVar = formSelectionVarService.findFormSelectionVar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (formSelectionVar == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(formSelectionVar.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<FormSelectionVar> result = formSelectionVarService.findAllFormSelectionVars();
        return new ResponseEntity<String>(FormSelectionVar.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        FormSelectionVar formSelectionVar = FormSelectionVar.fromJsonToFormSelectionVar(json);
        formSelectionVarService.saveFormSelectionVar(formSelectionVar);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (FormSelectionVar formSelectionVar: FormSelectionVar.fromJsonArrayToFormSelectionVars(json)) {
            formSelectionVarService.saveFormSelectionVar(formSelectionVar);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        FormSelectionVar formSelectionVar = FormSelectionVar.fromJsonToFormSelectionVar(json);
        if (formSelectionVarService.updateFormSelectionVar(formSelectionVar) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (FormSelectionVar formSelectionVar: FormSelectionVar.fromJsonArrayToFormSelectionVars(json)) {
            if (formSelectionVarService.updateFormSelectionVar(formSelectionVar) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") FormSelectionVarPK id) {
        FormSelectionVar formSelectionVar = formSelectionVarService.findFormSelectionVar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (formSelectionVar == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        formSelectionVarService.deleteFormSelectionVar(formSelectionVar);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	private ConversionService conversionService;

	@Autowired
    FormSelectionVarService formSelectionVarService;

	@Autowired
    FormService formService;

	@Autowired
    SelectionVariableItemService selectionVariableItemService;

	@Autowired
    public FormSelectionVarController(ConversionService conversionService) {
        super();
        this.conversionService = conversionService;
    }

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid FormSelectionVar formSelectionVar, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, formSelectionVar);
            return "formselectionvars/create";
        }
        uiModel.asMap().clear();
        formSelectionVarService.saveFormSelectionVar(formSelectionVar);
        return "redirect:/formselectionvars/" + encodeUrlPathSegment(conversionService.convert(formSelectionVar.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new FormSelectionVar());
        return "formselectionvars/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") FormSelectionVarPK id, Model uiModel) {
        uiModel.addAttribute("formselectionvar", formSelectionVarService.findFormSelectionVar(id));
        uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
        return "formselectionvars/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("formselectionvars", formSelectionVarService.findFormSelectionVarEntries(firstResult, sizeNo));
            float nrOfPages = (float) formSelectionVarService.countAllFormSelectionVars() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("formselectionvars", formSelectionVarService.findAllFormSelectionVars());
        }
        return "formselectionvars/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid FormSelectionVar formSelectionVar, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, formSelectionVar);
            return "formselectionvars/update";
        }
        uiModel.asMap().clear();
        formSelectionVarService.updateFormSelectionVar(formSelectionVar);
        return "redirect:/formselectionvars/" + encodeUrlPathSegment(conversionService.convert(formSelectionVar.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") FormSelectionVarPK id, Model uiModel) {
        populateEditForm(uiModel, formSelectionVarService.findFormSelectionVar(id));
        return "formselectionvars/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") FormSelectionVarPK id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        FormSelectionVar formSelectionVar = formSelectionVarService.findFormSelectionVar(id);
        formSelectionVarService.deleteFormSelectionVar(formSelectionVar);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/formselectionvars";
    }

	void populateEditForm(Model uiModel, FormSelectionVar formSelectionVar) {
        uiModel.addAttribute("formSelectionVar", formSelectionVar);
        uiModel.addAttribute("forms", formService.findAllForms());
        uiModel.addAttribute("selectionvariableitems", selectionVariableItemService.findAllSelectionVariableItems());
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
