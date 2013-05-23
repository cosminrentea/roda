package ro.roda.web;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
import ro.roda.domain.OrgRelations;
import ro.roda.domain.OrgRelationsPK;
import ro.roda.service.OrgRelationTypeService;
import ro.roda.service.OrgRelationsService;
import ro.roda.service.OrgService;

@RequestMapping("/orgrelationses")
@Controller


public class OrgRelationsController {

	private ConversionService conversionService;

	@Autowired
    OrgRelationsService orgRelationsService;

	@Autowired
    OrgService orgService;

	@Autowired
    OrgRelationTypeService orgRelationTypeService;

	@Autowired
    public OrgRelationsController(ConversionService conversionService) {
        super();
        this.conversionService = conversionService;
    }

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid OrgRelations orgRelations, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, orgRelations);
            return "orgrelationses/create";
        }
        uiModel.asMap().clear();
        orgRelationsService.saveOrgRelations(orgRelations);
        return "redirect:/orgrelationses/" + encodeUrlPathSegment(conversionService.convert(orgRelations.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new OrgRelations());
        return "orgrelationses/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") OrgRelationsPK id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("orgrelations", orgRelationsService.findOrgRelations(id));
        uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
        return "orgrelationses/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("orgrelationses", orgRelationsService.findOrgRelationsEntries(firstResult, sizeNo));
            float nrOfPages = (float) orgRelationsService.countAllOrgRelationses() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("orgrelationses", orgRelationsService.findAllOrgRelationses());
        }
        addDateTimeFormatPatterns(uiModel);
        return "orgrelationses/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid OrgRelations orgRelations, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, orgRelations);
            return "orgrelationses/update";
        }
        uiModel.asMap().clear();
        orgRelationsService.updateOrgRelations(orgRelations);
        return "redirect:/orgrelationses/" + encodeUrlPathSegment(conversionService.convert(orgRelations.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") OrgRelationsPK id, Model uiModel) {
        populateEditForm(uiModel, orgRelationsService.findOrgRelations(id));
        return "orgrelationses/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") OrgRelationsPK id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        OrgRelations orgRelations = orgRelationsService.findOrgRelations(id);
        orgRelationsService.deleteOrgRelations(orgRelations);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/orgrelationses";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("orgRelations_datestart_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("orgRelations_dateend_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, OrgRelations orgRelations) {
        uiModel.addAttribute("orgRelations", orgRelations);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("orgs", orgService.findAllOrgs());
        uiModel.addAttribute("orgrelationtypes", orgRelationTypeService.findAllOrgRelationTypes());
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

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") OrgRelationsPK id) {
        OrgRelations orgRelations = orgRelationsService.findOrgRelations(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (orgRelations == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(orgRelations.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<OrgRelations> result = orgRelationsService.findAllOrgRelationses();
        return new ResponseEntity<String>(OrgRelations.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        OrgRelations orgRelations = OrgRelations.fromJsonToOrgRelations(json);
        orgRelationsService.saveOrgRelations(orgRelations);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (OrgRelations orgRelations: OrgRelations.fromJsonArrayToOrgRelationses(json)) {
            orgRelationsService.saveOrgRelations(orgRelations);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        OrgRelations orgRelations = OrgRelations.fromJsonToOrgRelations(json);
        if (orgRelationsService.updateOrgRelations(orgRelations) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (OrgRelations orgRelations: OrgRelations.fromJsonArrayToOrgRelationses(json)) {
            if (orgRelationsService.updateOrgRelations(orgRelations) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") OrgRelationsPK id) {
        OrgRelations orgRelations = orgRelationsService.findOrgRelations(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (orgRelations == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        orgRelationsService.deleteOrgRelations(orgRelations);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
