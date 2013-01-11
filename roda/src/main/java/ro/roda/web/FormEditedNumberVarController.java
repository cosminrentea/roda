package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.FormEditedNumberVar;

@RequestMapping("/formeditednumbervars")
@Controller
@RooWebScaffold(path = "formeditednumbervars", formBackingObject = FormEditedNumberVar.class)
public class FormEditedNumberVarController {
}
