package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.AuditLogField;

@RequestMapping("/auditlogfields")
@Controller
@RooWebScaffold(path = "auditlogfields", formBackingObject = AuditLogField.class)
@RooWebJson(jsonObject = AuditLogField.class)
public class AuditLogFieldController {
}
