package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.Keyword;

@RequestMapping("/keywords")
@Controller
@RooWebScaffold(path = "keywords", formBackingObject = Keyword.class)
public class KeywordController {
}
