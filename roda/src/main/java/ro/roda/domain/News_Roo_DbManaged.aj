// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import ro.roda.domain.News;

privileged aspect News_Roo_DbManaged {
    
    @Column(name = "added", columnDefinition = "timestamp")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar News.added;
    
    @Column(name = "visible", columnDefinition = "bool")
    @NotNull
    private boolean News.visible;
    
    @Column(name = "title", columnDefinition = "text")
    @NotNull
    private String News.title;
    
    @Column(name = "content", columnDefinition = "text")
    private String News.content;
    
    public Calendar News.getAdded() {
        return added;
    }
    
    public void News.setAdded(Calendar added) {
        this.added = added;
    }
    
    public boolean News.isVisible() {
        return visible;
    }
    
    public void News.setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public String News.getTitle() {
        return title;
    }
    
    public void News.setTitle(String title) {
        this.title = title;
    }
    
    public String News.getContent() {
        return content;
    }
    
    public void News.setContent(String content) {
        this.content = content;
    }
    
}
