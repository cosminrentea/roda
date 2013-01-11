package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.Email;

@RequestMapping("/emails")
@Controller
@RooWebScaffold(path = "emails", formBackingObject = Email.class)
public class EmailController {
}
