// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import ro.roda.OrgSufix;

privileged aspect OrgSufix_Roo_Jpa_Entity {
    
    declare @type: OrgSufix: @Entity;
    
    declare @type: OrgSufix: @Table(schema = "public", name = "org_sufix");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "int4")
    private Integer OrgSufix.id;
    
    public Integer OrgSufix.getId() {
        return this.id;
    }
    
    public void OrgSufix.setId(Integer id) {
        this.id = id;
    }
    
}