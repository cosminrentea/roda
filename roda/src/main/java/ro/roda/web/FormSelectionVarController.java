package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.FormSelectionVar;

@RequestMapping("/formselectionvars")
@Controller
@RooWebScaffold(path = "formselectionvars", formBackingObject = FormSelectionVar.class)
public class FormSelectionVarController {
}
