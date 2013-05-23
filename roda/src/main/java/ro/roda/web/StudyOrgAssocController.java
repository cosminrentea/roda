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
import ro.roda.domain.StudyOrgAssoc;
import ro.roda.service.StudyOrgAssocService;
import ro.roda.service.StudyOrgService;

@RequestMapping("/studyorgassocs")
@Controller


public class StudyOrgAssocController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
        StudyOrgAssoc studyOrgAssoc = studyOrgAssocService.findStudyOrgAssoc(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (studyOrgAssoc == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(studyOrgAssoc.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<StudyOrgAssoc> result = studyOrgAssocService.findAllStudyOrgAssocs();
        return new ResponseEntity<String>(StudyOrgAssoc.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        StudyOrgAssoc studyOrgAssoc = StudyOrgAssoc.fromJsonToStudyOrgAssoc(json);
        studyOrgAssocService.saveStudyOrgAssoc(studyOrgAssoc);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (StudyOrgAssoc studyOrgAssoc: StudyOrgAssoc.fromJsonArrayToStudyOrgAssocs(json)) {
            studyOrgAssocService.saveStudyOrgAssoc(studyOrgAssoc);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        StudyOrgAssoc studyOrgAssoc = StudyOrgAssoc.fromJsonToStudyOrgAssoc(json);
        if (studyOrgAssocService.updateStudyOrgAssoc(studyOrgAssoc) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (StudyOrgAssoc studyOrgAssoc: StudyOrgAssoc.fromJsonArrayToStudyOrgAssocs(json)) {
            if (studyOrgAssocService.updateStudyOrgAssoc(studyOrgAssoc) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {
        StudyOrgAssoc studyOrgAssoc = studyOrgAssocService.findStudyOrgAssoc(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (studyOrgAssoc == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        studyOrgAssocService.deleteStudyOrgAssoc(studyOrgAssoc);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@Autowired
    StudyOrgAssocService studyOrgAssocService;

	@Autowired
    StudyOrgService studyOrgService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid StudyOrgAssoc studyOrgAssoc, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, studyOrgAssoc);
            return "studyorgassocs/create";
        }
        uiModel.asMap().clear();
        studyOrgAssocService.saveStudyOrgAssoc(studyOrgAssoc);
        return "redirect:/studyorgassocs/" + encodeUrlPathSegment(studyOrgAssoc.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new StudyOrgAssoc());
        return "studyorgassocs/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("studyorgassoc", studyOrgAssocService.findStudyOrgAssoc(id));
        uiModel.addAttribute("itemId", id);
        return "studyorgassocs/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("studyorgassocs", studyOrgAssocService.findStudyOrgAssocEntries(firstResult, sizeNo));
            float nrOfPages = (float) studyOrgAssocService.countAllStudyOrgAssocs() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("studyorgassocs", studyOrgAssocService.findAllStudyOrgAssocs());
        }
        return "studyorgassocs/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid StudyOrgAssoc studyOrgAssoc, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, studyOrgAssoc);
            return "studyorgassocs/update";
        }
        uiModel.asMap().clear();
        studyOrgAssocService.updateStudyOrgAssoc(studyOrgAssoc);
        return "redirect:/studyorgassocs/" + encodeUrlPathSegment(studyOrgAssoc.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, studyOrgAssocService.findStudyOrgAssoc(id));
        return "studyorgassocs/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        StudyOrgAssoc studyOrgAssoc = studyOrgAssocService.findStudyOrgAssoc(id);
        studyOrgAssocService.deleteStudyOrgAssoc(studyOrgAssoc);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/studyorgassocs";
    }

	void populateEditForm(Model uiModel, StudyOrgAssoc studyOrgAssoc) {
        uiModel.addAttribute("studyOrgAssoc", studyOrgAssoc);
        uiModel.addAttribute("studyorgs", studyOrgService.findAllStudyOrgs());
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
