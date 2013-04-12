package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.UnitAnalysis;

@RequestMapping("/unitanalyses")
@Controller
@RooWebScaffold(path = "unitanalyses", formBackingObject = UnitAnalysis.class)
@RooWebJson(jsonObject = UnitAnalysis.class)
public class UnitAnalysisController {
}
