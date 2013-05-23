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
import ro.roda.domain.OrgPhone;
import ro.roda.domain.OrgPhonePK;
import ro.roda.service.OrgPhoneService;
import ro.roda.service.OrgService;
import ro.roda.service.PhoneService;

@RequestMapping("/orgphones")
@Controller
public class OrgPhoneController {

	private ConversionService conversionService;

	@Autowired
	OrgPhoneService orgPhoneService;

	@Autowired
	OrgService orgService;

	@Autowired
	PhoneService phoneService;

	@Autowired
	public OrgPhoneController(ConversionService conversionService) {
		super();
		this.conversionService = conversionService;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid OrgPhone orgPhone, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, orgPhone);
			return "orgphones/create";
		}
		uiModel.asMap().clear();
		orgPhoneService.saveOrgPhone(orgPhone);
		return "redirect:/orgphones/"
				+ encodeUrlPathSegment(conversionService.convert(orgPhone.getId(), String.class), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new OrgPhone());
		return "orgphones/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") OrgPhonePK id, Model uiModel) {
		uiModel.addAttribute("orgphone", orgPhoneService.findOrgPhone(id));
		uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
		return "orgphones/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("orgphones", orgPhoneService.findOrgPhoneEntries(firstResult, sizeNo));
			float nrOfPages = (float) orgPhoneService.countAllOrgPhones() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("orgphones", orgPhoneService.findAllOrgPhones());
		}
		return "orgphones/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid OrgPhone orgPhone, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, orgPhone);
			return "orgphones/update";
		}
		uiModel.asMap().clear();
		orgPhoneService.updateOrgPhone(orgPhone);
		return "redirect:/orgphones/"
				+ encodeUrlPathSegment(conversionService.convert(orgPhone.getId(), String.class), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") OrgPhonePK id, Model uiModel) {
		populateEditForm(uiModel, orgPhoneService.findOrgPhone(id));
		return "orgphones/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") OrgPhonePK id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		OrgPhone orgPhone = orgPhoneService.findOrgPhone(id);
		orgPhoneService.deleteOrgPhone(orgPhone);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/orgphones";
	}

	void populateEditForm(Model uiModel, OrgPhone orgPhone) {
		uiModel.addAttribute("orgPhone", orgPhone);
		uiModel.addAttribute("orgs", orgService.findAllOrgs());
		uiModel.addAttribute("phones", phoneService.findAllPhones());
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
	public ResponseEntity<String> showJson(@PathVariable("id") OrgPhonePK id) {
		OrgPhone orgPhone = orgPhoneService.findOrgPhone(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (orgPhone == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(orgPhone.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<OrgPhone> result = orgPhoneService.findAllOrgPhones();
		return new ResponseEntity<String>(OrgPhone.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		OrgPhone orgPhone = OrgPhone.fromJsonToOrgPhone(json);
		orgPhoneService.saveOrgPhone(orgPhone);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (OrgPhone orgPhone : OrgPhone.fromJsonArrayToOrgPhones(json)) {
			orgPhoneService.saveOrgPhone(orgPhone);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		OrgPhone orgPhone = OrgPhone.fromJsonToOrgPhone(json);
		if (orgPhoneService.updateOrgPhone(orgPhone) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (OrgPhone orgPhone : OrgPhone.fromJsonArrayToOrgPhones(json)) {
			if (orgPhoneService.updateOrgPhone(orgPhone) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") OrgPhonePK id) {
		OrgPhone orgPhone = orgPhoneService.findOrgPhone(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (orgPhone == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		orgPhoneService.deleteOrgPhone(orgPhone);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
