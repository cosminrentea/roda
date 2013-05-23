// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import ro.roda.domain.Study;
import ro.roda.domain.UnitAnalysis;

privileged aspect UnitAnalysis_Roo_DbManaged {
    
    @OneToMany(mappedBy = "unitAnalysisId")
    private Set<Study> UnitAnalysis.studies;
    
    @Column(name = "name", columnDefinition = "varchar", length = 100)
    @NotNull
    private String UnitAnalysis.name;
    
    @Column(name = "description", columnDefinition = "text")
    private String UnitAnalysis.description;
    
    public Set<Study> UnitAnalysis.getStudies() {
        return studies;
    }
    
    public void UnitAnalysis.setStudies(Set<Study> studies) {
        this.studies = studies;
    }
    
    public String UnitAnalysis.getName() {
        return name;
    }
    
    public void UnitAnalysis.setName(String name) {
        this.name = name;
    }
    
    public String UnitAnalysis.getDescription() {
        return description;
    }
    
    public void UnitAnalysis.setDescription(String description) {
        this.description = description;
    }
    
}
