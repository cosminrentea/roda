package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.StudyPersonAcl;

@RequestMapping("/studypersonacls")
@Controller
@RooWebScaffold(path = "studypersonacls", formBackingObject = StudyPersonAcl.class)
public class StudyPersonAclController {
}
