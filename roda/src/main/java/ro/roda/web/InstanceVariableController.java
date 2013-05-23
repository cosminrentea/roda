package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.InstanceVariable;

@RequestMapping("/instancevariables")
@Controller
@RooWebScaffold(path = "instancevariables", formBackingObject = InstanceVariable.class)
@RooWebJson(jsonObject = InstanceVariable.class)
public class InstanceVariableController {
}
