// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.service;

import java.util.List;
import ro.roda.domain.AuditLogAction;
import ro.roda.service.AuditLogActionService;

privileged aspect AuditLogActionService_Roo_Service {
    
    public abstract long AuditLogActionService.countAllAuditLogActions();    
    public abstract void AuditLogActionService.deleteAuditLogAction(AuditLogAction auditLogAction);    
    public abstract AuditLogAction AuditLogActionService.findAuditLogAction(Integer id);    
    public abstract List<AuditLogAction> AuditLogActionService.findAllAuditLogActions();    
    public abstract List<AuditLogAction> AuditLogActionService.findAuditLogActionEntries(int firstResult, int maxResults);    
    public abstract void AuditLogActionService.saveAuditLogAction(AuditLogAction auditLogAction);    
    public abstract AuditLogAction AuditLogActionService.updateAuditLogAction(AuditLogAction auditLogAction);    
}