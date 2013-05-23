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
import ro.roda.domain.InstanceRightTargetGroup;
import ro.roda.domain.InstanceRightTargetGroupPK;
import ro.roda.service.InstanceRightService;
import ro.roda.service.InstanceRightTargetGroupService;
import ro.roda.service.InstanceRightValueService;
import ro.roda.service.InstanceService;
import ro.roda.service.TargetGroupService;

@RequestMapping("/instancerighttargetgroups")
@Controller
public class InstanceRightTargetGroupController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") InstanceRightTargetGroupPK id) {
		InstanceRightTargetGroup instanceRightTargetGroup = instanceRightTargetGroupService
				.findInstanceRightTargetGroup(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (instanceRightTargetGroup == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(instanceRightTargetGroup.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<InstanceRightTargetGroup> result = instanceRightTargetGroupService.findAllInstanceRightTargetGroups();
		return new ResponseEntity<String>(InstanceRightTargetGroup.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		InstanceRightTargetGroup instanceRightTargetGroup = InstanceRightTargetGroup
				.fromJsonToInstanceRightTargetGroup(json);
		instanceRightTargetGroupService.saveInstanceRightTargetGroup(instanceRightTargetGroup);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (InstanceRightTargetGroup instanceRightTargetGroup : InstanceRightTargetGroup
				.fromJsonArrayToInstanceRightTargetGroups(json)) {
			instanceRightTargetGroupService.saveInstanceRightTargetGroup(instanceRightTargetGroup);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		InstanceRightTargetGroup instanceRightTargetGroup = InstanceRightTargetGroup
				.fromJsonToInstanceRightTargetGroup(json);
		if (instanceRightTargetGroupService.updateInstanceRightTargetGroup(instanceRightTargetGroup) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (InstanceRightTargetGroup instanceRightTargetGroup : InstanceRightTargetGroup
				.fromJsonArrayToInstanceRightTargetGroups(json)) {
			if (instanceRightTargetGroupService.updateInstanceRightTargetGroup(instanceRightTargetGroup) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") InstanceRightTargetGroupPK id) {
		InstanceRightTargetGroup instanceRightTargetGroup = instanceRightTargetGroupService
				.findInstanceRightTargetGroup(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (instanceRightTargetGroup == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		instanceRightTargetGroupService.deleteInstanceRightTargetGroup(instanceRightTargetGroup);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	private ConversionService conversionService;

	@Autowired
	InstanceRightTargetGroupService instanceRightTargetGroupService;

	@Autowired
	InstanceService instanceService;

	@Autowired
	InstanceRightService instanceRightService;

	@Autowired
	InstanceRightValueService instanceRightValueService;

	@Autowired
	TargetGroupService targetGroupService;

	@Autowired
	public InstanceRightTargetGroupController(ConversionService conversionService) {
		super();
		this.conversionService = conversionService;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid InstanceRightTargetGroup instanceRightTargetGroup, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, instanceRightTargetGroup);
			return "instancerighttargetgroups/create";
		}
		uiModel.asMap().clear();
		instanceRightTargetGroupService.saveInstanceRightTargetGroup(instanceRightTargetGroup);
		return "redirect:/instancerighttargetgroups/"
				+ encodeUrlPathSegment(conversionService.convert(instanceRightTargetGroup.getId(), String.class),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new InstanceRightTargetGroup());
		return "instancerighttargetgroups/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") InstanceRightTargetGroupPK id, Model uiModel) {
		uiModel.addAttribute("instancerighttargetgroup",
				instanceRightTargetGroupService.findInstanceRightTargetGroup(id));
		uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
		return "instancerighttargetgroups/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("instancerighttargetgroups",
					instanceRightTargetGroupService.findInstanceRightTargetGroupEntries(firstResult, sizeNo));
			float nrOfPages = (float) instanceRightTargetGroupService.countAllInstanceRightTargetGroups() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("instancerighttargetgroups",
					instanceRightTargetGroupService.findAllInstanceRightTargetGroups());
		}
		return "instancerighttargetgroups/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid InstanceRightTargetGroup instanceRightTargetGroup, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, instanceRightTargetGroup);
			return "instancerighttargetgroups/update";
		}
		uiModel.asMap().clear();
		instanceRightTargetGroupService.updateInstanceRightTargetGroup(instanceRightTargetGroup);
		return "redirect:/instancerighttargetgroups/"
				+ encodeUrlPathSegment(conversionService.convert(instanceRightTargetGroup.getId(), String.class),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") InstanceRightTargetGroupPK id, Model uiModel) {
		populateEditForm(uiModel, instanceRightTargetGroupService.findInstanceRightTargetGroup(id));
		return "instancerighttargetgroups/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") InstanceRightTargetGroupPK id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		InstanceRightTargetGroup instanceRightTargetGroup = instanceRightTargetGroupService
				.findInstanceRightTargetGroup(id);
		instanceRightTargetGroupService.deleteInstanceRightTargetGroup(instanceRightTargetGroup);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/instancerighttargetgroups";
	}

	void populateEditForm(Model uiModel, InstanceRightTargetGroup instanceRightTargetGroup) {
		uiModel.addAttribute("instanceRightTargetGroup", instanceRightTargetGroup);
		uiModel.addAttribute("instances", instanceService.findAllInstances());
		uiModel.addAttribute("instancerights", instanceRightService.findAllInstanceRights());
		uiModel.addAttribute("instancerightvalues", instanceRightValueService.findAllInstanceRightValues());
		uiModel.addAttribute("targetgroups", targetGroupService.findAllTargetGroups());
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
