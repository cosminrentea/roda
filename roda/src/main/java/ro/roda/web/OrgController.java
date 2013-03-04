package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.Org;

@RequestMapping("/orgs")
@Controller
@RooWebScaffold(path = "orgs", formBackingObject = Org.class)
public class OrgController {
}
