// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import ro.roda.AuditRowIdPK;

privileged aspect AuditRowIdPK_Roo_Identifier {
    
    declare @type: AuditRowIdPK: @Embeddable;
    
    @Column(name = "audit_id", columnDefinition = "int4", nullable = false)
    private Integer AuditRowIdPK.auditId;
    
    @Column(name = "id", columnDefinition = "int4", nullable = false)
    private Integer AuditRowIdPK.id;
    
    public AuditRowIdPK.new(Integer auditId, Integer id) {
        super();
        this.auditId = auditId;
        this.id = id;
    }

    private AuditRowIdPK.new() {
        super();
    }

    public Integer AuditRowIdPK.getAuditId() {
        return auditId;
    }
    
    public Integer AuditRowIdPK.getId() {
        return id;
    }
    
}
