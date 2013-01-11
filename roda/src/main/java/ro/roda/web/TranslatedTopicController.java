package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.TranslatedTopic;

@RequestMapping("/translatedtopics")
@Controller
@RooWebScaffold(path = "translatedtopics", formBackingObject = TranslatedTopic.class)
public class TranslatedTopicController {
}
