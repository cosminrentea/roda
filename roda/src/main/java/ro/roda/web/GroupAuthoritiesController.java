package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.GroupAuthorities;

@RequestMapping("/groupauthoritieses")
@Controller
@RooWebScaffold(path = "groupauthoritieses", formBackingObject = GroupAuthorities.class)
public class GroupAuthoritiesController {
}
