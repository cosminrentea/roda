// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import ro.roda.InstanceOrgAssoc;

privileged aspect InstanceOrgAssoc_Roo_Jpa_Entity {
    
    declare @type: InstanceOrgAssoc: @Entity;
    
    declare @type: InstanceOrgAssoc: @Table(schema = "public", name = "instance_org_assoc");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "int4")
    private Integer InstanceOrgAssoc.id;
    
    public Integer InstanceOrgAssoc.getId() {
        return this.id;
    }
    
    public void InstanceOrgAssoc.setId(Integer id) {
        this.id = id;
    }
    
}
