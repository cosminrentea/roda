package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.Concept;

@RequestMapping("/concepts")
@Controller
@RooWebScaffold(path = "concepts", formBackingObject = Concept.class)
@RooWebJson(jsonObject = Concept.class)
public class ConceptController {
}
