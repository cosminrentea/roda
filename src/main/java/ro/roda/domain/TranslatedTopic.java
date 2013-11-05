package ro.roda.domain;

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
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "translated_topic")

public class TranslatedTopic {

	public static long countTranslatedTopics() {
		return entityManager().createQuery("SELECT COUNT(o) FROM TranslatedTopic o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(TranslatedTopic translatedTopic) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("translatedtopic_" + translatedTopic.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new TranslatedTopic().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<TranslatedTopic> findAllTranslatedTopics() {
		return entityManager().createQuery("SELECT o FROM TranslatedTopic o", TranslatedTopic.class).getResultList();
	}

	public static TranslatedTopic findTranslatedTopic(TranslatedTopicPK id) {
		if (id == null)
			return null;
		return entityManager().find(TranslatedTopic.class, id);
	}

	public static List<TranslatedTopic> findTranslatedTopicEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM TranslatedTopic o", TranslatedTopic.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<TranslatedTopic> fromJsonArrayToTranslatedTopics(String json) {
		return new JSONDeserializer<List<TranslatedTopic>>().use(null, ArrayList.class)
				.use("values", TranslatedTopic.class).deserialize(json);
	}

	public static TranslatedTopic fromJsonToTranslatedTopic(String json) {
		return new JSONDeserializer<TranslatedTopic>().use(null, TranslatedTopic.class).deserialize(json);
	}

	public static void indexTranslatedTopic(TranslatedTopic translatedTopic) {
		List<TranslatedTopic> translatedtopics = new ArrayList<TranslatedTopic>();
		translatedtopics.add(translatedTopic);
		indexTranslatedTopics(translatedtopics);
	}

	@Async
	public static void indexTranslatedTopics(Collection<TranslatedTopic> translatedtopics) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (TranslatedTopic translatedTopic : translatedtopics) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "translatedtopic_" + translatedTopic.getId());
			sid.addField("translatedTopic.id_t", translatedTopic.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("translatedtopic_solrsummary_t", new StringBuilder().append(translatedTopic.getId()));
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
		String searchString = "TranslatedTopic_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new TranslatedTopic().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<TranslatedTopic> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@EmbeddedId
	private TranslatedTopicPK id;

	@ManyToOne
	@JoinColumn(name = "lang_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Lang langId;

	@ManyToOne
	@JoinColumn(name = "topic_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Topic topicId;

	@Column(name = "translation", columnDefinition = "text")
	@NotNull
	private String translation;

	@PersistenceContext
	transient EntityManager entityManager;

	@Autowired(required=false)
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

	public TranslatedTopicPK getId() {
		return this.id;
	}

	public Lang getLangId() {
		return langId;
	}

	public Topic getTopicId() {
		return topicId;
	}

	public String getTranslation() {
		return translation;
	}

	@Transactional
	public TranslatedTopic merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		TranslatedTopic merged = this.entityManager.merge(this);
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
			TranslatedTopic attached = TranslatedTopic.findTranslatedTopic(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(TranslatedTopicPK id) {
		this.id = id;
	}

	public void setLangId(Lang langId) {
		this.langId = langId;
	}

	public void setTopicId(Topic topicId) {
		this.topicId = topicId;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
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
		indexTranslatedTopic(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
