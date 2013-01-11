package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.StudyDescr;

@RequestMapping("/studydescrs")
@Controller
@RooWebScaffold(path = "studydescrs", formBackingObject = StudyDescr.class)
public class StudyDescrController {
}
