package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.PropertyName;

@RequestMapping("/propertynames")
@Controller
@RooWebScaffold(path = "propertynames", formBackingObject = PropertyName.class)
@RooWebJson(jsonObject = PropertyName.class)
public class PropertyNameController {
}
