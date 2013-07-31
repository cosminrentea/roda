package ro.roda.domain;

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
import javax.persistence.TypedQuery;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "topic")
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
		return new JSONSerializer().exclude("*.class").serialize(collection);
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
	 * @param description
	 *            - descrierea subiectului.
	 * @return
	 */
	public static Topic checkTopic(Integer id, String name, String description) {
		Topic object;

		if (id != null) {
			object = findTopic(id);

			if (object != null) {
				return object;
			}
		}

		List<Topic> queryResult;

		if (name != null) {
			TypedQuery<Topic> query = entityManager().createQuery(
					"SELECT o FROM Topic o WHERE lower(o.name) = lower(:name)", Topic.class);
			query.setParameter("name", name);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		return null;
	}

	@Column(name = "description", columnDefinition = "text")
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "varchar", length = 100, unique = true)
	@NotNull
	private String name;

	@ManyToOne
	@JoinColumn(name = "parent_id", columnDefinition = "integer", referencedColumnName = "id", insertable = false, updatable = false)
	private Topic parentId;

	@ManyToOne
	@JoinColumn(name = "preferred_synonym_topic_id", columnDefinition = "integer", referencedColumnName = "id", insertable = false, updatable = false)
	private Topic preferredSynonymTopicId;

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

	public Set<Topic> getTopics1() {
		return topics1;
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

	public void setTopics1(Set<Topic> topics1) {
		this.topics1 = topics1;
	}

	public void setTranslatedTopics(Set<TranslatedTopic> translatedTopics) {
		this.translatedTopics = translatedTopics;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
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
	public boolean equals(Object obj) {
		return (id != null && id.equals(((Topic) obj).id))
				|| (name != null && name.equalsIgnoreCase(((Topic) obj).name));
	}
}
