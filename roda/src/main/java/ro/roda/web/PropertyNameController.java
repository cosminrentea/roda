package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.PropertyName;

@RequestMapping("/propertynames")
@Controller
@RooWebScaffold(path = "propertynames", formBackingObject = PropertyName.class)
public class PropertyNameController {
}
