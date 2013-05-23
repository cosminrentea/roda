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
import ro.roda.domain.CmsLayout;
import ro.roda.service.CmsLayoutGroupService;
import ro.roda.service.CmsLayoutService;
import ro.roda.service.CmsPageService;

@RequestMapping("/cmslayouts")
@Controller


public class CmsLayoutController {

	@Autowired
    CmsLayoutService cmsLayoutService;

	@Autowired
    CmsLayoutGroupService cmsLayoutGroupService;

	@Autowired
    CmsPageService cmsPageService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid CmsLayout cmsLayout, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, cmsLayout);
            return "cmslayouts/create";
        }
        uiModel.asMap().clear();
        cmsLayoutService.saveCmsLayout(cmsLayout);
        return "redirect:/cmslayouts/" + encodeUrlPathSegment(cmsLayout.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new CmsLayout());
        return "cmslayouts/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("cmslayout", cmsLayoutService.findCmsLayout(id));
        uiModel.addAttribute("itemId", id);
        return "cmslayouts/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("cmslayouts", cmsLayoutService.findCmsLayoutEntries(firstResult, sizeNo));
            float nrOfPages = (float) cmsLayoutService.countAllCmsLayouts() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("cmslayouts", cmsLayoutService.findAllCmsLayouts());
        }
        return "cmslayouts/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid CmsLayout cmsLayout, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, cmsLayout);
            return "cmslayouts/update";
        }
        uiModel.asMap().clear();
        cmsLayoutService.updateCmsLayout(cmsLayout);
        return "redirect:/cmslayouts/" + encodeUrlPathSegment(cmsLayout.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, cmsLayoutService.findCmsLayout(id));
        return "cmslayouts/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        CmsLayout cmsLayout = cmsLayoutService.findCmsLayout(id);
        cmsLayoutService.deleteCmsLayout(cmsLayout);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/cmslayouts";
    }

	void populateEditForm(Model uiModel, CmsLayout cmsLayout) {
        uiModel.addAttribute("cmsLayout", cmsLayout);
        uiModel.addAttribute("cmslayoutgroups", cmsLayoutGroupService.findAllCmsLayoutGroups());
        uiModel.addAttribute("cmspages", cmsPageService.findAllCmsPages());
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
        CmsLayout cmsLayout = cmsLayoutService.findCmsLayout(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (cmsLayout == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(cmsLayout.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<CmsLayout> result = cmsLayoutService.findAllCmsLayouts();
        return new ResponseEntity<String>(CmsLayout.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        CmsLayout cmsLayout = CmsLayout.fromJsonToCmsLayout(json);
        cmsLayoutService.saveCmsLayout(cmsLayout);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (CmsLayout cmsLayout: CmsLayout.fromJsonArrayToCmsLayouts(json)) {
            cmsLayoutService.saveCmsLayout(cmsLayout);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        CmsLayout cmsLayout = CmsLayout.fromJsonToCmsLayout(json);
        if (cmsLayoutService.updateCmsLayout(cmsLayout) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (CmsLayout cmsLayout: CmsLayout.fromJsonArrayToCmsLayouts(json)) {
            if (cmsLayoutService.updateCmsLayout(cmsLayout) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
        CmsLayout cmsLayout = cmsLayoutService.findCmsLayout(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (cmsLayout == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        cmsLayoutService.deleteCmsLayout(cmsLayout);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
