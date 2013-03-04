package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.StudyAcl;

@RequestMapping("/studyacls")
@Controller
@RooWebScaffold(path = "studyacls", formBackingObject = StudyAcl.class)
public class StudyAclController {
}
