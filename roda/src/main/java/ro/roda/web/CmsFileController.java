package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.CmsFile;

@RequestMapping("/cmsfiles")
@Controller
@RooWebScaffold(path = "cmsfiles", formBackingObject = CmsFile.class)
public class CmsFileController {
}
