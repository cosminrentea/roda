// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import ro.roda.domain.InstanceDescr;
import ro.roda.domain.Lang;
import ro.roda.domain.SeriesDescr;
import ro.roda.domain.StudyDescr;
import ro.roda.domain.TranslatedTopic;

privileged aspect Lang_Roo_DbManaged {
    
    @OneToMany(mappedBy = "langId")
    private Set<InstanceDescr> Lang.instanceDescrs;
    
    @OneToMany(mappedBy = "langId")
    private Set<SeriesDescr> Lang.seriesDescrs;
    
    @OneToMany(mappedBy = "langId")
    private Set<StudyDescr> Lang.studyDescrs;
    
    @OneToMany(mappedBy = "langId")
    private Set<TranslatedTopic> Lang.translatedTopics;
    
    @Column(name = "iso639", columnDefinition = "bpchar", length = 2, unique = true)
    @NotNull
    private String Lang.iso639;
    
    @Column(name = "name_self", columnDefinition = "varchar", length = 50)
    private String Lang.nameSelf;
    
    @Column(name = "name_ro", columnDefinition = "varchar", length = 50)
    private String Lang.nameRo;
    
    @Column(name = "name_en", columnDefinition = "varchar", length = 50)
    private String Lang.nameEn;
    
    public Set<InstanceDescr> Lang.getInstanceDescrs() {
        return instanceDescrs;
    }
    
    public void Lang.setInstanceDescrs(Set<InstanceDescr> instanceDescrs) {
        this.instanceDescrs = instanceDescrs;
    }
    
    public Set<SeriesDescr> Lang.getSeriesDescrs() {
        return seriesDescrs;
    }
    
    public void Lang.setSeriesDescrs(Set<SeriesDescr> seriesDescrs) {
        this.seriesDescrs = seriesDescrs;
    }
    
    public Set<StudyDescr> Lang.getStudyDescrs() {
        return studyDescrs;
    }
    
    public void Lang.setStudyDescrs(Set<StudyDescr> studyDescrs) {
        this.studyDescrs = studyDescrs;
    }
    
    public Set<TranslatedTopic> Lang.getTranslatedTopics() {
        return translatedTopics;
    }
    
    public void Lang.setTranslatedTopics(Set<TranslatedTopic> translatedTopics) {
        this.translatedTopics = translatedTopics;
    }
    
    public String Lang.getIso639() {
        return iso639;
    }
    
    public void Lang.setIso639(String iso639) {
        this.iso639 = iso639;
    }
    
    public String Lang.getNameSelf() {
        return nameSelf;
    }
    
    public void Lang.setNameSelf(String nameSelf) {
        this.nameSelf = nameSelf;
    }
    
    public String Lang.getNameRo() {
        return nameRo;
    }
    
    public void Lang.setNameRo(String nameRo) {
        this.nameRo = nameRo;
    }
    
    public String Lang.getNameEn() {
        return nameEn;
    }
    
    public void Lang.setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    
}
