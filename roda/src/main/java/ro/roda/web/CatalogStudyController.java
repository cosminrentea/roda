package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.CatalogStudy;

@RequestMapping("/catalogstudys")
@Controller
@RooWebScaffold(path = "catalogstudys", formBackingObject = CatalogStudy.class)
@RooWebJson(jsonObject = CatalogStudy.class)
public class CatalogStudyController {
}
