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
import ro.roda.Item;
import ro.roda.Scale;
import ro.roda.Value;
import ro.roda.web.ValueController;

privileged aspect ValueController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String ValueController.create(@Valid Value value, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, value);
            return "values/create";
        }
        uiModel.asMap().clear();
        value.persist();
        return "redirect:/values/" + encodeUrlPathSegment(value.getItemId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String ValueController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Value());
        return "values/create";
    }
    
    @RequestMapping(value = "/{itemId}", produces = "text/html")
    public String ValueController.show(@PathVariable("itemId") Integer itemId, Model uiModel) {
        uiModel.addAttribute("value", Value.findValue(itemId));
        uiModel.addAttribute("itemId", itemId);
        return "values/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String ValueController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("values", Value.findValueEntries(firstResult, sizeNo));
            float nrOfPages = (float) Value.countValues() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("values", Value.findAllValues());
        }
        return "values/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String ValueController.update(@Valid Value value, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, value);
            return "values/update";
        }
        uiModel.asMap().clear();
        value.merge();
        return "redirect:/values/" + encodeUrlPathSegment(value.getItemId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{itemId}", params = "form", produces = "text/html")
    public String ValueController.updateForm(@PathVariable("itemId") Integer itemId, Model uiModel) {
        populateEditForm(uiModel, Value.findValue(itemId));
        return "values/update";
    }
    
    @RequestMapping(value = "/{itemId}", method = RequestMethod.DELETE, produces = "text/html")
    public String ValueController.delete(@PathVariable("itemId") Integer itemId, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Value value = Value.findValue(itemId);
        value.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/values";
    }
    
    void ValueController.populateEditForm(Model uiModel, Value value) {
        uiModel.addAttribute("value", value);
        uiModel.addAttribute("items", Item.findAllItems());
        uiModel.addAttribute("scales", Scale.findAllScales());
    }
    
    String ValueController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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