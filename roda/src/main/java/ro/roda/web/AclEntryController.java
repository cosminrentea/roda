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
import ro.roda.domain.AclEntry;
import ro.roda.service.AclEntryService;
import ro.roda.service.AclObjectIdentityService;
import ro.roda.service.AclSidService;

@RequestMapping("/aclentrys")
@Controller


public class AclEntryController {

	@Autowired
    AclEntryService aclEntryService;

	@Autowired
    AclObjectIdentityService aclObjectIdentityService;

	@Autowired
    AclSidService aclSidService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid AclEntry aclEntry, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, aclEntry);
            return "aclentrys/create";
        }
        uiModel.asMap().clear();
        aclEntryService.saveAclEntry(aclEntry);
        return "redirect:/aclentrys/" + encodeUrlPathSegment(aclEntry.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new AclEntry());
        return "aclentrys/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("aclentry", aclEntryService.findAclEntry(id));
        uiModel.addAttribute("itemId", id);
        return "aclentrys/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("aclentrys", aclEntryService.findAclEntryEntries(firstResult, sizeNo));
            float nrOfPages = (float) aclEntryService.countAllAclEntrys() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("aclentrys", aclEntryService.findAllAclEntrys());
        }
        return "aclentrys/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid AclEntry aclEntry, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, aclEntry);
            return "aclentrys/update";
        }
        uiModel.asMap().clear();
        aclEntryService.updateAclEntry(aclEntry);
        return "redirect:/aclentrys/" + encodeUrlPathSegment(aclEntry.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, aclEntryService.findAclEntry(id));
        return "aclentrys/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        AclEntry aclEntry = aclEntryService.findAclEntry(id);
        aclEntryService.deleteAclEntry(aclEntry);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/aclentrys";
    }

	void populateEditForm(Model uiModel, AclEntry aclEntry) {
        uiModel.addAttribute("aclEntry", aclEntry);
        uiModel.addAttribute("aclobjectidentitys", aclObjectIdentityService.findAllAclObjectIdentitys());
        uiModel.addAttribute("aclsids", aclSidService.findAllAclSids());
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
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        AclEntry aclEntry = aclEntryService.findAclEntry(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (aclEntry == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(aclEntry.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<AclEntry> result = aclEntryService.findAllAclEntrys();
        return new ResponseEntity<String>(AclEntry.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        AclEntry aclEntry = AclEntry.fromJsonToAclEntry(json);
        aclEntryService.saveAclEntry(aclEntry);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (AclEntry aclEntry: AclEntry.fromJsonArrayToAclEntrys(json)) {
            aclEntryService.saveAclEntry(aclEntry);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        AclEntry aclEntry = AclEntry.fromJsonToAclEntry(json);
        if (aclEntryService.updateAclEntry(aclEntry) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (AclEntry aclEntry: AclEntry.fromJsonArrayToAclEntrys(json)) {
            if (aclEntryService.updateAclEntry(aclEntry) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        AclEntry aclEntry = aclEntryService.findAclEntry(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (aclEntry == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        aclEntryService.deleteAclEntry(aclEntry);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
