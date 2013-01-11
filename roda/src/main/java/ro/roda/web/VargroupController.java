package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.Vargroup;

@RequestMapping("/vargroups")
@Controller
@RooWebScaffold(path = "vargroups", formBackingObject = Vargroup.class)
public class VargroupController {
}
