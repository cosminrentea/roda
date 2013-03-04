package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.Address;

@RequestMapping("/addresses")
@Controller
@RooWebScaffold(path = "addresses", formBackingObject = Address.class)
public class AddressController {
}
