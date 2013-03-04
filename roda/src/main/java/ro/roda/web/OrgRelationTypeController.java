package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.OrgRelationType;

@RequestMapping("/orgrelationtypes")
@Controller
@RooWebScaffold(path = "orgrelationtypes", formBackingObject = OrgRelationType.class)
public class OrgRelationTypeController {
}
