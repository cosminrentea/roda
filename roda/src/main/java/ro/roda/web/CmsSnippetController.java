package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.CmsSnippet;

@RequestMapping("/cmssnippets")
@Controller
@RooWebScaffold(path = "cmssnippets", formBackingObject = CmsSnippet.class)
public class CmsSnippetController {
}
