// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import ro.roda.StudyPersonAssoc;

privileged aspect StudyPersonAssoc_Roo_Jpa_Entity {
    
    declare @type: StudyPersonAssoc: @Entity;
    
    declare @type: StudyPersonAssoc: @Table(schema = "public", name = "study_person_assoc");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "int4")
    private Integer StudyPersonAssoc.id;
    
    public Integer StudyPersonAssoc.getId() {
        return this.id;
    }
    
    public void StudyPersonAssoc.setId(Integer id) {
        this.id = id;
    }
    
}
