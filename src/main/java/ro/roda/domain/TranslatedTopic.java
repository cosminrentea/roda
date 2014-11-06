package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "translated_topic")
@Audited
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
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	/**
	 * Verifica existenta unui obiect de tip <code>TranslatedTopic</code> in
	 * baza de date. In caz afirmativ il returneaza, altfel il creeaza si apoi
	 * il returneaza.
	 */
	public static TranslatedTopic findOrCreateTranslatedTopic(String name, Integer langId) {
		TranslatedTopic translatedTopic = null;

		if (name != null) {
			try {
				TypedQuery<TranslatedTopic> query = entityManager().createQuery(
						"SELECT tt FROM TranslatedTopic tt WHERE tt.translation = :name AND tt.langId.id = :langId",
						TranslatedTopic.class);
				query.setParameter("name", name);
				query.setParameter("langId", langId);
				query.setMaxResults(1);
				query.setFlushMode(FlushModeType.COMMIT);
				translatedTopic = query.getSingleResult();
			} catch (Exception exception) {
			}
		}

		if (translatedTopic == null) {
			Topic topic = new Topic();
			topic.setParentId(null);
			topic.persist();

			translatedTopic = new TranslatedTopic();
			translatedTopic.setId(new TranslatedTopicPK(langId, topic.getId()));
			translatedTopic.setLangId(Lang.findLang(langId));
			translatedTopic.setTopicId(topic);
			translatedTopic.setTranslation(name);
			translatedTopic.persist();
		}

		return translatedTopic;
	}

	public static List<TranslatedTopic> findTranslatedTopicsByParent(String parentId) {
		List<TranslatedTopic> topics;
		if (parentId == null || "0".equalsIgnoreCase(parentId)) {
			// parent is null or "root" (as sent by ExtJS)
			// => first-level of topics
			topics = entityManager()
					.createQuery(
							"SELECT tt FROM TranslatedTopic tt WHERE tt.topicId.parentId IS NULL AND tt.langId.iso639 = :language",
							TranslatedTopic.class)
					.setParameter("language", LocaleContextHolder.getLocale().getLanguage()).getResultList();
		} else {
			// we assume parentId is a regular number
			topics = entityManager()
					.createQuery(
							"SELECT tt FROM TranslatedTopic tt WHERE tt.topicId.parentId = :parentId AND tt.langId.iso639 = :language",
							TranslatedTopic.class).setParameter("parentId", Topic.findTopic(Integer.valueOf(parentId)))
					.setParameter("language", LocaleContextHolder.getLocale().getLanguage()).getResultList();
		}

		return topics;
	}

	public static String toJsonByParent(String parentId) {
		return new JSONSerializer().include("translation", "indice", "leaf").exclude("*.*").rootName("topics")
				.serialize(findTranslatedTopicsByParent(parentId));
	}

	public static String toJsonRelevantTree() {
		// TODO Cosmin Auto-generated method stub
		return null;
	}

	@EmbeddedId
	private TranslatedTopicPK id;

	@ManyToOne
	@JoinColumn(name = "lang_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Lang langId;

	@ManyToOne
	@JoinColumn(name = "topic_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Topic topicId;

	@Column(name = "translation", columnDefinition = "varchar", length = 50)
	@NotNull
	private String translation;

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

	public Boolean getLeaf() {
		return topicId.getLeaf();
	}

	public Integer getIndice() {
		return topicId.getId();
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
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
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
