package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.InstanceOrg;

@RequestMapping("/instanceorgs")
@Controller
@RooWebScaffold(path = "instanceorgs", formBackingObject = InstanceOrg.class)
@RooWebJson(jsonObject = InstanceOrg.class)
public class InstanceOrgController {
}
