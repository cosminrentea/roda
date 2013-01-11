// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.Value;

privileged aspect Value_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Value.entityManager;
    
    public static final EntityManager Value.entityManager() {
        EntityManager em = new Value().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Value.countValues() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Value o", Long.class).getSingleResult();
    }
    
    public static List<Value> Value.findAllValues() {
        return entityManager().createQuery("SELECT o FROM Value o", Value.class).getResultList();
    }
    
    public static Value Value.findValue(Integer itemId) {
        if (itemId == null) return null;
        return entityManager().find(Value.class, itemId);
    }
    
    public static List<Value> Value.findValueEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Value o", Value.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Value.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Value.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Value attached = Value.findValue(this.itemId);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Value.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Value.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Value Value.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Value merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
