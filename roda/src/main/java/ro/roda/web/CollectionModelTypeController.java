package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.CollectionModelType;

@RequestMapping("/collectionmodeltypes")
@Controller
@RooWebScaffold(path = "collectionmodeltypes", formBackingObject = CollectionModelType.class)
public class CollectionModelTypeController {
}
