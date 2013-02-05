package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.SourceContactMethod;

@RequestMapping("/sourcecontactmethods")
@Controller
@RooWebScaffold(path = "sourcecontactmethods", formBackingObject = SourceContactMethod.class)
public class SourceContactMethodController {
}
