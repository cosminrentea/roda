// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import ro.roda.domain.Org;
import ro.roda.domain.Study;
import ro.roda.domain.StudyOrg;
import ro.roda.domain.StudyOrgAssoc;

privileged aspect StudyOrg_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "org_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Org StudyOrg.orgId;
    
    @ManyToOne
    @JoinColumn(name = "study_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Study StudyOrg.studyId;
    
    @ManyToOne
    @JoinColumn(name = "assoctype_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private StudyOrgAssoc StudyOrg.assoctypeId;
    
    @Column(name = "assoc_details", columnDefinition = "text")
    private String StudyOrg.assocDetails;
    
    public Org StudyOrg.getOrgId() {
        return orgId;
    }
    
    public void StudyOrg.setOrgId(Org orgId) {
        this.orgId = orgId;
    }
    
    public Study StudyOrg.getStudyId() {
        return studyId;
    }
    
    public void StudyOrg.setStudyId(Study studyId) {
        this.studyId = studyId;
    }
    
    public StudyOrgAssoc StudyOrg.getAssoctypeId() {
        return assoctypeId;
    }
    
    public void StudyOrg.setAssoctypeId(StudyOrgAssoc assoctypeId) {
        this.assoctypeId = assoctypeId;
    }
    
    public String StudyOrg.getAssocDetails() {
        return assocDetails;
    }
    
    public void StudyOrg.setAssocDetails(String assocDetails) {
        this.assocDetails = assocDetails;
    }
    
}
