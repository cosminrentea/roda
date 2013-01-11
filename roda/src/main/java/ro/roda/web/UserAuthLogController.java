package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.UserAuthLog;

@RequestMapping("/userauthlogs")
@Controller
@RooWebScaffold(path = "userauthlogs", formBackingObject = UserAuthLog.class)
public class UserAuthLogController {
}
