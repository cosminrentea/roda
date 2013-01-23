// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import ro.roda.Instance;
import ro.roda.InstanceAcl;

privileged aspect InstanceAcl_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "instance_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Instance InstanceAcl.instanceId;
    
    @Column(name = "read", columnDefinition = "bool")
    private Boolean InstanceAcl.read;
    
    @Column(name = "update", columnDefinition = "bool")
    private Boolean InstanceAcl.update;
    
    @Column(name = "delete", columnDefinition = "bool")
    private Boolean InstanceAcl.delete;
    
    @Column(name = "modacl", columnDefinition = "bool")
    private Boolean InstanceAcl.modacl;
    
    public Instance InstanceAcl.getInstanceId() {
        return instanceId;
    }
    
    public void InstanceAcl.setInstanceId(Instance instanceId) {
        this.instanceId = instanceId;
    }
    
    public Boolean InstanceAcl.getRead() {
        return read;
    }
    
    public void InstanceAcl.setRead(Boolean read) {
        this.read = read;
    }
    
    public Boolean InstanceAcl.getUpdate() {
        return update;
    }
    
    public void InstanceAcl.setUpdate(Boolean update) {
        this.update = update;
    }
    
    public Boolean InstanceAcl.getDelete() {
        return delete;
    }
    
    public void InstanceAcl.setDelete(Boolean delete) {
        this.delete = delete;
    }
    
    public Boolean InstanceAcl.getModacl() {
        return modacl;
    }
    
    public void InstanceAcl.setModacl(Boolean modacl) {
        this.modacl = modacl;
    }
    
}