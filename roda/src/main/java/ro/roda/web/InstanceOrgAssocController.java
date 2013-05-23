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
import ro.roda.domain.InstanceOrgAssoc;
import ro.roda.service.InstanceOrgAssocService;
import ro.roda.service.InstanceOrgService;

@RequestMapping("/instanceorgassocs")
@Controller


public class InstanceOrgAssocController {

	@Autowired
    InstanceOrgAssocService instanceOrgAssocService;

	@Autowired
    InstanceOrgService instanceOrgService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid InstanceOrgAssoc instanceOrgAssoc, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, instanceOrgAssoc);
            return "instanceorgassocs/create";
        }
        uiModel.asMap().clear();
        instanceOrgAssocService.saveInstanceOrgAssoc(instanceOrgAssoc);
        return "redirect:/instanceorgassocs/" + encodeUrlPathSegment(instanceOrgAssoc.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new InstanceOrgAssoc());
        return "instanceorgassocs/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("instanceorgassoc", instanceOrgAssocService.findInstanceOrgAssoc(id));
        uiModel.addAttribute("itemId", id);
        return "instanceorgassocs/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("instanceorgassocs", instanceOrgAssocService.findInstanceOrgAssocEntries(firstResult, sizeNo));
            float nrOfPages = (float) instanceOrgAssocService.countAllInstanceOrgAssocs() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("instanceorgassocs", instanceOrgAssocService.findAllInstanceOrgAssocs());
        }
        return "instanceorgassocs/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid InstanceOrgAssoc instanceOrgAssoc, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, instanceOrgAssoc);
            return "instanceorgassocs/update";
        }
        uiModel.asMap().clear();
        instanceOrgAssocService.updateInstanceOrgAssoc(instanceOrgAssoc);
        return "redirect:/instanceorgassocs/" + encodeUrlPathSegment(instanceOrgAssoc.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, instanceOrgAssocService.findInstanceOrgAssoc(id));
        return "instanceorgassocs/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        InstanceOrgAssoc instanceOrgAssoc = instanceOrgAssocService.findInstanceOrgAssoc(id);
        instanceOrgAssocService.deleteInstanceOrgAssoc(instanceOrgAssoc);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/instanceorgassocs";
    }

	void populateEditForm(Model uiModel, InstanceOrgAssoc instanceOrgAssoc) {
        uiModel.addAttribute("instanceOrgAssoc", instanceOrgAssoc);
        uiModel.addAttribute("instanceorgs", instanceOrgService.findAllInstanceOrgs());
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
        InstanceOrgAssoc instanceOrgAssoc = instanceOrgAssocService.findInstanceOrgAssoc(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (instanceOrgAssoc == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(instanceOrgAssoc.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<InstanceOrgAssoc> result = instanceOrgAssocService.findAllInstanceOrgAssocs();
        return new ResponseEntity<String>(InstanceOrgAssoc.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        InstanceOrgAssoc instanceOrgAssoc = InstanceOrgAssoc.fromJsonToInstanceOrgAssoc(json);
        instanceOrgAssocService.saveInstanceOrgAssoc(instanceOrgAssoc);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (InstanceOrgAssoc instanceOrgAssoc: InstanceOrgAssoc.fromJsonArrayToInstanceOrgAssocs(json)) {
            instanceOrgAssocService.saveInstanceOrgAssoc(instanceOrgAssoc);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        InstanceOrgAssoc instanceOrgAssoc = InstanceOrgAssoc.fromJsonToInstanceOrgAssoc(json);
        if (instanceOrgAssocService.updateInstanceOrgAssoc(instanceOrgAssoc) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (InstanceOrgAssoc instanceOrgAssoc: InstanceOrgAssoc.fromJsonArrayToInstanceOrgAssocs(json)) {
            if (instanceOrgAssocService.updateInstanceOrgAssoc(instanceOrgAssoc) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
        InstanceOrgAssoc instanceOrgAssoc = instanceOrgAssocService.findInstanceOrgAssoc(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (instanceOrgAssoc == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        instanceOrgAssocService.deleteInstanceOrgAssoc(instanceOrgAssoc);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
