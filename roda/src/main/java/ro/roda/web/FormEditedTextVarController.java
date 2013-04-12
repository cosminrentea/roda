package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.FormEditedTextVar;

@RequestMapping("/formeditedtextvars")
@Controller
@RooWebScaffold(path = "formeditedtextvars", formBackingObject = FormEditedTextVar.class)
@RooWebJson(jsonObject = FormEditedTextVar.class)
public class FormEditedTextVarController {
}
