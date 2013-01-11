package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.TitleType;

@RequestMapping("/titletypes")
@Controller
@RooWebScaffold(path = "titletypes", formBackingObject = TitleType.class)
public class TitleTypeController {
}
