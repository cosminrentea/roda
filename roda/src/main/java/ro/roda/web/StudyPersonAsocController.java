package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.StudyPersonAsoc;

@RequestMapping("/studypersonasocs")
@Controller
@RooWebScaffold(path = "studypersonasocs", formBackingObject = StudyPersonAsoc.class)
public class StudyPersonAsocController {
}
