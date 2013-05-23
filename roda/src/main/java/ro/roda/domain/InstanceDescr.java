package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

@Entity
@Table(schema = "public",name = "instance_descr")
@Configurable






public class InstanceDescr {

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static InstanceDescr fromJsonToInstanceDescr(String json) {
        return new JSONDeserializer<InstanceDescr>().use(null, InstanceDescr.class).deserialize(json);
    }

	public static String toJsonArray(Collection<InstanceDescr> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<InstanceDescr> fromJsonArrayToInstanceDescrs(String json) {
        return new JSONDeserializer<List<InstanceDescr>>().use(null, ArrayList.class).use("values", InstanceDescr.class).deserialize(json);
    }

	@EmbeddedId
    private InstanceDescrPK id;

	public InstanceDescrPK getId() {
        return this.id;
    }

	public void setId(InstanceDescrPK id) {
        this.id = id;
    }

	@Autowired
    transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
        String searchString = "InstanceDescr_solrsummary_t:" + queryString;
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

	public static void indexInstanceDescr(InstanceDescr instanceDescr) {
        List<InstanceDescr> instancedescrs = new ArrayList<InstanceDescr>();
        instancedescrs.add(instanceDescr);
        indexInstanceDescrs(instancedescrs);
    }

	@Async
    public static void indexInstanceDescrs(Collection<InstanceDescr> instancedescrs) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (InstanceDescr instanceDescr : instancedescrs) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "instancedescr_" + instanceDescr.getId());
            sid.addField("instanceDescr.instanceid_t", instanceDescr.getInstanceId());
            sid.addField("instanceDescr.langid_t", instanceDescr.getLangId());
            sid.addField("instanceDescr.accessconditions_s", instanceDescr.getAccessConditions());
            sid.addField("instanceDescr.notes_s", instanceDescr.getNotes());
            sid.addField("instanceDescr.title_s", instanceDescr.getTitle());
            sid.addField("instanceDescr.id_t", instanceDescr.getId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("instancedescr_solrsummary_t", new StringBuilder().append(instanceDescr.getInstanceId()).append(" ").append(instanceDescr.getLangId()).append(" ").append(instanceDescr.getAccessConditions()).append(" ").append(instanceDescr.getNotes()).append(" ").append(instanceDescr.getTitle()).append(" ").append(instanceDescr.getId()));
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
    public static void deleteIndex(InstanceDescr instanceDescr) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("instancedescr_" + instanceDescr.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@PostUpdate
    @PostPersist
    private void postPersistOrUpdate() {
        indexInstanceDescr(this);
    }

	@PreRemove
    private void preRemove() {
        deleteIndex(this);
    }

	public static SolrServer solrServer() {
        SolrServer _solrServer = new InstanceDescr().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new InstanceDescr().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countInstanceDescrs() {
        return entityManager().createQuery("SELECT COUNT(o) FROM InstanceDescr o", Long.class).getSingleResult();
    }

	public static List<InstanceDescr> findAllInstanceDescrs() {
        return entityManager().createQuery("SELECT o FROM InstanceDescr o", InstanceDescr.class).getResultList();
    }

	public static InstanceDescr findInstanceDescr(InstanceDescrPK id) {
        if (id == null) return null;
        return entityManager().find(InstanceDescr.class, id);
    }

	public static List<InstanceDescr> findInstanceDescrEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM InstanceDescr o", InstanceDescr.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            InstanceDescr attached = InstanceDescr.findInstanceDescr(this.id);
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
    public InstanceDescr merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        InstanceDescr merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	@ManyToOne
    @JoinColumn(name = "instance_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Instance instanceId;

	@ManyToOne
    @JoinColumn(name = "lang_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Lang langId;

	@Column(name = "access_conditions", columnDefinition = "text")
    private String accessConditions;

	@Column(name = "notes", columnDefinition = "text")
    private String notes;

	@Column(name = "title", columnDefinition = "text")
    @NotNull
    private String title;

	@Column(name = "original_title_language", columnDefinition = "bool")
    @NotNull
    private boolean originalTitleLanguage;

	public Instance getInstanceId() {
        return instanceId;
    }

	public void setInstanceId(Instance instanceId) {
        this.instanceId = instanceId;
    }

	public Lang getLangId() {
        return langId;
    }

	public void setLangId(Lang langId) {
        this.langId = langId;
    }

	public String getAccessConditions() {
        return accessConditions;
    }

	public void setAccessConditions(String accessConditions) {
        this.accessConditions = accessConditions;
    }

	public String getNotes() {
        return notes;
    }

	public void setNotes(String notes) {
        this.notes = notes;
    }

	public String getTitle() {
        return title;
    }

	public void setTitle(String title) {
        this.title = title;
    }

	public boolean isOriginalTitleLanguage() {
        return originalTitleLanguage;
    }

	public void setOriginalTitleLanguage(boolean originalTitleLanguage) {
        this.originalTitleLanguage = originalTitleLanguage;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
