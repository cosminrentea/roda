// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.service;

import java.util.List;
import ro.roda.domain.SourceContactMethod;
import ro.roda.service.SourceContactMethodService;

privileged aspect SourceContactMethodService_Roo_Service {
    
    public abstract long SourceContactMethodService.countAllSourceContactMethods();    
    public abstract void SourceContactMethodService.deleteSourceContactMethod(SourceContactMethod sourceContactMethod);    
    public abstract SourceContactMethod SourceContactMethodService.findSourceContactMethod(Integer id);    
    public abstract List<SourceContactMethod> SourceContactMethodService.findAllSourceContactMethods();    
    public abstract List<SourceContactMethod> SourceContactMethodService.findSourceContactMethodEntries(int firstResult, int maxResults);    
    public abstract void SourceContactMethodService.saveSourceContactMethod(SourceContactMethod sourceContactMethod);    
    public abstract SourceContactMethod SourceContactMethodService.updateSourceContactMethod(SourceContactMethod sourceContactMethod);    
}