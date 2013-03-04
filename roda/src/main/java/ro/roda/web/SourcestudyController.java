package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.Sourcestudy;

@RequestMapping("/sourcestudys")
@Controller
@RooWebScaffold(path = "sourcestudys", formBackingObject = Sourcestudy.class)
public class SourcestudyController {
}
