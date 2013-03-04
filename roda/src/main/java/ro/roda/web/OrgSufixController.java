package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.OrgSufix;

@RequestMapping("/orgsufixes")
@Controller
@RooWebScaffold(path = "orgsufixes", formBackingObject = OrgSufix.class)
public class OrgSufixController {
}
