// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.web;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import ro.roda.domain.Instance;
import ro.roda.service.CollectionModelTypeService;
import ro.roda.service.FileService;
import ro.roda.service.FormService;
import ro.roda.service.InstanceAclService;
import ro.roda.service.InstanceDescrService;
import ro.roda.service.InstanceKeywordService;
import ro.roda.service.InstanceOrgService;
import ro.roda.service.InstancePersonService;
import ro.roda.service.InstanceService;
import ro.roda.service.RodauserService;
import ro.roda.service.SamplingProcedureService;
import ro.roda.service.StudyService;
import ro.roda.service.TimeMethTypeService;
import ro.roda.service.TopicService;
import ro.roda.service.UnitAnalysisService;
import ro.roda.service.VariableService;
import ro.roda.web.InstanceController;

privileged aspect InstanceController_Roo_Controller {
    
    @Autowired
    InstanceService InstanceController.instanceService;
    
    @Autowired
    CollectionModelTypeService InstanceController.collectionModelTypeService;
    
    @Autowired
    FileService InstanceController.fileService;
    
    @Autowired
    FormService InstanceController.formService;
    
    @Autowired
    InstanceAclService InstanceController.instanceAclService;
    
    @Autowired
    InstanceDescrService InstanceController.instanceDescrService;
    
    @Autowired
    InstanceKeywordService InstanceController.instanceKeywordService;
    
    @Autowired
    InstanceOrgService InstanceController.instanceOrgService;
    
    @Autowired
    InstancePersonService InstanceController.instancePersonService;
    
    @Autowired
    RodauserService InstanceController.rodauserService;
    
    @Autowired
    SamplingProcedureService InstanceController.samplingProcedureService;
    
    @Autowired
    StudyService InstanceController.studyService;
    
    @Autowired
    TimeMethTypeService InstanceController.timeMethTypeService;
    
    @Autowired
    TopicService InstanceController.topicService;
    
    @Autowired
    UnitAnalysisService InstanceController.unitAnalysisService;
    
    @Autowired
    VariableService InstanceController.variableService;
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String InstanceController.create(@Valid Instance instance, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, instance);
            return "instances/create";
        }
        uiModel.asMap().clear();
        instanceService.saveInstance(instance);
        return "redirect:/instances/" + encodeUrlPathSegment(instance.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String InstanceController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Instance());
        return "instances/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String InstanceController.show(@PathVariable("id") Integer id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("instance", instanceService.findInstance(id));
        uiModel.addAttribute("itemId", id);
        return "instances/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String InstanceController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("instances", instanceService.findInstanceEntries(firstResult, sizeNo));
            float nrOfPages = (float) instanceService.countAllInstances() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("instances", instanceService.findAllInstances());
        }
        addDateTimeFormatPatterns(uiModel);
        return "instances/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String InstanceController.update(@Valid Instance instance, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, instance);
            return "instances/update";
        }
        uiModel.asMap().clear();
        instanceService.updateInstance(instance);
        return "redirect:/instances/" + encodeUrlPathSegment(instance.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String InstanceController.updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, instanceService.findInstance(id));
        return "instances/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String InstanceController.delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Instance instance = instanceService.findInstance(id);
        instanceService.deleteInstance(instance);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/instances";
    }
    
    void InstanceController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("instance_datestart_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("instance_dateend_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("instance_added_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }
    
    void InstanceController.populateEditForm(Model uiModel, Instance instance) {
        uiModel.addAttribute("instance", instance);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("collectionmodeltypes", collectionModelTypeService.findAllCollectionModelTypes());
        uiModel.addAttribute("files", fileService.findAllFiles());
        uiModel.addAttribute("forms", formService.findAllForms());
        uiModel.addAttribute("instanceacls", instanceAclService.findAllInstanceAcls());
        uiModel.addAttribute("instancedescrs", instanceDescrService.findAllInstanceDescrs());
        uiModel.addAttribute("instancekeywords", instanceKeywordService.findAllInstanceKeywords());
        uiModel.addAttribute("instanceorgs", instanceOrgService.findAllInstanceOrgs());
        uiModel.addAttribute("instancepeople", instancePersonService.findAllInstancepeople());
        uiModel.addAttribute("rodausers", rodauserService.findAllRodausers());
        uiModel.addAttribute("samplingprocedures", samplingProcedureService.findAllSamplingProcedures());
        uiModel.addAttribute("studys", studyService.findAllStudys());
        uiModel.addAttribute("timemethtypes", timeMethTypeService.findAllTimeMethTypes());
        uiModel.addAttribute("topics", topicService.findAllTopics());
        uiModel.addAttribute("unitanalyses", unitAnalysisService.findAllUnitAnalyses());
        uiModel.addAttribute("variables", variableService.findAllVariables());
    }
    
    String InstanceController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
