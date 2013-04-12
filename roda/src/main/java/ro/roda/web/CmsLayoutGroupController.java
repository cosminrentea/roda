package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.CmsLayoutGroup;

@RequestMapping("/cmslayoutgroups")
@Controller
@RooWebScaffold(path = "cmslayoutgroups", formBackingObject = CmsLayoutGroup.class)
@RooWebJson(jsonObject = CmsLayoutGroup.class)
public class CmsLayoutGroupController {
}
