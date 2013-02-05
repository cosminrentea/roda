package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.Lang;

@RequestMapping("/langs")
@Controller
@RooWebScaffold(path = "langs", formBackingObject = Lang.class)
public class LangController {
}
