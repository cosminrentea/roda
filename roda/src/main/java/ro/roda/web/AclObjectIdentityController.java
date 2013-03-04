package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.AclObjectIdentity;

@RequestMapping("/aclobjectidentitys")
@Controller
@RooWebScaffold(path = "aclobjectidentitys", formBackingObject = AclObjectIdentity.class)
public class AclObjectIdentityController {
}
