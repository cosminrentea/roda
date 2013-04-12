package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.AclClass;

@RequestMapping("/aclclasses")
@Controller
@RooWebScaffold(path = "aclclasses", formBackingObject = AclClass.class)
@RooWebJson(jsonObject = AclClass.class)
public class AclClassController {
}
