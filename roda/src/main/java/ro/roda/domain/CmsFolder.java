package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
@Table(schema = "public",name = "cms_folder")






public class CmsFolder {

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static CmsFolder fromJsonToCmsFolder(String json) {
        return new JSONDeserializer<CmsFolder>().use(null, CmsFolder.class).deserialize(json);
    }

	public static String toJsonArray(Collection<CmsFolder> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<CmsFolder> fromJsonArrayToCmsFolders(String json) {
        return new JSONDeserializer<List<CmsFolder>>().use(null, ArrayList.class).use("values", CmsFolder.class).deserialize(json);
    }

	@OneToMany(mappedBy = "cmsFolderId")
    private Set<CmsFile> cmsFiles;

	@OneToMany(mappedBy = "parentId")
    private Set<CmsFolder> cmsFolders;

	@ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CmsFolder parentId;

	@Column(name = "name", columnDefinition = "text")
    @NotNull
    private String name;

	@Column(name = "description", columnDefinition = "text")
    private String description;

	public Set<CmsFile> getCmsFiles() {
        return cmsFiles;
    }

	public void setCmsFiles(Set<CmsFile> cmsFiles) {
        this.cmsFiles = cmsFiles;
    }

	public Set<CmsFolder> getCmsFolders() {
        return cmsFolders;
    }

	public void setCmsFolders(Set<CmsFolder> cmsFolders) {
        this.cmsFolders = cmsFolders;
    }

	public CmsFolder getParentId() {
        return parentId;
    }

	public void setParentId(CmsFolder parentId) {
        this.parentId = parentId;
    }

	public String getName() {
        return name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public String getDescription() {
        return description;
    }

	public void setDescription(String description) {
        this.description = description;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "serial")
    private Integer id;

	public Integer getId() {
        return this.id;
    }

	public void setId(Integer id) {
        this.id = id;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new CmsFolder().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countCmsFolders() {
        return entityManager().createQuery("SELECT COUNT(o) FROM CmsFolder o", Long.class).getSingleResult();
    }

	public static List<CmsFolder> findAllCmsFolders() {
        return entityManager().createQuery("SELECT o FROM CmsFolder o", CmsFolder.class).getResultList();
    }

	public static CmsFolder findCmsFolder(Integer id) {
        if (id == null) return null;
        return entityManager().find(CmsFolder.class, id);
    }

	public static List<CmsFolder> findCmsFolderEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CmsFolder o", CmsFolder.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            CmsFolder attached = CmsFolder.findCmsFolder(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public CmsFolder merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CmsFolder merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@Autowired
    transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
        String searchString = "CmsFolder_solrsummary_t:" + queryString;
        return search(new SolrQuery(searchString.toLowerCase()));
    }

	public static QueryResponse search(SolrQuery query) {
        try {
            return solrServer().query(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new QueryResponse();
    }

	public static void indexCmsFolder(CmsFolder cmsFolder) {
        List<CmsFolder> cmsfolders = new ArrayList<CmsFolder>();
        cmsfolders.add(cmsFolder);
        indexCmsFolders(cmsfolders);
    }

	@Async
    public static void indexCmsFolders(Collection<CmsFolder> cmsfolders) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (CmsFolder cmsFolder : cmsfolders) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "cmsfolder_" + cmsFolder.getId());
            sid.addField("cmsFolder.parentid_t", cmsFolder.getParentId());
            sid.addField("cmsFolder.name_s", cmsFolder.getName());
            sid.addField("cmsFolder.description_s", cmsFolder.getDescription());
            sid.addField("cmsFolder.id_i", cmsFolder.getId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("cmsfolder_solrsummary_t", new StringBuilder().append(cmsFolder.getParentId()).append(" ").append(cmsFolder.getName()).append(" ").append(cmsFolder.getDescription()).append(" ").append(cmsFolder.getId()));
            documents.add(sid);
        }
        try {
            SolrServer solrServer = solrServer();
            solrServer.add(documents);
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Async
    public static void deleteIndex(CmsFolder cmsFolder) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("cmsfolder_" + cmsFolder.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@PostUpdate
    @PostPersist
    private void postPersistOrUpdate() {
        indexCmsFolder(this);
    }

	@PreRemove
    private void preRemove() {
        deleteIndex(this);
    }

	public static SolrServer solrServer() {
        SolrServer _solrServer = new CmsFolder().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }
}
