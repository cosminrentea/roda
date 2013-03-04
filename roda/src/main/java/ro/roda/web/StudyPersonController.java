package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.StudyPerson;

@RequestMapping("/studypeople")
@Controller
@RooWebScaffold(path = "studypeople", formBackingObject = StudyPerson.class)
public class StudyPersonController {
}
