package ro.roda.webjson;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RodaControllerAdvice {

	private final Log log = LogFactory.getLog(this.getClass());

	@ExceptionHandler
	@ResponseBody
	// @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleException(HttpServletRequest request, HttpServletResponse response, Exception e)
			throws Exception {
		log.error("Exception: ", e);

		String resultString;
		String acceptHeader = request.getHeader("Accept");
		if (acceptHeader.contains("application/json")) {
			// return as JSON
			response.setContentType("application/json");
			resultString = "{\"success\": false, \"exception_message\": \"" + e + "\" }";
		} else {
			// return as HTML
			response.setContentType("text/html");
			resultString = e.toString();
		}
		return resultString;
	}
}
