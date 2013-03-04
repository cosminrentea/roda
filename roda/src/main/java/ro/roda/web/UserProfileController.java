package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.UserProfile;

@RequestMapping("/userprofiles")
@Controller
@RooWebScaffold(path = "userprofiles", formBackingObject = UserProfile.class)
public class UserProfileController {
}
