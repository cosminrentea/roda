package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.InstanceForm;

@RequestMapping("/instanceforms")
@Controller
@RooWebScaffold(path = "instanceforms", formBackingObject = InstanceForm.class)
@RooWebJson(jsonObject = InstanceForm.class)
public class InstanceFormController {
}
