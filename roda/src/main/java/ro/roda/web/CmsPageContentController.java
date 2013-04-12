package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.CmsPageContent;

@RequestMapping("/cmspagecontents")
@Controller
@RooWebScaffold(path = "cmspagecontents", formBackingObject = CmsPageContent.class)
@RooWebJson(jsonObject = CmsPageContent.class)
public class CmsPageContentController {
}
