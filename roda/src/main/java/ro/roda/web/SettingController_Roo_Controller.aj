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
import ro.roda.Setting;
import ro.roda.SettingGroup;
import ro.roda.SettingValue;
import ro.roda.web.SettingController;

privileged aspect SettingController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String SettingController.create(@Valid Setting setting, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, setting);
            return "settings/create";
        }
        uiModel.asMap().clear();
        setting.persist();
        return "redirect:/settings/" + encodeUrlPathSegment(setting.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String SettingController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Setting());
        return "settings/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String SettingController.show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("setting", Setting.findSetting(id));
        uiModel.addAttribute("itemId", id);
        return "settings/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String SettingController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("settings", Setting.findSettingEntries(firstResult, sizeNo));
            float nrOfPages = (float) Setting.countSettings() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("settings", Setting.findAllSettings());
        }
        return "settings/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String SettingController.update(@Valid Setting setting, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, setting);
            return "settings/update";
        }
        uiModel.asMap().clear();
        setting.merge();
        return "redirect:/settings/" + encodeUrlPathSegment(setting.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String SettingController.updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, Setting.findSetting(id));
        return "settings/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String SettingController.delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Setting setting = Setting.findSetting(id);
        setting.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/settings";
    }
    
    void SettingController.populateEditForm(Model uiModel, Setting setting) {
        uiModel.addAttribute("setting", setting);
        uiModel.addAttribute("settinggroups", SettingGroup.findAllSettingGroups());
        uiModel.addAttribute("settingvalues", SettingValue.findAllSettingValues());
    }
    
    String SettingController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
