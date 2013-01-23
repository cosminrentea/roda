// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.SelectionVariableItem;
import ro.roda.SelectionVariableItemPK;

privileged aspect SelectionVariableItem_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager SelectionVariableItem.entityManager;
    
    public static final EntityManager SelectionVariableItem.entityManager() {
        EntityManager em = new SelectionVariableItem().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long SelectionVariableItem.countSelectionVariableItems() {
        return entityManager().createQuery("SELECT COUNT(o) FROM SelectionVariableItem o", Long.class).getSingleResult();
    }
    
    public static List<SelectionVariableItem> SelectionVariableItem.findAllSelectionVariableItems() {
        return entityManager().createQuery("SELECT o FROM SelectionVariableItem o", SelectionVariableItem.class).getResultList();
    }
    
    public static SelectionVariableItem SelectionVariableItem.findSelectionVariableItem(SelectionVariableItemPK id) {
        if (id == null) return null;
        return entityManager().find(SelectionVariableItem.class, id);
    }
    
    public static List<SelectionVariableItem> SelectionVariableItem.findSelectionVariableItemEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM SelectionVariableItem o", SelectionVariableItem.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void SelectionVariableItem.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void SelectionVariableItem.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            SelectionVariableItem attached = SelectionVariableItem.findSelectionVariableItem(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void SelectionVariableItem.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void SelectionVariableItem.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public SelectionVariableItem SelectionVariableItem.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        SelectionVariableItem merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}