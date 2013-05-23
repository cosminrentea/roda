// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import ro.roda.domain.SamplingProcedure;
import ro.roda.domain.Study;

privileged aspect SamplingProcedure_Roo_DbManaged {
    
    @ManyToMany
    @JoinTable(name = "instance_sampling_procedure", joinColumns = { @JoinColumn(name = "sampling_procedure_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "study_id", nullable = false) })
    private Set<Study> SamplingProcedure.studies;
    
    @Column(name = "name", columnDefinition = "varchar", length = 100)
    @NotNull
    private String SamplingProcedure.name;
    
    @Column(name = "description", columnDefinition = "text")
    private String SamplingProcedure.description;
    
    public Set<Study> SamplingProcedure.getStudies() {
        return studies;
    }
    
    public void SamplingProcedure.setStudies(Set<Study> studies) {
        this.studies = studies;
    }
    
    public String SamplingProcedure.getName() {
        return name;
    }
    
    public void SamplingProcedure.setName(String name) {
        this.name = name;
    }
    
    public String SamplingProcedure.getDescription() {
        return description;
    }
    
    public void SamplingProcedure.setDescription(String description) {
        this.description = description;
    }
    
}
