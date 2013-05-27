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

import ro.roda.domain.UserSettingValue;
import ro.roda.domain.UserSettingValuePK;
import ro.roda.service.UserSettingService;
import ro.roda.service.UserSettingValueService;
import ro.roda.service.UsersService;

@RequestMapping("/usersettingvalues")
@Controller
public class UserSettingValueController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") UserSettingValuePK id) {
		UserSettingValue userSettingValue = userSettingValueService.findUserSettingValue(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (userSettingValue == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(userSettingValue.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<UserSettingValue> result = userSettingValueService.findAllUserSettingValues();
		return new ResponseEntity<String>(UserSettingValue.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		UserSettingValue userSettingValue = UserSettingValue.fromJsonToUserSettingValue(json);
		userSettingValueService.saveUserSettingValue(userSettingValue);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (UserSettingValue userSettingValue : UserSettingValue.fromJsonArrayToUserSettingValues(json)) {
			userSettingValueService.saveUserSettingValue(userSettingValue);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		UserSettingValue userSettingValue = UserSettingValue.fromJsonToUserSettingValue(json);
		if (userSettingValueService.updateUserSettingValue(userSettingValue) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (UserSettingValue userSettingValue : UserSettingValue.fromJsonArrayToUserSettingValues(json)) {
			if (userSettingValueService.updateUserSettingValue(userSettingValue) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") UserSettingValuePK id) {
		UserSettingValue userSettingValue = userSettingValueService.findUserSettingValue(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (userSettingValue == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		userSettingValueService.deleteUserSettingValue(userSettingValue);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	private ConversionService conversionService;

	@Autowired
	UserSettingValueService userSettingValueService;

	@Autowired
	UserSettingService userSettingService;

	@Autowired
	UsersService usersService;

	@Autowired
	public UserSettingValueController(ConversionService conversionService) {
		super();
		this.conversionService = conversionService;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid UserSettingValue userSettingValue, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, userSettingValue);
			return "usersettingvalues/create";
		}
		uiModel.asMap().clear();
		userSettingValueService.saveUserSettingValue(userSettingValue);
		return "redirect:/usersettingvalues/"
				+ encodeUrlPathSegment(conversionService.convert(userSettingValue.getId(), String.class),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new UserSettingValue());
		return "usersettingvalues/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") UserSettingValuePK id, Model uiModel) {
		uiModel.addAttribute("usersettingvalue", userSettingValueService.findUserSettingValue(id));
		uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
		return "usersettingvalues/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("usersettingvalues",
					userSettingValueService.findUserSettingValueEntries(firstResult, sizeNo));
			float nrOfPages = (float) userSettingValueService.countAllUserSettingValues() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("usersettingvalues", userSettingValueService.findAllUserSettingValues());
		}
		return "usersettingvalues/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid UserSettingValue userSettingValue, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, userSettingValue);
			return "usersettingvalues/update";
		}
		uiModel.asMap().clear();
		userSettingValueService.updateUserSettingValue(userSettingValue);
		return "redirect:/usersettingvalues/"
				+ encodeUrlPathSegment(conversionService.convert(userSettingValue.getId(), String.class),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") UserSettingValuePK id, Model uiModel) {
		populateEditForm(uiModel, userSettingValueService.findUserSettingValue(id));
		return "usersettingvalues/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") UserSettingValuePK id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		UserSettingValue userSettingValue = userSettingValueService.findUserSettingValue(id);
		userSettingValueService.deleteUserSettingValue(userSettingValue);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/usersettingvalues";
	}

	void populateEditForm(Model uiModel, UserSettingValue userSettingValue) {
		uiModel.addAttribute("userSettingValue", userSettingValue);
		uiModel.addAttribute("usersettings", userSettingService.findAllUserSettings());
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
}
