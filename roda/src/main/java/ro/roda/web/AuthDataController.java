package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.AuthData;

@RequestMapping("/authdatas")
@Controller
@RooWebScaffold(path = "authdatas", formBackingObject = AuthData.class)
public class AuthDataController {
}
