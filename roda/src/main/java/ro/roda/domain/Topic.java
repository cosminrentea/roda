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
import javax.persistence.ManyToMany;
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
@Table(schema = "public",name = "topic")






public class Topic {

	@Autowired
    transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
        String searchString = "Topic_solrsummary_t:" + queryString;
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

	public static void indexTopic(Topic topic) {
        List<Topic> topics = new ArrayList<Topic>();
        topics.add(topic);
        indexTopics(topics);
    }

	@Async
    public static void indexTopics(Collection<Topic> topics) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (Topic topic : topics) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "topic_" + topic.getId());
            sid.addField("topic.parentid_t", topic.getParentId());
            sid.addField("topic.preferredsynonymtopicid_t", topic.getPreferredSynonymTopicId());
            sid.addField("topic.name_s", topic.getName());
            sid.addField("topic.description_s", topic.getDescription());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("topic_solrsummary_t", new StringBuilder().append(topic.getParentId()).append(" ").append(topic.getPreferredSynonymTopicId()).append(" ").append(topic.getName()).append(" ").append(topic.getDescription()));
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
    public static void deleteIndex(Topic topic) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("topic_" + topic.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@PostUpdate
    @PostPersist
    private void postPersistOrUpdate() {
        indexTopic(this);
    }

	@PreRemove
    private void preRemove() {
        deleteIndex(this);
    }

	public static SolrServer solrServer() {
        SolrServer _solrServer = new Topic().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static Topic fromJsonToTopic(String json) {
        return new JSONDeserializer<Topic>().use(null, Topic.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Topic> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Topic> fromJsonArrayToTopics(String json) {
        return new JSONDeserializer<List<Topic>>().use(null, ArrayList.class).use("values", Topic.class).deserialize(json);
    }

	@ManyToMany(mappedBy = "topics")
    private Set<Series> series;

	@ManyToMany(mappedBy = "topics")
    private Set<Study> studies;

	@OneToMany(mappedBy = "parentId")
    private Set<Topic> topics;

	@OneToMany(mappedBy = "preferredSynonymTopicId")
    private Set<Topic> topics1;

	@OneToMany(mappedBy = "topicId")
    private Set<TranslatedTopic> translatedTopics;

	@ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Topic parentId;

	@ManyToOne
    @JoinColumn(name = "preferred_synonym_topic_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Topic preferredSynonymTopicId;

	@Column(name = "name", columnDefinition = "varchar", length = 100)
    @NotNull
    private String name;

	@Column(name = "description", columnDefinition = "text")
    private String description;

	public Set<Series> getSeries() {
        return series;
    }

	public void setSeries(Set<Series> series) {
        this.series = series;
    }

	public Set<Study> getStudies() {
        return studies;
    }

	public void setStudies(Set<Study> studies) {
        this.studies = studies;
    }

	public Set<Topic> getTopics() {
        return topics;
    }

	public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }

	public Set<Topic> getTopics1() {
        return topics1;
    }

	public void setTopics1(Set<Topic> topics1) {
        this.topics1 = topics1;
    }

	public Set<TranslatedTopic> getTranslatedTopics() {
        return translatedTopics;
    }

	public void setTranslatedTopics(Set<TranslatedTopic> translatedTopics) {
        this.translatedTopics = translatedTopics;
    }

	public Topic getParentId() {
        return parentId;
    }

	public void setParentId(Topic parentId) {
        this.parentId = parentId;
    }

	public Topic getPreferredSynonymTopicId() {
        return preferredSynonymTopicId;
    }

	public void setPreferredSynonymTopicId(Topic preferredSynonymTopicId) {
        this.preferredSynonymTopicId = preferredSynonymTopicId;
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

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Topic().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countTopics() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Topic o", Long.class).getSingleResult();
    }

	public static List<Topic> findAllTopics() {
        return entityManager().createQuery("SELECT o FROM Topic o", Topic.class).getResultList();
    }

	public static Topic findTopic(Integer id) {
        if (id == null) return null;
        return entityManager().find(Topic.class, id);
    }

	public static List<Topic> findTopicEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Topic o", Topic.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Topic attached = Topic.findTopic(this.id);
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
    public Topic merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Topic merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
