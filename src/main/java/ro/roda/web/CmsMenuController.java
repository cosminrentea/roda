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

import ro.roda.domain.CmsMenu;
import ro.roda.service.CmsMenuService;

@RequestMapping("/cmsmenus")
@Controller
public class CmsMenuController {

	@Autowired
	CmsMenuService cmsMenuService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid CmsMenu cmsMenu, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, cmsMenu);
			return "cmsmenus/create";
		}
		uiModel.asMap().clear();
		cmsMenuService.saveCmsMenu(cmsMenu);
		return "redirect:/cmsmenus/" + encodeUrlPathSegment(cmsMenu.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new CmsMenu());
		return "cmsmenus/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Integer id, Model uiModel) {
		uiModel.addAttribute("cmsmenu", cmsMenuService.findCmsMenu(id));
		uiModel.addAttribute("itemId", id);
		return "cmsmenus/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("cmsmenus", cmsMenuService.findCmsMenuEntries(firstResult, sizeNo));
			float nrOfPages = (float) cmsMenuService.countAllCmsMenus() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("cmsmenus", cmsMenuService.findAllCmsMenus());
		}
		return "cmsmenus/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid CmsMenu cmsMenu, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, cmsMenu);
			return "cmsmenus/update";
		}
		uiModel.asMap().clear();
		cmsMenuService.updateCmsMenu(cmsMenu);
		return "redirect:/cmsmenus/" + encodeUrlPathSegment(cmsMenu.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		populateEditForm(uiModel, cmsMenuService.findCmsMenu(id));
		return "cmsmenus/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		CmsMenu cmsMenu = cmsMenuService.findCmsMenu(id);
		cmsMenuService.deleteCmsMenu(cmsMenu);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/cmsmenus";
	}

	void populateEditForm(Model uiModel, CmsMenu cmsMenu) {
		uiModel.addAttribute("cmsMenu", cmsMenu);
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
		CmsMenu cmsMenu = cmsMenuService.findCmsMenu(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (cmsMenu == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(cmsMenu.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<CmsMenu> result = cmsMenuService.findAllCmsMenus();
		return new ResponseEntity<String>(CmsMenu.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		CmsMenu cmsMenu = CmsMenu.fromJsonToCmsMenu(json);
		cmsMenuService.saveCmsMenu(cmsMenu);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (CmsMenu cmsMenu : CmsMenu.fromJsonArrayToCmsMenus(json)) {
			cmsMenuService.saveCmsMenu(cmsMenu);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		CmsMenu cmsMenu = CmsMenu.fromJsonToCmsMenu(json);
		if (cmsMenuService.updateCmsMenu(cmsMenu) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (CmsMenu cmsMenu : CmsMenu.fromJsonArrayToCmsMenus(json)) {
			if (cmsMenuService.updateCmsMenu(cmsMenu) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
		CmsMenu cmsMenu = cmsMenuService.findCmsMenu(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (cmsMenu == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		cmsMenuService.deleteCmsMenu(cmsMenu);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
