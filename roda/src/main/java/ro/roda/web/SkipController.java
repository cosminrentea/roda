package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.Skip;

@RequestMapping("/skips")
@Controller
@RooWebScaffold(path = "skips", formBackingObject = Skip.class)
public class SkipController {
}
