package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.StudyPersonAssoc;

@RequestMapping("/studypersonassocs")
@Controller
@RooWebScaffold(path = "studypersonassocs", formBackingObject = StudyPersonAssoc.class)
public class StudyPersonAssocController {
}
