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

import ro.roda.domain.Org;
import ro.roda.service.InstanceOrgService;
import ro.roda.service.OrgPhoneService;
import ro.roda.service.OrgPrefixService;
import ro.roda.service.OrgRelationsService;
import ro.roda.service.OrgService;
import ro.roda.service.OrgSufixService;
import ro.roda.service.PersonOrgService;
import ro.roda.service.StudyOrgService;

@RequestMapping("/orgs")
@Controller
public class OrgController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		Org org = orgService.findOrg(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (org == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(org.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<Org> result = orgService.findAllOrgs();
		return new ResponseEntity<String>(Org.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		Org org = Org.fromJsonToOrg(json);
		orgService.saveOrg(org);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (Org org : Org.fromJsonArrayToOrgs(json)) {
			orgService.saveOrg(org);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		Org org = Org.fromJsonToOrg(json);
		if (orgService.updateOrg(org) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (Org org : Org.fromJsonArrayToOrgs(json)) {
			if (orgService.updateOrg(org) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		Org org = orgService.findOrg(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (org == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		orgService.deleteOrg(org);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@Autowired
	OrgService orgService;

	@Autowired
	InstanceOrgService instanceOrgService;

	@Autowired
	OrgPhoneService orgPhoneService;

	@Autowired
	OrgPrefixService orgPrefixService;

	@Autowired
	OrgRelationsService orgRelationsService;

	@Autowired
	OrgSufixService orgSufixService;

	@Autowired
	PersonOrgService personOrgService;

	@Autowired
	StudyOrgService studyOrgService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Org org, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, org);
			return "orgs/create";
		}
		uiModel.asMap().clear();
		orgService.saveOrg(org);
		return "redirect:/orgs/" + encodeUrlPathSegment(org.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new Org());
		return "orgs/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		uiModel.addAttribute("org", orgService.findOrg(id));
		uiModel.addAttribute("itemId", id);
		return "orgs/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("orgs", orgService.findOrgEntries(firstResult, sizeNo));
			float nrOfPages = (float) orgService.countAllOrgs() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("orgs", orgService.findAllOrgs());
		}
		return "orgs/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Org org, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, org);
			return "orgs/update";
		}
		uiModel.asMap().clear();
		orgService.updateOrg(org);
		return "redirect:/orgs/" + encodeUrlPathSegment(org.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, orgService.findOrg(id));
		return "orgs/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Org org = orgService.findOrg(id);
		orgService.deleteOrg(org);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/orgs";
	}

	void populateEditForm(Model uiModel, Org org) {
		uiModel.addAttribute("org", org);
		uiModel.addAttribute("instanceorgs", instanceOrgService.findAllInstanceOrgs());
		uiModel.addAttribute("orgphones", orgPhoneService.findAllOrgPhones());
		uiModel.addAttribute("orgprefixes", orgPrefixService.findAllOrgPrefixes());
		uiModel.addAttribute("orgrelationses", orgRelationsService.findAllOrgRelationses());
		uiModel.addAttribute("orgsufixes", orgSufixService.findAllOrgSufixes());
		uiModel.addAttribute("personorgs", personOrgService.findAllPersonOrgs());
		uiModel.addAttribute("studyorgs", studyOrgService.findAllStudyOrgs());
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
