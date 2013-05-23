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
import ro.roda.domain.CmsFolder;
import ro.roda.service.CmsFileService;
import ro.roda.service.CmsFolderService;

@RequestMapping("/cmsfolders")
@Controller


public class CmsFolderController {

	@Autowired
    CmsFolderService cmsFolderService;

	@Autowired
    CmsFileService cmsFileService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid CmsFolder cmsFolder, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, cmsFolder);
            return "cmsfolders/create";
        }
        uiModel.asMap().clear();
        cmsFolderService.saveCmsFolder(cmsFolder);
        return "redirect:/cmsfolders/" + encodeUrlPathSegment(cmsFolder.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new CmsFolder());
        return "cmsfolders/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("cmsfolder", cmsFolderService.findCmsFolder(id));
        uiModel.addAttribute("itemId", id);
        return "cmsfolders/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("cmsfolders", cmsFolderService.findCmsFolderEntries(firstResult, sizeNo));
            float nrOfPages = (float) cmsFolderService.countAllCmsFolders() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("cmsfolders", cmsFolderService.findAllCmsFolders());
        }
        return "cmsfolders/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid CmsFolder cmsFolder, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, cmsFolder);
            return "cmsfolders/update";
        }
        uiModel.asMap().clear();
        cmsFolderService.updateCmsFolder(cmsFolder);
        return "redirect:/cmsfolders/" + encodeUrlPathSegment(cmsFolder.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, cmsFolderService.findCmsFolder(id));
        return "cmsfolders/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        CmsFolder cmsFolder = cmsFolderService.findCmsFolder(id);
        cmsFolderService.deleteCmsFolder(cmsFolder);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/cmsfolders";
    }

	void populateEditForm(Model uiModel, CmsFolder cmsFolder) {
        uiModel.addAttribute("cmsFolder", cmsFolder);
        uiModel.addAttribute("cmsfiles", cmsFileService.findAllCmsFiles());
        uiModel.addAttribute("cmsfolders", cmsFolderService.findAllCmsFolders());
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
        CmsFolder cmsFolder = cmsFolderService.findCmsFolder(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (cmsFolder == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(cmsFolder.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<CmsFolder> result = cmsFolderService.findAllCmsFolders();
        return new ResponseEntity<String>(CmsFolder.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        CmsFolder cmsFolder = CmsFolder.fromJsonToCmsFolder(json);
        cmsFolderService.saveCmsFolder(cmsFolder);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (CmsFolder cmsFolder: CmsFolder.fromJsonArrayToCmsFolders(json)) {
            cmsFolderService.saveCmsFolder(cmsFolder);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        CmsFolder cmsFolder = CmsFolder.fromJsonToCmsFolder(json);
        if (cmsFolderService.updateCmsFolder(cmsFolder) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (CmsFolder cmsFolder: CmsFolder.fromJsonArrayToCmsFolders(json)) {
            if (cmsFolderService.updateCmsFolder(cmsFolder) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
        CmsFolder cmsFolder = cmsFolderService.findCmsFolder(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (cmsFolder == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        cmsFolderService.deleteCmsFolder(cmsFolder);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
