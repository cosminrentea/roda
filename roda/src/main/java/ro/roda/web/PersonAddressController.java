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
import ro.roda.domain.PersonAddress;
import ro.roda.domain.PersonAddressPK;
import ro.roda.service.AddressService;
import ro.roda.service.PersonAddressService;
import ro.roda.service.PersonService;

@RequestMapping("/personaddresses")
@Controller


public class PersonAddressController {

	private ConversionService conversionService;

	@Autowired
    PersonAddressService personAddressService;

	@Autowired
    AddressService addressService;

	@Autowired
    PersonService personService;

	@Autowired
    public PersonAddressController(ConversionService conversionService) {
        super();
        this.conversionService = conversionService;
    }

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid PersonAddress personAddress, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, personAddress);
            return "personaddresses/create";
        }
        uiModel.asMap().clear();
        personAddressService.savePersonAddress(personAddress);
        return "redirect:/personaddresses/" + encodeUrlPathSegment(conversionService.convert(personAddress.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new PersonAddress());
        return "personaddresses/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") PersonAddressPK id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("personaddress", personAddressService.findPersonAddress(id));
        uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
        return "personaddresses/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("personaddresses", personAddressService.findPersonAddressEntries(firstResult, sizeNo));
            float nrOfPages = (float) personAddressService.countAllPersonAddresses() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("personaddresses", personAddressService.findAllPersonAddresses());
        }
        addDateTimeFormatPatterns(uiModel);
        return "personaddresses/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid PersonAddress personAddress, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, personAddress);
            return "personaddresses/update";
        }
        uiModel.asMap().clear();
        personAddressService.updatePersonAddress(personAddress);
        return "redirect:/personaddresses/" + encodeUrlPathSegment(conversionService.convert(personAddress.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") PersonAddressPK id, Model uiModel) {
        populateEditForm(uiModel, personAddressService.findPersonAddress(id));
        return "personaddresses/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") PersonAddressPK id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        PersonAddress personAddress = personAddressService.findPersonAddress(id);
        personAddressService.deletePersonAddress(personAddress);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/personaddresses";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("personAddress_datestart_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("personAddress_dateend_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, PersonAddress personAddress) {
        uiModel.addAttribute("personAddress", personAddress);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("addresses", addressService.findAllAddresses());
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
    public ResponseEntity<String> showJson(@PathVariable("id") PersonAddressPK id) {
        PersonAddress personAddress = personAddressService.findPersonAddress(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (personAddress == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(personAddress.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<PersonAddress> result = personAddressService.findAllPersonAddresses();
        return new ResponseEntity<String>(PersonAddress.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        PersonAddress personAddress = PersonAddress.fromJsonToPersonAddress(json);
        personAddressService.savePersonAddress(personAddress);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (PersonAddress personAddress: PersonAddress.fromJsonArrayToPersonAddresses(json)) {
            personAddressService.savePersonAddress(personAddress);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        PersonAddress personAddress = PersonAddress.fromJsonToPersonAddress(json);
        if (personAddressService.updatePersonAddress(personAddress) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (PersonAddress personAddress: PersonAddress.fromJsonArrayToPersonAddresses(json)) {
            if (personAddressService.updatePersonAddress(personAddress) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") PersonAddressPK id) {
        PersonAddress personAddress = personAddressService.findPersonAddress(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (personAddress == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        personAddressService.deletePersonAddress(personAddress);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
