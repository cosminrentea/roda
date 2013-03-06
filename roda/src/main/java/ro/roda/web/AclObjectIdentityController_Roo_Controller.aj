// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.web;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import ro.roda.domain.AclObjectIdentity;
import ro.roda.service.AclClassService;
import ro.roda.service.AclEntryService;
import ro.roda.service.AclObjectIdentityService;
import ro.roda.service.AclSidService;
import ro.roda.web.AclObjectIdentityController;

privileged aspect AclObjectIdentityController_Roo_Controller {
    
    @Autowired
    AclObjectIdentityService AclObjectIdentityController.aclObjectIdentityService;
    
    @Autowired
    AclClassService AclObjectIdentityController.aclClassService;
    
    @Autowired
    AclEntryService AclObjectIdentityController.aclEntryService;
    
    @Autowired
    AclSidService AclObjectIdentityController.aclSidService;
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String AclObjectIdentityController.create(@Valid AclObjectIdentity aclObjectIdentity, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, aclObjectIdentity);
            return "aclobjectidentitys/create";
        }
        uiModel.asMap().clear();
        aclObjectIdentityService.saveAclObjectIdentity(aclObjectIdentity);
        return "redirect:/aclobjectidentitys/" + encodeUrlPathSegment(aclObjectIdentity.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String AclObjectIdentityController.createForm(Model uiModel) {
        populateEditForm(uiModel, new AclObjectIdentity());
        return "aclobjectidentitys/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String AclObjectIdentityController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("aclobjectidentity", aclObjectIdentityService.findAclObjectIdentity(id));
        uiModel.addAttribute("itemId", id);
        return "aclobjectidentitys/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String AclObjectIdentityController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("aclobjectidentitys", aclObjectIdentityService.findAclObjectIdentityEntries(firstResult, sizeNo));
            float nrOfPages = (float) aclObjectIdentityService.countAllAclObjectIdentitys() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("aclobjectidentitys", aclObjectIdentityService.findAllAclObjectIdentitys());
        }
        return "aclobjectidentitys/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String AclObjectIdentityController.update(@Valid AclObjectIdentity aclObjectIdentity, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, aclObjectIdentity);
            return "aclobjectidentitys/update";
        }
        uiModel.asMap().clear();
        aclObjectIdentityService.updateAclObjectIdentity(aclObjectIdentity);
        return "redirect:/aclobjectidentitys/" + encodeUrlPathSegment(aclObjectIdentity.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String AclObjectIdentityController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, aclObjectIdentityService.findAclObjectIdentity(id));
        return "aclobjectidentitys/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String AclObjectIdentityController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        AclObjectIdentity aclObjectIdentity = aclObjectIdentityService.findAclObjectIdentity(id);
        aclObjectIdentityService.deleteAclObjectIdentity(aclObjectIdentity);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/aclobjectidentitys";
    }
    
    void AclObjectIdentityController.populateEditForm(Model uiModel, AclObjectIdentity aclObjectIdentity) {
        uiModel.addAttribute("aclObjectIdentity", aclObjectIdentity);
        uiModel.addAttribute("aclclasses", aclClassService.findAllAclClasses());
        uiModel.addAttribute("aclentrys", aclEntryService.findAllAclEntrys());
        uiModel.addAttribute("aclobjectidentitys", aclObjectIdentityService.findAllAclObjectIdentitys());
        uiModel.addAttribute("aclsids", aclSidService.findAllAclSids());
    }
    
    String AclObjectIdentityController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
