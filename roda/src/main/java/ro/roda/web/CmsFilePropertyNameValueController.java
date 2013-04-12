package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.CmsFilePropertyNameValue;

@RequestMapping("/cmsfilepropertynamevalues")
@Controller
@RooWebScaffold(path = "cmsfilepropertynamevalues", formBackingObject = CmsFilePropertyNameValue.class)
@RooWebJson(jsonObject = CmsFilePropertyNameValue.class)
public class CmsFilePropertyNameValueController {
}
