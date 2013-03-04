package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.AuditLogChangeset;

@RequestMapping("/auditlogchangesets")
@Controller
@RooWebScaffold(path = "auditlogchangesets", formBackingObject = AuditLogChangeset.class)
public class AuditLogChangesetController {
}
