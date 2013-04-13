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
import ro.roda.domain.StudyKeyword;
import ro.roda.domain.StudyKeywordPK;
import ro.roda.web.StudyKeywordController;

privileged aspect StudyKeywordController_Roo_Controller_Json {
    
    @RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> StudyKeywordController.showJson(@PathVariable("id") StudyKeywordPK id) {
        StudyKeyword studyKeyword = studyKeywordService.findStudyKeyword(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (studyKeyword == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(studyKeyword.toJson(), headers, HttpStatus.OK);
    }
    
    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> StudyKeywordController.listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<StudyKeyword> result = studyKeywordService.findAllStudyKeywords();
        return new ResponseEntity<String>(StudyKeyword.toJsonArray(result), headers, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> StudyKeywordController.createFromJson(@RequestBody String json) {
        StudyKeyword studyKeyword = StudyKeyword.fromJsonToStudyKeyword(json);
        studyKeywordService.saveStudyKeyword(studyKeyword);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> StudyKeywordController.createFromJsonArray(@RequestBody String json) {
        for (StudyKeyword studyKeyword: StudyKeyword.fromJsonArrayToStudyKeywords(json)) {
            studyKeywordService.saveStudyKeyword(studyKeyword);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> StudyKeywordController.updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        StudyKeyword studyKeyword = StudyKeyword.fromJsonToStudyKeyword(json);
        if (studyKeywordService.updateStudyKeyword(studyKeyword) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> StudyKeywordController.updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (StudyKeyword studyKeyword: StudyKeyword.fromJsonArrayToStudyKeywords(json)) {
            if (studyKeywordService.updateStudyKeyword(studyKeyword) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> StudyKeywordController.deleteFromJson(@PathVariable("id") StudyKeywordPK id) {
        StudyKeyword studyKeyword = studyKeywordService.findStudyKeyword(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (studyKeyword == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        studyKeywordService.deleteStudyKeyword(studyKeyword);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
}