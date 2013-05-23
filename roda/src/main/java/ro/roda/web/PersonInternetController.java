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
import ro.roda.domain.PersonInternet;
import ro.roda.domain.PersonInternetPK;
import ro.roda.service.InternetService;
import ro.roda.service.PersonInternetService;
import ro.roda.service.PersonService;

@RequestMapping("/personinternets")
@Controller


public class PersonInternetController {

	private ConversionService conversionService;

	@Autowired
    PersonInternetService personInternetService;

	@Autowired
    InternetService internetService;

	@Autowired
    PersonService personService;

	@Autowired
    public PersonInternetController(ConversionService conversionService) {
        super();
        this.conversionService = conversionService;
    }

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid PersonInternet personInternet, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, personInternet);
            return "personinternets/create";
        }
        uiModel.asMap().clear();
        personInternetService.savePersonInternet(personInternet);
        return "redirect:/personinternets/" + encodeUrlPathSegment(conversionService.convert(personInternet.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new PersonInternet());
        return "personinternets/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") PersonInternetPK id, Model uiModel) {
        uiModel.addAttribute("personinternet", personInternetService.findPersonInternet(id));
        uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
        return "personinternets/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("personinternets", personInternetService.findPersonInternetEntries(firstResult, sizeNo));
            float nrOfPages = (float) personInternetService.countAllPersonInternets() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("personinternets", personInternetService.findAllPersonInternets());
        }
        return "personinternets/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid PersonInternet personInternet, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, personInternet);
            return "personinternets/update";
        }
        uiModel.asMap().clear();
        personInternetService.updatePersonInternet(personInternet);
        return "redirect:/personinternets/" + encodeUrlPathSegment(conversionService.convert(personInternet.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") PersonInternetPK id, Model uiModel) {
        populateEditForm(uiModel, personInternetService.findPersonInternet(id));
        return "personinternets/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") PersonInternetPK id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        PersonInternet personInternet = personInternetService.findPersonInternet(id);
        personInternetService.deletePersonInternet(personInternet);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/personinternets";
    }

	void populateEditForm(Model uiModel, PersonInternet personInternet) {
        uiModel.addAttribute("personInternet", personInternet);
        uiModel.addAttribute("internets", internetService.findAllInternets());
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
    public ResponseEntity<String> showJson(@PathVariable("id") PersonInternetPK id) {
        PersonInternet personInternet = personInternetService.findPersonInternet(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (personInternet == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(personInternet.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<PersonInternet> result = personInternetService.findAllPersonInternets();
        return new ResponseEntity<String>(PersonInternet.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        PersonInternet personInternet = PersonInternet.fromJsonToPersonInternet(json);
        personInternetService.savePersonInternet(personInternet);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (PersonInternet personInternet: PersonInternet.fromJsonArrayToPersonInternets(json)) {
            personInternetService.savePersonInternet(personInternet);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        PersonInternet personInternet = PersonInternet.fromJsonToPersonInternet(json);
        if (personInternetService.updatePersonInternet(personInternet) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (PersonInternet personInternet: PersonInternet.fromJsonArrayToPersonInternets(json)) {
            if (personInternetService.updatePersonInternet(personInternet) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") PersonInternetPK id) {
        PersonInternet personInternet = personInternetService.findPersonInternet(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (personInternet == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        personInternetService.deletePersonInternet(personInternet);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
