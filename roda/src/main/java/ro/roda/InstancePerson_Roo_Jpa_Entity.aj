// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import ro.roda.InstancePerson;
import ro.roda.InstancePersonPK;

privileged aspect InstancePerson_Roo_Jpa_Entity {
    
    declare @type: InstancePerson: @Entity;
    
    declare @type: InstancePerson: @Table(schema = "public", name = "instance_person");
    
    @EmbeddedId
    private InstancePersonPK InstancePerson.id;
    
    public InstancePersonPK InstancePerson.getId() {
        return this.id;
    }
    
    public void InstancePerson.setId(InstancePersonPK id) {
        this.id = id;
    }
    
}
