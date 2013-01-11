package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.PersonRole;

@RequestMapping("/personroles")
@Controller
@RooWebScaffold(path = "personroles", formBackingObject = PersonRole.class)
public class PersonRoleController {
}
