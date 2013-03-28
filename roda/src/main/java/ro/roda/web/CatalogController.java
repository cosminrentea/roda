package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ro.roda.domain.Catalog;

@RequestMapping("/catalogs")
@Controller
@RooWebScaffold(path = "catalogs", formBackingObject = Catalog.class)
public class CatalogController {
}
