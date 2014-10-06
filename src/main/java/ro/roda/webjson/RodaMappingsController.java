package ro.roda.webjson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Controller
public class RodaMappingsController {
	private final RequestMappingHandlerMapping handlerMapping;

	@Autowired
	public RodaMappingsController(RequestMappingHandlerMapping handlerMapping) {
		this.handlerMapping = handlerMapping;
	}

	@RequestMapping(value = "/mappings", method = RequestMethod.GET, produces = "text/html")
	public String show(Model model) {
		model.addAttribute("handlerMethods", this.handlerMapping.getHandlerMethods());
		return "mappings";
	}
}