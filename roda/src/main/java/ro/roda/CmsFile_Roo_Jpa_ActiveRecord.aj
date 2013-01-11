// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.CmsFile;

privileged aspect CmsFile_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager CmsFile.entityManager;
    
    public static final EntityManager CmsFile.entityManager() {
        EntityManager em = new CmsFile().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long CmsFile.countCmsFiles() {
        return entityManager().createQuery("SELECT COUNT(o) FROM CmsFile o", Long.class).getSingleResult();
    }
    
    public static List<CmsFile> CmsFile.findAllCmsFiles() {
        return entityManager().createQuery("SELECT o FROM CmsFile o", CmsFile.class).getResultList();
    }
    
    public static CmsFile CmsFile.findCmsFile(Integer id) {
        if (id == null) return null;
        return entityManager().find(CmsFile.class, id);
    }
    
    public static List<CmsFile> CmsFile.findCmsFileEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CmsFile o", CmsFile.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void CmsFile.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void CmsFile.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            CmsFile attached = CmsFile.findCmsFile(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void CmsFile.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void CmsFile.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public CmsFile CmsFile.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CmsFile merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
