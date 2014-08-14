package ro.roda.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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

import ro.roda.domain.Instance;
import ro.roda.service.FileService;
import ro.roda.service.InstanceDescrService;
import ro.roda.service.InstanceFormService;
import ro.roda.service.InstanceOrgService;
import ro.roda.service.InstancePersonService;
import ro.roda.service.InstanceRightTargetGroupService;
import ro.roda.service.InstanceService;
import ro.roda.service.StudyService;
import ro.roda.service.UsersService;

@RequestMapping("/instances")
@Controller
public class InstanceController {

	@Autowired
	InstanceService instanceService;

	@Autowired
	FileService fileService;

	@Autowired
	InstanceDescrService instanceDescrService;

	@Autowired
	InstanceFormService instanceFormService;

	@Autowired
	InstanceOrgService instanceOrgService;

	@Autowired
	InstancePersonService instancePersonService;

	@Autowired
	InstanceRightTargetGroupService instanceRightTargetGroupService;

	@Autowired
	StudyService studyService;

	@Autowired
	UsersService usersService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Instance instance, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, instance);
			return "instances/create";
		}
		uiModel.asMap().clear();
		instanceService.saveInstance(instance);
		return "redirect:/instances/" + encodeUrlPathSegment(instance.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new Instance());
		return "instances/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("instance", instanceService.findInstance(id));
		uiModel.addAttribute("itemId", id);
		return "instances/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("instances", instanceService.findInstanceEntries(firstResult, sizeNo));
			float nrOfPages = (float) instanceService.countAllInstances() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("instances", instanceService.findAllInstances());
		}
		addDateTimeFormatPatterns(uiModel);
		return "instances/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Instance instance, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, instance);
			return "instances/update";
		}
		uiModel.asMap().clear();
		instanceService.updateInstance(instance);
		return "redirect:/instances/" + encodeUrlPathSegment(instance.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, instanceService.findInstance(id));
		return "instances/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Instance instance = instanceService.findInstance(id);
		instanceService.deleteInstance(instance);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/instances";
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute("instance_added_date_format",
				DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, Instance instance) {
		uiModel.addAttribute("instance", instance);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("files", fileService.findAllFiles());
		uiModel.addAttribute("instancedescrs", instanceDescrService.findAllInstanceDescrs());
		uiModel.addAttribute("instanceforms", instanceFormService.findAllInstanceForms());
		uiModel.addAttribute("instanceorgs", instanceOrgService.findAllInstanceOrgs());
		uiModel.addAttribute("instancepeople", instancePersonService.findAllInstancepeople());
		uiModel.addAttribute("instancerighttargetgroups",
				instanceRightTargetGroupService.findAllInstanceRightTargetGroups());
		uiModel.addAttribute("studys", studyService.findAllStudys());
		uiModel.addAttribute("userses", usersService.findAllUserses());
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
		Instance instance = instanceService.findInstance(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (instance == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(instance.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<Instance> result = instanceService.findAllInstances();
		return new ResponseEntity<String>(Instance.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		Instance instance = Instance.fromJsonToInstance(json);
		instanceService.saveInstance(instance);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (Instance instance : Instance.fromJsonArrayToInstances(json)) {
			instanceService.saveInstance(instance);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		Instance instance = Instance.fromJsonToInstance(json);
		if (instanceService.updateInstance(instance) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (Instance instance : Instance.fromJsonArrayToInstances(json)) {
			if (instanceService.updateInstance(instance) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		Instance instance = instanceService.findInstance(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (instance == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		instanceService.deleteInstance(instance);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
