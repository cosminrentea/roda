// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import ro.roda.domain.Org;
import ro.roda.domain.OrgRelationType;
import ro.roda.domain.OrgRelations;

privileged aspect OrgRelations_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "org_2_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Org OrgRelations.org2Id;
    
    @ManyToOne
    @JoinColumn(name = "org_1_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Org OrgRelations.org1Id;
    
    @ManyToOne
    @JoinColumn(name = "org_relation_type_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private OrgRelationType OrgRelations.orgRelationTypeId;
    
    @Column(name = "date_start", columnDefinition = "date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date OrgRelations.dateStart;
    
    @Column(name = "date_end", columnDefinition = "date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date OrgRelations.dateEnd;
    
    @Column(name = "details", columnDefinition = "text")
    @NotNull
    private String OrgRelations.details;
    
    public Org OrgRelations.getOrg2Id() {
        return org2Id;
    }
    
    public void OrgRelations.setOrg2Id(Org org2Id) {
        this.org2Id = org2Id;
    }
    
    public Org OrgRelations.getOrg1Id() {
        return org1Id;
    }
    
    public void OrgRelations.setOrg1Id(Org org1Id) {
        this.org1Id = org1Id;
    }
    
    public OrgRelationType OrgRelations.getOrgRelationTypeId() {
        return orgRelationTypeId;
    }
    
    public void OrgRelations.setOrgRelationTypeId(OrgRelationType orgRelationTypeId) {
        this.orgRelationTypeId = orgRelationTypeId;
    }
    
    public Date OrgRelations.getDateStart() {
        return dateStart;
    }
    
    public void OrgRelations.setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }
    
    public Date OrgRelations.getDateEnd() {
        return dateEnd;
    }
    
    public void OrgRelations.setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
    
    public String OrgRelations.getDetails() {
        return details;
    }
    
    public void OrgRelations.setDetails(String details) {
        this.details = details;
    }
    
}