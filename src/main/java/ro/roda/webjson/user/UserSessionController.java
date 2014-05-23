package ro.roda.webjson.user;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/user/session")
@Controller
public class UserSessionController {

	private final Log log = LogFactory.getLog(this.getClass());

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> getSessionAttributes(HttpSession session) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		StringBuilder sb = new StringBuilder();
		for (Enumeration<String> attributes = session.getAttributeNames(); attributes.hasMoreElements();) {
			String attributeName = attributes.nextElement();
			sb.append("{\"name\" : \"").append(attributeName).append("\", \"value\" : \"")
					.append(session.getAttribute(attributeName)).append("\"},");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		log.trace(sb);
		return new ResponseEntity<String>("{\"success\" : true, \"data\" : [ " + sb.toString() + " ]}", headers,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/set", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> setSessionAttribute(HttpSession session,
			@RequestParam(value = "name") String attributeName, @RequestParam(value = "value") String attributeValue) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		log.trace(attributeName + " : " + attributeValue);
		session.setAttribute(attributeName, attributeValue);
		return new ResponseEntity<String>("{\"success\": true}", headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> getSessionAttribute(HttpSession session,
			@RequestParam(value = "name") String attributeName) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String attributeValue = (String) session.getAttribute(attributeName);
		log.trace(attributeName + " : " + attributeValue);
		StringBuilder sb = new StringBuilder();
		sb.append("{\"success\" : true, \"data\" : [\"{\"name\" : \"").append(attributeName)
				.append("\", \"value\" : \"").append(session.getAttribute(attributeName)).append("\"}]}");
		return new ResponseEntity<String>(sb.toString(), headers, HttpStatus.OK);
	}

	// A negative interval value => session does not expire !
	@RequestMapping(value = "/set-interval", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> setInterval(HttpSession session, @RequestParam(value = "interval") Integer interval) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		log.trace(interval);
		session.setMaxInactiveInterval(interval);
		return new ResponseEntity<String>("{\"success\" : true, \"message\" : \"Session MaxInactiveInterval set\"}",
				headers, HttpStatus.OK);
	}

}
