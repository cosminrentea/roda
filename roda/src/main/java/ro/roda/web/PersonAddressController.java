package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.PersonAddress;

@RequestMapping("/personaddresses")
@Controller
@RooWebScaffold(path = "personaddresses", formBackingObject = PersonAddress.class)
@RooWebJson(jsonObject = PersonAddress.class)
public class PersonAddressController {
}
