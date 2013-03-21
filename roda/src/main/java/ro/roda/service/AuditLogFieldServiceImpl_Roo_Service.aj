// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.AuditLogField;
import ro.roda.service.AuditLogFieldServiceImpl;

privileged aspect AuditLogFieldServiceImpl_Roo_Service {
    
    declare @type: AuditLogFieldServiceImpl: @Service;
    
    declare @type: AuditLogFieldServiceImpl: @Transactional;
    
    public long AuditLogFieldServiceImpl.countAllAuditLogFields() {
        return AuditLogField.countAuditLogFields();
    }
    
    public void AuditLogFieldServiceImpl.deleteAuditLogField(AuditLogField auditLogField) {
        auditLogField.remove();
    }
    
    public AuditLogField AuditLogFieldServiceImpl.findAuditLogField(Integer id) {
        return AuditLogField.findAuditLogField(id);
    }
    
    public List<AuditLogField> AuditLogFieldServiceImpl.findAllAuditLogFields() {
        return AuditLogField.findAllAuditLogFields();
    }
    
    public List<AuditLogField> AuditLogFieldServiceImpl.findAuditLogFieldEntries(int firstResult, int maxResults) {
        return AuditLogField.findAuditLogFieldEntries(firstResult, maxResults);
    }
    
    public void AuditLogFieldServiceImpl.saveAuditLogField(AuditLogField auditLogField) {
        auditLogField.persist();
    }
    
    public AuditLogField AuditLogFieldServiceImpl.updateAuditLogField(AuditLogField auditLogField) {
        return auditLogField.merge();
    }
    
}