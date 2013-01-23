// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.web;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import ro.roda.Form;
import ro.roda.FormEditedNumberVar;
import ro.roda.FormEditedTextVar;
import ro.roda.FormSelectionVar;
import ro.roda.Instance;
import ro.roda.web.FormController;

privileged aspect FormController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String FormController.create(@Valid Form form, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, form);
            return "forms/create";
        }
        uiModel.asMap().clear();
        form.persist();
        return "redirect:/forms/" + encodeUrlPathSegment(form.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String FormController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Form());
        return "forms/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String FormController.show(@PathVariable("id") Integer id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("form", Form.findForm(id));
        uiModel.addAttribute("itemId", id);
        return "forms/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String FormController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("forms", Form.findFormEntries(firstResult, sizeNo));
            float nrOfPages = (float) Form.countForms() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("forms", Form.findAllForms());
        }
        addDateTimeFormatPatterns(uiModel);
        return "forms/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String FormController.update(@Valid Form form, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, form);
            return "forms/update";
        }
        uiModel.asMap().clear();
        form.merge();
        return "redirect:/forms/" + encodeUrlPathSegment(form.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String FormController.updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, Form.findForm(id));
        return "forms/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String FormController.delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Form form = Form.findForm(id);
        form.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/forms";
    }
    
    void FormController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("form_filldate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
    
    void FormController.populateEditForm(Model uiModel, Form form) {
        uiModel.addAttribute("form", form);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("formeditednumbervars", FormEditedNumberVar.findAllFormEditedNumberVars());
        uiModel.addAttribute("formeditedtextvars", FormEditedTextVar.findAllFormEditedTextVars());
        uiModel.addAttribute("formselectionvars", FormSelectionVar.findAllFormSelectionVars());
        uiModel.addAttribute("instances", Instance.findAllInstances());
    }
    
    String FormController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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