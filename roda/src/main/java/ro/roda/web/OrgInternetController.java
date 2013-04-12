package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.OrgInternet;

@RequestMapping("/orginternets")
@Controller
@RooWebScaffold(path = "orginternets", formBackingObject = OrgInternet.class)
@RooWebJson(jsonObject = OrgInternet.class)
public class OrgInternetController {
}
