package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.Methodology;

@RequestMapping("/methodologys")
@Controller
@RooWebScaffold(path = "methodologys", formBackingObject = Methodology.class)
public class MethodologyController {
}
