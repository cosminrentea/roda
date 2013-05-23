package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.Series;

@RequestMapping("/serieses")
@Controller
@RooWebScaffold(path = "serieses", formBackingObject = Series.class)
@RooWebJson(jsonObject = Series.class)
public class SeriesController {
}
