// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import ro.roda.PersonRole;

privileged aspect PersonRole_Roo_Jpa_Entity {
    
    declare @type: PersonRole: @Entity;
    
    declare @type: PersonRole: @Table(schema = "public", name = "person_role");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "int4")
    private Integer PersonRole.id;
    
    public Integer PersonRole.getId() {
        return this.id;
    }
    
    public void PersonRole.setId(Integer id) {
        this.id = id;
    }
    
}
