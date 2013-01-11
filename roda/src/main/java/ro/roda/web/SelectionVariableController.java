package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.SelectionVariable;

@RequestMapping("/selectionvariables")
@Controller
@RooWebScaffold(path = "selectionvariables", formBackingObject = SelectionVariable.class)
public class SelectionVariableController {
}
