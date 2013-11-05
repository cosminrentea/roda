package ro.roda.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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

import ro.roda.domain.PersonLinks;
import ro.roda.service.PersonLinksService;
import ro.roda.service.PersonService;
import ro.roda.service.UsersService;

@RequestMapping("/personlinkses")
@Controller
public class PersonLinksController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		PersonLinks personLinks = personLinksService.findPersonLinks(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (personLinks == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(personLinks.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<PersonLinks> result = personLinksService.findAllPersonLinkses();
		return new ResponseEntity<String>(PersonLinks.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		PersonLinks personLinks = PersonLinks.fromJsonToPersonLinks(json);
		personLinksService.savePersonLinks(personLinks);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (PersonLinks personLinks : PersonLinks.fromJsonArrayToPersonLinkses(json)) {
			personLinksService.savePersonLinks(personLinks);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		PersonLinks personLinks = PersonLinks.fromJsonToPersonLinks(json);
		if (personLinksService.updatePersonLinks(personLinks) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (PersonLinks personLinks : PersonLinks.fromJsonArrayToPersonLinkses(json)) {
			if (personLinksService.updatePersonLinks(personLinks) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		PersonLinks personLinks = personLinksService.findPersonLinks(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (personLinks == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		personLinksService.deletePersonLinks(personLinks);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@Autowired
	PersonLinksService personLinksService;

	@Autowired
	PersonService personService;

	@Autowired
	UsersService usersService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid PersonLinks personLinks, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, personLinks);
			return "personlinkses/create";
		}
		uiModel.asMap().clear();
		personLinksService.savePersonLinks(personLinks);
		return "redirect:/personlinkses/" + encodeUrlPathSegment(personLinks.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new PersonLinks());
		return "personlinkses/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("personlinks", personLinksService.findPersonLinks(id));
		uiModel.addAttribute("itemId", id);
		return "personlinkses/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("personlinkses", personLinksService.findPersonLinksEntries(firstResult, sizeNo));
			float nrOfPages = (float) personLinksService.countAllPersonLinkses() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("personlinkses", personLinksService.findAllPersonLinkses());
		}
		addDateTimeFormatPatterns(uiModel);
		return "personlinkses/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid PersonLinks personLinks, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, personLinks);
			return "personlinkses/update";
		}
		uiModel.asMap().clear();
		personLinksService.updatePersonLinks(personLinks);
		return "redirect:/personlinkses/" + encodeUrlPathSegment(personLinks.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, personLinksService.findPersonLinks(id));
		return "personlinkses/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		PersonLinks personLinks = personLinksService.findPersonLinks(id);
		personLinksService.deletePersonLinks(personLinks);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/personlinkses";
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute("personLinks_statustime_date_format",
				DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, PersonLinks personLinks) {
		uiModel.addAttribute("personLinks", personLinks);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("people", personService.findAllPeople());
		uiModel.addAttribute("userses", usersService.findAllUserses());
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
