package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.InstanceDescr;

@RequestMapping("/instancedescrs")
@Controller
@RooWebScaffold(path = "instancedescrs", formBackingObject = InstanceDescr.class)
public class InstanceDescrController {
}
