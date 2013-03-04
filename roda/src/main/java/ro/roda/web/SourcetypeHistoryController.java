package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.SourcetypeHistory;

@RequestMapping("/sourcetypehistorys")
@Controller
@RooWebScaffold(path = "sourcetypehistorys", formBackingObject = SourcetypeHistory.class)
public class SourcetypeHistoryController {
}
