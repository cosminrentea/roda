package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.Scale;

@RequestMapping("/scales")
@Controller
@RooWebScaffold(path = "scales", formBackingObject = Scale.class)
public class ScaleController {
}
