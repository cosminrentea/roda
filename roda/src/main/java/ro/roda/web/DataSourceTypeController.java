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

import ro.roda.domain.DataSourceType;
import ro.roda.service.DataSourceTypeService;
import ro.roda.service.StudyService;

@RequestMapping("/datasourcetypes")
@Controller
public class DataSourceTypeController {

	@Autowired
	DataSourceTypeService dataSourceTypeService;

	@Autowired
	StudyService studyService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid DataSourceType dataSourceType, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, dataSourceType);
			return "datasourcetypes/create";
		}
		uiModel.asMap().clear();
		dataSourceTypeService.saveDataSourceType(dataSourceType);
		return "redirect:/datasourcetypes/"
				+ encodeUrlPathSegment(dataSourceType.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new DataSourceType());
		return "datasourcetypes/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		uiModel.addAttribute("datasourcetype", dataSourceTypeService.findDataSourceType(id));
		uiModel.addAttribute("itemId", id);
		return "datasourcetypes/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("datasourcetypes",
					dataSourceTypeService.findDataSourceTypeEntries(firstResult, sizeNo));
			float nrOfPages = (float) dataSourceTypeService.countAllDataSourceTypes() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("datasourcetypes", dataSourceTypeService.findAllDataSourceTypes());
		}
		return "datasourcetypes/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid DataSourceType dataSourceType, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, dataSourceType);
			return "datasourcetypes/update";
		}
		uiModel.asMap().clear();
		dataSourceTypeService.updateDataSourceType(dataSourceType);
		return "redirect:/datasourcetypes/"
				+ encodeUrlPathSegment(dataSourceType.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, dataSourceTypeService.findDataSourceType(id));
		return "datasourcetypes/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		DataSourceType dataSourceType = dataSourceTypeService.findDataSourceType(id);
		dataSourceTypeService.deleteDataSourceType(dataSourceType);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/datasourcetypes";
	}

	void populateEditForm(Model uiModel, DataSourceType dataSourceType) {
		uiModel.addAttribute("dataSourceType", dataSourceType);
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

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		DataSourceType dataSourceType = dataSourceTypeService.findDataSourceType(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (dataSourceType == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(dataSourceType.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<DataSourceType> result = dataSourceTypeService.findAllDataSourceTypes();
		return new ResponseEntity<String>(DataSourceType.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		DataSourceType dataSourceType = DataSourceType.fromJsonToDataSourceType(json);
		dataSourceTypeService.saveDataSourceType(dataSourceType);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (DataSourceType dataSourceType : DataSourceType.fromJsonArrayToDataSourceTypes(json)) {
			dataSourceTypeService.saveDataSourceType(dataSourceType);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		DataSourceType dataSourceType = DataSourceType.fromJsonToDataSourceType(json);
		if (dataSourceTypeService.updateDataSourceType(dataSourceType) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (DataSourceType dataSourceType : DataSourceType.fromJsonArrayToDataSourceTypes(json)) {
			if (dataSourceTypeService.updateDataSourceType(dataSourceType) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		DataSourceType dataSourceType = dataSourceTypeService.findDataSourceType(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (dataSourceType == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		dataSourceTypeService.deleteDataSourceType(dataSourceType);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
