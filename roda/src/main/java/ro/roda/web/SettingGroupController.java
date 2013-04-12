package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.SettingGroup;

@RequestMapping("/settinggroups")
@Controller
@RooWebScaffold(path = "settinggroups", formBackingObject = SettingGroup.class)
@RooWebJson(jsonObject = SettingGroup.class)
public class SettingGroupController {
}
