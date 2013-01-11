// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.PersonRole;

privileged aspect PersonRole_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager PersonRole.entityManager;
    
    public static final EntityManager PersonRole.entityManager() {
        EntityManager em = new PersonRole().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long PersonRole.countPersonRoles() {
        return entityManager().createQuery("SELECT COUNT(o) FROM PersonRole o", Long.class).getSingleResult();
    }
    
    public static List<PersonRole> PersonRole.findAllPersonRoles() {
        return entityManager().createQuery("SELECT o FROM PersonRole o", PersonRole.class).getResultList();
    }
    
    public static PersonRole PersonRole.findPersonRole(Integer id) {
        if (id == null) return null;
        return entityManager().find(PersonRole.class, id);
    }
    
    public static List<PersonRole> PersonRole.findPersonRoleEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM PersonRole o", PersonRole.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void PersonRole.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void PersonRole.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            PersonRole attached = PersonRole.findPersonRole(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void PersonRole.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void PersonRole.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public PersonRole PersonRole.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        PersonRole merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
