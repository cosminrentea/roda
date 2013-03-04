package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.AuditLogAction;

@RequestMapping("/auditlogactions")
@Controller
@RooWebScaffold(path = "auditlogactions", formBackingObject = AuditLogAction.class)
public class AuditLogActionController {
}
