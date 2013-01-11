package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.CatalogAcl;

@RequestMapping("/catalogacls")
@Controller
@RooWebScaffold(path = "catalogacls", formBackingObject = CatalogAcl.class)
public class CatalogAclController {
}
