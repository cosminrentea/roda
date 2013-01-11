package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.UserSetting;

@RequestMapping("/usersettings")
@Controller
@RooWebScaffold(path = "usersettings", formBackingObject = UserSetting.class)
public class UserSettingController {
}
