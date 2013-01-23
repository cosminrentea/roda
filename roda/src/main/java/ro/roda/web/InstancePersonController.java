package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.InstancePerson;

@RequestMapping("/instancepeople")
@Controller
@RooWebScaffold(path = "instancepeople", formBackingObject = InstancePerson.class)
public class InstancePersonController {
}