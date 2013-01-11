package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.PersonAddress;

@RequestMapping("/personaddresses")
@Controller
@RooWebScaffold(path = "personaddresses", formBackingObject = PersonAddress.class)
public class PersonAddressController {
}
