package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.SamplingProcedure;

@RequestMapping("/samplingprocedures")
@Controller
@RooWebScaffold(path = "samplingprocedures", formBackingObject = SamplingProcedure.class)
@RooWebJson(jsonObject = SamplingProcedure.class)
public class SamplingProcedureController {
}
