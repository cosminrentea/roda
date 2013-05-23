package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(schema = "public",name = "study_person_asoc")
@Configurable






public class StudyPersonAsoc {

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new StudyPersonAsoc().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countStudyPersonAsocs() {
        return entityManager().createQuery("SELECT COUNT(o) FROM StudyPersonAsoc o", Long.class).getSingleResult();
    }

	public static List<StudyPersonAsoc> findAllStudyPersonAsocs() {
        return entityManager().createQuery("SELECT o FROM StudyPersonAsoc o", StudyPersonAsoc.class).getResultList();
    }

	public static StudyPersonAsoc findStudyPersonAsoc(Integer id) {
        if (id == null) return null;
        return entityManager().find(StudyPersonAsoc.class, id);
    }

	public static List<StudyPersonAsoc> findStudyPersonAsocEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM StudyPersonAsoc o", StudyPersonAsoc.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            StudyPersonAsoc attached = StudyPersonAsoc.findStudyPersonAsoc(this.id);
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
    public StudyPersonAsoc merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        StudyPersonAsoc merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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

	@Column(name = "asoc_name", columnDefinition = "varchar", length = 100)
    @NotNull
    private String asocName;

	@Column(name = "asoc_description", columnDefinition = "text")
    private String asocDescription;

	public String getAsocName() {
        return asocName;
    }

	public void setAsocName(String asocName) {
        this.asocName = asocName;
    }

	public String getAsocDescription() {
        return asocDescription;
    }

	public void setAsocDescription(String asocDescription) {
        this.asocDescription = asocDescription;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static StudyPersonAsoc fromJsonToStudyPersonAsoc(String json) {
        return new JSONDeserializer<StudyPersonAsoc>().use(null, StudyPersonAsoc.class).deserialize(json);
    }

	public static String toJsonArray(Collection<StudyPersonAsoc> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<StudyPersonAsoc> fromJsonArrayToStudyPersonAsocs(String json) {
        return new JSONDeserializer<List<StudyPersonAsoc>>().use(null, ArrayList.class).use("values", StudyPersonAsoc.class).deserialize(json);
    }

	@Autowired
    transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
        String searchString = "StudyPersonAsoc_solrsummary_t:" + queryString;
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

	public static void indexStudyPersonAsoc(StudyPersonAsoc studyPersonAsoc) {
        List<StudyPersonAsoc> studypersonasocs = new ArrayList<StudyPersonAsoc>();
        studypersonasocs.add(studyPersonAsoc);
        indexStudyPersonAsocs(studypersonasocs);
    }

	@Async
    public static void indexStudyPersonAsocs(Collection<StudyPersonAsoc> studypersonasocs) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (StudyPersonAsoc studyPersonAsoc : studypersonasocs) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "studypersonasoc_" + studyPersonAsoc.getId());
            sid.addField("studyPersonAsoc.asocname_s", studyPersonAsoc.getAsocName());
            sid.addField("studyPersonAsoc.asocdescription_s", studyPersonAsoc.getAsocDescription());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("studypersonasoc_solrsummary_t", new StringBuilder().append(studyPersonAsoc.getAsocName()).append(" ").append(studyPersonAsoc.getAsocDescription()));
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
    public static void deleteIndex(StudyPersonAsoc studyPersonAsoc) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("studypersonasoc_" + studyPersonAsoc.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@PostUpdate
    @PostPersist
    private void postPersistOrUpdate() {
        indexStudyPersonAsoc(this);
    }

	@PreRemove
    private void preRemove() {
        deleteIndex(this);
    }

	public static SolrServer solrServer() {
        SolrServer _solrServer = new StudyPersonAsoc().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }
}
