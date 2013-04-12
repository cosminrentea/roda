package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.PersonPhone;

@RequestMapping("/personphones")
@Controller
@RooWebScaffold(path = "personphones", formBackingObject = PersonPhone.class)
@RooWebJson(jsonObject = PersonPhone.class)
public class PersonPhoneController {
}
