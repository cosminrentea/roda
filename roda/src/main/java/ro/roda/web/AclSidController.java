package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.AclSid;

@RequestMapping("/aclsids")
@Controller
@RooWebScaffold(path = "aclsids", formBackingObject = AclSid.class)
public class AclSidController {
}
