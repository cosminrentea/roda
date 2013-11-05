package ro.roda.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import ro.roda.domain.TimeMeth;
import ro.roda.service.StudyService;
import ro.roda.service.TimeMethService;

@RequestMapping("/timemethtypes")
@Controller
public class TimeMethController {

	@Autowired
	TimeMethService timeMethService;

	@Autowired
	StudyService studyService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid TimeMeth timeMeth, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, timeMeth);
			return "timemethtypes/create";
		}
		uiModel.asMap().clear();
		timeMethService.saveTimeMeth(timeMeth);
		return "redirect:/timemethtypes/"
				+ encodeUrlPathSegment(timeMeth.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new TimeMeth());
		return "timemethtypes/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		uiModel.addAttribute("timemethtype", timeMethService.findTimeMeth(id));
		uiModel.addAttribute("itemId", id);
		return "timemethtypes/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			uiModel.addAttribute("timemethtypes",
					timeMethService.findTimeMethEntries(firstResult, sizeNo));
			float nrOfPages = (float) timeMethService.countAllTimeMeths()
					/ sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("timemethtypes",
					timeMethService.findAllTimeMeths());
		}
		return "timemethtypes/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid TimeMeth timeMeth, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, timeMeth);
			return "timemethtypes/update";
		}
		uiModel.asMap().clear();
		timeMethService.updateTimeMeth(timeMeth);
		return "redirect:/timemethtypes/"
				+ encodeUrlPathSegment(timeMeth.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, timeMethService.findTimeMeth(id));
		return "timemethtypes/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		TimeMeth timeMeth = timeMethService.findTimeMeth(id);
		timeMethService.deleteTimeMeth(timeMeth);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/timemethtypes";
	}

	void populateEditForm(Model uiModel, TimeMeth timeMeth) {
		uiModel.addAttribute("timeMeth", timeMeth);
		uiModel.addAttribute("studys", studyService.findAllStudys());
	}

	String encodeUrlPathSegment(String pathSegment,
			HttpServletRequest httpServletRequest) {
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
		TimeMeth timeMeth = timeMethService.findTimeMeth(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-", "application/json; charset=utf-8");
		if (timeMeth == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(timeMeth.toJson(), headers,
				HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-", "application/json; charset=utf-8");
		List<TimeMeth> result = timeMethService.findAllTimeMeths();
		return new ResponseEntity<String>(TimeMeth.toJsonArray(result),
				headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		TimeMeth timeMeth = TimeMeth.fromJsonToTimeMeth(json);
		timeMethService.saveTimeMeth(timeMeth);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (TimeMeth timeMeth : TimeMeth.fromJsonArrayToTimeMeths(json)) {
			timeMethService.saveTimeMeth(timeMeth);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-", "application/json");
		TimeMeth timeMeth = TimeMeth.fromJsonToTimeMeth(json);
		if (timeMethService.updateTimeMeth(timeMeth) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-", "application/json");
		for (TimeMeth timeMeth : TimeMeth.fromJsonArrayToTimeMeths(json)) {
			if (timeMethService.updateTimeMeth(timeMeth) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		TimeMeth timeMeth = timeMethService.findTimeMeth(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-", "application/json");
		if (timeMeth == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		timeMethService.deleteTimeMeth(timeMeth);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
