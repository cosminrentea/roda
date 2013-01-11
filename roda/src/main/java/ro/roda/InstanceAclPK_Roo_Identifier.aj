// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import ro.roda.InstanceAclPK;

privileged aspect InstanceAclPK_Roo_Identifier {
    
    declare @type: InstanceAclPK: @Embeddable;
    
    @Column(name = "instance_id", columnDefinition = "int4", nullable = false)
    private Integer InstanceAclPK.instanceId;
    
    @Column(name = "aro_id", columnDefinition = "int4", nullable = false)
    private Integer InstanceAclPK.aroId;
    
    @Column(name = "aro_type", columnDefinition = "int4", nullable = false)
    private Integer InstanceAclPK.aroType;
    
    public InstanceAclPK.new(Integer instanceId, Integer aroId, Integer aroType) {
        super();
        this.instanceId = instanceId;
        this.aroId = aroId;
        this.aroType = aroType;
    }

    private InstanceAclPK.new() {
        super();
    }

    public Integer InstanceAclPK.getInstanceId() {
        return instanceId;
    }
    
    public Integer InstanceAclPK.getAroId() {
        return aroId;
    }
    
    public Integer InstanceAclPK.getAroType() {
        return aroType;
    }
    
}
