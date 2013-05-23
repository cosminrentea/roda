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
import ro.roda.domain.InstanceRight;
import ro.roda.service.InstanceRightService;
import ro.roda.service.InstanceRightTargetGroupService;
import ro.roda.service.InstanceRightValueService;

@RequestMapping("/instancerights")
@Controller
public class InstanceRightController {

	@Autowired
	InstanceRightService instanceRightService;

	@Autowired
	InstanceRightTargetGroupService instanceRightTargetGroupService;

	@Autowired
	InstanceRightValueService instanceRightValueService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid InstanceRight instanceRight, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, instanceRight);
			return "instancerights/create";
		}
		uiModel.asMap().clear();
		instanceRightService.saveInstanceRight(instanceRight);
		return "redirect:/instancerights/" + encodeUrlPathSegment(instanceRight.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new InstanceRight());
		return "instancerights/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		uiModel.addAttribute("instanceright", instanceRightService.findInstanceRight(id));
		uiModel.addAttribute("itemId", id);
		return "instancerights/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("instancerights", instanceRightService.findInstanceRightEntries(firstResult, sizeNo));
			float nrOfPages = (float) instanceRightService.countAllInstanceRights() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("instancerights", instanceRightService.findAllInstanceRights());
		}
		return "instancerights/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid InstanceRight instanceRight, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, instanceRight);
			return "instancerights/update";
		}
		uiModel.asMap().clear();
		instanceRightService.updateInstanceRight(instanceRight);
		return "redirect:/instancerights/" + encodeUrlPathSegment(instanceRight.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, instanceRightService.findInstanceRight(id));
		return "instancerights/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		InstanceRight instanceRight = instanceRightService.findInstanceRight(id);
		instanceRightService.deleteInstanceRight(instanceRight);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/instancerights";
	}

	void populateEditForm(Model uiModel, InstanceRight instanceRight) {
		uiModel.addAttribute("instanceRight", instanceRight);
		uiModel.addAttribute("instancerighttargetgroups",
				instanceRightTargetGroupService.findAllInstanceRightTargetGroups());
		uiModel.addAttribute("instancerightvalues", instanceRightValueService.findAllInstanceRightValues());
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
		InstanceRight instanceRight = instanceRightService.findInstanceRight(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (instanceRight == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(instanceRight.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<InstanceRight> result = instanceRightService.findAllInstanceRights();
		return new ResponseEntity<String>(InstanceRight.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		InstanceRight instanceRight = InstanceRight.fromJsonToInstanceRight(json);
		instanceRightService.saveInstanceRight(instanceRight);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (InstanceRight instanceRight : InstanceRight.fromJsonArrayToInstanceRights(json)) {
			instanceRightService.saveInstanceRight(instanceRight);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		InstanceRight instanceRight = InstanceRight.fromJsonToInstanceRight(json);
		if (instanceRightService.updateInstanceRight(instanceRight) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (InstanceRight instanceRight : InstanceRight.fromJsonArrayToInstanceRights(json)) {
			if (instanceRightService.updateInstanceRight(instanceRight) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		InstanceRight instanceRight = instanceRightService.findInstanceRight(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (instanceRight == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		instanceRightService.deleteInstanceRight(instanceRight);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
