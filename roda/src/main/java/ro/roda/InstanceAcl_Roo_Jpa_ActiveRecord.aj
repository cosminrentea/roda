// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.InstanceAcl;
import ro.roda.InstanceAclPK;

privileged aspect InstanceAcl_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager InstanceAcl.entityManager;
    
    public static final EntityManager InstanceAcl.entityManager() {
        EntityManager em = new InstanceAcl().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long InstanceAcl.countInstanceAcls() {
        return entityManager().createQuery("SELECT COUNT(o) FROM InstanceAcl o", Long.class).getSingleResult();
    }
    
    public static List<InstanceAcl> InstanceAcl.findAllInstanceAcls() {
        return entityManager().createQuery("SELECT o FROM InstanceAcl o", InstanceAcl.class).getResultList();
    }
    
    public static InstanceAcl InstanceAcl.findInstanceAcl(InstanceAclPK id) {
        if (id == null) return null;
        return entityManager().find(InstanceAcl.class, id);
    }
    
    public static List<InstanceAcl> InstanceAcl.findInstanceAclEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM InstanceAcl o", InstanceAcl.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void InstanceAcl.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void InstanceAcl.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            InstanceAcl attached = InstanceAcl.findInstanceAcl(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void InstanceAcl.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void InstanceAcl.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public InstanceAcl InstanceAcl.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        InstanceAcl merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
