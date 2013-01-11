package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.OtherStatistic;

@RequestMapping("/otherstatistics")
@Controller
@RooWebScaffold(path = "otherstatistics", formBackingObject = OtherStatistic.class)
public class OtherStatisticController {
}
