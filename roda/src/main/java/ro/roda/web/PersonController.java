package ro.roda.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RequestMapping("/people")
@Controller
public class PersonController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		Person person = personService.findPerson(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (person == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(person.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<Person> result = personService.findAllPeople();
		return new ResponseEntity<String>(Person.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		Person person = Person.fromJsonToPerson(json);
		personService.savePerson(person);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (Person person : Person.fromJsonArrayToPeople(json)) {
			personService.savePerson(person);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		Person person = Person.fromJsonToPerson(json);
		if (personService.updatePerson(person) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (Person person : Person.fromJsonArrayToPeople(json)) {
			if (personService.updatePerson(person) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		Person person = personService.findPerson(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (person == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		personService.deletePerson(person);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@Autowired
	PersonService personService;

	@Autowired
	FormService formService;

	@Autowired
	InstancePersonService instancePersonService;

	@Autowired
	PersonAddressService personAddressService;

	@Autowired
	PersonEmailService personEmailService;

	@Autowired
	PersonInternetService personInternetService;

	@Autowired
	PersonLinksService personLinksService;

	@Autowired
	PersonOrgService personOrgService;

	@Autowired
	PersonPhoneService personPhoneService;

	@Autowired
	PrefixService prefixService;

	@Autowired
	StudyPersonService studyPersonService;

	@Autowired
	SuffixService suffixService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Person person, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, person);
			return "people/create";
		}
		uiModel.asMap().clear();
		personService.savePerson(person);
		return "redirect:/people/" + encodeUrlPathSegment(person.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new Person());
		return "people/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		uiModel.addAttribute("person", personService.findPerson(id));
		uiModel.addAttribute("itemId", id);
		return "people/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("people", personService.findPersonEntries(firstResult, sizeNo));
			float nrOfPages = (float) personService.countAllPeople() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("people", personService.findAllPeople());
		}
		return "people/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Person person, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, person);
			return "people/update";
		}
		uiModel.asMap().clear();
		personService.updatePerson(person);
		return "redirect:/people/" + encodeUrlPathSegment(person.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, personService.findPerson(id));
		return "people/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Person person = personService.findPerson(id);
		personService.deletePerson(person);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/people";
	}

	void populateEditForm(Model uiModel, Person person) {
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

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
		String enc = httpServletRequest.getCharacterEncoding();
		if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		try {
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
		} catch (UnsupportedEncodingException uee) {
		}
		return pathSegment;
	}
}
