package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.StudyKeyword;

@RequestMapping("/studykeywords")
@Controller
@RooWebScaffold(path = "studykeywords", formBackingObject = StudyKeyword.class)
public class StudyKeywordController {
}
