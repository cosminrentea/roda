// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import ro.roda.Concept;
import ro.roda.Variable;

privileged aspect Concept_Roo_DbManaged {
    
    @ManyToMany
    @JoinTable(name = "concept_variable", joinColumns = { @JoinColumn(name = "concept_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "variable_id", nullable = false) })
    private Set<Variable> Concept.variables;
    
    @Column(name = "name", columnDefinition = "varchar", length = 100)
    @NotNull
    private String Concept.name;
    
    @Column(name = "description", columnDefinition = "text")
    private String Concept.description;
    
    public Set<Variable> Concept.getVariables() {
        return variables;
    }
    
    public void Concept.setVariables(Set<Variable> variables) {
        this.variables = variables;
    }
    
    public String Concept.getName() {
        return name;
    }
    
    public void Concept.setName(String name) {
        this.name = name;
    }
    
    public String Concept.getDescription() {
        return description;
    }
    
    public void Concept.setDescription(String description) {
        this.description = description;
    }
    
}
