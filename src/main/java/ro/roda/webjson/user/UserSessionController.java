package ro.roda.webjson.user;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.roda.domain.UserSetting;
import ro.roda.service.UserSettingService;

@RequestMapping("/user/session")
@Controller
public class UserSessionController {

	private final Log log = LogFactory.getLog(this.getClass());

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> getSesstionAttributes(HttpSession session) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		StringBuilder sb = new StringBuilder();
		for (Enumeration<String> attributes = session.getAttributeNames(); attributes.hasMoreElements();) {
			String attributeName = attributes.nextElement();
			sb.append("{name:\"").append(attributeName).append("\",value:\"")
					.append(session.getAttribute(attributeName)).append("\"},");
		}
		sb.deleteCharAt(sb.length() - 1);
		log.info(sb);
		return new ResponseEntity<String>("{success:true, data : [" + sb.toString() + "]}", headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/setAttribute", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> getSesstion(HttpSession session, @RequestParam(value = "name") String attributeName,
			@RequestParam(value = "value") String attributeValue) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		log.info(attributeName);
		log.info(attributeValue);
		session.setAttribute(attributeName, attributeValue);
		return new ResponseEntity<String>("{success:true}", headers, HttpStatus.OK);
	}

	// A negative interval value => session does not expire !
	@RequestMapping(value = "/setMaxInactiveInterval", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> setMaxInactiveInterval(HttpSession session,
			@RequestParam(value = "interval") Integer interval) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		log.info(interval);
		session.setMaxInactiveInterval(interval);
		return new ResponseEntity<String>("{success:true}", headers, HttpStatus.OK);
	}

}
