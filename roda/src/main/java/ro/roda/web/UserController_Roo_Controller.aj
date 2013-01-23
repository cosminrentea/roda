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
import ro.roda.Audit;
import ro.roda.AuthData;
import ro.roda.Catalog;
import ro.roda.CatalogStudy;
import ro.roda.CmsPage;
import ro.roda.News;
import ro.roda.PersonLinks;
import ro.roda.Role;
import ro.roda.User;
import ro.roda.UserAuthLog;
import ro.roda.UserMessage;
import ro.roda.UserProfile;
import ro.roda.UserSettingValue;
import ro.roda.web.UserController;

privileged aspect UserController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String UserController.create(@Valid User user, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, user);
            return "users/create";
        }
        uiModel.asMap().clear();
        user.persist();
        return "redirect:/users/" + encodeUrlPathSegment(user.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String UserController.createForm(Model uiModel) {
        populateEditForm(uiModel, new User());
        return "users/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String UserController.show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("user", User.findUser(id));
        uiModel.addAttribute("itemId", id);
        return "users/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String UserController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("users", User.findUserEntries(firstResult, sizeNo));
            float nrOfPages = (float) User.countUsers() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("users", User.findAllUsers());
        }
        return "users/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String UserController.update(@Valid User user, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, user);
            return "users/update";
        }
        uiModel.asMap().clear();
        user.merge();
        return "redirect:/users/" + encodeUrlPathSegment(user.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String UserController.updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, User.findUser(id));
        return "users/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String UserController.delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        User user = User.findUser(id);
        user.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/users";
    }
    
    void UserController.populateEditForm(Model uiModel, User user) {
        uiModel.addAttribute("user", user);
        uiModel.addAttribute("audits", Audit.findAllAudits());
        uiModel.addAttribute("authdatas", AuthData.findAllAuthDatas());
        uiModel.addAttribute("catalogs", Catalog.findAllCatalogs());
        uiModel.addAttribute("catalogstudys", CatalogStudy.findAllCatalogStudys());
        uiModel.addAttribute("cmspages", CmsPage.findAllCmsPages());
        uiModel.addAttribute("newsitems", News.findAllNews());
        uiModel.addAttribute("personlinkses", PersonLinks.findAllPersonLinkses());
        uiModel.addAttribute("roles", Role.findAllRoles());
        uiModel.addAttribute("userauthlogs", UserAuthLog.findAllUserAuthLogs());
        uiModel.addAttribute("usermessages", UserMessage.findAllUserMessages());
        uiModel.addAttribute("userprofiles", UserProfile.findAllUserProfiles());
        uiModel.addAttribute("usersettingvalues", UserSettingValue.findAllUserSettingValues());
    }
    
    String UserController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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