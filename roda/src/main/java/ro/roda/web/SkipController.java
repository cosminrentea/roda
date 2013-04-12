package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.Skip;

@RequestMapping("/skips")
@Controller
@RooWebScaffold(path = "skips", formBackingObject = Skip.class)
@RooWebJson(jsonObject = Skip.class)
public class SkipController {
}
