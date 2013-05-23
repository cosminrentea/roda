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
import ro.roda.domain.UserSetting;
import ro.roda.service.UserSettingGroupService;
import ro.roda.service.UserSettingService;
import ro.roda.service.UserSettingValueService;

@RequestMapping("/usersettings")
@Controller
public class UserSettingController {

	@Autowired
	UserSettingService userSettingService;

	@Autowired
	UserSettingGroupService userSettingGroupService;

	@Autowired
	UserSettingValueService userSettingValueService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid UserSetting userSetting, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, userSetting);
			return "usersettings/create";
		}
		uiModel.asMap().clear();
		userSettingService.saveUserSetting(userSetting);
		return "redirect:/usersettings/" + encodeUrlPathSegment(userSetting.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new UserSetting());
		return "usersettings/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		uiModel.addAttribute("usersetting", userSettingService.findUserSetting(id));
		uiModel.addAttribute("itemId", id);
		return "usersettings/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("usersettings", userSettingService.findUserSettingEntries(firstResult, sizeNo));
			float nrOfPages = (float) userSettingService.countAllUserSettings() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("usersettings", userSettingService.findAllUserSettings());
		}
		return "usersettings/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid UserSetting userSetting, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, userSetting);
			return "usersettings/update";
		}
		uiModel.asMap().clear();
		userSettingService.updateUserSetting(userSetting);
		return "redirect:/usersettings/" + encodeUrlPathSegment(userSetting.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, userSettingService.findUserSetting(id));
		return "usersettings/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		UserSetting userSetting = userSettingService.findUserSetting(id);
		userSettingService.deleteUserSetting(userSetting);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/usersettings";
	}

	void populateEditForm(Model uiModel, UserSetting userSetting) {
		uiModel.addAttribute("userSetting", userSetting);
		uiModel.addAttribute("usersettinggroups", userSettingGroupService.findAllUserSettingGroups());
		uiModel.addAttribute("usersettingvalues", userSettingValueService.findAllUserSettingValues());
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
		UserSetting userSetting = userSettingService.findUserSetting(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (userSetting == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(userSetting.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<UserSetting> result = userSettingService.findAllUserSettings();
		return new ResponseEntity<String>(UserSetting.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		UserSetting userSetting = UserSetting.fromJsonToUserSetting(json);
		userSettingService.saveUserSetting(userSetting);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (UserSetting userSetting : UserSetting.fromJsonArrayToUserSettings(json)) {
			userSettingService.saveUserSetting(userSetting);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		UserSetting userSetting = UserSetting.fromJsonToUserSetting(json);
		if (userSettingService.updateUserSetting(userSetting) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (UserSetting userSetting : UserSetting.fromJsonArrayToUserSettings(json)) {
			if (userSettingService.updateUserSetting(userSetting) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		UserSetting userSetting = userSettingService.findUserSetting(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (userSetting == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		userSettingService.deleteUserSetting(userSetting);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
