package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.StudyDocuments;

@RequestMapping("/studydocumentses")
@Controller
@RooWebScaffold(path = "studydocumentses", formBackingObject = StudyDocuments.class)
public class StudyDocumentsController {
}
