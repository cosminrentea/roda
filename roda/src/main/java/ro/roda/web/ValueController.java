package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.Value;

@RequestMapping("/values")
@Controller
@RooWebScaffold(path = "values", formBackingObject = Value.class)
public class ValueController {
}
