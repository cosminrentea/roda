package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.Phone;

@RequestMapping("/phones")
@Controller
@RooWebScaffold(path = "phones", formBackingObject = Phone.class)
public class PhoneController {
}
