package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.InstanceAcl;

@RequestMapping("/instanceacls")
@Controller
@RooWebScaffold(path = "instanceacls", formBackingObject = InstanceAcl.class)
public class InstanceAclController {
}
