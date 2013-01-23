// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import ro.roda.StudyPerson;
import ro.roda.StudyPersonAssoc;

privileged aspect StudyPersonAssoc_Roo_DbManaged {
    
    @OneToMany(mappedBy = "assoctypeId")
    private Set<StudyPerson> StudyPersonAssoc.studypeople;
    
    @Column(name = "asoc_name", columnDefinition = "varchar", length = 100)
    @NotNull
    private String StudyPersonAssoc.asocName;
    
    @Column(name = "asoc_description", columnDefinition = "text")
    private String StudyPersonAssoc.asocDescription;
    
    public Set<StudyPerson> StudyPersonAssoc.getStudypeople() {
        return studypeople;
    }
    
    public void StudyPersonAssoc.setStudypeople(Set<StudyPerson> studypeople) {
        this.studypeople = studypeople;
    }
    
    public String StudyPersonAssoc.getAsocName() {
        return asocName;
    }
    
    public void StudyPersonAssoc.setAsocName(String asocName) {
        this.asocName = asocName;
    }
    
    public String StudyPersonAssoc.getAsocDescription() {
        return asocDescription;
    }
    
    public void StudyPersonAssoc.setAsocDescription(String asocDescription) {
        this.asocDescription = asocDescription;
    }
    
}