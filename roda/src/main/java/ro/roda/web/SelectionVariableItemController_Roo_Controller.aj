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
import ro.roda.File;
import ro.roda.FormSelectionVar;
import ro.roda.Frequency;
import ro.roda.Item;
import ro.roda.SelectionVariable;
import ro.roda.SelectionVariableItem;
import ro.roda.SelectionVariableItemPK;
import ro.roda.web.SelectionVariableItemController;

privileged aspect SelectionVariableItemController_Roo_Controller {
    
    private ConversionService SelectionVariableItemController.conversionService;
    
    @Autowired
    public SelectionVariableItemController.new(ConversionService conversionService) {
        super();
        this.conversionService = conversionService;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String SelectionVariableItemController.create(@Valid SelectionVariableItem selectionVariableItem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, selectionVariableItem);
            return "selectionvariableitems/create";
        }
        uiModel.asMap().clear();
        selectionVariableItem.persist();
        return "redirect:/selectionvariableitems/" + encodeUrlPathSegment(conversionService.convert(selectionVariableItem.getId(), String.class), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String SelectionVariableItemController.createForm(Model uiModel) {
        populateEditForm(uiModel, new SelectionVariableItem());
        return "selectionvariableitems/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String SelectionVariableItemController.show(@PathVariable("id") SelectionVariableItemPK id, Model uiModel) {
        uiModel.addAttribute("selectionvariableitem", SelectionVariableItem.findSelectionVariableItem(id));
        uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
        return "selectionvariableitems/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String SelectionVariableItemController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("selectionvariableitems", SelectionVariableItem.findSelectionVariableItemEntries(firstResult, sizeNo));
            float nrOfPages = (float) SelectionVariableItem.countSelectionVariableItems() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("selectionvariableitems", SelectionVariableItem.findAllSelectionVariableItems());
        }
        return "selectionvariableitems/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String SelectionVariableItemController.update(@Valid SelectionVariableItem selectionVariableItem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, selectionVariableItem);
            return "selectionvariableitems/update";
        }
        uiModel.asMap().clear();
        selectionVariableItem.merge();
        return "redirect:/selectionvariableitems/" + encodeUrlPathSegment(conversionService.convert(selectionVariableItem.getId(), String.class), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String SelectionVariableItemController.updateForm(@PathVariable("id") SelectionVariableItemPK id, Model uiModel) {
        populateEditForm(uiModel, SelectionVariableItem.findSelectionVariableItem(id));
        return "selectionvariableitems/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String SelectionVariableItemController.delete(@PathVariable("id") SelectionVariableItemPK id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        SelectionVariableItem selectionVariableItem = SelectionVariableItem.findSelectionVariableItem(id);
        selectionVariableItem.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/selectionvariableitems";
    }
    
    void SelectionVariableItemController.populateEditForm(Model uiModel, SelectionVariableItem selectionVariableItem) {
        uiModel.addAttribute("selectionVariableItem", selectionVariableItem);
        uiModel.addAttribute("files", File.findAllFiles());
        uiModel.addAttribute("formselectionvars", FormSelectionVar.findAllFormSelectionVars());
        uiModel.addAttribute("frequencys", Frequency.findAllFrequencys());
        uiModel.addAttribute("items", Item.findAllItems());
        uiModel.addAttribute("selectionvariables", SelectionVariable.findAllSelectionVariables());
    }
    
    String SelectionVariableItemController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
