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
import ro.roda.domain.OrgAddress;
import ro.roda.domain.OrgAddressPK;
import ro.roda.service.AddressService;
import ro.roda.service.OrgAddressService;
import ro.roda.service.OrgService;

@RequestMapping("/orgaddresses")
@Controller


public class OrgAddressController {

	private ConversionService conversionService;

	@Autowired
    OrgAddressService orgAddressService;

	@Autowired
    AddressService addressService;

	@Autowired
    OrgService orgService;

	@Autowired
    public OrgAddressController(ConversionService conversionService) {
        super();
        this.conversionService = conversionService;
    }

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid OrgAddress orgAddress, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, orgAddress);
            return "orgaddresses/create";
        }
        uiModel.asMap().clear();
        orgAddressService.saveOrgAddress(orgAddress);
        return "redirect:/orgaddresses/" + encodeUrlPathSegment(conversionService.convert(orgAddress.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new OrgAddress());
        return "orgaddresses/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") OrgAddressPK id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("orgaddress", orgAddressService.findOrgAddress(id));
        uiModel.addAttribute("itemId", conversionService.convert(id, String.class));
        return "orgaddresses/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("orgaddresses", orgAddressService.findOrgAddressEntries(firstResult, sizeNo));
            float nrOfPages = (float) orgAddressService.countAllOrgAddresses() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("orgaddresses", orgAddressService.findAllOrgAddresses());
        }
        addDateTimeFormatPatterns(uiModel);
        return "orgaddresses/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid OrgAddress orgAddress, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, orgAddress);
            return "orgaddresses/update";
        }
        uiModel.asMap().clear();
        orgAddressService.updateOrgAddress(orgAddress);
        return "redirect:/orgaddresses/" + encodeUrlPathSegment(conversionService.convert(orgAddress.getId(), String.class), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") OrgAddressPK id, Model uiModel) {
        populateEditForm(uiModel, orgAddressService.findOrgAddress(id));
        return "orgaddresses/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") OrgAddressPK id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        OrgAddress orgAddress = orgAddressService.findOrgAddress(id);
        orgAddressService.deleteOrgAddress(orgAddress);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/orgaddresses";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("orgAddress_datestart_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("orgAddress_dateend_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, OrgAddress orgAddress) {
        uiModel.addAttribute("orgAddress", orgAddress);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("addresses", addressService.findAllAddresses());
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

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") OrgAddressPK id) {
        OrgAddress orgAddress = orgAddressService.findOrgAddress(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (orgAddress == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(orgAddress.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<OrgAddress> result = orgAddressService.findAllOrgAddresses();
        return new ResponseEntity<String>(OrgAddress.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        OrgAddress orgAddress = OrgAddress.fromJsonToOrgAddress(json);
        orgAddressService.saveOrgAddress(orgAddress);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (OrgAddress orgAddress: OrgAddress.fromJsonArrayToOrgAddresses(json)) {
            orgAddressService.saveOrgAddress(orgAddress);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        OrgAddress orgAddress = OrgAddress.fromJsonToOrgAddress(json);
        if (orgAddressService.updateOrgAddress(orgAddress) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (OrgAddress orgAddress: OrgAddress.fromJsonArrayToOrgAddresses(json)) {
            if (orgAddressService.updateOrgAddress(orgAddress) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") OrgAddressPK id) {
        OrgAddress orgAddress = orgAddressService.findOrgAddress(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (orgAddress == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        orgAddressService.deleteOrgAddress(orgAddress);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
