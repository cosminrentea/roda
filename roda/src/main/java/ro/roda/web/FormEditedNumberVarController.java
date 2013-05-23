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
import ro.roda.domain.FormEditedNumberVar;
import ro.roda.domain.FormEditedNumberVarPK;
import ro.roda.service.FormEditedNumberVarService;
import ro.roda.service.FormService;
import ro.roda.service.VariableService;

@RequestMapping("/formeditednumbervars")
@Controller


public class FormEditedNumberVarController {

	private ConversionService conversionService;

	@Autowired
    FormEditedNumberVarService formEditedNumberVarService;

	@Autowired
    FormService formService;

	@Autowired
    VariableService variableService;

	@Autowired
    public FormEditedNumberVarController(ConversionService conversionService) {
        super();
        this.conversionService = conversionService;
    }

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid FormEditedNumberVar formEditedNumberVar, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, formEditedNumberVar);
            return "formeditednumbervars/create";
        }
        uiModel.asMap().clear();
        formEditedNumberVarService.saveFormEditedNumberVar(formEditedNumberVar);
        return "redirect:/formeditednumbervars/" + encodeUrlPathSegment(conversionService.convert(formEditedNumberVar.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new FormEditedNumberVar());
        return "formeditednumbervars/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") FormEditedNumberVarPK id, Model uiModel) {
        uiModel.addAttribute("formeditednumbervar", formEditedNumberVarService.findFormEditedNumberVar(id));
        uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
        return "formeditednumbervars/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("formeditednumbervars", formEditedNumberVarService.findFormEditedNumberVarEntries(firstResult, sizeNo));
            float nrOfPages = (float) formEditedNumberVarService.countAllFormEditedNumberVars() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("formeditednumbervars", formEditedNumberVarService.findAllFormEditedNumberVars());
        }
        return "formeditednumbervars/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid FormEditedNumberVar formEditedNumberVar, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, formEditedNumberVar);
            return "formeditednumbervars/update";
        }
        uiModel.asMap().clear();
        formEditedNumberVarService.updateFormEditedNumberVar(formEditedNumberVar);
        return "redirect:/formeditednumbervars/" + encodeUrlPathSegment(conversionService.convert(formEditedNumberVar.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") FormEditedNumberVarPK id, Model uiModel) {
        populateEditForm(uiModel, formEditedNumberVarService.findFormEditedNumberVar(id));
        return "formeditednumbervars/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") FormEditedNumberVarPK id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        FormEditedNumberVar formEditedNumberVar = formEditedNumberVarService.findFormEditedNumberVar(id);
        formEditedNumberVarService.deleteFormEditedNumberVar(formEditedNumberVar);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/formeditednumbervars";
    }

	void populateEditForm(Model uiModel, FormEditedNumberVar formEditedNumberVar) {
        uiModel.addAttribute("formEditedNumberVar", formEditedNumberVar);
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
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") FormEditedNumberVarPK id) {
        FormEditedNumberVar formEditedNumberVar = formEditedNumberVarService.findFormEditedNumberVar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (formEditedNumberVar == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(formEditedNumberVar.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<FormEditedNumberVar> result = formEditedNumberVarService.findAllFormEditedNumberVars();
        return new ResponseEntity<String>(FormEditedNumberVar.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        FormEditedNumberVar formEditedNumberVar = FormEditedNumberVar.fromJsonToFormEditedNumberVar(json);
        formEditedNumberVarService.saveFormEditedNumberVar(formEditedNumberVar);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (FormEditedNumberVar formEditedNumberVar: FormEditedNumberVar.fromJsonArrayToFormEditedNumberVars(json)) {
            formEditedNumberVarService.saveFormEditedNumberVar(formEditedNumberVar);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        FormEditedNumberVar formEditedNumberVar = FormEditedNumberVar.fromJsonToFormEditedNumberVar(json);
        if (formEditedNumberVarService.updateFormEditedNumberVar(formEditedNumberVar) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (FormEditedNumberVar formEditedNumberVar: FormEditedNumberVar.fromJsonArrayToFormEditedNumberVars(json)) {
            if (formEditedNumberVarService.updateFormEditedNumberVar(formEditedNumberVar) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") FormEditedNumberVarPK id) {
        FormEditedNumberVar formEditedNumberVar = formEditedNumberVarService.findFormEditedNumberVar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (formEditedNumberVar == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        formEditedNumberVarService.deleteFormEditedNumberVar(formEditedNumberVar);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
