// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import ro.roda.domain.InstanceVariable;
import ro.roda.domain.InstanceVariablePK;

privileged aspect InstanceVariable_Roo_Jpa_Entity {
    
    declare @type: InstanceVariable: @Entity;
    
    declare @type: InstanceVariable: @Table(schema = "public", name = "instance_variable");
    
    @EmbeddedId
    private InstanceVariablePK InstanceVariable.id;
    
    public InstanceVariablePK InstanceVariable.getId() {
        return this.id;
    }
    
    public void InstanceVariable.setId(InstanceVariablePK id) {
        this.id = id;
    }
    
}
