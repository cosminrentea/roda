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

import ro.roda.domain.CmsPageType;
import ro.roda.service.CmsPageService;
import ro.roda.service.CmsPageTypeService;

@RequestMapping("/cmspagetypes")
@Controller
public class CmsPageTypeController {

	@Autowired
	CmsPageTypeService cmsPageTypeService;

	@Autowired
	CmsPageService cmsPageService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid CmsPageType cmsPageType, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, cmsPageType);
			return "cmspagetypes/create";
		}
		uiModel.asMap().clear();
		cmsPageTypeService.saveCmsPageType(cmsPageType);
		return "redirect:/cmspagetypes/" + encodeUrlPathSegment(cmsPageType.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new CmsPageType());
		return "cmspagetypes/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		uiModel.addAttribute("cmspagetype", cmsPageTypeService.findCmsPageType(id));
		uiModel.addAttribute("itemId", id);
		return "cmspagetypes/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("cmspagetypes", cmsPageTypeService.findCmsPageTypeEntries(firstResult, sizeNo));
			float nrOfPages = (float) cmsPageTypeService.countAllCmsPageTypes() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("cmspagetypes", cmsPageTypeService.findAllCmsPageTypes());
		}
		return "cmspagetypes/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid CmsPageType cmsPageType, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, cmsPageType);
			return "cmspagetypes/update";
		}
		uiModel.asMap().clear();
		cmsPageTypeService.updateCmsPageType(cmsPageType);
		return "redirect:/cmspagetypes/" + encodeUrlPathSegment(cmsPageType.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, cmsPageTypeService.findCmsPageType(id));
		return "cmspagetypes/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		CmsPageType cmsPageType = cmsPageTypeService.findCmsPageType(id);
		cmsPageTypeService.deleteCmsPageType(cmsPageType);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/cmspagetypes";
	}

	void populateEditForm(Model uiModel, CmsPageType cmsPageType) {
		uiModel.addAttribute("cmsPageType", cmsPageType);
		uiModel.addAttribute("cmspages", cmsPageService.findAllCmsPages());
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
		CmsPageType cmsPageType = cmsPageTypeService.findCmsPageType(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (cmsPageType == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(cmsPageType.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<CmsPageType> result = cmsPageTypeService.findAllCmsPageTypes();
		return new ResponseEntity<String>(CmsPageType.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		CmsPageType cmsPageType = CmsPageType.fromJsonToCmsPageType(json);
		cmsPageTypeService.saveCmsPageType(cmsPageType);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (CmsPageType cmsPageType : CmsPageType.fromJsonArrayToCmsPageTypes(json)) {
			cmsPageTypeService.saveCmsPageType(cmsPageType);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		CmsPageType cmsPageType = CmsPageType.fromJsonToCmsPageType(json);
		if (cmsPageTypeService.updateCmsPageType(cmsPageType) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (CmsPageType cmsPageType : CmsPageType.fromJsonArrayToCmsPageTypes(json)) {
			if (cmsPageTypeService.updateCmsPageType(cmsPageType) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		CmsPageType cmsPageType = cmsPageTypeService.findCmsPageType(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (cmsPageType == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		cmsPageTypeService.deleteCmsPageType(cmsPageType);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
