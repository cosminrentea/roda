package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.Language;

@RequestMapping("/languages")
@Controller
@RooWebScaffold(path = "languages", formBackingObject = Language.class)
public class LanguageController {
}
