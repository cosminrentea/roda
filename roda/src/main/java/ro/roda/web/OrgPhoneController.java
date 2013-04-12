package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.OrgPhone;

@RequestMapping("/orgphones")
@Controller
@RooWebScaffold(path = "orgphones", formBackingObject = OrgPhone.class)
@RooWebJson(jsonObject = OrgPhone.class)
public class OrgPhoneController {
}
