// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import ro.roda.domain.Lang;
import ro.roda.domain.Series;
import ro.roda.domain.SeriesDescr;

privileged aspect SeriesDescr_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "lang_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Lang SeriesDescr.langId;
    
    @ManyToOne
    @JoinColumn(name = "catalog_id", referencedColumnName = "catalog_id", nullable = false, insertable = false, updatable = false)
    private Series SeriesDescr.catalogId;
    
    @Column(name = "notes", columnDefinition = "text")
    private String SeriesDescr.notes;
    
    @Column(name = "title", columnDefinition = "text")
    @NotNull
    private String SeriesDescr.title;
    
    @Column(name = "subtitle", columnDefinition = "text")
    private String SeriesDescr.subtitle;
    
    @Column(name = "alternative_title", columnDefinition = "text")
    private String SeriesDescr.alternativeTitle;
    
    @Column(name = "abstract", columnDefinition = "text")
    private String SeriesDescr.abstract1;
    
    @Column(name = "time_covered", columnDefinition = "text")
    private String SeriesDescr.timeCovered;
    
    @Column(name = "geographic_coverage", columnDefinition = "text")
    private String SeriesDescr.geographicCoverage;
    
    public Lang SeriesDescr.getLangId() {
        return langId;
    }
    
    public void SeriesDescr.setLangId(Lang langId) {
        this.langId = langId;
    }
    
    public Series SeriesDescr.getCatalogId() {
        return catalogId;
    }
    
    public void SeriesDescr.setCatalogId(Series catalogId) {
        this.catalogId = catalogId;
    }
    
    public String SeriesDescr.getNotes() {
        return notes;
    }
    
    public void SeriesDescr.setNotes(String notes) {
        this.notes = notes;
    }
    
    public String SeriesDescr.getTitle() {
        return title;
    }
    
    public void SeriesDescr.setTitle(String title) {
        this.title = title;
    }
    
    public String SeriesDescr.getSubtitle() {
        return subtitle;
    }
    
    public void SeriesDescr.setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
    
    public String SeriesDescr.getAlternativeTitle() {
        return alternativeTitle;
    }
    
    public void SeriesDescr.setAlternativeTitle(String alternativeTitle) {
        this.alternativeTitle = alternativeTitle;
    }
    
    public String SeriesDescr.getAbstract1() {
        return abstract1;
    }
    
    public void SeriesDescr.setAbstract1(String abstract1) {
        this.abstract1 = abstract1;
    }
    
    public String SeriesDescr.getTimeCovered() {
        return timeCovered;
    }
    
    public void SeriesDescr.setTimeCovered(String timeCovered) {
        this.timeCovered = timeCovered;
    }
    
    public String SeriesDescr.getGeographicCoverage() {
        return geographicCoverage;
    }
    
    public void SeriesDescr.setGeographicCoverage(String geographicCoverage) {
        this.geographicCoverage = geographicCoverage;
    }
    
}
