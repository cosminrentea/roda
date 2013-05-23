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
import ro.roda.domain.PersonPhone;
import ro.roda.domain.PersonPhonePK;
import ro.roda.service.PersonPhoneService;
import ro.roda.service.PersonService;
import ro.roda.service.PhoneService;

@RequestMapping("/personphones")
@Controller


public class PersonPhoneController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") PersonPhonePK id) {
        PersonPhone personPhone = personPhoneService.findPersonPhone(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (personPhone == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(personPhone.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<PersonPhone> result = personPhoneService.findAllPersonPhones();
        return new ResponseEntity<String>(PersonPhone.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        PersonPhone personPhone = PersonPhone.fromJsonToPersonPhone(json);
        personPhoneService.savePersonPhone(personPhone);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (PersonPhone personPhone: PersonPhone.fromJsonArrayToPersonPhones(json)) {
            personPhoneService.savePersonPhone(personPhone);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        PersonPhone personPhone = PersonPhone.fromJsonToPersonPhone(json);
        if (personPhoneService.updatePersonPhone(personPhone) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (PersonPhone personPhone: PersonPhone.fromJsonArrayToPersonPhones(json)) {
            if (personPhoneService.updatePersonPhone(personPhone) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") PersonPhonePK id) {
        PersonPhone personPhone = personPhoneService.findPersonPhone(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (personPhone == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        personPhoneService.deletePersonPhone(personPhone);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	private ConversionService conversionService;

	@Autowired
    PersonPhoneService personPhoneService;

	@Autowired
    PersonService personService;

	@Autowired
    PhoneService phoneService;

	@Autowired
    public PersonPhoneController(ConversionService conversionService) {
        super();
        this.conversionService = conversionService;
    }

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid PersonPhone personPhone, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, personPhone);
            return "personphones/create";
        }
        uiModel.asMap().clear();
        personPhoneService.savePersonPhone(personPhone);
        return "redirect:/personphones/" + encodeUrlPathSegment(conversionService.convert(personPhone.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new PersonPhone());
        return "personphones/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") PersonPhonePK id, Model uiModel) {
        uiModel.addAttribute("personphone", personPhoneService.findPersonPhone(id));
        uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
        return "personphones/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("personphones", personPhoneService.findPersonPhoneEntries(firstResult, sizeNo));
            float nrOfPages = (float) personPhoneService.countAllPersonPhones() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("personphones", personPhoneService.findAllPersonPhones());
        }
        return "personphones/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid PersonPhone personPhone, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, personPhone);
            return "personphones/update";
        }
        uiModel.asMap().clear();
        personPhoneService.updatePersonPhone(personPhone);
        return "redirect:/personphones/" + encodeUrlPathSegment(conversionService.convert(personPhone.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") PersonPhonePK id, Model uiModel) {
        populateEditForm(uiModel, personPhoneService.findPersonPhone(id));
        return "personphones/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") PersonPhonePK id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        PersonPhone personPhone = personPhoneService.findPersonPhone(id);
        personPhoneService.deletePersonPhone(personPhone);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/personphones";
    }

	void populateEditForm(Model uiModel, PersonPhone personPhone) {
        uiModel.addAttribute("personPhone", personPhone);
        uiModel.addAttribute("people", personService.findAllPeople());
        uiModel.addAttribute("phones", phoneService.findAllPhones());
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
