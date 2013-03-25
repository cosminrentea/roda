// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import ro.roda.domain.InstanceDescrPK;

privileged aspect InstanceDescrPK_Roo_Identifier {
    
    declare @type: InstanceDescrPK: @Embeddable;
    
    @Column(name = "instance_id", columnDefinition = "int4", nullable = false)
    private Integer InstanceDescrPK.instanceId;
    
    @Column(name = "lang_id", columnDefinition = "int4", nullable = false)
    private Integer InstanceDescrPK.langId;
    
    public InstanceDescrPK.new(Integer instanceId, Integer langId) {
        super();
        this.instanceId = instanceId;
        this.langId = langId;
    }

    private InstanceDescrPK.new() {
        super();
    }

    public Integer InstanceDescrPK.getInstanceId() {
        return instanceId;
    }
    
    public Integer InstanceDescrPK.getLangId() {
        return langId;
    }
    
}