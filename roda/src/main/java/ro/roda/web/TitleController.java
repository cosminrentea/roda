package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.Title;

@RequestMapping("/titles")
@Controller
@RooWebScaffold(path = "titles", formBackingObject = Title.class)
public class TitleController {
}
