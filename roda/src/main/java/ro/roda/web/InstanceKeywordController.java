package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.InstanceKeyword;

@RequestMapping("/instancekeywords")
@Controller
@RooWebScaffold(path = "instancekeywords", formBackingObject = InstanceKeyword.class)
public class InstanceKeywordController {
}