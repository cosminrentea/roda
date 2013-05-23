// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.service;

import java.util.List;
import ro.roda.domain.InstanceForm;
import ro.roda.domain.InstanceFormPK;
import ro.roda.service.InstanceFormService;

privileged aspect InstanceFormService_Roo_Service {
    
    public abstract long InstanceFormService.countAllInstanceForms();    
    public abstract void InstanceFormService.deleteInstanceForm(InstanceForm instanceForm);    
    public abstract InstanceForm InstanceFormService.findInstanceForm(InstanceFormPK id);    
    public abstract List<InstanceForm> InstanceFormService.findAllInstanceForms();    
    public abstract List<InstanceForm> InstanceFormService.findInstanceFormEntries(int firstResult, int maxResults);    
    public abstract void InstanceFormService.saveInstanceForm(InstanceForm instanceForm);    
    public abstract InstanceForm InstanceFormService.updateInstanceForm(InstanceForm instanceForm);    
}
