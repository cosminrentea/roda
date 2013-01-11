// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.InstanceOrg;
import ro.roda.InstanceOrgPK;

privileged aspect InstanceOrg_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager InstanceOrg.entityManager;
    
    public static final EntityManager InstanceOrg.entityManager() {
        EntityManager em = new InstanceOrg().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long InstanceOrg.countInstanceOrgs() {
        return entityManager().createQuery("SELECT COUNT(o) FROM InstanceOrg o", Long.class).getSingleResult();
    }
    
    public static List<InstanceOrg> InstanceOrg.findAllInstanceOrgs() {
        return entityManager().createQuery("SELECT o FROM InstanceOrg o", InstanceOrg.class).getResultList();
    }
    
    public static InstanceOrg InstanceOrg.findInstanceOrg(InstanceOrgPK id) {
        if (id == null) return null;
        return entityManager().find(InstanceOrg.class, id);
    }
    
    public static List<InstanceOrg> InstanceOrg.findInstanceOrgEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM InstanceOrg o", InstanceOrg.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void InstanceOrg.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void InstanceOrg.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            InstanceOrg attached = InstanceOrg.findInstanceOrg(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void InstanceOrg.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void InstanceOrg.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public InstanceOrg InstanceOrg.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        InstanceOrg merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
