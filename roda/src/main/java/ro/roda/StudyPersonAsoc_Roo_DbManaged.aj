// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import ro.roda.StudyPersonAsoc;

privileged aspect StudyPersonAsoc_Roo_DbManaged {
    
    @Column(name = "asoc_name", columnDefinition = "varchar", length = 100)
    @NotNull
    private String StudyPersonAsoc.asocName;
    
    @Column(name = "asoc_description", columnDefinition = "text")
    private String StudyPersonAsoc.asocDescription;
    
    public String StudyPersonAsoc.getAsocName() {
        return asocName;
    }
    
    public void StudyPersonAsoc.setAsocName(String asocName) {
        this.asocName = asocName;
    }
    
    public String StudyPersonAsoc.getAsocDescription() {
        return asocDescription;
    }
    
    public void StudyPersonAsoc.setAsocDescription(String asocDescription) {
        this.asocDescription = asocDescription;
    }
    
}