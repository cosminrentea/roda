// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import ro.roda.StudyPersonAcl;
import ro.roda.StudyPersonAclPK;

privileged aspect StudyPersonAcl_Roo_Jpa_Entity {
    
    declare @type: StudyPersonAcl: @Entity;
    
    declare @type: StudyPersonAcl: @Table(schema = "public", name = "study_person_acl");
    
    @EmbeddedId
    private StudyPersonAclPK StudyPersonAcl.id;
    
    public StudyPersonAclPK StudyPersonAcl.getId() {
        return this.id;
    }
    
    public void StudyPersonAcl.setId(StudyPersonAclPK id) {
        this.id = id;
    }
    
}