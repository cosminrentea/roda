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
import ro.roda.Source;
import ro.roda.Sourcestudy;
import ro.roda.SourcestudyType;
import ro.roda.SourcestudyTypeHistory;
import ro.roda.web.SourcestudyController;

privileged aspect SourcestudyController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String SourcestudyController.create(@Valid Sourcestudy sourcestudy, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, sourcestudy);
            return "sourcestudys/create";
        }
        uiModel.asMap().clear();
        sourcestudy.persist();
        return "redirect:/sourcestudys/" + encodeUrlPathSegment(sourcestudy.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String SourcestudyController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Sourcestudy());
        return "sourcestudys/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String SourcestudyController.show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("sourcestudy", Sourcestudy.findSourcestudy(id));
        uiModel.addAttribute("itemId", id);
        return "sourcestudys/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String SourcestudyController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("sourcestudys", Sourcestudy.findSourcestudyEntries(firstResult, sizeNo));
            float nrOfPages = (float) Sourcestudy.countSourcestudys() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("sourcestudys", Sourcestudy.findAllSourcestudys());
        }
        return "sourcestudys/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String SourcestudyController.update(@Valid Sourcestudy sourcestudy, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, sourcestudy);
            return "sourcestudys/update";
        }
        uiModel.asMap().clear();
        sourcestudy.merge();
        return "redirect:/sourcestudys/" + encodeUrlPathSegment(sourcestudy.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String SourcestudyController.updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, Sourcestudy.findSourcestudy(id));
        return "sourcestudys/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String SourcestudyController.delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Sourcestudy sourcestudy = Sourcestudy.findSourcestudy(id);
        sourcestudy.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/sourcestudys";
    }
    
    void SourcestudyController.populateEditForm(Model uiModel, Sourcestudy sourcestudy) {
        uiModel.addAttribute("sourcestudy", sourcestudy);
        uiModel.addAttribute("sources", Source.findAllSources());
        uiModel.addAttribute("sourcestudytypes", SourcestudyType.findAllSourcestudyTypes());
        uiModel.addAttribute("sourcestudytypehistorys", SourcestudyTypeHistory.findAllSourcestudyTypeHistorys());
    }
    
    String SourcestudyController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
