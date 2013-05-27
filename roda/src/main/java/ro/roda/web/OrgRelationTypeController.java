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

import ro.roda.domain.OrgRelationType;
import ro.roda.service.OrgRelationTypeService;
import ro.roda.service.OrgRelationsService;

@RequestMapping("/orgrelationtypes")
@Controller
public class OrgRelationTypeController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		OrgRelationType orgRelationType = orgRelationTypeService.findOrgRelationType(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (orgRelationType == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(orgRelationType.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<OrgRelationType> result = orgRelationTypeService.findAllOrgRelationTypes();
		return new ResponseEntity<String>(OrgRelationType.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		OrgRelationType orgRelationType = OrgRelationType.fromJsonToOrgRelationType(json);
		orgRelationTypeService.saveOrgRelationType(orgRelationType);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (OrgRelationType orgRelationType : OrgRelationType.fromJsonArrayToOrgRelationTypes(json)) {
			orgRelationTypeService.saveOrgRelationType(orgRelationType);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		OrgRelationType orgRelationType = OrgRelationType.fromJsonToOrgRelationType(json);
		if (orgRelationTypeService.updateOrgRelationType(orgRelationType) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (OrgRelationType orgRelationType : OrgRelationType.fromJsonArrayToOrgRelationTypes(json)) {
			if (orgRelationTypeService.updateOrgRelationType(orgRelationType) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		OrgRelationType orgRelationType = orgRelationTypeService.findOrgRelationType(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (orgRelationType == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		orgRelationTypeService.deleteOrgRelationType(orgRelationType);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@Autowired
	OrgRelationTypeService orgRelationTypeService;

	@Autowired
	OrgRelationsService orgRelationsService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid OrgRelationType orgRelationType, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, orgRelationType);
			return "orgrelationtypes/create";
		}
		uiModel.asMap().clear();
		orgRelationTypeService.saveOrgRelationType(orgRelationType);
		return "redirect:/orgrelationtypes/"
				+ encodeUrlPathSegment(orgRelationType.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new OrgRelationType());
		return "orgrelationtypes/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		uiModel.addAttribute("orgrelationtype", orgRelationTypeService.findOrgRelationType(id));
		uiModel.addAttribute("itemId", id);
		return "orgrelationtypes/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("orgrelationtypes",
					orgRelationTypeService.findOrgRelationTypeEntries(firstResult, sizeNo));
			float nrOfPages = (float) orgRelationTypeService.countAllOrgRelationTypes() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("orgrelationtypes", orgRelationTypeService.findAllOrgRelationTypes());
		}
		return "orgrelationtypes/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid OrgRelationType orgRelationType, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, orgRelationType);
			return "orgrelationtypes/update";
		}
		uiModel.asMap().clear();
		orgRelationTypeService.updateOrgRelationType(orgRelationType);
		return "redirect:/orgrelationtypes/"
				+ encodeUrlPathSegment(orgRelationType.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, orgRelationTypeService.findOrgRelationType(id));
		return "orgrelationtypes/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		OrgRelationType orgRelationType = orgRelationTypeService.findOrgRelationType(id);
		orgRelationTypeService.deleteOrgRelationType(orgRelationType);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/orgrelationtypes";
	}

	void populateEditForm(Model uiModel, OrgRelationType orgRelationType) {
		uiModel.addAttribute("orgRelationType", orgRelationType);
		uiModel.addAttribute("orgrelationses", orgRelationsService.findAllOrgRelationses());
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
