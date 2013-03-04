package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.Suffix;

@RequestMapping("/suffixes")
@Controller
@RooWebScaffold(path = "suffixes", formBackingObject = Suffix.class)
public class SuffixController {
}
