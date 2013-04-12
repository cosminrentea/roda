package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.SourceContacts;

@RequestMapping("/sourcecontactses")
@Controller
@RooWebScaffold(path = "sourcecontactses", formBackingObject = SourceContacts.class)
@RooWebJson(jsonObject = SourceContacts.class)
public class SourceContactsController {
}
