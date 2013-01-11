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
import ro.roda.UserSetting;
import ro.roda.UserSettingGroup;
import ro.roda.web.UserSettingGroupController;

privileged aspect UserSettingGroupController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String UserSettingGroupController.create(@Valid UserSettingGroup userSettingGroup, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, userSettingGroup);
            return "usersettinggroups/create";
        }
        uiModel.asMap().clear();
        userSettingGroup.persist();
        return "redirect:/usersettinggroups/" + encodeUrlPathSegment(userSettingGroup.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String UserSettingGroupController.createForm(Model uiModel) {
        populateEditForm(uiModel, new UserSettingGroup());
        return "usersettinggroups/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String UserSettingGroupController.show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("usersettinggroup", UserSettingGroup.findUserSettingGroup(id));
        uiModel.addAttribute("itemId", id);
        return "usersettinggroups/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String UserSettingGroupController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("usersettinggroups", UserSettingGroup.findUserSettingGroupEntries(firstResult, sizeNo));
            float nrOfPages = (float) UserSettingGroup.countUserSettingGroups() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("usersettinggroups", UserSettingGroup.findAllUserSettingGroups());
        }
        return "usersettinggroups/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String UserSettingGroupController.update(@Valid UserSettingGroup userSettingGroup, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, userSettingGroup);
            return "usersettinggroups/update";
        }
        uiModel.asMap().clear();
        userSettingGroup.merge();
        return "redirect:/usersettinggroups/" + encodeUrlPathSegment(userSettingGroup.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String UserSettingGroupController.updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, UserSettingGroup.findUserSettingGroup(id));
        return "usersettinggroups/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String UserSettingGroupController.delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        UserSettingGroup userSettingGroup = UserSettingGroup.findUserSettingGroup(id);
        userSettingGroup.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/usersettinggroups";
    }
    
    void UserSettingGroupController.populateEditForm(Model uiModel, UserSettingGroup userSettingGroup) {
        uiModel.addAttribute("userSettingGroup", userSettingGroup);
        uiModel.addAttribute("usersettings", UserSetting.findAllUserSettings());
    }
    
    String UserSettingGroupController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
