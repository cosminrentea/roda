// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import ro.roda.Internet;
import ro.roda.Person;

privileged aspect Internet_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "entity_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Person Internet.entityId;
    
    @Column(name = "entity_type", columnDefinition = "int4")
    @NotNull
    private Integer Internet.entityType;
    
    @Column(name = "internet_type_id", columnDefinition = "int4")
    @NotNull
    private Integer Internet.internetTypeId;
    
    @Column(name = "content", columnDefinition = "text")
    @NotNull
    private String Internet.content;
    
    public Person Internet.getEntityId() {
        return entityId;
    }
    
    public void Internet.setEntityId(Person entityId) {
        this.entityId = entityId;
    }
    
    public Integer Internet.getEntityType() {
        return entityType;
    }
    
    public void Internet.setEntityType(Integer entityType) {
        this.entityType = entityType;
    }
    
    public Integer Internet.getInternetTypeId() {
        return internetTypeId;
    }
    
    public void Internet.setInternetTypeId(Integer internetTypeId) {
        this.internetTypeId = internetTypeId;
    }
    
    public String Internet.getContent() {
        return content;
    }
    
    public void Internet.setContent(String content) {
        this.content = content;
    }
    
}