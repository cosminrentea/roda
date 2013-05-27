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

import ro.roda.domain.UserSettingGroup;
import ro.roda.service.UserSettingGroupService;
import ro.roda.service.UserSettingService;

@RequestMapping("/usersettinggroups")
@Controller
public class UserSettingGroupController {

	@Autowired
	UserSettingGroupService userSettingGroupService;

	@Autowired
	UserSettingService userSettingService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid UserSettingGroup userSettingGroup, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, userSettingGroup);
			return "usersettinggroups/create";
		}
		uiModel.asMap().clear();
		userSettingGroupService.saveUserSettingGroup(userSettingGroup);
		return "redirect:/usersettinggroups/"
				+ encodeUrlPathSegment(userSettingGroup.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new UserSettingGroup());
		return "usersettinggroups/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		uiModel.addAttribute("usersettinggroup", userSettingGroupService.findUserSettingGroup(id));
		uiModel.addAttribute("itemId", id);
		return "usersettinggroups/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("usersettinggroups",
					userSettingGroupService.findUserSettingGroupEntries(firstResult, sizeNo));
			float nrOfPages = (float) userSettingGroupService.countAllUserSettingGroups() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("usersettinggroups", userSettingGroupService.findAllUserSettingGroups());
		}
		return "usersettinggroups/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid UserSettingGroup userSettingGroup, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, userSettingGroup);
			return "usersettinggroups/update";
		}
		uiModel.asMap().clear();
		userSettingGroupService.updateUserSettingGroup(userSettingGroup);
		return "redirect:/usersettinggroups/"
				+ encodeUrlPathSegment(userSettingGroup.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, userSettingGroupService.findUserSettingGroup(id));
		return "usersettinggroups/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		UserSettingGroup userSettingGroup = userSettingGroupService.findUserSettingGroup(id);
		userSettingGroupService.deleteUserSettingGroup(userSettingGroup);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/usersettinggroups";
	}

	void populateEditForm(Model uiModel, UserSettingGroup userSettingGroup) {
		uiModel.addAttribute("userSettingGroup", userSettingGroup);
		uiModel.addAttribute("usersettings", userSettingService.findAllUserSettings());
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
		UserSettingGroup userSettingGroup = userSettingGroupService.findUserSettingGroup(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (userSettingGroup == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(userSettingGroup.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<UserSettingGroup> result = userSettingGroupService.findAllUserSettingGroups();
		return new ResponseEntity<String>(UserSettingGroup.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		UserSettingGroup userSettingGroup = UserSettingGroup.fromJsonToUserSettingGroup(json);
		userSettingGroupService.saveUserSettingGroup(userSettingGroup);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (UserSettingGroup userSettingGroup : UserSettingGroup.fromJsonArrayToUserSettingGroups(json)) {
			userSettingGroupService.saveUserSettingGroup(userSettingGroup);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		UserSettingGroup userSettingGroup = UserSettingGroup.fromJsonToUserSettingGroup(json);
		if (userSettingGroupService.updateUserSettingGroup(userSettingGroup) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (UserSettingGroup userSettingGroup : UserSettingGroup.fromJsonArrayToUserSettingGroups(json)) {
			if (userSettingGroupService.updateUserSettingGroup(userSettingGroup) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		UserSettingGroup userSettingGroup = userSettingGroupService.findUserSettingGroup(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (userSettingGroup == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		userSettingGroupService.deleteUserSettingGroup(userSettingGroup);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
