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
import ro.roda.Sourcestudy;
import ro.roda.SourcestudyType;
import ro.roda.SourcestudyTypeHistory;
import ro.roda.web.SourcestudyTypeController;

privileged aspect SourcestudyTypeController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String SourcestudyTypeController.create(@Valid SourcestudyType sourcestudyType, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, sourcestudyType);
            return "sourcestudytypes/create";
        }
        uiModel.asMap().clear();
        sourcestudyType.persist();
        return "redirect:/sourcestudytypes/" + encodeUrlPathSegment(sourcestudyType.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String SourcestudyTypeController.createForm(Model uiModel) {
        populateEditForm(uiModel, new SourcestudyType());
        return "sourcestudytypes/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String SourcestudyTypeController.show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("sourcestudytype", SourcestudyType.findSourcestudyType(id));
        uiModel.addAttribute("itemId", id);
        return "sourcestudytypes/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String SourcestudyTypeController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("sourcestudytypes", SourcestudyType.findSourcestudyTypeEntries(firstResult, sizeNo));
            float nrOfPages = (float) SourcestudyType.countSourcestudyTypes() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("sourcestudytypes", SourcestudyType.findAllSourcestudyTypes());
        }
        return "sourcestudytypes/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String SourcestudyTypeController.update(@Valid SourcestudyType sourcestudyType, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, sourcestudyType);
            return "sourcestudytypes/update";
        }
        uiModel.asMap().clear();
        sourcestudyType.merge();
        return "redirect:/sourcestudytypes/" + encodeUrlPathSegment(sourcestudyType.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String SourcestudyTypeController.updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, SourcestudyType.findSourcestudyType(id));
        return "sourcestudytypes/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String SourcestudyTypeController.delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        SourcestudyType sourcestudyType = SourcestudyType.findSourcestudyType(id);
        sourcestudyType.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/sourcestudytypes";
    }
    
    void SourcestudyTypeController.populateEditForm(Model uiModel, SourcestudyType sourcestudyType) {
        uiModel.addAttribute("sourcestudyType", sourcestudyType);
        uiModel.addAttribute("sourcestudys", Sourcestudy.findAllSourcestudys());
        uiModel.addAttribute("sourcestudytypehistorys", SourcestudyTypeHistory.findAllSourcestudyTypeHistorys());
    }
    
    String SourcestudyTypeController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
