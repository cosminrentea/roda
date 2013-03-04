package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.Form;

@RequestMapping("/forms")
@Controller
@RooWebScaffold(path = "forms", formBackingObject = Form.class)
public class FormController {
}
