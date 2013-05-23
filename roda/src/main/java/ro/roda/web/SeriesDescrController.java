package ro.roda.web;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
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
import ro.roda.domain.SeriesDescr;
import ro.roda.domain.SeriesDescrPK;
import ro.roda.service.LangService;
import ro.roda.service.SeriesDescrService;
import ro.roda.service.SeriesService;

@RequestMapping("/seriesdescrs")
@Controller
public class SeriesDescrController {

	private ConversionService conversionService;

	@Autowired
	SeriesDescrService seriesDescrService;

	@Autowired
	LangService langService;

	@Autowired
	SeriesService seriesService;

	@Autowired
	public SeriesDescrController(ConversionService conversionService) {
		super();
		this.conversionService = conversionService;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid SeriesDescr seriesDescr, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, seriesDescr);
			return "seriesdescrs/create";
		}
		uiModel.asMap().clear();
		seriesDescrService.saveSeriesDescr(seriesDescr);
		return "redirect:/seriesdescrs/"
				+ encodeUrlPathSegment(conversionService.convert(seriesDescr.getId(), String.class), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new SeriesDescr());
		return "seriesdescrs/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") SeriesDescrPK id, Model uiModel) {
		uiModel.addAttribute("seriesdescr", seriesDescrService.findSeriesDescr(id));
		uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
		return "seriesdescrs/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("seriesdescrs", seriesDescrService.findSeriesDescrEntries(firstResult, sizeNo));
			float nrOfPages = (float) seriesDescrService.countAllSeriesDescrs() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("seriesdescrs", seriesDescrService.findAllSeriesDescrs());
		}
		return "seriesdescrs/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid SeriesDescr seriesDescr, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, seriesDescr);
			return "seriesdescrs/update";
		}
		uiModel.asMap().clear();
		seriesDescrService.updateSeriesDescr(seriesDescr);
		return "redirect:/seriesdescrs/"
				+ encodeUrlPathSegment(conversionService.convert(seriesDescr.getId(), String.class), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") SeriesDescrPK id, Model uiModel) {
		populateEditForm(uiModel, seriesDescrService.findSeriesDescr(id));
		return "seriesdescrs/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") SeriesDescrPK id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		SeriesDescr seriesDescr = seriesDescrService.findSeriesDescr(id);
		seriesDescrService.deleteSeriesDescr(seriesDescr);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/seriesdescrs";
	}

	void populateEditForm(Model uiModel, SeriesDescr seriesDescr) {
		uiModel.addAttribute("seriesDescr", seriesDescr);
		uiModel.addAttribute("langs", langService.findAllLangs());
		uiModel.addAttribute("serieses", seriesService.findAllSerieses());
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

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") SeriesDescrPK id) {
		SeriesDescr seriesDescr = seriesDescrService.findSeriesDescr(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (seriesDescr == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(seriesDescr.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<SeriesDescr> result = seriesDescrService.findAllSeriesDescrs();
		return new ResponseEntity<String>(SeriesDescr.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		SeriesDescr seriesDescr = SeriesDescr.fromJsonToSeriesDescr(json);
		seriesDescrService.saveSeriesDescr(seriesDescr);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (SeriesDescr seriesDescr : SeriesDescr.fromJsonArrayToSeriesDescrs(json)) {
			seriesDescrService.saveSeriesDescr(seriesDescr);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		SeriesDescr seriesDescr = SeriesDescr.fromJsonToSeriesDescr(json);
		if (seriesDescrService.updateSeriesDescr(seriesDescr) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (SeriesDescr seriesDescr : SeriesDescr.fromJsonArrayToSeriesDescrs(json)) {
			if (seriesDescrService.updateSeriesDescr(seriesDescr) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") SeriesDescrPK id) {
		SeriesDescr seriesDescr = seriesDescrService.findSeriesDescr(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (seriesDescr == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		seriesDescrService.deleteSeriesDescr(seriesDescr);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
