package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.FilePropertyNameValue;

@RequestMapping("/filepropertynamevalues")
@Controller
@RooWebScaffold(path = "filepropertynamevalues", formBackingObject = FilePropertyNameValue.class)
public class FilePropertyNameValueController {
}
