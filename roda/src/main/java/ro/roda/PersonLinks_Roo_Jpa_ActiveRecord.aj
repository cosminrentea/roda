// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.PersonLinks;

privileged aspect PersonLinks_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager PersonLinks.entityManager;
    
    public static final EntityManager PersonLinks.entityManager() {
        EntityManager em = new PersonLinks().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long PersonLinks.countPersonLinkses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM PersonLinks o", Long.class).getSingleResult();
    }
    
    public static List<PersonLinks> PersonLinks.findAllPersonLinkses() {
        return entityManager().createQuery("SELECT o FROM PersonLinks o", PersonLinks.class).getResultList();
    }
    
    public static PersonLinks PersonLinks.findPersonLinks(Integer id) {
        if (id == null) return null;
        return entityManager().find(PersonLinks.class, id);
    }
    
    public static List<PersonLinks> PersonLinks.findPersonLinksEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM PersonLinks o", PersonLinks.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void PersonLinks.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void PersonLinks.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            PersonLinks attached = PersonLinks.findPersonLinks(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void PersonLinks.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void PersonLinks.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public PersonLinks PersonLinks.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        PersonLinks merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
