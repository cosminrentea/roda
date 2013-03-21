// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.AuditLogAction;

privileged aspect AuditLogAction_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager AuditLogAction.entityManager;
    
    public static final EntityManager AuditLogAction.entityManager() {
        EntityManager em = new AuditLogAction().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long AuditLogAction.countAuditLogActions() {
        return entityManager().createQuery("SELECT COUNT(o) FROM AuditLogAction o", Long.class).getSingleResult();
    }
    
    public static List<AuditLogAction> AuditLogAction.findAllAuditLogActions() {
        return entityManager().createQuery("SELECT o FROM AuditLogAction o", AuditLogAction.class).getResultList();
    }
    
    public static AuditLogAction AuditLogAction.findAuditLogAction(Integer id) {
        if (id == null) return null;
        return entityManager().find(AuditLogAction.class, id);
    }
    
    public static List<AuditLogAction> AuditLogAction.findAuditLogActionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM AuditLogAction o", AuditLogAction.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void AuditLogAction.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void AuditLogAction.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            AuditLogAction attached = AuditLogAction.findAuditLogAction(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void AuditLogAction.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void AuditLogAction.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public AuditLogAction AuditLogAction.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        AuditLogAction merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}