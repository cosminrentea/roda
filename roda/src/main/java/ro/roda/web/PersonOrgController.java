package ro.roda.web;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
import ro.roda.domain.PersonOrg;
import ro.roda.domain.PersonOrgPK;
import ro.roda.service.OrgService;
import ro.roda.service.PersonOrgService;
import ro.roda.service.PersonRoleService;
import ro.roda.service.PersonService;

@RequestMapping("/personorgs")
@Controller


public class PersonOrgController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") PersonOrgPK id) {
        PersonOrg personOrg = personOrgService.findPersonOrg(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (personOrg == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(personOrg.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<PersonOrg> result = personOrgService.findAllPersonOrgs();
        return new ResponseEntity<String>(PersonOrg.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        PersonOrg personOrg = PersonOrg.fromJsonToPersonOrg(json);
        personOrgService.savePersonOrg(personOrg);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (PersonOrg personOrg: PersonOrg.fromJsonArrayToPersonOrgs(json)) {
            personOrgService.savePersonOrg(personOrg);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        PersonOrg personOrg = PersonOrg.fromJsonToPersonOrg(json);
        if (personOrgService.updatePersonOrg(personOrg) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (PersonOrg personOrg: PersonOrg.fromJsonArrayToPersonOrgs(json)) {
            if (personOrgService.updatePersonOrg(personOrg) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") PersonOrgPK id) {
        PersonOrg personOrg = personOrgService.findPersonOrg(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (personOrg == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        personOrgService.deletePersonOrg(personOrg);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	private ConversionService conversionService;

	@Autowired
    PersonOrgService personOrgService;

	@Autowired
    OrgService orgService;

	@Autowired
    PersonService personService;

	@Autowired
    PersonRoleService personRoleService;

	@Autowired
    public PersonOrgController(ConversionService conversionService) {
        super();
        this.conversionService = conversionService;
    }

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid PersonOrg personOrg, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, personOrg);
            return "personorgs/create";
        }
        uiModel.asMap().clear();
        personOrgService.savePersonOrg(personOrg);
        return "redirect:/personorgs/" + encodeUrlPathSegment(conversionService.convert(personOrg.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new PersonOrg());
        return "personorgs/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") PersonOrgPK id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("personorg", personOrgService.findPersonOrg(id));
        uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
        return "personorgs/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("personorgs", personOrgService.findPersonOrgEntries(firstResult, sizeNo));
            float nrOfPages = (float) personOrgService.countAllPersonOrgs() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("personorgs", personOrgService.findAllPersonOrgs());
        }
        addDateTimeFormatPatterns(uiModel);
        return "personorgs/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid PersonOrg personOrg, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, personOrg);
            return "personorgs/update";
        }
        uiModel.asMap().clear();
        personOrgService.updatePersonOrg(personOrg);
        return "redirect:/personorgs/" + encodeUrlPathSegment(conversionService.convert(personOrg.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") PersonOrgPK id, Model uiModel) {
        populateEditForm(uiModel, personOrgService.findPersonOrg(id));
        return "personorgs/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") PersonOrgPK id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        PersonOrg personOrg = personOrgService.findPersonOrg(id);
        personOrgService.deletePersonOrg(personOrg);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/personorgs";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("personOrg_datestart_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("personOrg_dateend_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, PersonOrg personOrg) {
        uiModel.addAttribute("personOrg", personOrg);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("orgs", orgService.findAllOrgs());
        uiModel.addAttribute("people", personService.findAllPeople());
        uiModel.addAttribute("personroles", personRoleService.findAllPersonRoles());
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
