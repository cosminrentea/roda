package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.UnitAnalysis;

@RequestMapping("/unitanalyses")
@Controller
@RooWebScaffold(path = "unitanalyses", formBackingObject = UnitAnalysis.class)
public class UnitAnalysisController {
}
