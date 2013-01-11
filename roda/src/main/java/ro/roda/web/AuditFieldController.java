package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.AuditField;

@RequestMapping("/auditfields")
@Controller
@RooWebScaffold(path = "auditfields", formBackingObject = AuditField.class)
public class AuditFieldController {
}
