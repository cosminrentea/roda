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
import ro.roda.domain.Series;
import ro.roda.service.CatalogService;
import ro.roda.service.SeriesDescrService;
import ro.roda.service.SeriesService;
import ro.roda.service.TopicService;

@RequestMapping("/serieses")
@Controller
public class SeriesController {

	@RequestMapping(value = "/{catalogId}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("catalogId") Integer catalogId) {
		Series series = seriesService.findSeries(catalogId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (series == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(series.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<Series> result = seriesService.findAllSerieses();
		return new ResponseEntity<String>(Series.toJsonArray(result), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		Series series = Series.fromJsonToSeries(json);
		seriesService.saveSeries(series);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (Series series : Series.fromJsonArrayToSerieses(json)) {
			seriesService.saveSeries(series);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		Series series = Series.fromJsonToSeries(json);
		if (seriesService.updateSeries(series) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (Series series : Series.fromJsonArrayToSerieses(json)) {
			if (seriesService.updateSeries(series) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{catalogId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("catalogId") Integer catalogId) {
		Series series = seriesService.findSeries(catalogId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (series == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		seriesService.deleteSeries(series);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@Autowired
	SeriesService seriesService;

	@Autowired
	CatalogService catalogService;

	@Autowired
	SeriesDescrService seriesDescrService;

	@Autowired
	TopicService topicService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Series series, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, series);
			return "serieses/create";
		}
		uiModel.asMap().clear();
		seriesService.saveSeries(series);
		return "redirect:/serieses/" + encodeUrlPathSegment(series.getCatalogId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new Series());
		return "serieses/create";
	}

	@RequestMapping(value = "/{catalogId}", produces = "text/html")
	public String show(@PathVariable("catalogId") Integer catalogId, Model uiModel) {
		uiModel.addAttribute("series", seriesService.findSeries(catalogId));
		uiModel.addAttribute("itemId", catalogId);
		return "serieses/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("serieses", seriesService.findSeriesEntries(firstResult, sizeNo));
			float nrOfPages = (float) seriesService.countAllSerieses() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
					: nrOfPages));
		} else {
			uiModel.addAttribute("serieses", seriesService.findAllSerieses());
		}
		return "serieses/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Series series, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, series);
			return "serieses/update";
		}
		uiModel.asMap().clear();
		seriesService.updateSeries(series);
		return "redirect:/serieses/" + encodeUrlPathSegment(series.getCatalogId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{catalogId}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("catalogId") Integer catalogId, Model uiModel) {
		populateEditForm(uiModel, seriesService.findSeries(catalogId));
		return "serieses/update";
	}

	@RequestMapping(value = "/{catalogId}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("catalogId") Integer catalogId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Series series = seriesService.findSeries(catalogId);
		seriesService.deleteSeries(series);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/serieses";
	}

	void populateEditForm(Model uiModel, Series series) {
		uiModel.addAttribute("series", series);
		uiModel.addAttribute("catalogs", catalogService.findAllCatalogs());
		uiModel.addAttribute("seriesdescrs", seriesDescrService.findAllSeriesDescrs());
		uiModel.addAttribute("topics", topicService.findAllTopics());
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
