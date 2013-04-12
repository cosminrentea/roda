package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.InstanceOrgAssoc;

@RequestMapping("/instanceorgassocs")
@Controller
@RooWebScaffold(path = "instanceorgassocs", formBackingObject = InstanceOrgAssoc.class)
@RooWebJson(jsonObject = InstanceOrgAssoc.class)
public class InstanceOrgAssocController {
}
