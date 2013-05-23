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
import ro.roda.domain.OrgEmail;
import ro.roda.domain.OrgEmailPK;
import ro.roda.service.EmailService;
import ro.roda.service.OrgEmailService;
import ro.roda.service.OrgService;

@RequestMapping("/orgemails")
@Controller
public class OrgEmailController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") OrgEmailPK id) {
		OrgEmail orgEmail = orgEmailService.findOrgEmail(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (orgEmail == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(orgEmail.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<OrgEmail> result = orgEmailService.findAllOrgEmails();
		return new ResponseEntity<String>(OrgEmail.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		OrgEmail orgEmail = OrgEmail.fromJsonToOrgEmail(json);
		orgEmailService.saveOrgEmail(orgEmail);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (OrgEmail orgEmail : OrgEmail.fromJsonArrayToOrgEmails(json)) {
			orgEmailService.saveOrgEmail(orgEmail);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		OrgEmail orgEmail = OrgEmail.fromJsonToOrgEmail(json);
		if (orgEmailService.updateOrgEmail(orgEmail) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (OrgEmail orgEmail : OrgEmail.fromJsonArrayToOrgEmails(json)) {
			if (orgEmailService.updateOrgEmail(orgEmail) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") OrgEmailPK id) {
		OrgEmail orgEmail = orgEmailService.findOrgEmail(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (orgEmail == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		orgEmailService.deleteOrgEmail(orgEmail);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	private ConversionService conversionService;

	@Autowired
	OrgEmailService orgEmailService;

	@Autowired
	EmailService emailService;

	@Autowired
	OrgService orgService;

	@Autowired
	public OrgEmailController(ConversionService conversionService) {
		super();
		this.conversionService = conversionService;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid OrgEmail orgEmail, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, orgEmail);
			return "orgemails/create";
		}
		uiModel.asMap().clear();
		orgEmailService.saveOrgEmail(orgEmail);
		return "redirect:/orgemails/"
				+ encodeUrlPathSegment(conversionService.convert(orgEmail.getId(), String.class), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new OrgEmail());
		return "orgemails/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") OrgEmailPK id, Model uiModel) {
		uiModel.addAttribute("orgemail", orgEmailService.findOrgEmail(id));
		uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
		return "orgemails/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("orgemails", orgEmailService.findOrgEmailEntries(firstResult, sizeNo));
			float nrOfPages = (float) orgEmailService.countAllOrgEmails() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("orgemails", orgEmailService.findAllOrgEmails());
		}
		return "orgemails/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid OrgEmail orgEmail, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, orgEmail);
			return "orgemails/update";
		}
		uiModel.asMap().clear();
		orgEmailService.updateOrgEmail(orgEmail);
		return "redirect:/orgemails/"
				+ encodeUrlPathSegment(conversionService.convert(orgEmail.getId(), String.class), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") OrgEmailPK id, Model uiModel) {
		populateEditForm(uiModel, orgEmailService.findOrgEmail(id));
		return "orgemails/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") OrgEmailPK id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		OrgEmail orgEmail = orgEmailService.findOrgEmail(id);
		orgEmailService.deleteOrgEmail(orgEmail);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/orgemails";
	}

	void populateEditForm(Model uiModel, OrgEmail orgEmail) {
		uiModel.addAttribute("orgEmail", orgEmail);
		uiModel.addAttribute("emails", emailService.findAllEmails());
		uiModel.addAttribute("orgs", orgService.findAllOrgs());
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
