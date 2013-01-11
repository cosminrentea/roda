// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.Prefix;

privileged aspect Prefix_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Prefix.entityManager;
    
    public static final EntityManager Prefix.entityManager() {
        EntityManager em = new Prefix().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Prefix.countPrefixes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Prefix o", Long.class).getSingleResult();
    }
    
    public static List<Prefix> Prefix.findAllPrefixes() {
        return entityManager().createQuery("SELECT o FROM Prefix o", Prefix.class).getResultList();
    }
    
    public static Prefix Prefix.findPrefix(Integer id) {
        if (id == null) return null;
        return entityManager().find(Prefix.class, id);
    }
    
    public static List<Prefix> Prefix.findPrefixEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Prefix o", Prefix.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Prefix.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Prefix.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Prefix attached = Prefix.findPrefix(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Prefix.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Prefix.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Prefix Prefix.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Prefix merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
