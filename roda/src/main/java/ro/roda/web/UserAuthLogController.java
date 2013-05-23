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
import ro.roda.domain.UserAuthLog;
import ro.roda.service.UserAuthLogService;
import ro.roda.service.UsersService;

@RequestMapping("/userauthlogs")
@Controller


public class UserAuthLogController {

	@Autowired
    UserAuthLogService userAuthLogService;

	@Autowired
    UsersService usersService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid UserAuthLog userAuthLog, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, userAuthLog);
            return "userauthlogs/create";
        }
        uiModel.asMap().clear();
        userAuthLogService.saveUserAuthLog(userAuthLog);
        return "redirect:/userauthlogs/" + encodeUrlPathSegment(userAuthLog.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new UserAuthLog());
        return "userauthlogs/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("userauthlog", userAuthLogService.findUserAuthLog(id));
        uiModel.addAttribute("itemId", id);
        return "userauthlogs/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("userauthlogs", userAuthLogService.findUserAuthLogEntries(firstResult, sizeNo));
            float nrOfPages = (float) userAuthLogService.countAllUserAuthLogs() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("userauthlogs", userAuthLogService.findAllUserAuthLogs());
        }
        addDateTimeFormatPatterns(uiModel);
        return "userauthlogs/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid UserAuthLog userAuthLog, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, userAuthLog);
            return "userauthlogs/update";
        }
        uiModel.asMap().clear();
        userAuthLogService.updateUserAuthLog(userAuthLog);
        return "redirect:/userauthlogs/" + encodeUrlPathSegment(userAuthLog.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, userAuthLogService.findUserAuthLog(id));
        return "userauthlogs/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        UserAuthLog userAuthLog = userAuthLogService.findUserAuthLog(id);
        userAuthLogService.deleteUserAuthLog(userAuthLog);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/userauthlogs";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("userAuthLog_authattemptedat_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, UserAuthLog userAuthLog) {
        uiModel.addAttribute("userAuthLog", userAuthLog);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("userses", usersService.findAllUserses());
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        UserAuthLog userAuthLog = userAuthLogService.findUserAuthLog(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (userAuthLog == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(userAuthLog.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<UserAuthLog> result = userAuthLogService.findAllUserAuthLogs();
        return new ResponseEntity<String>(UserAuthLog.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        UserAuthLog userAuthLog = UserAuthLog.fromJsonToUserAuthLog(json);
        userAuthLogService.saveUserAuthLog(userAuthLog);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (UserAuthLog userAuthLog: UserAuthLog.fromJsonArrayToUserAuthLogs(json)) {
            userAuthLogService.saveUserAuthLog(userAuthLog);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        UserAuthLog userAuthLog = UserAuthLog.fromJsonToUserAuthLog(json);
        if (userAuthLogService.updateUserAuthLog(userAuthLog) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (UserAuthLog userAuthLog: UserAuthLog.fromJsonArrayToUserAuthLogs(json)) {
            if (userAuthLogService.updateUserAuthLog(userAuthLog) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        UserAuthLog userAuthLog = userAuthLogService.findUserAuthLog(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (userAuthLog == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        userAuthLogService.deleteUserAuthLog(userAuthLog);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
