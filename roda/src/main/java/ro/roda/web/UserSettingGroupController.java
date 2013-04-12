package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.UserSettingGroup;

@RequestMapping("/usersettinggroups")
@Controller
@RooWebScaffold(path = "usersettinggroups", formBackingObject = UserSettingGroup.class)
@RooWebJson(jsonObject = UserSettingGroup.class)
public class UserSettingGroupController {
}
