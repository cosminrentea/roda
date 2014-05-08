package ro.roda.webjson;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.domainjson.AdminJson;
import ro.roda.service.AdminJsonService;

@RequestMapping("/auth")
@Controller
public class LoginController {

	@Autowired
	AdminJsonService adminJsonService;

	private final Log log = LogFactory.getLog(this.getClass());

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String login(@ModelAttribute("username") String username, @ModelAttribute("password") String password,
			BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		// if (bindingResult.hasErrors()) {
		// populateEditForm(uiModel, cmsFolder);
		// return "admin/login";
		// }
		// uiModel.asMap().clear();

		// only for testing:
		// username = "admin";
		// password = "e10adc3949ba59abbe56e057f20f883e";

		log.trace(username);
		log.trace(password);
		AdminJson login = adminJsonService.findLogin(username, password);
		// HttpHeaders headers = new HttpHeaders();
		// headers.add("Content-Type", "application/json; charset=utf-8");
		if (login == null) {
			return null;
		}
		return login.toJson();
	}

}
