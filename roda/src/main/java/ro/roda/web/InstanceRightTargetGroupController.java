package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.InstanceRightTargetGroup;

@RequestMapping("/instancerighttargetgroups")
@Controller
@RooWebScaffold(path = "instancerighttargetgroups", formBackingObject = InstanceRightTargetGroup.class)
@RooWebJson(jsonObject = InstanceRightTargetGroup.class)
public class InstanceRightTargetGroupController {
}
