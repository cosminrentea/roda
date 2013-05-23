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
import ro.roda.domain.CmsSnippetGroup;
import ro.roda.service.CmsSnippetGroupService;
import ro.roda.service.CmsSnippetService;

@RequestMapping("/cmssnippetgroups")
@Controller


public class CmsSnippetGroupController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
        CmsSnippetGroup cmsSnippetGroup = cmsSnippetGroupService.findCmsSnippetGroup(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (cmsSnippetGroup == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(cmsSnippetGroup.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<CmsSnippetGroup> result = cmsSnippetGroupService.findAllCmsSnippetGroups();
        return new ResponseEntity<String>(CmsSnippetGroup.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        CmsSnippetGroup cmsSnippetGroup = CmsSnippetGroup.fromJsonToCmsSnippetGroup(json);
        cmsSnippetGroupService.saveCmsSnippetGroup(cmsSnippetGroup);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (CmsSnippetGroup cmsSnippetGroup: CmsSnippetGroup.fromJsonArrayToCmsSnippetGroups(json)) {
            cmsSnippetGroupService.saveCmsSnippetGroup(cmsSnippetGroup);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        CmsSnippetGroup cmsSnippetGroup = CmsSnippetGroup.fromJsonToCmsSnippetGroup(json);
        if (cmsSnippetGroupService.updateCmsSnippetGroup(cmsSnippetGroup) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (CmsSnippetGroup cmsSnippetGroup: CmsSnippetGroup.fromJsonArrayToCmsSnippetGroups(json)) {
            if (cmsSnippetGroupService.updateCmsSnippetGroup(cmsSnippetGroup) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
        CmsSnippetGroup cmsSnippetGroup = cmsSnippetGroupService.findCmsSnippetGroup(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (cmsSnippetGroup == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        cmsSnippetGroupService.deleteCmsSnippetGroup(cmsSnippetGroup);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@Autowired
    CmsSnippetGroupService cmsSnippetGroupService;

	@Autowired
    CmsSnippetService cmsSnippetService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid CmsSnippetGroup cmsSnippetGroup, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, cmsSnippetGroup);
            return "cmssnippetgroups/create";
        }
        uiModel.asMap().clear();
        cmsSnippetGroupService.saveCmsSnippetGroup(cmsSnippetGroup);
        return "redirect:/cmssnippetgroups/" + encodeUrlPathSegment(cmsSnippetGroup.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new CmsSnippetGroup());
        return "cmssnippetgroups/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("cmssnippetgroup", cmsSnippetGroupService.findCmsSnippetGroup(id));
        uiModel.addAttribute("itemId", id);
        return "cmssnippetgroups/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("cmssnippetgroups", cmsSnippetGroupService.findCmsSnippetGroupEntries(firstResult, sizeNo));
            float nrOfPages = (float) cmsSnippetGroupService.countAllCmsSnippetGroups() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("cmssnippetgroups", cmsSnippetGroupService.findAllCmsSnippetGroups());
        }
        return "cmssnippetgroups/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid CmsSnippetGroup cmsSnippetGroup, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, cmsSnippetGroup);
            return "cmssnippetgroups/update";
        }
        uiModel.asMap().clear();
        cmsSnippetGroupService.updateCmsSnippetGroup(cmsSnippetGroup);
        return "redirect:/cmssnippetgroups/" + encodeUrlPathSegment(cmsSnippetGroup.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, cmsSnippetGroupService.findCmsSnippetGroup(id));
        return "cmssnippetgroups/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        CmsSnippetGroup cmsSnippetGroup = cmsSnippetGroupService.findCmsSnippetGroup(id);
        cmsSnippetGroupService.deleteCmsSnippetGroup(cmsSnippetGroup);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/cmssnippetgroups";
    }

	void populateEditForm(Model uiModel, CmsSnippetGroup cmsSnippetGroup) {
        uiModel.addAttribute("cmsSnippetGroup", cmsSnippetGroup);
        uiModel.addAttribute("cmssnippets", cmsSnippetService.findAllCmsSnippets());
        uiModel.addAttribute("cmssnippetgroups", cmsSnippetGroupService.findAllCmsSnippetGroups());
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
}
