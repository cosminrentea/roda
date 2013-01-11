// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import ro.roda.Audit;
import ro.roda.AuditRowId;

privileged aspect AuditRowId_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "audit_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Audit AuditRowId.auditId;
    
    @Column(name = "column_name", columnDefinition = "text")
    @NotNull
    private String AuditRowId.columnName;
    
    @Column(name = "column_value", columnDefinition = "int4")
    @NotNull
    private Integer AuditRowId.columnValue;
    
    public Audit AuditRowId.getAuditId() {
        return auditId;
    }
    
    public void AuditRowId.setAuditId(Audit auditId) {
        this.auditId = auditId;
    }
    
    public String AuditRowId.getColumnName() {
        return columnName;
    }
    
    public void AuditRowId.setColumnName(String columnName) {
        this.columnName = columnName;
    }
    
    public Integer AuditRowId.getColumnValue() {
        return columnValue;
    }
    
    public void AuditRowId.setColumnValue(Integer columnValue) {
        this.columnValue = columnValue;
    }
    
}
