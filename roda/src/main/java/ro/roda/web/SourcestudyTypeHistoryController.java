package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.SourcestudyTypeHistory;

@RequestMapping("/sourcestudytypehistorys")
@Controller
@RooWebScaffold(path = "sourcestudytypehistorys", formBackingObject = SourcestudyTypeHistory.class)
@RooWebJson(jsonObject = SourcestudyTypeHistory.class)
public class SourcestudyTypeHistoryController {
}
