// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import ro.roda.domain.InstanceRight;
import ro.roda.domain.InstanceRightTargetGroup;
import ro.roda.domain.InstanceRightValue;

privileged aspect InstanceRightValue_Roo_DbManaged {
    
    @OneToMany(mappedBy = "instanceRightValueId")
    private Set<InstanceRightTargetGroup> InstanceRightValue.instanceRightTargetGroups;
    
    @ManyToOne
    @JoinColumn(name = "instance_right_id", referencedColumnName = "id", nullable = false)
    private InstanceRight InstanceRightValue.instanceRightId;
    
    @Column(name = "value", columnDefinition = "int4")
    @NotNull
    private Integer InstanceRightValue.value;
    
    @Column(name = "description", columnDefinition = "text")
    private String InstanceRightValue.description;
    
    @Column(name = "fee", columnDefinition = "int4")
    private Integer InstanceRightValue.fee;
    
    @Column(name = "fee_currency_abbr", columnDefinition = "varchar", length = 3)
    private String InstanceRightValue.feeCurrencyAbbr;
    
    public Set<InstanceRightTargetGroup> InstanceRightValue.getInstanceRightTargetGroups() {
        return instanceRightTargetGroups;
    }
    
    public void InstanceRightValue.setInstanceRightTargetGroups(Set<InstanceRightTargetGroup> instanceRightTargetGroups) {
        this.instanceRightTargetGroups = instanceRightTargetGroups;
    }
    
    public InstanceRight InstanceRightValue.getInstanceRightId() {
        return instanceRightId;
    }
    
    public void InstanceRightValue.setInstanceRightId(InstanceRight instanceRightId) {
        this.instanceRightId = instanceRightId;
    }
    
    public Integer InstanceRightValue.getValue() {
        return value;
    }
    
    public void InstanceRightValue.setValue(Integer value) {
        this.value = value;
    }
    
    public String InstanceRightValue.getDescription() {
        return description;
    }
    
    public void InstanceRightValue.setDescription(String description) {
        this.description = description;
    }
    
    public Integer InstanceRightValue.getFee() {
        return fee;
    }
    
    public void InstanceRightValue.setFee(Integer fee) {
        this.fee = fee;
    }
    
    public String InstanceRightValue.getFeeCurrencyAbbr() {
        return feeCurrencyAbbr;
    }
    
    public void InstanceRightValue.setFeeCurrencyAbbr(String feeCurrencyAbbr) {
        this.feeCurrencyAbbr = feeCurrencyAbbr;
    }
    
}
