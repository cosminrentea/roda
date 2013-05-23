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
import ro.roda.domain.InstanceForm;
import ro.roda.domain.InstanceFormPK;
import ro.roda.service.FormService;
import ro.roda.service.InstanceFormService;
import ro.roda.service.InstanceService;

@RequestMapping("/instanceforms")
@Controller


public class InstanceFormController {

	private ConversionService conversionService;

	@Autowired
    InstanceFormService instanceFormService;

	@Autowired
    FormService formService;

	@Autowired
    InstanceService instanceService;

	@Autowired
    public InstanceFormController(ConversionService conversionService) {
        super();
        this.conversionService = conversionService;
    }

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid InstanceForm instanceForm, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, instanceForm);
            return "instanceforms/create";
        }
        uiModel.asMap().clear();
        instanceFormService.saveInstanceForm(instanceForm);
        return "redirect:/instanceforms/" + encodeUrlPathSegment(conversionService.convert(instanceForm.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new InstanceForm());
        return "instanceforms/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") InstanceFormPK id, Model uiModel) {
        uiModel.addAttribute("instanceform", instanceFormService.findInstanceForm(id));
        uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
        return "instanceforms/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("instanceforms", instanceFormService.findInstanceFormEntries(firstResult, sizeNo));
            float nrOfPages = (float) instanceFormService.countAllInstanceForms() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("instanceforms", instanceFormService.findAllInstanceForms());
        }
        return "instanceforms/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid InstanceForm instanceForm, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, instanceForm);
            return "instanceforms/update";
        }
        uiModel.asMap().clear();
        instanceFormService.updateInstanceForm(instanceForm);
        return "redirect:/instanceforms/" + encodeUrlPathSegment(conversionService.convert(instanceForm.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") InstanceFormPK id, Model uiModel) {
        populateEditForm(uiModel, instanceFormService.findInstanceForm(id));
        return "instanceforms/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") InstanceFormPK id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        InstanceForm instanceForm = instanceFormService.findInstanceForm(id);
        instanceFormService.deleteInstanceForm(instanceForm);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/instanceforms";
    }

	void populateEditForm(Model uiModel, InstanceForm instanceForm) {
        uiModel.addAttribute("instanceForm", instanceForm);
        uiModel.addAttribute("forms", formService.findAllForms());
        uiModel.addAttribute("instances", instanceService.findAllInstances());
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
    public ResponseEntity<String> showJson(@PathVariable("id") InstanceFormPK id) {
        InstanceForm instanceForm = instanceFormService.findInstanceForm(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (instanceForm == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(instanceForm.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<InstanceForm> result = instanceFormService.findAllInstanceForms();
        return new ResponseEntity<String>(InstanceForm.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        InstanceForm instanceForm = InstanceForm.fromJsonToInstanceForm(json);
        instanceFormService.saveInstanceForm(instanceForm);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (InstanceForm instanceForm: InstanceForm.fromJsonArrayToInstanceForms(json)) {
            instanceFormService.saveInstanceForm(instanceForm);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        InstanceForm instanceForm = InstanceForm.fromJsonToInstanceForm(json);
        if (instanceFormService.updateInstanceForm(instanceForm) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (InstanceForm instanceForm: InstanceForm.fromJsonArrayToInstanceForms(json)) {
            if (instanceFormService.updateInstanceForm(instanceForm) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") InstanceFormPK id) {
        InstanceForm instanceForm = instanceFormService.findInstanceForm(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (instanceForm == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        instanceFormService.deleteInstanceForm(instanceForm);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
