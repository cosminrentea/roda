package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.Sourcetype;

@RequestMapping("/sourcetypes")
@Controller
@RooWebScaffold(path = "sourcetypes", formBackingObject = Sourcetype.class)
@RooWebJson(jsonObject = Sourcetype.class)
public class SourcetypeController {
}
