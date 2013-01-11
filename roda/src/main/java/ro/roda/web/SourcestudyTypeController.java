package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.SourcestudyType;

@RequestMapping("/sourcestudytypes")
@Controller
@RooWebScaffold(path = "sourcestudytypes", formBackingObject = SourcestudyType.class)
public class SourcestudyTypeController {
}
