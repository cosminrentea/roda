// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.web;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import ro.roda.OrgRelationType;
import ro.roda.OrgRelations;
import ro.roda.web.OrgRelationTypeController;

privileged aspect OrgRelationTypeController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String OrgRelationTypeController.create(@Valid OrgRelationType orgRelationType, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, orgRelationType);
            return "orgrelationtypes/create";
        }
        uiModel.asMap().clear();
        orgRelationType.persist();
        return "redirect:/orgrelationtypes/" + encodeUrlPathSegment(orgRelationType.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String OrgRelationTypeController.createForm(Model uiModel) {
        populateEditForm(uiModel, new OrgRelationType());
        return "orgrelationtypes/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String OrgRelationTypeController.show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("orgrelationtype", OrgRelationType.findOrgRelationType(id));
        uiModel.addAttribute("itemId", id);
        return "orgrelationtypes/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String OrgRelationTypeController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("orgrelationtypes", OrgRelationType.findOrgRelationTypeEntries(firstResult, sizeNo));
            float nrOfPages = (float) OrgRelationType.countOrgRelationTypes() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("orgrelationtypes", OrgRelationType.findAllOrgRelationTypes());
        }
        return "orgrelationtypes/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String OrgRelationTypeController.update(@Valid OrgRelationType orgRelationType, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, orgRelationType);
            return "orgrelationtypes/update";
        }
        uiModel.asMap().clear();
        orgRelationType.merge();
        return "redirect:/orgrelationtypes/" + encodeUrlPathSegment(orgRelationType.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String OrgRelationTypeController.updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, OrgRelationType.findOrgRelationType(id));
        return "orgrelationtypes/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String OrgRelationTypeController.delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        OrgRelationType orgRelationType = OrgRelationType.findOrgRelationType(id);
        orgRelationType.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/orgrelationtypes";
    }
    
    void OrgRelationTypeController.populateEditForm(Model uiModel, OrgRelationType orgRelationType) {
        uiModel.addAttribute("orgRelationType", orgRelationType);
        uiModel.addAttribute("orgrelationses", OrgRelations.findAllOrgRelationses());
    }
    
    String OrgRelationTypeController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
