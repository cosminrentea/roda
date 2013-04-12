package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.Instance;

@RequestMapping("/instances")
@Controller
@RooWebScaffold(path = "instances", formBackingObject = Instance.class)
@RooWebJson(jsonObject = Instance.class)
public class InstanceController {
}
