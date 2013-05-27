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

import ro.roda.domain.SamplingProcedure;
import ro.roda.service.SamplingProcedureService;
import ro.roda.service.StudyService;

@RequestMapping("/samplingprocedures")
@Controller
public class SamplingProcedureController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		SamplingProcedure samplingProcedure = samplingProcedureService.findSamplingProcedure(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (samplingProcedure == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(samplingProcedure.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<SamplingProcedure> result = samplingProcedureService.findAllSamplingProcedures();
		return new ResponseEntity<String>(SamplingProcedure.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		SamplingProcedure samplingProcedure = SamplingProcedure.fromJsonToSamplingProcedure(json);
		samplingProcedureService.saveSamplingProcedure(samplingProcedure);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (SamplingProcedure samplingProcedure : SamplingProcedure.fromJsonArrayToSamplingProcedures(json)) {
			samplingProcedureService.saveSamplingProcedure(samplingProcedure);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		SamplingProcedure samplingProcedure = SamplingProcedure.fromJsonToSamplingProcedure(json);
		if (samplingProcedureService.updateSamplingProcedure(samplingProcedure) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (SamplingProcedure samplingProcedure : SamplingProcedure.fromJsonArrayToSamplingProcedures(json)) {
			if (samplingProcedureService.updateSamplingProcedure(samplingProcedure) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		SamplingProcedure samplingProcedure = samplingProcedureService.findSamplingProcedure(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (samplingProcedure == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		samplingProcedureService.deleteSamplingProcedure(samplingProcedure);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@Autowired
	SamplingProcedureService samplingProcedureService;

	@Autowired
	StudyService studyService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid SamplingProcedure samplingProcedure, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, samplingProcedure);
			return "samplingprocedures/create";
		}
		uiModel.asMap().clear();
		samplingProcedureService.saveSamplingProcedure(samplingProcedure);
		return "redirect:/samplingprocedures/"
				+ encodeUrlPathSegment(samplingProcedure.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new SamplingProcedure());
		return "samplingprocedures/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		uiModel.addAttribute("samplingprocedure", samplingProcedureService.findSamplingProcedure(id));
		uiModel.addAttribute("itemId", id);
		return "samplingprocedures/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("samplingprocedures",
					samplingProcedureService.findSamplingProcedureEntries(firstResult, sizeNo));
			float nrOfPages = (float) samplingProcedureService.countAllSamplingProcedures() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("samplingprocedures", samplingProcedureService.findAllSamplingProcedures());
		}
		return "samplingprocedures/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid SamplingProcedure samplingProcedure, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, samplingProcedure);
			return "samplingprocedures/update";
		}
		uiModel.asMap().clear();
		samplingProcedureService.updateSamplingProcedure(samplingProcedure);
		return "redirect:/samplingprocedures/"
				+ encodeUrlPathSegment(samplingProcedure.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, samplingProcedureService.findSamplingProcedure(id));
		return "samplingprocedures/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		SamplingProcedure samplingProcedure = samplingProcedureService.findSamplingProcedure(id);
		samplingProcedureService.deleteSamplingProcedure(samplingProcedure);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/samplingprocedures";
	}

	void populateEditForm(Model uiModel, SamplingProcedure samplingProcedure) {
		uiModel.addAttribute("samplingProcedure", samplingProcedure);
		uiModel.addAttribute("studys", studyService.findAllStudys());
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
}
