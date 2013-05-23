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
import ro.roda.domain.InstanceOrg;
import ro.roda.domain.InstanceOrgPK;
import ro.roda.service.InstanceOrgAssocService;
import ro.roda.service.InstanceOrgService;
import ro.roda.service.InstanceService;
import ro.roda.service.OrgService;

@RequestMapping("/instanceorgs")
@Controller
public class InstanceOrgController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") InstanceOrgPK id) {
		InstanceOrg instanceOrg = instanceOrgService.findInstanceOrg(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (instanceOrg == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(instanceOrg.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<InstanceOrg> result = instanceOrgService.findAllInstanceOrgs();
		return new ResponseEntity<String>(InstanceOrg.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		InstanceOrg instanceOrg = InstanceOrg.fromJsonToInstanceOrg(json);
		instanceOrgService.saveInstanceOrg(instanceOrg);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (InstanceOrg instanceOrg : InstanceOrg.fromJsonArrayToInstanceOrgs(json)) {
			instanceOrgService.saveInstanceOrg(instanceOrg);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		InstanceOrg instanceOrg = InstanceOrg.fromJsonToInstanceOrg(json);
		if (instanceOrgService.updateInstanceOrg(instanceOrg) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (InstanceOrg instanceOrg : InstanceOrg.fromJsonArrayToInstanceOrgs(json)) {
			if (instanceOrgService.updateInstanceOrg(instanceOrg) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") InstanceOrgPK id) {
		InstanceOrg instanceOrg = instanceOrgService.findInstanceOrg(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (instanceOrg == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		instanceOrgService.deleteInstanceOrg(instanceOrg);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	private ConversionService conversionService;

	@Autowired
	InstanceOrgService instanceOrgService;

	@Autowired
	InstanceService instanceService;

	@Autowired
	InstanceOrgAssocService instanceOrgAssocService;

	@Autowired
	OrgService orgService;

	@Autowired
	public InstanceOrgController(ConversionService conversionService) {
		super();
		this.conversionService = conversionService;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid InstanceOrg instanceOrg, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, instanceOrg);
			return "instanceorgs/create";
		}
		uiModel.asMap().clear();
		instanceOrgService.saveInstanceOrg(instanceOrg);
		return "redirect:/instanceorgs/"
				+ encodeUrlPathSegment(conversionService.convert(instanceOrg.getId(), String.class), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new InstanceOrg());
		return "instanceorgs/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") InstanceOrgPK id, Model uiModel) {
		uiModel.addAttribute("instanceorg", instanceOrgService.findInstanceOrg(id));
		uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
		return "instanceorgs/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("instanceorgs", instanceOrgService.findInstanceOrgEntries(firstResult, sizeNo));
			float nrOfPages = (float) instanceOrgService.countAllInstanceOrgs() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("instanceorgs", instanceOrgService.findAllInstanceOrgs());
		}
		return "instanceorgs/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid InstanceOrg instanceOrg, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, instanceOrg);
			return "instanceorgs/update";
		}
		uiModel.asMap().clear();
		instanceOrgService.updateInstanceOrg(instanceOrg);
		return "redirect:/instanceorgs/"
				+ encodeUrlPathSegment(conversionService.convert(instanceOrg.getId(), String.class), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") InstanceOrgPK id, Model uiModel) {
		populateEditForm(uiModel, instanceOrgService.findInstanceOrg(id));
		return "instanceorgs/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") InstanceOrgPK id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		InstanceOrg instanceOrg = instanceOrgService.findInstanceOrg(id);
		instanceOrgService.deleteInstanceOrg(instanceOrg);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/instanceorgs";
	}

	void populateEditForm(Model uiModel, InstanceOrg instanceOrg) {
		uiModel.addAttribute("instanceOrg", instanceOrg);
		uiModel.addAttribute("instances", instanceService.findAllInstances());
		uiModel.addAttribute("instanceorgassocs", instanceOrgAssocService.findAllInstanceOrgAssocs());
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
