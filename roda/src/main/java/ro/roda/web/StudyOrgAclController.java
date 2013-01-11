package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.StudyOrgAcl;

@RequestMapping("/studyorgacls")
@Controller
@RooWebScaffold(path = "studyorgacls", formBackingObject = StudyOrgAcl.class)
public class StudyOrgAclController {
}
