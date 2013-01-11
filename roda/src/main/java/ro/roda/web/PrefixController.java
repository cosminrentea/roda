package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.Prefix;

@RequestMapping("/prefixes")
@Controller
@RooWebScaffold(path = "prefixes", formBackingObject = Prefix.class)
public class PrefixController {
}
