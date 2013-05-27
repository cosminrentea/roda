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

import ro.roda.domain.SettingGroup;
import ro.roda.service.SettingGroupService;
import ro.roda.service.SettingService;

@RequestMapping("/settinggroups")
@Controller
public class SettingGroupController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		SettingGroup settingGroup = settingGroupService.findSettingGroup(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (settingGroup == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(settingGroup.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<SettingGroup> result = settingGroupService.findAllSettingGroups();
		return new ResponseEntity<String>(SettingGroup.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		SettingGroup settingGroup = SettingGroup.fromJsonToSettingGroup(json);
		settingGroupService.saveSettingGroup(settingGroup);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (SettingGroup settingGroup : SettingGroup.fromJsonArrayToSettingGroups(json)) {
			settingGroupService.saveSettingGroup(settingGroup);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		SettingGroup settingGroup = SettingGroup.fromJsonToSettingGroup(json);
		if (settingGroupService.updateSettingGroup(settingGroup) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (SettingGroup settingGroup : SettingGroup.fromJsonArrayToSettingGroups(json)) {
			if (settingGroupService.updateSettingGroup(settingGroup) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		SettingGroup settingGroup = settingGroupService.findSettingGroup(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (settingGroup == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		settingGroupService.deleteSettingGroup(settingGroup);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@Autowired
	SettingGroupService settingGroupService;

	@Autowired
	SettingService settingService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid SettingGroup settingGroup, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, settingGroup);
			return "settinggroups/create";
		}
		uiModel.asMap().clear();
		settingGroupService.saveSettingGroup(settingGroup);
		return "redirect:/settinggroups/" + encodeUrlPathSegment(settingGroup.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new SettingGroup());
		return "settinggroups/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		uiModel.addAttribute("settinggroup", settingGroupService.findSettingGroup(id));
		uiModel.addAttribute("itemId", id);
		return "settinggroups/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("settinggroups", settingGroupService.findSettingGroupEntries(firstResult, sizeNo));
			float nrOfPages = (float) settingGroupService.countAllSettingGroups() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("settinggroups", settingGroupService.findAllSettingGroups());
		}
		return "settinggroups/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid SettingGroup settingGroup, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, settingGroup);
			return "settinggroups/update";
		}
		uiModel.asMap().clear();
		settingGroupService.updateSettingGroup(settingGroup);
		return "redirect:/settinggroups/" + encodeUrlPathSegment(settingGroup.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, settingGroupService.findSettingGroup(id));
		return "settinggroups/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		SettingGroup settingGroup = settingGroupService.findSettingGroup(id);
		settingGroupService.deleteSettingGroup(settingGroup);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/settinggroups";
	}

	void populateEditForm(Model uiModel, SettingGroup settingGroup) {
		uiModel.addAttribute("settingGroup", settingGroup);
		uiModel.addAttribute("settings", settingService.findAllSettings());
		uiModel.addAttribute("settinggroups", settingGroupService.findAllSettingGroups());
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
