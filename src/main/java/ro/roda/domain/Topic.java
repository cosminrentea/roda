package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.FlushModeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "topic", indexes = @Index(columnList = "name"))
@Audited
public class Topic {

	public static long countTopics() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Topic o", Long.class).getSingleResult();
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

	public static final EntityManager entityManager() {
		EntityManager em = new Topic().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Topic> findAllTopics() {
		return entityManager().createQuery("SELECT o FROM Topic o", Topic.class).getResultList();
	}

	public static Topic findTopic(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Topic.class, id);
	}

	public static List<Topic> findTopicEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Topic o", Topic.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<Topic> fromJsonArrayToTopics(String json) {
		return new JSONDeserializer<List<Topic>>().use(null, ArrayList.class).use("values", Topic.class)
				.deserialize(json);
	}

	public static Topic fromJsonToTopic(String json) {
		return new JSONDeserializer<Topic>().use(null, Topic.class).deserialize(json);
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
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"topic_solrsummary_t",
					new StringBuilder().append(topic.getParentId()).append(" ")
							.append(topic.getPreferredSynonymTopicId()).append(" ").append(topic.getName()).append(" ")
							.append(topic.getDescription()));
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

	public static QueryResponse search(SolrQuery query) {
		try {
			return solrServer().query(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new QueryResponse();
	}

	public static QueryResponse search(String queryString) {
		String searchString = "Topic_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Topic().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Topic> collection) {
		return new JSONSerializer().exclude("*.class", "classAuditReader", "auditReader").serialize(collection);
	}

	public static List<Topic> findTopicsByParent(String parentId) {
		List<Topic> topics;
		if (parentId == null || "root".equalsIgnoreCase(parentId)) {
			// parent is null or "root" (as sent by ExtJS) => first-level of
			// topics
			topics = Topic.entityManager().createQuery("SELECT o FROM Topic o WHERE o.parentId IS NULL", Topic.class)
					.getResultList();
		} else {
			// we assume parentId is a number
			topics = Topic.entityManager()
					.createQuery("SELECT o FROM Topic o WHERE o.parentId = :parentId", Topic.class)
					.setParameter("parentId", findTopic(Integer.valueOf(parentId))).getResultList();
		}
		// if (topics != null && topics.size() > 0) {
		// Iterator<Topic> topicIterator = topics.iterator();
		// while (topicIterator.hasNext()) {
		// Topic topic = (Topic) topicIterator.next();
		// result.add(findTopic(topic.getId()));
		// }
		// }

		return topics;
	}

	public static String toJsonByParent(String parentId) {
		return new JSONSerializer().include("id", "name", "leaf").exclude("*", "*.*").rootName("topics")
				.serialize(findTopicsByParent(parentId));
	}

	/**
	 * Verifica existenta unui obiect de tip <code>Topic</code> (subiect) in
	 * baza de date; in caz afirmativ il returneaza, altfel, metoda returneaza
	 * null. Verificarea existentei in baza de date se realizeaza fie dupa
	 * identificator, fie dupa un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>name
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul subiectului.
	 * @param name
	 *            - numele subiectului.
	 * @return
	 */
	public static Topic checkTopic(Integer id, String name) {
		Topic object;

		if (id != null) {
			object = findTopic(id);
			if (object != null) {
				return object;
			}
		}

		if (name != null) {
			TypedQuery<Topic> query = entityManager().createQuery(
					"SELECT o FROM Topic o WHERE lower(o.name) = lower(:name)", Topic.class);
			query.setParameter("name", name);
			query.setMaxResults(1);
			query.setFlushMode(FlushModeType.COMMIT);
			try {
				Topic queryResult = query.getSingleResult();
				return queryResult;
			} catch (Exception exception) {
			}
		}
		return null;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@Column(name = "description", columnDefinition = "text")
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "varchar", length = 50, unique = true)
	@NotNull
	private String name;

	// Original: insertable = false, updatable = false
	@ManyToOne
	@JoinColumn(name = "parent_id", columnDefinition = "integer", referencedColumnName = "id")
	private Topic parentId;

	@ManyToOne
	@JoinColumn(name = "preferred_synonym_topic_id", columnDefinition = "integer", referencedColumnName = "id")
	private Topic preferredSynonymTopicId;

	@ManyToMany(mappedBy = "topics", fetch = FetchType.LAZY)
	private Set<Series> series;

	@ManyToMany(mappedBy = "topics", fetch = FetchType.LAZY)
	private Set<Study> studies;

	@OneToMany(mappedBy = "parentId", fetch = FetchType.LAZY)
	private Set<Topic> topics;

	@OneToMany(mappedBy = "preferredSynonymTopicId", fetch = FetchType.LAZY)
	private Set<Topic> synonimTopics;

	@OneToMany(mappedBy = "topicId", fetch = FetchType.LAZY)
	private Set<TranslatedTopic> translatedTopics;

	@PersistenceContext
	transient EntityManager entityManager;

	@Autowired(required = false)
	transient SolrServer solrServer;

	@Transactional
	public void clear() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.clear();
	}

	@Transactional
	public void flush() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.flush();
	}

	public String getDescription() {
		return description;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public Topic getParentId() {
		return parentId;
	}

	public Topic getPreferredSynonymTopicId() {
		return preferredSynonymTopicId;
	}

	public Set<Series> getSeries() {
		return series;
	}

	public Set<Study> getStudies() {
		return studies;
	}

	public Set<Topic> getTopics() {
		return topics;
	}

	public Set<Topic> getSynonimTopics() {
		return synonimTopics;
	}

	public Set<TranslatedTopic> getTranslatedTopics() {
		return translatedTopics;
	}

	@Transactional
	public Topic merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Topic merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	@Transactional
	public Topic merge(boolean doFlush) {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Topic merged = this.entityManager.merge(this);
		if (doFlush) {
			this.entityManager.flush();
		}
		return merged;
	}

	@Transactional
	public void persist() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
	}

	@Transactional
	public void remove() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		if (this.entityManager.contains(this)) {
			this.entityManager.remove(this);
		} else {
			Topic attached = Topic.findTopic(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentId(Topic parentId) {
		this.parentId = parentId;
	}

	public void setPreferredSynonymTopicId(Topic preferredSynonymTopicId) {
		this.preferredSynonymTopicId = preferredSynonymTopicId;
	}

	public void setSeries(Set<Series> series) {
		this.series = series;
	}

	public void setStudies(Set<Study> studies) {
		this.studies = studies;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}

	public void setSynonimTopics(Set<Topic> synonims) {
		this.synonimTopics = synonims;
	}

	public void setTranslatedTopics(Set<TranslatedTopic> translatedTopics) {
		this.translatedTopics = translatedTopics;
	}

	public Boolean getLeaf() {
		return new Boolean(topics == null || topics.size() == 0);
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Topic) {
			final Topic other = (Topic) obj;
			return new EqualsBuilder().append(name, other.name).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(name).toHashCode();
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
