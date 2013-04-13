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
import ro.roda.domain.UnitAnalysis;
import ro.roda.web.UnitAnalysisController;

privileged aspect UnitAnalysisController_Roo_Controller_Json {
    
    @RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> UnitAnalysisController.showJson(@PathVariable("id") Integer id) {
        UnitAnalysis unitAnalysis = unitAnalysisService.findUnitAnalysis(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (unitAnalysis == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(unitAnalysis.toJson(), headers, HttpStatus.OK);
    }
    
    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> UnitAnalysisController.listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<UnitAnalysis> result = unitAnalysisService.findAllUnitAnalyses();
        return new ResponseEntity<String>(UnitAnalysis.toJsonArray(result), headers, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> UnitAnalysisController.createFromJson(@RequestBody String json) {
        UnitAnalysis unitAnalysis = UnitAnalysis.fromJsonToUnitAnalysis(json);
        unitAnalysisService.saveUnitAnalysis(unitAnalysis);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> UnitAnalysisController.createFromJsonArray(@RequestBody String json) {
        for (UnitAnalysis unitAnalysis: UnitAnalysis.fromJsonArrayToUnitAnalyses(json)) {
            unitAnalysisService.saveUnitAnalysis(unitAnalysis);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> UnitAnalysisController.updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        UnitAnalysis unitAnalysis = UnitAnalysis.fromJsonToUnitAnalysis(json);
        if (unitAnalysisService.updateUnitAnalysis(unitAnalysis) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> UnitAnalysisController.updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (UnitAnalysis unitAnalysis: UnitAnalysis.fromJsonArrayToUnitAnalyses(json)) {
            if (unitAnalysisService.updateUnitAnalysis(unitAnalysis) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> UnitAnalysisController.deleteFromJson(@PathVariable("id") Integer id) {
        UnitAnalysis unitAnalysis = unitAnalysisService.findUnitAnalysis(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (unitAnalysis == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        unitAnalysisService.deleteUnitAnalysis(unitAnalysis);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
}