package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.InstancePersonAssoc;

@RequestMapping("/instancepersonassocs")
@Controller
@RooWebScaffold(path = "instancepersonassocs", formBackingObject = InstancePersonAssoc.class)
public class InstancePersonAssocController {
}
