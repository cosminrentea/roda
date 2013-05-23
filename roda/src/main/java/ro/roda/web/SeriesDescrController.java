package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.SeriesDescr;

@RequestMapping("/seriesdescrs")
@Controller
@RooWebScaffold(path = "seriesdescrs", formBackingObject = SeriesDescr.class)
@RooWebJson(jsonObject = SeriesDescr.class)
public class SeriesDescrController {
}
