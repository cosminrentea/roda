package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.OrgPrefix;

@RequestMapping("/orgprefixes")
@Controller
@RooWebScaffold(path = "orgprefixes", formBackingObject = OrgPrefix.class)
@RooWebJson(jsonObject = OrgPrefix.class)
public class OrgPrefixController {
}
