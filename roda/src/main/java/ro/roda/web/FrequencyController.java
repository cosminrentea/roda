package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.Frequency;

@RequestMapping("/frequencys")
@Controller
@RooWebScaffold(path = "frequencys", formBackingObject = Frequency.class)
public class FrequencyController {
}
