package ro.roda.webjson;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/exception")
@Controller
public class RodaExceptionController {

	@RequestMapping(value = "/runtime", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> retException() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		if (true) {
			throw new RuntimeException("This is a triggered RuntimeException");
		}

		return null;
	}

}
