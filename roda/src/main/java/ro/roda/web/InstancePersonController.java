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
import ro.roda.domain.InstancePerson;
import ro.roda.domain.InstancePersonPK;
import ro.roda.service.InstancePersonAssocService;
import ro.roda.service.InstancePersonService;
import ro.roda.service.InstanceService;
import ro.roda.service.PersonService;

@RequestMapping("/instancepeople")
@Controller


public class InstancePersonController {

	private ConversionService conversionService;

	@Autowired
    InstancePersonService instancePersonService;

	@Autowired
    InstanceService instanceService;

	@Autowired
    InstancePersonAssocService instancePersonAssocService;

	@Autowired
    PersonService personService;

	@Autowired
    public InstancePersonController(ConversionService conversionService) {
        super();
        this.conversionService = conversionService;
    }

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid InstancePerson instancePerson, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, instancePerson);
            return "instancepeople/create";
        }
        uiModel.asMap().clear();
        instancePersonService.saveInstancePerson(instancePerson);
        return "redirect:/instancepeople/" + encodeUrlPathSegment(conversionService.convert(instancePerson.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new InstancePerson());
        return "instancepeople/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") InstancePersonPK id, Model uiModel) {
        uiModel.addAttribute("instanceperson", instancePersonService.findInstancePerson(id));
        uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
        return "instancepeople/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("instancepeople", instancePersonService.findInstancePersonEntries(firstResult, sizeNo));
            float nrOfPages = (float) instancePersonService.countAllInstancepeople() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("instancepeople", instancePersonService.findAllInstancepeople());
        }
        return "instancepeople/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid InstancePerson instancePerson, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, instancePerson);
            return "instancepeople/update";
        }
        uiModel.asMap().clear();
        instancePersonService.updateInstancePerson(instancePerson);
        return "redirect:/instancepeople/" + encodeUrlPathSegment(conversionService.convert(instancePerson.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") InstancePersonPK id, Model uiModel) {
        populateEditForm(uiModel, instancePersonService.findInstancePerson(id));
        return "instancepeople/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") InstancePersonPK id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        InstancePerson instancePerson = instancePersonService.findInstancePerson(id);
        instancePersonService.deleteInstancePerson(instancePerson);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/instancepeople";
    }

	void populateEditForm(Model uiModel, InstancePerson instancePerson) {
        uiModel.addAttribute("instancePerson", instancePerson);
        uiModel.addAttribute("instances", instanceService.findAllInstances());
        uiModel.addAttribute("instancepersonassocs", instancePersonAssocService.findAllInstancePersonAssocs());
        uiModel.addAttribute("people", personService.findAllPeople());
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
    public ResponseEntity<String> showJson(@PathVariable("id") InstancePersonPK id) {
        InstancePerson instancePerson = instancePersonService.findInstancePerson(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (instancePerson == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(instancePerson.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<InstancePerson> result = instancePersonService.findAllInstancepeople();
        return new ResponseEntity<String>(InstancePerson.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        InstancePerson instancePerson = InstancePerson.fromJsonToInstancePerson(json);
        instancePersonService.saveInstancePerson(instancePerson);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (InstancePerson instancePerson: InstancePerson.fromJsonArrayToInstancepeople(json)) {
            instancePersonService.saveInstancePerson(instancePerson);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        InstancePerson instancePerson = InstancePerson.fromJsonToInstancePerson(json);
        if (instancePersonService.updateInstancePerson(instancePerson) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (InstancePerson instancePerson: InstancePerson.fromJsonArrayToInstancepeople(json)) {
            if (instancePersonService.updateInstancePerson(instancePerson) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") InstancePersonPK id) {
        InstancePerson instancePerson = instancePersonService.findInstancePerson(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (instancePerson == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        instancePersonService.deleteInstancePerson(instancePerson);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
