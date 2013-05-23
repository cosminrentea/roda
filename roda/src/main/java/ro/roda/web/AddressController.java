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
import ro.roda.domain.Address;
import ro.roda.domain.City;
import ro.roda.service.AddressService;
import ro.roda.service.CityService;
import ro.roda.service.OrgAddressService;
import ro.roda.service.PersonAddressService;

@RequestMapping("/addresses")
@Controller


public class AddressController {

	@Autowired
    AddressService addressService;

	@Autowired
    CityService cityService;

	@Autowired
    OrgAddressService orgAddressService;

	@Autowired
    PersonAddressService personAddressService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Address address, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, address);
            return "addresses/create";
        }
        uiModel.asMap().clear();
        addressService.saveAddress(address);
        return "redirect:/addresses/" + encodeUrlPathSegment(address.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Address());
        return "addresses/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("address", addressService.findAddress(id));
        uiModel.addAttribute("itemId", id);
        return "addresses/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("addresses", addressService.findAddressEntries(firstResult, sizeNo));
            float nrOfPages = (float) addressService.countAllAddresses() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("addresses", addressService.findAllAddresses());
        }
        return "addresses/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Address address, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, address);
            return "addresses/update";
        }
        uiModel.asMap().clear();
        addressService.updateAddress(address);
        return "redirect:/addresses/" + encodeUrlPathSegment(address.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, addressService.findAddress(id));
        return "addresses/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Address address = addressService.findAddress(id);
        addressService.deleteAddress(address);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/addresses";
    }

	void populateEditForm(Model uiModel, Address address) {
        uiModel.addAttribute("address", address);
        uiModel.addAttribute("citys", cityService.findAllCitys());
        uiModel.addAttribute("orgaddresses", orgAddressService.findAllOrgAddresses());
        uiModel.addAttribute("personaddresses", personAddressService.findAllPersonAddresses());
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
    public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
        Address address = addressService.findAddress(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (address == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(address.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Address> result = addressService.findAllAddresses();
        return new ResponseEntity<String>(Address.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        Address address = Address.fromJsonToAddress(json);
        addressService.saveAddress(address);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (Address address: Address.fromJsonArrayToAddresses(json)) {
            addressService.saveAddress(address);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Address address = Address.fromJsonToAddress(json);
        if (addressService.updateAddress(address) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (Address address: Address.fromJsonArrayToAddresses(json)) {
            if (addressService.updateAddress(address) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
        Address address = addressService.findAddress(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (address == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        addressService.deleteAddress(address);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(params = "find=ByCityId", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> jsonFindAddressesByCityId(@RequestParam("cityId") City cityId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return new ResponseEntity<String>(Address.toJsonArray(Address.findAddressesByCityId(cityId).getResultList()), headers, HttpStatus.OK);
    }

	@RequestMapping(params = "find=ByCityIdAndPostalCodeEquals", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> jsonFindAddressesByCityIdAndPostalCodeEquals(@RequestParam("cityId") City cityId, @RequestParam("postalCode") String postalCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return new ResponseEntity<String>(Address.toJsonArray(Address.findAddressesByCityIdAndPostalCodeEquals(cityId, postalCode).getResultList()), headers, HttpStatus.OK);
    }

	@RequestMapping(params = "find=ByPostalCodeEquals", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> jsonFindAddressesByPostalCodeEquals(@RequestParam("postalCode") String postalCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return new ResponseEntity<String>(Address.toJsonArray(Address.findAddressesByPostalCodeEquals(postalCode).getResultList()), headers, HttpStatus.OK);
    }
}
