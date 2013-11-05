package ro.roda.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import ro.roda.domain.Scale;
import ro.roda.service.ItemService;
import ro.roda.service.ScaleService;
import ro.roda.service.ValueService;

@RequestMapping("/scales")
@Controller
public class ScaleController {

	@RequestMapping(value = "/{itemId}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("itemId") Long itemId) {
		Scale scale = scaleService.findScale(itemId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (scale == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(scale.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<Scale> result = scaleService.findAllScales();
		return new ResponseEntity<String>(Scale.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		Scale scale = Scale.fromJsonToScale(json);
		scaleService.saveScale(scale);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (Scale scale : Scale.fromJsonArrayToScales(json)) {
			scaleService.saveScale(scale);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		Scale scale = Scale.fromJsonToScale(json);
		if (scaleService.updateScale(scale) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (Scale scale : Scale.fromJsonArrayToScales(json)) {
			if (scaleService.updateScale(scale) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{itemId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("itemId") Long itemId) {
		Scale scale = scaleService.findScale(itemId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (scale == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		scaleService.deleteScale(scale);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@Autowired
	ScaleService scaleService;

	@Autowired
	ItemService itemService;

	@Autowired
	ValueService valueService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Scale scale, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, scale);
			return "scales/create";
		}
		uiModel.asMap().clear();
		scaleService.saveScale(scale);
		return "redirect:/scales/" + encodeUrlPathSegment(scale.getItemId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new Scale());
		return "scales/create";
	}

	@RequestMapping(value = "/{itemId}", produces = "text/html")
	public String show(@PathVariable("itemId") Long itemId, Model uiModel) {
		uiModel.addAttribute("scale", scaleService.findScale(itemId));
		uiModel.addAttribute("itemId", itemId);
		return "scales/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("scales", scaleService.findScaleEntries(firstResult, sizeNo));
			float nrOfPages = (float) scaleService.countAllScales() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("scales", scaleService.findAllScales());
		}
		return "scales/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Scale scale, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, scale);
			return "scales/update";
		}
		uiModel.asMap().clear();
		scaleService.updateScale(scale);
		return "redirect:/scales/" + encodeUrlPathSegment(scale.getItemId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{itemId}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("itemId") Long itemId, Model uiModel) {
		populateEditForm(uiModel, scaleService.findScale(itemId));
		return "scales/update";
	}

	@RequestMapping(value = "/{itemId}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("itemId") Long itemId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Scale scale = scaleService.findScale(itemId);
		scaleService.deleteScale(scale);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/scales";
	}

	void populateEditForm(Model uiModel, Scale scale) {
		uiModel.addAttribute("scale", scale);
		uiModel.addAttribute("items", itemService.findAllItems());
		uiModel.addAttribute("values", valueService.findAllValues());
	}

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
		String enc = httpServletRequest.getCharacterEncoding();
		if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		try {
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
		} catch (UnsupportedEncodingException uee) {
		}
		return pathSegment;
	}
}
