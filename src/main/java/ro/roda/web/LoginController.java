package ro.roda.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.service.LoginService;
import ro.roda.transformer.Login;

@RequestMapping("/admin/login")
@Controller
public class LoginController {

	@Autowired
	LoginService loginService;

	@RequestMapping(value = "/{username}/{password}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("username") String username,
			@PathVariable("password") String password) {
		Login login = loginService.findLogin(username, password);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (login == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(login.toJson(), headers, HttpStatus.OK);
	}

}
