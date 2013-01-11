package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.PersonOrg;

@RequestMapping("/personorgs")
@Controller
@RooWebScaffold(path = "personorgs", formBackingObject = PersonOrg.class)
public class PersonOrgController {
}
