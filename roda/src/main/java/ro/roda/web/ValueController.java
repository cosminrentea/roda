package ro.roda.web;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import ro.roda.domain.Value;
import ro.roda.service.ItemService;
import ro.roda.service.ScaleService;
import ro.roda.service.ValueService;

@RequestMapping("/values")
@Controller


public class ValueController {

	@RequestMapping(value = "/{itemId}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("itemId") Long itemId) {
        Value value = valueService.findValue(itemId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (value == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(value.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Value> result = valueService.findAllValues();
        return new ResponseEntity<String>(Value.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        Value value = Value.fromJsonToValue(json);
        valueService.saveValue(value);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (Value value: Value.fromJsonArrayToValues(json)) {
            valueService.saveValue(value);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Value value = Value.fromJsonToValue(json);
        if (valueService.updateValue(value) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (Value value: Value.fromJsonArrayToValues(json)) {
            if (valueService.updateValue(value) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{itemId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("itemId") Long itemId) {
        Value value = valueService.findValue(itemId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (value == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        valueService.deleteValue(value);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@Autowired
    ValueService valueService;

	@Autowired
    ItemService itemService;

	@Autowired
    ScaleService scaleService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Value value, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, value);
            return "values/create";
        }
        uiModel.asMap().clear();
        valueService.saveValue(value);
        return "redirect:/values/" + encodeUrlPathSegment(value.getItemId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Value());
        return "values/create";
    }

	@RequestMapping(value = "/{itemId}", produces = "text/html")
    public String show(@PathVariable("itemId") Long itemId, Model uiModel) {
        uiModel.addAttribute("value", valueService.findValue(itemId));
        uiModel.addAttribute("itemId", itemId);
        return "values/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("values", valueService.findValueEntries(firstResult, sizeNo));
            float nrOfPages = (float) valueService.countAllValues() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("values", valueService.findAllValues());
        }
        return "values/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Value value, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, value);
            return "values/update";
        }
        uiModel.asMap().clear();
        valueService.updateValue(value);
        return "redirect:/values/" + encodeUrlPathSegment(value.getItemId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{itemId}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("itemId") Long itemId, Model uiModel) {
        populateEditForm(uiModel, valueService.findValue(itemId));
        return "values/update";
    }

	@RequestMapping(value = "/{itemId}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("itemId") Long itemId, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Value value = valueService.findValue(itemId);
        valueService.deleteValue(value);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/values";
    }

	void populateEditForm(Model uiModel, Value value) {
        uiModel.addAttribute("value", value);
        uiModel.addAttribute("items", itemService.findAllItems());
        uiModel.addAttribute("scales", scaleService.findAllScales());
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
