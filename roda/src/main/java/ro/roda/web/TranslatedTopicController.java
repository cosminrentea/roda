package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.TranslatedTopic;

@RequestMapping("/translatedtopics")
@Controller
@RooWebScaffold(path = "translatedtopics", formBackingObject = TranslatedTopic.class)
@RooWebJson(jsonObject = TranslatedTopic.class)
public class TranslatedTopicController {
}
