// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import ro.roda.Audit;

privileged aspect Audit_Roo_Jpa_Entity {
    
    declare @type: Audit: @Entity;
    
    declare @type: Audit: @Table(schema = "public", name = "audit");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "int4")
    private Integer Audit.id;
    
    public Integer Audit.getId() {
        return this.id;
    }
    
    public void Audit.setId(Integer id) {
        this.id = id;
    }
    
}
