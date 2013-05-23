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
import ro.roda.domain.OrgInternet;
import ro.roda.domain.OrgInternetPK;
import ro.roda.service.InternetService;
import ro.roda.service.OrgInternetService;
import ro.roda.service.OrgService;

@RequestMapping("/orginternets")
@Controller


public class OrgInternetController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") OrgInternetPK id) {
        OrgInternet orgInternet = orgInternetService.findOrgInternet(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (orgInternet == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(orgInternet.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<OrgInternet> result = orgInternetService.findAllOrgInternets();
        return new ResponseEntity<String>(OrgInternet.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        OrgInternet orgInternet = OrgInternet.fromJsonToOrgInternet(json);
        orgInternetService.saveOrgInternet(orgInternet);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (OrgInternet orgInternet: OrgInternet.fromJsonArrayToOrgInternets(json)) {
            orgInternetService.saveOrgInternet(orgInternet);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        OrgInternet orgInternet = OrgInternet.fromJsonToOrgInternet(json);
        if (orgInternetService.updateOrgInternet(orgInternet) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (OrgInternet orgInternet: OrgInternet.fromJsonArrayToOrgInternets(json)) {
            if (orgInternetService.updateOrgInternet(orgInternet) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") OrgInternetPK id) {
        OrgInternet orgInternet = orgInternetService.findOrgInternet(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (orgInternet == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        orgInternetService.deleteOrgInternet(orgInternet);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	private ConversionService conversionService;

	@Autowired
    OrgInternetService orgInternetService;

	@Autowired
    InternetService internetService;

	@Autowired
    OrgService orgService;

	@Autowired
    public OrgInternetController(ConversionService conversionService) {
        super();
        this.conversionService = conversionService;
    }

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid OrgInternet orgInternet, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, orgInternet);
            return "orginternets/create";
        }
        uiModel.asMap().clear();
        orgInternetService.saveOrgInternet(orgInternet);
        return "redirect:/orginternets/" + encodeUrlPathSegment(conversionService.convert(orgInternet.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new OrgInternet());
        return "orginternets/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") OrgInternetPK id, Model uiModel) {
        uiModel.addAttribute("orginternet", orgInternetService.findOrgInternet(id));
        uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
        return "orginternets/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("orginternets", orgInternetService.findOrgInternetEntries(firstResult, sizeNo));
            float nrOfPages = (float) orgInternetService.countAllOrgInternets() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("orginternets", orgInternetService.findAllOrgInternets());
        }
        return "orginternets/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid OrgInternet orgInternet, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, orgInternet);
            return "orginternets/update";
        }
        uiModel.asMap().clear();
        orgInternetService.updateOrgInternet(orgInternet);
        return "redirect:/orginternets/" + encodeUrlPathSegment(conversionService.convert(orgInternet.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") OrgInternetPK id, Model uiModel) {
        populateEditForm(uiModel, orgInternetService.findOrgInternet(id));
        return "orginternets/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") OrgInternetPK id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        OrgInternet orgInternet = orgInternetService.findOrgInternet(id);
        orgInternetService.deleteOrgInternet(orgInternet);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/orginternets";
    }

	void populateEditForm(Model uiModel, OrgInternet orgInternet) {
        uiModel.addAttribute("orgInternet", orgInternet);
        uiModel.addAttribute("internets", internetService.findAllInternets());
        uiModel.addAttribute("orgs", orgService.findAllOrgs());
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
