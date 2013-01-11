package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.CmsPage;

@RequestMapping("/cmspages")
@Controller
@RooWebScaffold(path = "cmspages", formBackingObject = CmsPage.class)
public class CmsPageController {
}
