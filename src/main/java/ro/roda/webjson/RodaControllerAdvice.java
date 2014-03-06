package ro.roda.webjson;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RodaControllerAdvice {

	@ExceptionHandler
	@ResponseBody
	// @ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleException(HttpServletRequest request, HttpServletResponse response, Exception e)
			throws IOException {
		String acceptHeader = request.getHeader("Accept");
		if (acceptHeader.contains("application/json")) {
			// return as JSON
			String jsonString = "{\"success\": false, \"exception_message\": \"" + e.getMessage() + "\" }";
			return jsonString;
		} else {
			// return as HTML
			response.setContentType("text/html");
			return response.toString();
		}
	}

}
