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
import ro.roda.domain.OrgPrefix;
import ro.roda.service.OrgPrefixService;
import ro.roda.service.OrgService;

@RequestMapping("/orgprefixes")
@Controller
public class OrgPrefixController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		OrgPrefix orgPrefix = orgPrefixService.findOrgPrefix(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (orgPrefix == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(orgPrefix.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<OrgPrefix> result = orgPrefixService.findAllOrgPrefixes();
		return new ResponseEntity<String>(OrgPrefix.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		OrgPrefix orgPrefix = OrgPrefix.fromJsonToOrgPrefix(json);
		orgPrefixService.saveOrgPrefix(orgPrefix);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (OrgPrefix orgPrefix : OrgPrefix.fromJsonArrayToOrgPrefixes(json)) {
			orgPrefixService.saveOrgPrefix(orgPrefix);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		OrgPrefix orgPrefix = OrgPrefix.fromJsonToOrgPrefix(json);
		if (orgPrefixService.updateOrgPrefix(orgPrefix) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (OrgPrefix orgPrefix : OrgPrefix.fromJsonArrayToOrgPrefixes(json)) {
			if (orgPrefixService.updateOrgPrefix(orgPrefix) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		OrgPrefix orgPrefix = orgPrefixService.findOrgPrefix(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (orgPrefix == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		orgPrefixService.deleteOrgPrefix(orgPrefix);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@Autowired
	OrgPrefixService orgPrefixService;

	@Autowired
	OrgService orgService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid OrgPrefix orgPrefix, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, orgPrefix);
			return "orgprefixes/create";
		}
		uiModel.asMap().clear();
		orgPrefixService.saveOrgPrefix(orgPrefix);
		return "redirect:/orgprefixes/" + encodeUrlPathSegment(orgPrefix.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new OrgPrefix());
		return "orgprefixes/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		uiModel.addAttribute("orgprefix", orgPrefixService.findOrgPrefix(id));
		uiModel.addAttribute("itemId", id);
		return "orgprefixes/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("orgprefixes", orgPrefixService.findOrgPrefixEntries(firstResult, sizeNo));
			float nrOfPages = (float) orgPrefixService.countAllOrgPrefixes() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("orgprefixes", orgPrefixService.findAllOrgPrefixes());
		}
		return "orgprefixes/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid OrgPrefix orgPrefix, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, orgPrefix);
			return "orgprefixes/update";
		}
		uiModel.asMap().clear();
		orgPrefixService.updateOrgPrefix(orgPrefix);
		return "redirect:/orgprefixes/" + encodeUrlPathSegment(orgPrefix.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, orgPrefixService.findOrgPrefix(id));
		return "orgprefixes/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		OrgPrefix orgPrefix = orgPrefixService.findOrgPrefix(id);
		orgPrefixService.deleteOrgPrefix(orgPrefix);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/orgprefixes";
	}

	void populateEditForm(Model uiModel, OrgPrefix orgPrefix) {
		uiModel.addAttribute("orgPrefix", orgPrefix);
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
