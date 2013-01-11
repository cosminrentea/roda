// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.web;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import ro.roda.EditedVariable;
import ro.roda.Form;
import ro.roda.FormEditedTextVar;
import ro.roda.FormEditedTextVarPK;
import ro.roda.web.FormEditedTextVarController;

privileged aspect FormEditedTextVarController_Roo_Controller {
    
    private ConversionService FormEditedTextVarController.conversionService;
    
    @Autowired
    public FormEditedTextVarController.new(ConversionService conversionService) {
        super();
        this.conversionService = conversionService;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String FormEditedTextVarController.create(@Valid FormEditedTextVar formEditedTextVar, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, formEditedTextVar);
            return "formeditedtextvars/create";
        }
        uiModel.asMap().clear();
        formEditedTextVar.persist();
        return "redirect:/formeditedtextvars/" + encodeUrlPathSegment(conversionService.convert(formEditedTextVar.getId(), String.class), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String FormEditedTextVarController.createForm(Model uiModel) {
        populateEditForm(uiModel, new FormEditedTextVar());
        return "formeditedtextvars/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String FormEditedTextVarController.show(@PathVariable("id") FormEditedTextVarPK id, Model uiModel) {
        uiModel.addAttribute("formeditedtextvar", FormEditedTextVar.findFormEditedTextVar(id));
        uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
        return "formeditedtextvars/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String FormEditedTextVarController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("formeditedtextvars", FormEditedTextVar.findFormEditedTextVarEntries(firstResult, sizeNo));
            float nrOfPages = (float) FormEditedTextVar.countFormEditedTextVars() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("formeditedtextvars", FormEditedTextVar.findAllFormEditedTextVars());
        }
        return "formeditedtextvars/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String FormEditedTextVarController.update(@Valid FormEditedTextVar formEditedTextVar, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, formEditedTextVar);
            return "formeditedtextvars/update";
        }
        uiModel.asMap().clear();
        formEditedTextVar.merge();
        return "redirect:/formeditedtextvars/" + encodeUrlPathSegment(conversionService.convert(formEditedTextVar.getId(), String.class), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String FormEditedTextVarController.updateForm(@PathVariable("id") FormEditedTextVarPK id, Model uiModel) {
        populateEditForm(uiModel, FormEditedTextVar.findFormEditedTextVar(id));
        return "formeditedtextvars/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String FormEditedTextVarController.delete(@PathVariable("id") FormEditedTextVarPK id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        FormEditedTextVar formEditedTextVar = FormEditedTextVar.findFormEditedTextVar(id);
        formEditedTextVar.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/formeditedtextvars";
    }
    
    void FormEditedTextVarController.populateEditForm(Model uiModel, FormEditedTextVar formEditedTextVar) {
        uiModel.addAttribute("formEditedTextVar", formEditedTextVar);
        uiModel.addAttribute("editedvariables", EditedVariable.findAllEditedVariables());
        uiModel.addAttribute("forms", Form.findAllForms());
    }
    
    String FormEditedTextVarController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
