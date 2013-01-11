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
import ro.roda.Org;
import ro.roda.Study;
import ro.roda.StudyOrg;
import ro.roda.StudyOrgAcl;
import ro.roda.StudyOrgAssoc;
import ro.roda.web.StudyOrgController;

privileged aspect StudyOrgController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String StudyOrgController.create(@Valid StudyOrg studyOrg, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, studyOrg);
            return "studyorgs/create";
        }
        uiModel.asMap().clear();
        studyOrg.persist();
        return "redirect:/studyorgs/" + encodeUrlPathSegment(studyOrg.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String StudyOrgController.createForm(Model uiModel) {
        populateEditForm(uiModel, new StudyOrg());
        return "studyorgs/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String StudyOrgController.show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("studyorg", StudyOrg.findStudyOrg(id));
        uiModel.addAttribute("itemId", id);
        return "studyorgs/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String StudyOrgController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("studyorgs", StudyOrg.findStudyOrgEntries(firstResult, sizeNo));
            float nrOfPages = (float) StudyOrg.countStudyOrgs() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("studyorgs", StudyOrg.findAllStudyOrgs());
        }
        return "studyorgs/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String StudyOrgController.update(@Valid StudyOrg studyOrg, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, studyOrg);
            return "studyorgs/update";
        }
        uiModel.asMap().clear();
        studyOrg.merge();
        return "redirect:/studyorgs/" + encodeUrlPathSegment(studyOrg.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String StudyOrgController.updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, StudyOrg.findStudyOrg(id));
        return "studyorgs/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String StudyOrgController.delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        StudyOrg studyOrg = StudyOrg.findStudyOrg(id);
        studyOrg.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/studyorgs";
    }
    
    void StudyOrgController.populateEditForm(Model uiModel, StudyOrg studyOrg) {
        uiModel.addAttribute("studyOrg", studyOrg);
        uiModel.addAttribute("orgs", Org.findAllOrgs());
        uiModel.addAttribute("studys", Study.findAllStudys());
        uiModel.addAttribute("studyorgacls", StudyOrgAcl.findAllStudyOrgAcls());
        uiModel.addAttribute("studyorgassocs", StudyOrgAssoc.findAllStudyOrgAssocs());
    }
    
    String StudyOrgController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
