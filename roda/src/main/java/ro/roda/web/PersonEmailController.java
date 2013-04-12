package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.PersonEmail;

@RequestMapping("/personemails")
@Controller
@RooWebScaffold(path = "personemails", formBackingObject = PersonEmail.class)
@RooWebJson(jsonObject = PersonEmail.class)
public class PersonEmailController {
}
