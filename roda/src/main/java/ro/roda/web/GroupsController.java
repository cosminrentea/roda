package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.Groups;

@RequestMapping("/groupses")
@Controller
@RooWebScaffold(path = "groupses", formBackingObject = Groups.class)
@RooWebJson(jsonObject = Groups.class)
public class GroupsController {
}
