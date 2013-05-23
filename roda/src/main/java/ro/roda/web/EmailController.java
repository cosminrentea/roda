package ro.roda.web;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
import ro.roda.domain.Email;
import ro.roda.service.EmailService;
import ro.roda.service.OrgEmailService;
import ro.roda.service.PersonEmailService;

@RequestMapping("/emails")
@Controller
public class EmailController {

	@Autowired
	EmailService emailService;

	@Autowired
	OrgEmailService orgEmailService;

	@Autowired
	PersonEmailService personEmailService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Email email, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, email);
			return "emails/create";
		}
		uiModel.asMap().clear();
		emailService.saveEmail(email);
		return "redirect:/emails/" + encodeUrlPathSegment(email.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new Email());
		return "emails/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		uiModel.addAttribute("email", emailService.findEmail(id));
		uiModel.addAttribute("itemId", id);
		return "emails/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("emails", emailService.findEmailEntries(firstResult, sizeNo));
			float nrOfPages = (float) emailService.countAllEmails() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("emails", emailService.findAllEmails());
		}
		return "emails/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Email email, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, email);
			return "emails/update";
		}
		uiModel.asMap().clear();
		emailService.updateEmail(email);
		return "redirect:/emails/" + encodeUrlPathSegment(email.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, emailService.findEmail(id));
		return "emails/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Email email = emailService.findEmail(id);
		emailService.deleteEmail(email);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/emails";
	}

	void populateEditForm(Model uiModel, Email email) {
		uiModel.addAttribute("email", email);
		uiModel.addAttribute("orgemails", orgEmailService.findAllOrgEmails());
		uiModel.addAttribute("personemails", personEmailService.findAllPersonEmails());
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

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		Email email = emailService.findEmail(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (email == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(email.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<Email> result = emailService.findAllEmails();
		return new ResponseEntity<String>(Email.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		Email email = Email.fromJsonToEmail(json);
		emailService.saveEmail(email);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (Email email : Email.fromJsonArrayToEmails(json)) {
			emailService.saveEmail(email);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		Email email = Email.fromJsonToEmail(json);
		if (emailService.updateEmail(email) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (Email email : Email.fromJsonArrayToEmails(json)) {
			if (emailService.updateEmail(email) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		Email email = emailService.findEmail(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (email == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		emailService.deleteEmail(email);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
