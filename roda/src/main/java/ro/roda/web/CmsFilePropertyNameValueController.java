package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.CmsFilePropertyNameValue;

@RequestMapping("/cmsfilepropertynamevalues")
@Controller
@RooWebScaffold(path = "cmsfilepropertynamevalues", formBackingObject = CmsFilePropertyNameValue.class)
public class CmsFilePropertyNameValueController {
}
