package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.PersonInternet;

@RequestMapping("/personinternets")
@Controller
@RooWebScaffold(path = "personinternets", formBackingObject = PersonInternet.class)
public class PersonInternetController {
}
