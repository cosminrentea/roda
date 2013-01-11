// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import ro.roda.Keyword;
import ro.roda.Study;
import ro.roda.StudyKeyword;

privileged aspect StudyKeyword_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "keyword_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Keyword StudyKeyword.keywordId;
    
    @ManyToOne
    @JoinColumn(name = "study_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Study StudyKeyword.studyId;
    
    @Column(name = "added", columnDefinition = "timestamp")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date StudyKeyword.added;
    
    @Column(name = "added_by", columnDefinition = "int4")
    @NotNull
    private Integer StudyKeyword.addedBy;
    
    public Keyword StudyKeyword.getKeywordId() {
        return keywordId;
    }
    
    public void StudyKeyword.setKeywordId(Keyword keywordId) {
        this.keywordId = keywordId;
    }
    
    public Study StudyKeyword.getStudyId() {
        return studyId;
    }
    
    public void StudyKeyword.setStudyId(Study studyId) {
        this.studyId = studyId;
    }
    
    public Date StudyKeyword.getAdded() {
        return added;
    }
    
    public void StudyKeyword.setAdded(Date added) {
        this.added = added;
    }
    
    public Integer StudyKeyword.getAddedBy() {
        return addedBy;
    }
    
    public void StudyKeyword.setAddedBy(Integer addedBy) {
        this.addedBy = addedBy;
    }
    
}
