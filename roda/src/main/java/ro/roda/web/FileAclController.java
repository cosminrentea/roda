package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.FileAcl;

@RequestMapping("/fileacls")
@Controller
@RooWebScaffold(path = "fileacls", formBackingObject = FileAcl.class)
public class FileAclController {
}
