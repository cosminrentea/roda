package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.StudyOrgAssoc;

@RequestMapping("/studyorgassocs")
@Controller
@RooWebScaffold(path = "studyorgassocs", formBackingObject = StudyOrgAssoc.class)
public class StudyOrgAssocController {
}
