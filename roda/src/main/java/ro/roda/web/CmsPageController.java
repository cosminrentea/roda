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

import ro.roda.domain.CmsPage;
import ro.roda.service.CmsLayoutService;
import ro.roda.service.CmsPageContentService;
import ro.roda.service.CmsPageService;
import ro.roda.service.CmsPageTypeService;

@RequestMapping("/cmspages")
@Controller
public class CmsPageController {

	@Autowired
	CmsPageService cmsPageService;

	@Autowired
	CmsLayoutService cmsLayoutService;

	@Autowired
	CmsPageContentService cmsPageContentService;

	@Autowired
	CmsPageTypeService cmsPageTypeService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid CmsPage cmsPage, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, cmsPage);
			return "cmspages/create";
		}
		uiModel.asMap().clear();
		cmsPageService.saveCmsPage(cmsPage);
		return "redirect:/cmspages/" + encodeUrlPathSegment(cmsPage.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new CmsPage());
		return "cmspages/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		uiModel.addAttribute("cmspage", cmsPageService.findCmsPage(id));
		uiModel.addAttribute("itemId", id);
		return "cmspages/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("cmspages", cmsPageService.findCmsPageEntries(firstResult, sizeNo));
			float nrOfPages = (float) cmsPageService.countAllCmsPages() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("cmspages", cmsPageService.findAllCmsPages());
		}
		return "cmspages/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid CmsPage cmsPage, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, cmsPage);
			return "cmspages/update";
		}
		uiModel.asMap().clear();
		cmsPageService.updateCmsPage(cmsPage);
		return "redirect:/cmspages/" + encodeUrlPathSegment(cmsPage.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, cmsPageService.findCmsPage(id));
		return "cmspages/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		CmsPage cmsPage = cmsPageService.findCmsPage(id);
		cmsPageService.deleteCmsPage(cmsPage);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/cmspages";
	}

	void populateEditForm(Model uiModel, CmsPage cmsPage) {
		uiModel.addAttribute("cmsPage", cmsPage);
		uiModel.addAttribute("cmslayouts", cmsLayoutService.findAllCmsLayouts());
		uiModel.addAttribute("cmspagecontents", cmsPageContentService.findAllCmsPageContents());
		uiModel.addAttribute("cmspagetypes", cmsPageTypeService.findAllCmsPageTypes());
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
		CmsPage cmsPage = cmsPageService.findCmsPage(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (cmsPage == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(cmsPage.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<CmsPage> result = cmsPageService.findAllCmsPages();
		return new ResponseEntity<String>(CmsPage.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		CmsPage cmsPage = CmsPage.fromJsonToCmsPage(json);
		cmsPageService.saveCmsPage(cmsPage);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (CmsPage cmsPage : CmsPage.fromJsonArrayToCmsPages(json)) {
			cmsPageService.saveCmsPage(cmsPage);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		CmsPage cmsPage = CmsPage.fromJsonToCmsPage(json);
		if (cmsPageService.updateCmsPage(cmsPage) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (CmsPage cmsPage : CmsPage.fromJsonArrayToCmsPages(json)) {
			if (cmsPageService.updateCmsPage(cmsPage) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		CmsPage cmsPage = cmsPageService.findCmsPage(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (cmsPage == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		cmsPageService.deleteCmsPage(cmsPage);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
