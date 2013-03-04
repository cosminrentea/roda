package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.SettingValue;

@RequestMapping("/settingvalues")
@Controller
@RooWebScaffold(path = "settingvalues", formBackingObject = SettingValue.class)
public class SettingValueController {
}
