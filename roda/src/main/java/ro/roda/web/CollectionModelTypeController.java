package ro.roda.web;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
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
import ro.roda.domain.CollectionModelType;
import ro.roda.service.CollectionModelTypeService;
import ro.roda.service.StudyService;

@RequestMapping("/collectionmodeltypes")
@Controller
public class CollectionModelTypeController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
		CollectionModelType collectionModelType = collectionModelTypeService.findCollectionModelType(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (collectionModelType == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(collectionModelType.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<CollectionModelType> result = collectionModelTypeService.findAllCollectionModelTypes();
		return new ResponseEntity<String>(CollectionModelType.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		CollectionModelType collectionModelType = CollectionModelType.fromJsonToCollectionModelType(json);
		collectionModelTypeService.saveCollectionModelType(collectionModelType);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (CollectionModelType collectionModelType : CollectionModelType.fromJsonArrayToCollectionModelTypes(json)) {
			collectionModelTypeService.saveCollectionModelType(collectionModelType);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		CollectionModelType collectionModelType = CollectionModelType.fromJsonToCollectionModelType(json);
		if (collectionModelTypeService.updateCollectionModelType(collectionModelType) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (CollectionModelType collectionModelType : CollectionModelType.fromJsonArrayToCollectionModelTypes(json)) {
			if (collectionModelTypeService.updateCollectionModelType(collectionModelType) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		CollectionModelType collectionModelType = collectionModelTypeService.findCollectionModelType(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (collectionModelType == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		collectionModelTypeService.deleteCollectionModelType(collectionModelType);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@Autowired
	CollectionModelTypeService collectionModelTypeService;

	@Autowired
	StudyService studyService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid CollectionModelType collectionModelType, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, collectionModelType);
			return "collectionmodeltypes/create";
		}
		uiModel.asMap().clear();
		collectionModelTypeService.saveCollectionModelType(collectionModelType);
		return "redirect:/collectionmodeltypes/"
				+ encodeUrlPathSegment(collectionModelType.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new CollectionModelType());
		return "collectionmodeltypes/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		uiModel.addAttribute("collectionmodeltype", collectionModelTypeService.findCollectionModelType(id));
		uiModel.addAttribute("itemId", id);
		return "collectionmodeltypes/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("collectionmodeltypes",
					collectionModelTypeService.findCollectionModelTypeEntries(firstResult, sizeNo));
			float nrOfPages = (float) collectionModelTypeService.countAllCollectionModelTypes() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("collectionmodeltypes", collectionModelTypeService.findAllCollectionModelTypes());
		}
		return "collectionmodeltypes/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid CollectionModelType collectionModelType, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, collectionModelType);
			return "collectionmodeltypes/update";
		}
		uiModel.asMap().clear();
		collectionModelTypeService.updateCollectionModelType(collectionModelType);
		return "redirect:/collectionmodeltypes/"
				+ encodeUrlPathSegment(collectionModelType.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, collectionModelTypeService.findCollectionModelType(id));
		return "collectionmodeltypes/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		CollectionModelType collectionModelType = collectionModelTypeService.findCollectionModelType(id);
		collectionModelTypeService.deleteCollectionModelType(collectionModelType);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/collectionmodeltypes";
	}

	void populateEditForm(Model uiModel, CollectionModelType collectionModelType) {
		uiModel.addAttribute("collectionModelType", collectionModelType);
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
