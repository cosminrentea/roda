package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.CmsPage;

@RequestMapping("/cmspages")
@Controller
@RooWebScaffold(path = "cmspages", formBackingObject = CmsPage.class)
@RooWebJson(jsonObject = CmsPage.class)
public class CmsPageController {
}
