package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.Setting;

@RequestMapping("/settings")
@Controller
@RooWebScaffold(path = "settings", formBackingObject = Setting.class)
public class SettingController {
}