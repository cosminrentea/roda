package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.OrgAddress;

@RequestMapping("/orgaddresses")
@Controller
@RooWebScaffold(path = "orgaddresses", formBackingObject = OrgAddress.class)
@RooWebJson(jsonObject = OrgAddress.class)
public class OrgAddressController {
}
