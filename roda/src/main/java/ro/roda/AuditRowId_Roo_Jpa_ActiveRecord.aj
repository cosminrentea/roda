// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.AuditRowId;
import ro.roda.AuditRowIdPK;

privileged aspect AuditRowId_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager AuditRowId.entityManager;
    
    public static final EntityManager AuditRowId.entityManager() {
        EntityManager em = new AuditRowId().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long AuditRowId.countAuditRowIds() {
        return entityManager().createQuery("SELECT COUNT(o) FROM AuditRowId o", Long.class).getSingleResult();
    }
    
    public static List<AuditRowId> AuditRowId.findAllAuditRowIds() {
        return entityManager().createQuery("SELECT o FROM AuditRowId o", AuditRowId.class).getResultList();
    }
    
    public static AuditRowId AuditRowId.findAuditRowId(AuditRowIdPK id) {
        if (id == null) return null;
        return entityManager().find(AuditRowId.class, id);
    }
    
    public static List<AuditRowId> AuditRowId.findAuditRowIdEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM AuditRowId o", AuditRowId.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void AuditRowId.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void AuditRowId.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            AuditRowId attached = AuditRowId.findAuditRowId(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void AuditRowId.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void AuditRowId.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public AuditRowId AuditRowId.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        AuditRowId merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}