package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.CmsFolder;

@RequestMapping("/cmsfolders")
@Controller
@RooWebScaffold(path = "cmsfolders", formBackingObject = CmsFolder.class)
@RooWebJson(jsonObject = CmsFolder.class)
public class CmsFolderController {
}
