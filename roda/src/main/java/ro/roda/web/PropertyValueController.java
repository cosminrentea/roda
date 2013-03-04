package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.PropertyValue;

@RequestMapping("/propertyvalues")
@Controller
@RooWebScaffold(path = "propertyvalues", formBackingObject = PropertyValue.class)
public class PropertyValueController {
}
