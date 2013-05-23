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
import ro.roda.domain.PersonEmail;
import ro.roda.domain.PersonEmailPK;
import ro.roda.service.EmailService;
import ro.roda.service.PersonEmailService;
import ro.roda.service.PersonService;

@RequestMapping("/personemails")
@Controller


public class PersonEmailController {

	private ConversionService conversionService;

	@Autowired
    PersonEmailService personEmailService;

	@Autowired
    EmailService emailService;

	@Autowired
    PersonService personService;

	@Autowired
    public PersonEmailController(ConversionService conversionService) {
        super();
        this.conversionService = conversionService;
    }

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid PersonEmail personEmail, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, personEmail);
            return "personemails/create";
        }
        uiModel.asMap().clear();
        personEmailService.savePersonEmail(personEmail);
        return "redirect:/personemails/" + encodeUrlPathSegment(conversionService.convert(personEmail.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new PersonEmail());
        return "personemails/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") PersonEmailPK id, Model uiModel) {
        uiModel.addAttribute("personemail", personEmailService.findPersonEmail(id));
        uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
        return "personemails/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("personemails", personEmailService.findPersonEmailEntries(firstResult, sizeNo));
            float nrOfPages = (float) personEmailService.countAllPersonEmails() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("personemails", personEmailService.findAllPersonEmails());
        }
        return "personemails/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid PersonEmail personEmail, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, personEmail);
            return "personemails/update";
        }
        uiModel.asMap().clear();
        personEmailService.updatePersonEmail(personEmail);
        return "redirect:/personemails/" + encodeUrlPathSegment(conversionService.convert(personEmail.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") PersonEmailPK id, Model uiModel) {
        populateEditForm(uiModel, personEmailService.findPersonEmail(id));
        return "personemails/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") PersonEmailPK id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        PersonEmail personEmail = personEmailService.findPersonEmail(id);
        personEmailService.deletePersonEmail(personEmail);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/personemails";
    }

	void populateEditForm(Model uiModel, PersonEmail personEmail) {
        uiModel.addAttribute("personEmail", personEmail);
        uiModel.addAttribute("emails", emailService.findAllEmails());
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
    public ResponseEntity<String> showJson(@PathVariable("id") PersonEmailPK id) {
        PersonEmail personEmail = personEmailService.findPersonEmail(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (personEmail == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(personEmail.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<PersonEmail> result = personEmailService.findAllPersonEmails();
        return new ResponseEntity<String>(PersonEmail.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        PersonEmail personEmail = PersonEmail.fromJsonToPersonEmail(json);
        personEmailService.savePersonEmail(personEmail);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (PersonEmail personEmail: PersonEmail.fromJsonArrayToPersonEmails(json)) {
            personEmailService.savePersonEmail(personEmail);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        PersonEmail personEmail = PersonEmail.fromJsonToPersonEmail(json);
        if (personEmailService.updatePersonEmail(personEmail) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (PersonEmail personEmail: PersonEmail.fromJsonArrayToPersonEmails(json)) {
            if (personEmailService.updatePersonEmail(personEmail) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") PersonEmailPK id) {
        PersonEmail personEmail = personEmailService.findPersonEmail(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (personEmail == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        personEmailService.deletePersonEmail(personEmail);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
