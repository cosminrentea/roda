package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.OrgRelations;

@RequestMapping("/orgrelationses")
@Controller
@RooWebScaffold(path = "orgrelationses", formBackingObject = OrgRelations.class)
@RooWebJson(jsonObject = OrgRelations.class)
public class OrgRelationsController {
}
