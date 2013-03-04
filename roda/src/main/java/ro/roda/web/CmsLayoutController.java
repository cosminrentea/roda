package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.CmsLayout;

@RequestMapping("/cmslayouts")
@Controller
@RooWebScaffold(path = "cmslayouts", formBackingObject = CmsLayout.class)
public class CmsLayoutController {
}
