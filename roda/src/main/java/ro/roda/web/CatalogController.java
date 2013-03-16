package ro.roda.web;

import org.apache.log4j.Logger;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ro.roda.domain.Catalog;


@RequestMapping("/catalogs")
@Controller
@RooWebScaffold(path = "catalogs", formBackingObject = Catalog.class)
public class CatalogController {
	
	protected static Logger logger = Logger.getLogger(CatalogController.class.getName());
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
		logger.error("Catalog add page");
        populateEditForm(uiModel, new Catalog());
        return "catalogs/create";
    }
}
