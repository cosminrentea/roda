// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import java.util.Calendar;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import ro.roda.domain.File;
import ro.roda.domain.Instance;
import ro.roda.domain.InstanceDescr;
import ro.roda.domain.InstanceForm;
import ro.roda.domain.InstanceOrg;
import ro.roda.domain.InstancePerson;
import ro.roda.domain.InstanceRightTargetGroup;
import ro.roda.domain.InstanceVariable;
import ro.roda.domain.Study;
import ro.roda.domain.Users;

privileged aspect Instance_Roo_DbManaged {
    
    @ManyToMany(mappedBy = "instances")
    private Set<File> Instance.files;
    
    @OneToMany(mappedBy = "instanceId")
    private Set<InstanceDescr> Instance.instanceDescrs;
    
    @OneToMany(mappedBy = "instanceId")
    private Set<InstanceForm> Instance.instanceForms;
    
    @OneToMany(mappedBy = "instanceId")
    private Set<InstanceOrg> Instance.instanceOrgs;
    
    @OneToMany(mappedBy = "instanceId")
    private Set<InstancePerson> Instance.instancepeople;
    
    @OneToMany(mappedBy = "instanceId")
    private Set<InstanceRightTargetGroup> Instance.instanceRightTargetGroups;
    
    @OneToMany(mappedBy = "instanceId")
    private Set<InstanceVariable> Instance.instanceVariables;
    
    @ManyToOne
    @JoinColumn(name = "study_id", referencedColumnName = "id", nullable = false)
    private Study Instance.studyId;
    
    @ManyToOne
    @JoinColumn(name = "added_by", referencedColumnName = "id", nullable = false)
    private Users Instance.addedBy;
    
    @Column(name = "added", columnDefinition = "timestamp")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar Instance.added;
    
    @Column(name = "disseminator_identifier", columnDefinition = "text")
    private String Instance.disseminatorIdentifier;
    
    @Column(name = "main", columnDefinition = "bool")
    @NotNull
    private boolean Instance.main;
    
    public Set<File> Instance.getFiles() {
        return files;
    }
    
    public void Instance.setFiles(Set<File> files) {
        this.files = files;
    }
    
    public Set<InstanceDescr> Instance.getInstanceDescrs() {
        return instanceDescrs;
    }
    
    public void Instance.setInstanceDescrs(Set<InstanceDescr> instanceDescrs) {
        this.instanceDescrs = instanceDescrs;
    }
    
    public Set<InstanceForm> Instance.getInstanceForms() {
        return instanceForms;
    }
    
    public void Instance.setInstanceForms(Set<InstanceForm> instanceForms) {
        this.instanceForms = instanceForms;
    }
    
    public Set<InstanceOrg> Instance.getInstanceOrgs() {
        return instanceOrgs;
    }
    
    public void Instance.setInstanceOrgs(Set<InstanceOrg> instanceOrgs) {
        this.instanceOrgs = instanceOrgs;
    }
    
    public Set<InstancePerson> Instance.getInstancepeople() {
        return instancepeople;
    }
    
    public void Instance.setInstancepeople(Set<InstancePerson> instancepeople) {
        this.instancepeople = instancepeople;
    }
    
    public Set<InstanceRightTargetGroup> Instance.getInstanceRightTargetGroups() {
        return instanceRightTargetGroups;
    }
    
    public void Instance.setInstanceRightTargetGroups(Set<InstanceRightTargetGroup> instanceRightTargetGroups) {
        this.instanceRightTargetGroups = instanceRightTargetGroups;
    }
    
    public Set<InstanceVariable> Instance.getInstanceVariables() {
        return instanceVariables;
    }
    
    public void Instance.setInstanceVariables(Set<InstanceVariable> instanceVariables) {
        this.instanceVariables = instanceVariables;
    }
    
    public Study Instance.getStudyId() {
        return studyId;
    }
    
    public void Instance.setStudyId(Study studyId) {
        this.studyId = studyId;
    }
    
    public Users Instance.getAddedBy() {
        return addedBy;
    }
    
    public void Instance.setAddedBy(Users addedBy) {
        this.addedBy = addedBy;
    }
    
    public Calendar Instance.getAdded() {
        return added;
    }
    
    public void Instance.setAdded(Calendar added) {
        this.added = added;
    }
    
    public String Instance.getDisseminatorIdentifier() {
        return disseminatorIdentifier;
    }
    
    public void Instance.setDisseminatorIdentifier(String disseminatorIdentifier) {
        this.disseminatorIdentifier = disseminatorIdentifier;
    }
    
    public boolean Instance.isMain() {
        return main;
    }
    
    public void Instance.setMain(boolean main) {
        this.main = main;
    }
    
}
