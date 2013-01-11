package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.Variable;

@RequestMapping("/variables")
@Controller
@RooWebScaffold(path = "variables", formBackingObject = Variable.class)
public class VariableController {
}
