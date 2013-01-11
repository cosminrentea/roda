package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.Source;

@RequestMapping("/sources")
@Controller
@RooWebScaffold(path = "sources", formBackingObject = Source.class)
public class SourceController {
}
