package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.StudyOrg;

@RequestMapping("/studyorgs")
@Controller
@RooWebScaffold(path = "studyorgs", formBackingObject = StudyOrg.class)
@RooWebJson(jsonObject = StudyOrg.class)
public class StudyOrgController {
}
