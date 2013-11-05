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

import ro.roda.domain.AclObjectIdentity;
import ro.roda.service.AclClassService;
import ro.roda.service.AclEntryService;
import ro.roda.service.AclObjectIdentityService;
import ro.roda.service.AclSidService;

@RequestMapping("/aclobjectidentitys")
@Controller
public class AclObjectIdentityController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
		AclObjectIdentity aclObjectIdentity = aclObjectIdentityService.findAclObjectIdentity(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (aclObjectIdentity == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(aclObjectIdentity.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<AclObjectIdentity> result = aclObjectIdentityService.findAllAclObjectIdentitys();
		return new ResponseEntity<String>(AclObjectIdentity.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		AclObjectIdentity aclObjectIdentity = AclObjectIdentity.fromJsonToAclObjectIdentity(json);
		aclObjectIdentityService.saveAclObjectIdentity(aclObjectIdentity);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (AclObjectIdentity aclObjectIdentity : AclObjectIdentity.fromJsonArrayToAclObjectIdentitys(json)) {
			aclObjectIdentityService.saveAclObjectIdentity(aclObjectIdentity);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		AclObjectIdentity aclObjectIdentity = AclObjectIdentity.fromJsonToAclObjectIdentity(json);
		if (aclObjectIdentityService.updateAclObjectIdentity(aclObjectIdentity) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (AclObjectIdentity aclObjectIdentity : AclObjectIdentity.fromJsonArrayToAclObjectIdentitys(json)) {
			if (aclObjectIdentityService.updateAclObjectIdentity(aclObjectIdentity) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
		AclObjectIdentity aclObjectIdentity = aclObjectIdentityService.findAclObjectIdentity(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (aclObjectIdentity == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		aclObjectIdentityService.deleteAclObjectIdentity(aclObjectIdentity);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@Autowired
	AclObjectIdentityService aclObjectIdentityService;

	@Autowired
	AclClassService aclClassService;

	@Autowired
	AclEntryService aclEntryService;

	@Autowired
	AclSidService aclSidService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid AclObjectIdentity aclObjectIdentity, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, aclObjectIdentity);
			return "aclobjectidentitys/create";
		}
		uiModel.asMap().clear();
		aclObjectIdentityService.saveAclObjectIdentity(aclObjectIdentity);
		return "redirect:/aclobjectidentitys/"
				+ encodeUrlPathSegment(aclObjectIdentity.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new AclObjectIdentity());
		return "aclobjectidentitys/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		uiModel.addAttribute("aclobjectidentity", aclObjectIdentityService.findAclObjectIdentity(id));
		uiModel.addAttribute("itemId", id);
		return "aclobjectidentitys/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("aclobjectidentitys",
					aclObjectIdentityService.findAclObjectIdentityEntries(firstResult, sizeNo));
			float nrOfPages = (float) aclObjectIdentityService.countAllAclObjectIdentitys() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("aclobjectidentitys", aclObjectIdentityService.findAllAclObjectIdentitys());
		}
		return "aclobjectidentitys/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid AclObjectIdentity aclObjectIdentity, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, aclObjectIdentity);
			return "aclobjectidentitys/update";
		}
		uiModel.asMap().clear();
		aclObjectIdentityService.updateAclObjectIdentity(aclObjectIdentity);
		return "redirect:/aclobjectidentitys/"
				+ encodeUrlPathSegment(aclObjectIdentity.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, aclObjectIdentityService.findAclObjectIdentity(id));
		return "aclobjectidentitys/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		AclObjectIdentity aclObjectIdentity = aclObjectIdentityService.findAclObjectIdentity(id);
		aclObjectIdentityService.deleteAclObjectIdentity(aclObjectIdentity);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/aclobjectidentitys";
	}

	void populateEditForm(Model uiModel, AclObjectIdentity aclObjectIdentity) {
		uiModel.addAttribute("aclObjectIdentity", aclObjectIdentity);
		uiModel.addAttribute("aclclasses", aclClassService.findAllAclClasses());
		uiModel.addAttribute("aclentrys", aclEntryService.findAllAclEntrys());
		uiModel.addAttribute("aclobjectidentitys", aclObjectIdentityService.findAllAclObjectIdentitys());
		uiModel.addAttribute("aclsids", aclSidService.findAllAclSids());
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
