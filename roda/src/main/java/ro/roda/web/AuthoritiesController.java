package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.Authorities;

@RequestMapping("/authoritieses")
@Controller
@RooWebScaffold(path = "authoritieses", formBackingObject = Authorities.class)
public class AuthoritiesController {
}
