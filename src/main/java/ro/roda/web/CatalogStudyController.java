package ro.roda.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.ConversionService;
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

import ro.roda.domain.CatalogStudy;
import ro.roda.domain.CatalogStudyPK;
import ro.roda.service.CatalogService;
import ro.roda.service.CatalogStudyService;
import ro.roda.service.StudyService;

@RequestMapping("/catalogstudys")
@Controller
public class CatalogStudyController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") CatalogStudyPK id) {
		CatalogStudy catalogStudy = catalogStudyService.findCatalogStudy(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (catalogStudy == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(catalogStudy.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<CatalogStudy> result = catalogStudyService.findAllCatalogStudys();
		return new ResponseEntity<String>(CatalogStudy.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		CatalogStudy catalogStudy = CatalogStudy.fromJsonToCatalogStudy(json);
		catalogStudyService.saveCatalogStudy(catalogStudy);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (CatalogStudy catalogStudy : CatalogStudy.fromJsonArrayToCatalogStudys(json)) {
			catalogStudyService.saveCatalogStudy(catalogStudy);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		CatalogStudy catalogStudy = CatalogStudy.fromJsonToCatalogStudy(json);
		if (catalogStudyService.updateCatalogStudy(catalogStudy) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (CatalogStudy catalogStudy : CatalogStudy.fromJsonArrayToCatalogStudys(json)) {
			if (catalogStudyService.updateCatalogStudy(catalogStudy) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") CatalogStudyPK id) {
		CatalogStudy catalogStudy = catalogStudyService.findCatalogStudy(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (catalogStudy == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		catalogStudyService.deleteCatalogStudy(catalogStudy);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	private ConversionService conversionService;

	@Autowired
	CatalogStudyService catalogStudyService;

	@Autowired
	CatalogService catalogService;

	@Autowired
	StudyService studyService;

	@Autowired
	public CatalogStudyController(ConversionService conversionService) {
		super();
		this.conversionService = conversionService;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid CatalogStudy catalogStudy, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, catalogStudy);
			return "catalogstudys/create";
		}
		uiModel.asMap().clear();
		catalogStudyService.saveCatalogStudy(catalogStudy);
		return "redirect:/catalogstudys/"
				+ encodeUrlPathSegment(conversionService.convert(catalogStudy.getId(), String.class),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new CatalogStudy());
		return "catalogstudys/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") CatalogStudyPK id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("catalogstudy", catalogStudyService.findCatalogStudy(id));
		uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
		return "catalogstudys/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("catalogstudys", catalogStudyService.findCatalogStudyEntries(firstResult, sizeNo));
			float nrOfPages = (float) catalogStudyService.countAllCatalogStudys() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("catalogstudys", catalogStudyService.findAllCatalogStudys());
		}
		addDateTimeFormatPatterns(uiModel);
		return "catalogstudys/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid CatalogStudy catalogStudy, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, catalogStudy);
			return "catalogstudys/update";
		}
		uiModel.asMap().clear();
		catalogStudyService.updateCatalogStudy(catalogStudy);
		return "redirect:/catalogstudys/"
				+ encodeUrlPathSegment(conversionService.convert(catalogStudy.getId(), String.class),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") CatalogStudyPK id, Model uiModel) {
		populateEditForm(uiModel, catalogStudyService.findCatalogStudy(id));
		return "catalogstudys/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") CatalogStudyPK id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		CatalogStudy catalogStudy = catalogStudyService.findCatalogStudy(id);
		catalogStudyService.deleteCatalogStudy(catalogStudy);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/catalogstudys";
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute("catalogStudy_added_date_format",
				DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, CatalogStudy catalogStudy) {
		uiModel.addAttribute("catalogStudy", catalogStudy);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("catalogs", catalogService.findAllCatalogs());
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
