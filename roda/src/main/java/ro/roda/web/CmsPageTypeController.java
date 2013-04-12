package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.CmsPageType;

@RequestMapping("/cmspagetypes")
@Controller
@RooWebScaffold(path = "cmspagetypes", formBackingObject = CmsPageType.class)
@RooWebJson(jsonObject = CmsPageType.class)
public class CmsPageTypeController {
}
