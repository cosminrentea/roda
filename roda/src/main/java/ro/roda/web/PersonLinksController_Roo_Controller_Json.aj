// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.web;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.roda.domain.PersonLinks;
import ro.roda.web.PersonLinksController;

privileged aspect PersonLinksController_Roo_Controller_Json {
    
    @RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> PersonLinksController.showJson(@PathVariable("id") Integer id) {
        PersonLinks personLinks = personLinksService.findPersonLinks(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (personLinks == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(personLinks.toJson(), headers, HttpStatus.OK);
    }
    
    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> PersonLinksController.listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<PersonLinks> result = personLinksService.findAllPersonLinkses();
        return new ResponseEntity<String>(PersonLinks.toJsonArray(result), headers, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> PersonLinksController.createFromJson(@RequestBody String json) {
        PersonLinks personLinks = PersonLinks.fromJsonToPersonLinks(json);
        personLinksService.savePersonLinks(personLinks);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> PersonLinksController.createFromJsonArray(@RequestBody String json) {
        for (PersonLinks personLinks: PersonLinks.fromJsonArrayToPersonLinkses(json)) {
            personLinksService.savePersonLinks(personLinks);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> PersonLinksController.updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        PersonLinks personLinks = PersonLinks.fromJsonToPersonLinks(json);
        if (personLinksService.updatePersonLinks(personLinks) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> PersonLinksController.updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (PersonLinks personLinks: PersonLinks.fromJsonArrayToPersonLinkses(json)) {
            if (personLinksService.updatePersonLinks(personLinks) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> PersonLinksController.deleteFromJson(@PathVariable("id") Integer id) {
        PersonLinks personLinks = personLinksService.findPersonLinks(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (personLinks == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        personLinksService.deletePersonLinks(personLinks);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
}