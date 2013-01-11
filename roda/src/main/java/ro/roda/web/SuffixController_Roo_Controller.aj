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
import ro.roda.Person;
import ro.roda.Suffix;
import ro.roda.web.SuffixController;

privileged aspect SuffixController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String SuffixController.create(@Valid Suffix suffix, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, suffix);
            return "suffixes/create";
        }
        uiModel.asMap().clear();
        suffix.persist();
        return "redirect:/suffixes/" + encodeUrlPathSegment(suffix.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String SuffixController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Suffix());
        return "suffixes/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String SuffixController.show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("suffix", Suffix.findSuffix(id));
        uiModel.addAttribute("itemId", id);
        return "suffixes/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String SuffixController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("suffixes", Suffix.findSuffixEntries(firstResult, sizeNo));
            float nrOfPages = (float) Suffix.countSuffixes() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("suffixes", Suffix.findAllSuffixes());
        }
        return "suffixes/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String SuffixController.update(@Valid Suffix suffix, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, suffix);
            return "suffixes/update";
        }
        uiModel.asMap().clear();
        suffix.merge();
        return "redirect:/suffixes/" + encodeUrlPathSegment(suffix.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String SuffixController.updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, Suffix.findSuffix(id));
        return "suffixes/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String SuffixController.delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Suffix suffix = Suffix.findSuffix(id);
        suffix.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/suffixes";
    }
    
    void SuffixController.populateEditForm(Model uiModel, Suffix suffix) {
        uiModel.addAttribute("suffix", suffix);
        uiModel.addAttribute("people", Person.findAllPeople());
    }
    
    String SuffixController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
