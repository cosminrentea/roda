package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.finder.RooWebFinder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.CmsFolder;

@RequestMapping("/cmsfolders")
@Controller
@RooWebScaffold(path = "cmsfolders", formBackingObject = CmsFolder.class)
@RooWebFinder
public class CmsFolderController {
}
