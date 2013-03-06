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
import ro.roda.domain.StudyOrgAssoc;
import ro.roda.service.StudyOrgAssocService;
import ro.roda.web.StudyOrgAssocController;

privileged aspect StudyOrgAssocController_Roo_Controller {
    
    @Autowired
    StudyOrgAssocService StudyOrgAssocController.studyOrgAssocService;
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String StudyOrgAssocController.create(@Valid StudyOrgAssoc studyOrgAssoc, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, studyOrgAssoc);
            return "studyorgassocs/create";
        }
        uiModel.asMap().clear();
        studyOrgAssocService.saveStudyOrgAssoc(studyOrgAssoc);
        return "redirect:/studyorgassocs/" + encodeUrlPathSegment(studyOrgAssoc.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String StudyOrgAssocController.createForm(Model uiModel) {
        populateEditForm(uiModel, new StudyOrgAssoc());
        return "studyorgassocs/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String StudyOrgAssocController.show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("studyorgassoc", studyOrgAssocService.findStudyOrgAssoc(id));
        uiModel.addAttribute("itemId", id);
        return "studyorgassocs/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String StudyOrgAssocController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("studyorgassocs", studyOrgAssocService.findStudyOrgAssocEntries(firstResult, sizeNo));
            float nrOfPages = (float) studyOrgAssocService.countAllStudyOrgAssocs() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("studyorgassocs", studyOrgAssocService.findAllStudyOrgAssocs());
        }
        return "studyorgassocs/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String StudyOrgAssocController.update(@Valid StudyOrgAssoc studyOrgAssoc, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, studyOrgAssoc);
            return "studyorgassocs/update";
        }
        uiModel.asMap().clear();
        studyOrgAssocService.updateStudyOrgAssoc(studyOrgAssoc);
        return "redirect:/studyorgassocs/" + encodeUrlPathSegment(studyOrgAssoc.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String StudyOrgAssocController.updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, studyOrgAssocService.findStudyOrgAssoc(id));
        return "studyorgassocs/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String StudyOrgAssocController.delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        StudyOrgAssoc studyOrgAssoc = studyOrgAssocService.findStudyOrgAssoc(id);
        studyOrgAssocService.deleteStudyOrgAssoc(studyOrgAssoc);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/studyorgassocs";
    }
    
    void StudyOrgAssocController.populateEditForm(Model uiModel, StudyOrgAssoc studyOrgAssoc) {
        uiModel.addAttribute("studyOrgAssoc", studyOrgAssoc);
    }
    
    String StudyOrgAssocController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
