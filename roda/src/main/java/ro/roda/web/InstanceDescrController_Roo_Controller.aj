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
import ro.roda.Instance;
import ro.roda.InstanceDescr;
import ro.roda.web.InstanceDescrController;

privileged aspect InstanceDescrController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String InstanceDescrController.create(@Valid InstanceDescr instanceDescr, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, instanceDescr);
            return "instancedescrs/create";
        }
        uiModel.asMap().clear();
        instanceDescr.persist();
        return "redirect:/instancedescrs/" + encodeUrlPathSegment(instanceDescr.getInstanceId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String InstanceDescrController.createForm(Model uiModel) {
        populateEditForm(uiModel, new InstanceDescr());
        return "instancedescrs/create";
    }
    
    @RequestMapping(value = "/{instanceId}", produces = "text/html")
    public String InstanceDescrController.show(@PathVariable("instanceId") Integer instanceId, Model uiModel) {
        uiModel.addAttribute("instancedescr", InstanceDescr.findInstanceDescr(instanceId));
        uiModel.addAttribute("itemId", instanceId);
        return "instancedescrs/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String InstanceDescrController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("instancedescrs", InstanceDescr.findInstanceDescrEntries(firstResult, sizeNo));
            float nrOfPages = (float) InstanceDescr.countInstanceDescrs() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("instancedescrs", InstanceDescr.findAllInstanceDescrs());
        }
        return "instancedescrs/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String InstanceDescrController.update(@Valid InstanceDescr instanceDescr, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, instanceDescr);
            return "instancedescrs/update";
        }
        uiModel.asMap().clear();
        instanceDescr.merge();
        return "redirect:/instancedescrs/" + encodeUrlPathSegment(instanceDescr.getInstanceId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{instanceId}", params = "form", produces = "text/html")
    public String InstanceDescrController.updateForm(@PathVariable("instanceId") Integer instanceId, Model uiModel) {
        populateEditForm(uiModel, InstanceDescr.findInstanceDescr(instanceId));
        return "instancedescrs/update";
    }
    
    @RequestMapping(value = "/{instanceId}", method = RequestMethod.DELETE, produces = "text/html")
    public String InstanceDescrController.delete(@PathVariable("instanceId") Integer instanceId, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        InstanceDescr instanceDescr = InstanceDescr.findInstanceDescr(instanceId);
        instanceDescr.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/instancedescrs";
    }
    
    void InstanceDescrController.populateEditForm(Model uiModel, InstanceDescr instanceDescr) {
        uiModel.addAttribute("instanceDescr", instanceDescr);
        uiModel.addAttribute("instances", Instance.findAllInstances());
    }
    
    String InstanceDescrController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
