package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.StudyOrgAssoc;

@RequestMapping("/studyorgassocs")
@Controller
@RooWebScaffold(path = "studyorgassocs", formBackingObject = StudyOrgAssoc.class)
@RooWebJson(jsonObject = StudyOrgAssoc.class)
public class StudyOrgAssocController {
}
