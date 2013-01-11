package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.Filetype;

@RequestMapping("/filetypes")
@Controller
@RooWebScaffold(path = "filetypes", formBackingObject = Filetype.class)
public class FiletypeController {
}
