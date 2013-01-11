package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.SettingGroup;

@RequestMapping("/settinggroups")
@Controller
@RooWebScaffold(path = "settinggroups", formBackingObject = SettingGroup.class)
public class SettingGroupController {
}
