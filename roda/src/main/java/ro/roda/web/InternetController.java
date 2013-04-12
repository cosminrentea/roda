package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.Internet;

@RequestMapping("/internets")
@Controller
@RooWebScaffold(path = "internets", formBackingObject = Internet.class)
@RooWebJson(jsonObject = Internet.class)
public class InternetController {
}
