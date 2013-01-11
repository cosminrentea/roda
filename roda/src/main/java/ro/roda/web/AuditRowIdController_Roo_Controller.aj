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
import ro.roda.Audit;
import ro.roda.AuditRowId;
import ro.roda.AuditRowIdPK;
import ro.roda.web.AuditRowIdController;

privileged aspect AuditRowIdController_Roo_Controller {
    
    private ConversionService AuditRowIdController.conversionService;
    
    @Autowired
    public AuditRowIdController.new(ConversionService conversionService) {
        super();
        this.conversionService = conversionService;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String AuditRowIdController.create(@Valid AuditRowId auditRowId, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, auditRowId);
            return "auditrowids/create";
        }
        uiModel.asMap().clear();
        auditRowId.persist();
        return "redirect:/auditrowids/" + encodeUrlPathSegment(conversionService.convert(auditRowId.getId(), String.class), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String AuditRowIdController.createForm(Model uiModel) {
        populateEditForm(uiModel, new AuditRowId());
        return "auditrowids/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String AuditRowIdController.show(@PathVariable("id") AuditRowIdPK id, Model uiModel) {
        uiModel.addAttribute("auditrowid", AuditRowId.findAuditRowId(id));
        uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
        return "auditrowids/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String AuditRowIdController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("auditrowids", AuditRowId.findAuditRowIdEntries(firstResult, sizeNo));
            float nrOfPages = (float) AuditRowId.countAuditRowIds() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("auditrowids", AuditRowId.findAllAuditRowIds());
        }
        return "auditrowids/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String AuditRowIdController.update(@Valid AuditRowId auditRowId, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, auditRowId);
            return "auditrowids/update";
        }
        uiModel.asMap().clear();
        auditRowId.merge();
        return "redirect:/auditrowids/" + encodeUrlPathSegment(conversionService.convert(auditRowId.getId(), String.class), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String AuditRowIdController.updateForm(@PathVariable("id") AuditRowIdPK id, Model uiModel) {
        populateEditForm(uiModel, AuditRowId.findAuditRowId(id));
        return "auditrowids/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String AuditRowIdController.delete(@PathVariable("id") AuditRowIdPK id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        AuditRowId auditRowId = AuditRowId.findAuditRowId(id);
        auditRowId.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/auditrowids";
    }
    
    void AuditRowIdController.populateEditForm(Model uiModel, AuditRowId auditRowId) {
        uiModel.addAttribute("auditRowId", auditRowId);
        uiModel.addAttribute("audits", Audit.findAllAudits());
    }
    
    String AuditRowIdController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
