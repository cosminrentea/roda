// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.web;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import ro.roda.domain.Person;
import ro.roda.service.FormService;
import ro.roda.service.InstancePersonService;
import ro.roda.service.PersonAddressService;
import ro.roda.service.PersonEmailService;
import ro.roda.service.PersonInternetService;
import ro.roda.service.PersonLinksService;
import ro.roda.service.PersonOrgService;
import ro.roda.service.PersonPhoneService;
import ro.roda.service.PersonService;
import ro.roda.service.PrefixService;
import ro.roda.service.StudyPersonService;
import ro.roda.service.SuffixService;
import ro.roda.web.PersonController;

privileged aspect PersonController_Roo_Controller {
    
    @Autowired
    PersonService PersonController.personService;
    
    @Autowired
    FormService PersonController.formService;
    
    @Autowired
    InstancePersonService PersonController.instancePersonService;
    
    @Autowired
    PersonAddressService PersonController.personAddressService;
    
    @Autowired
    PersonEmailService PersonController.personEmailService;
    
    @Autowired
    PersonInternetService PersonController.personInternetService;
    
    @Autowired
    PersonLinksService PersonController.personLinksService;
    
    @Autowired
    PersonOrgService PersonController.personOrgService;
    
    @Autowired
    PersonPhoneService PersonController.personPhoneService;
    
    @Autowired
    PrefixService PersonController.prefixService;
    
    @Autowired
    StudyPersonService PersonController.studyPersonService;
    
    @Autowired
    SuffixService PersonController.suffixService;
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String PersonController.create(@Valid Person person, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, person);
            return "people/create";
        }
        uiModel.asMap().clear();
        personService.savePerson(person);
        return "redirect:/people/" + encodeUrlPathSegment(person.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String PersonController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Person());
        return "people/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String PersonController.show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("person", personService.findPerson(id));
        uiModel.addAttribute("itemId", id);
        return "people/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String PersonController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("people", personService.findPersonEntries(firstResult, sizeNo));
            float nrOfPages = (float) personService.countAllPeople() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("people", personService.findAllPeople());
        }
        return "people/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String PersonController.update(@Valid Person person, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, person);
            return "people/update";
        }
        uiModel.asMap().clear();
        personService.updatePerson(person);
        return "redirect:/people/" + encodeUrlPathSegment(person.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String PersonController.updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, personService.findPerson(id));
        return "people/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String PersonController.delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Person person = personService.findPerson(id);
        personService.deletePerson(person);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/people";
    }
    
    void PersonController.populateEditForm(Model uiModel, Person person) {
        uiModel.addAttribute("person", person);
        uiModel.addAttribute("forms", formService.findAllForms());
        uiModel.addAttribute("instancepeople", instancePersonService.findAllInstancepeople());
        uiModel.addAttribute("personaddresses", personAddressService.findAllPersonAddresses());
        uiModel.addAttribute("personemails", personEmailService.findAllPersonEmails());
        uiModel.addAttribute("personinternets", personInternetService.findAllPersonInternets());
        uiModel.addAttribute("personlinkses", personLinksService.findAllPersonLinkses());
        uiModel.addAttribute("personorgs", personOrgService.findAllPersonOrgs());
        uiModel.addAttribute("personphones", personPhoneService.findAllPersonPhones());
        uiModel.addAttribute("prefixes", prefixService.findAllPrefixes());
        uiModel.addAttribute("studypeople", studyPersonService.findAllStudypeople());
        uiModel.addAttribute("suffixes", suffixService.findAllSuffixes());
    }
    
    String PersonController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
