// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import ro.roda.InstanceDescr;

privileged aspect InstanceDescr_Roo_Jpa_Entity {
    
    declare @type: InstanceDescr: @Entity;
    
    declare @type: InstanceDescr: @Table(schema = "public", name = "instance_descr");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "instance_id", columnDefinition = "int4")
    private Integer InstanceDescr.instanceId;
    
    public Integer InstanceDescr.getInstanceId() {
        return this.instanceId;
    }
    
    public void InstanceDescr.setInstanceId(Integer id) {
        this.instanceId = id;
    }
    
}
