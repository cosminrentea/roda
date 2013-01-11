package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.Region;

@RequestMapping("/regions")
@Controller
@RooWebScaffold(path = "regions", formBackingObject = Region.class)
public class RegionController {
}
