package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.SourcestudyType;

@RequestMapping("/sourcestudytypes")
@Controller
@RooWebScaffold(path = "sourcestudytypes", formBackingObject = SourcestudyType.class)
@RooWebJson(jsonObject = SourcestudyType.class)
public class SourcestudyTypeController {
}
