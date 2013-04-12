package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.Study;

@RequestMapping("/studys")
@Controller
@RooWebScaffold(path = "studys", formBackingObject = Study.class)
@RooWebJson(jsonObject = Study.class)
public class StudyController {
}
