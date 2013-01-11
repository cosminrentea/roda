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
import ro.roda.Region;
import ro.roda.Regiontype;
import ro.roda.web.RegiontypeController;

privileged aspect RegiontypeController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String RegiontypeController.create(@Valid Regiontype regiontype, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, regiontype);
            return "regiontypes/create";
        }
        uiModel.asMap().clear();
        regiontype.persist();
        return "redirect:/regiontypes/" + encodeUrlPathSegment(regiontype.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String RegiontypeController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Regiontype());
        return "regiontypes/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String RegiontypeController.show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("regiontype", Regiontype.findRegiontype(id));
        uiModel.addAttribute("itemId", id);
        return "regiontypes/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String RegiontypeController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("regiontypes", Regiontype.findRegiontypeEntries(firstResult, sizeNo));
            float nrOfPages = (float) Regiontype.countRegiontypes() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("regiontypes", Regiontype.findAllRegiontypes());
        }
        return "regiontypes/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String RegiontypeController.update(@Valid Regiontype regiontype, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, regiontype);
            return "regiontypes/update";
        }
        uiModel.asMap().clear();
        regiontype.merge();
        return "redirect:/regiontypes/" + encodeUrlPathSegment(regiontype.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String RegiontypeController.updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, Regiontype.findRegiontype(id));
        return "regiontypes/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String RegiontypeController.delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Regiontype regiontype = Regiontype.findRegiontype(id);
        regiontype.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/regiontypes";
    }
    
    void RegiontypeController.populateEditForm(Model uiModel, Regiontype regiontype) {
        uiModel.addAttribute("regiontype", regiontype);
        uiModel.addAttribute("regions", Region.findAllRegions());
    }
    
    String RegiontypeController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
