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
import ro.roda.domain.Users;
import ro.roda.service.AuthoritiesService;
import ro.roda.service.CatalogService;
import ro.roda.service.InstanceService;
import ro.roda.service.PersonLinksService;
import ro.roda.service.StudyKeywordService;
import ro.roda.service.StudyService;
import ro.roda.service.UserAuthLogService;
import ro.roda.service.UserMessageService;
import ro.roda.service.UserSettingValueService;
import ro.roda.service.UsersService;

@RequestMapping("/userses")
@Controller
public class UsersController {

	@Autowired
	UsersService usersService;

	@Autowired
	AuthoritiesService authoritiesService;

	@Autowired
	CatalogService catalogService;

	@Autowired
	InstanceService instanceService;

	@Autowired
	PersonLinksService personLinksService;

	@Autowired
	StudyService studyService;

	@Autowired
	StudyKeywordService studyKeywordService;

	@Autowired
	UserAuthLogService userAuthLogService;

	@Autowired
	UserMessageService userMessageService;

	@Autowired
	UserSettingValueService userSettingValueService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Users users, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, users);
			return "userses/create";
		}
		uiModel.asMap().clear();
		usersService.saveUsers(users);
		return "redirect:/userses/" + encodeUrlPathSegment(users.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new Users());
		return "userses/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		uiModel.addAttribute("users", usersService.findUsers(id));
		uiModel.addAttribute("itemId", id);
		return "userses/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("userses", usersService.findUsersEntries(firstResult, sizeNo));
			float nrOfPages = (float) usersService.countAllUserses() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("userses", usersService.findAllUserses());
		}
		return "userses/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Users users, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, users);
			return "userses/update";
		}
		uiModel.asMap().clear();
		usersService.updateUsers(users);
		return "redirect:/userses/" + encodeUrlPathSegment(users.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, usersService.findUsers(id));
		return "userses/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Users users = usersService.findUsers(id);
		usersService.deleteUsers(users);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/userses";
	}

	void populateEditForm(Model uiModel, Users users) {
		uiModel.addAttribute("users", users);
		uiModel.addAttribute("authoritieses", authoritiesService.findAllAuthoritieses());
		uiModel.addAttribute("catalogs", catalogService.findAllCatalogs());
		uiModel.addAttribute("instances", instanceService.findAllInstances());
		uiModel.addAttribute("personlinkses", personLinksService.findAllPersonLinkses());
		uiModel.addAttribute("studys", studyService.findAllStudys());
		uiModel.addAttribute("studykeywords", studyKeywordService.findAllStudyKeywords());
		uiModel.addAttribute("userauthlogs", userAuthLogService.findAllUserAuthLogs());
		uiModel.addAttribute("usermessages", userMessageService.findAllUserMessages());
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
		Users users = usersService.findUsers(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (users == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(users.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<Users> result = usersService.findAllUserses();
		return new ResponseEntity<String>(Users.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		Users users = Users.fromJsonToUsers(json);
		usersService.saveUsers(users);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (Users users : Users.fromJsonArrayToUserses(json)) {
			usersService.saveUsers(users);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		Users users = Users.fromJsonToUsers(json);
		if (usersService.updateUsers(users) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (Users users : Users.fromJsonArrayToUserses(json)) {
			if (usersService.updateUsers(users) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		Users users = usersService.findUsers(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (users == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		usersService.deleteUsers(users);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(params = "find=ByUsernameLike", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> jsonFindUsersesByUsernameLike(@RequestParam("username") String username) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(Users.toJsonArray(Users.findUsersesByUsernameLike(username).getResultList()),
				headers, HttpStatus.OK);
	}

	@RequestMapping(params = "find=ByUsernameLikeAndEnabled", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> jsonFindUsersesByUsernameLikeAndEnabled(@RequestParam("username") String username,
			@RequestParam(value = "enabled", required = false) boolean enabled) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(Users.toJsonArray(Users
				.findUsersesByUsernameLikeAndEnabled(username, enabled).getResultList()), headers, HttpStatus.OK);
	}
}
