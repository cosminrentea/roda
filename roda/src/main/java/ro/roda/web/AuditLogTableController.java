package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.AuditLogTable;

@RequestMapping("/auditlogtables")
@Controller
@RooWebScaffold(path = "auditlogtables", formBackingObject = AuditLogTable.class)
@RooWebJson(jsonObject = AuditLogTable.class)
public class AuditLogTableController {
}
