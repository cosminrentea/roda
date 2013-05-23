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
import ro.roda.domain.CmsLayoutGroup;
import ro.roda.service.CmsLayoutGroupService;
import ro.roda.service.CmsLayoutService;

@RequestMapping("/cmslayoutgroups")
@Controller
public class CmsLayoutGroupController {

	@Autowired
	CmsLayoutGroupService cmsLayoutGroupService;

	@Autowired
	CmsLayoutService cmsLayoutService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid CmsLayoutGroup cmsLayoutGroup, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, cmsLayoutGroup);
			return "cmslayoutgroups/create";
		}
		uiModel.asMap().clear();
		cmsLayoutGroupService.saveCmsLayoutGroup(cmsLayoutGroup);
		return "redirect:/cmslayoutgroups/"
				+ encodeUrlPathSegment(cmsLayoutGroup.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new CmsLayoutGroup());
		return "cmslayoutgroups/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		uiModel.addAttribute("cmslayoutgroup", cmsLayoutGroupService.findCmsLayoutGroup(id));
		uiModel.addAttribute("itemId", id);
		return "cmslayoutgroups/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("cmslayoutgroups",
					cmsLayoutGroupService.findCmsLayoutGroupEntries(firstResult, sizeNo));
			float nrOfPages = (float) cmsLayoutGroupService.countAllCmsLayoutGroups() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("cmslayoutgroups", cmsLayoutGroupService.findAllCmsLayoutGroups());
		}
		return "cmslayoutgroups/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid CmsLayoutGroup cmsLayoutGroup, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, cmsLayoutGroup);
			return "cmslayoutgroups/update";
		}
		uiModel.asMap().clear();
		cmsLayoutGroupService.updateCmsLayoutGroup(cmsLayoutGroup);
		return "redirect:/cmslayoutgroups/"
				+ encodeUrlPathSegment(cmsLayoutGroup.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, cmsLayoutGroupService.findCmsLayoutGroup(id));
		return "cmslayoutgroups/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		CmsLayoutGroup cmsLayoutGroup = cmsLayoutGroupService.findCmsLayoutGroup(id);
		cmsLayoutGroupService.deleteCmsLayoutGroup(cmsLayoutGroup);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/cmslayoutgroups";
	}

	void populateEditForm(Model uiModel, CmsLayoutGroup cmsLayoutGroup) {
		uiModel.addAttribute("cmsLayoutGroup", cmsLayoutGroup);
		uiModel.addAttribute("cmslayouts", cmsLayoutService.findAllCmsLayouts());
		uiModel.addAttribute("cmslayoutgroups", cmsLayoutGroupService.findAllCmsLayoutGroups());
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
		CmsLayoutGroup cmsLayoutGroup = cmsLayoutGroupService.findCmsLayoutGroup(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (cmsLayoutGroup == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(cmsLayoutGroup.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<CmsLayoutGroup> result = cmsLayoutGroupService.findAllCmsLayoutGroups();
		return new ResponseEntity<String>(CmsLayoutGroup.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		CmsLayoutGroup cmsLayoutGroup = CmsLayoutGroup.fromJsonToCmsLayoutGroup(json);
		cmsLayoutGroupService.saveCmsLayoutGroup(cmsLayoutGroup);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (CmsLayoutGroup cmsLayoutGroup : CmsLayoutGroup.fromJsonArrayToCmsLayoutGroups(json)) {
			cmsLayoutGroupService.saveCmsLayoutGroup(cmsLayoutGroup);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		CmsLayoutGroup cmsLayoutGroup = CmsLayoutGroup.fromJsonToCmsLayoutGroup(json);
		if (cmsLayoutGroupService.updateCmsLayoutGroup(cmsLayoutGroup) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (CmsLayoutGroup cmsLayoutGroup : CmsLayoutGroup.fromJsonArrayToCmsLayoutGroups(json)) {
			if (cmsLayoutGroupService.updateCmsLayoutGroup(cmsLayoutGroup) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		CmsLayoutGroup cmsLayoutGroup = cmsLayoutGroupService.findCmsLayoutGroup(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (cmsLayoutGroup == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		cmsLayoutGroupService.deleteCmsLayoutGroup(cmsLayoutGroup);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
