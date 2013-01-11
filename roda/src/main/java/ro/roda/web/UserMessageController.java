package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.UserMessage;

@RequestMapping("/usermessages")
@Controller
@RooWebScaffold(path = "usermessages", formBackingObject = UserMessage.class)
public class UserMessageController {
}
