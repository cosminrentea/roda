package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.SelectionVariableItem;

@RequestMapping("/selectionvariableitems")
@Controller
@RooWebScaffold(path = "selectionvariableitems", formBackingObject = SelectionVariableItem.class)
@RooWebJson(jsonObject = SelectionVariableItem.class)
public class SelectionVariableItemController {
}
