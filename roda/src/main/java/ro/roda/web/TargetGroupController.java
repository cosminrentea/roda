package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.TargetGroup;

@RequestMapping("/targetgroups")
@Controller
@RooWebScaffold(path = "targetgroups", formBackingObject = TargetGroup.class)
@RooWebJson(jsonObject = TargetGroup.class)
public class TargetGroupController {
}
