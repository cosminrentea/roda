package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.AuditLogChange;

@RequestMapping("/auditlogchanges")
@Controller
@RooWebScaffold(path = "auditlogchanges", formBackingObject = AuditLogChange.class)
@RooWebJson(jsonObject = AuditLogChange.class)
public class AuditLogChangeController {
}
