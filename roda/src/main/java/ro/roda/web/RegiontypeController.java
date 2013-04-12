package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.Regiontype;

@RequestMapping("/regiontypes")
@Controller
@RooWebScaffold(path = "regiontypes", formBackingObject = Regiontype.class)
@RooWebJson(jsonObject = Regiontype.class)
public class RegiontypeController {
}
