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
@Table(schema = "public", name = "translated_question")
@Audited
public class TranslatedQuestion {

	public static long countTranslatedQuestions() {
		return entityManager().createQuery("SELECT COUNT(o) FROM TranslatedQuestion o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(TranslatedQuestion translatedQuestion) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("translatedquestion_" + translatedQuestion.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new TranslatedQuestion().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<TranslatedQuestion> findAllTranslatedQuestions() {
		return entityManager().createQuery("SELECT o FROM TranslatedQuestion o", TranslatedQuestion.class)
				.getResultList();
	}

	public static TranslatedQuestion findTranslatedQuestion(TranslatedQuestionPK id) {
		if (id == null)
			return null;
		return entityManager().find(TranslatedQuestion.class, id);
	}

	public static List<TranslatedQuestion> findTranslatedQuestionEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM TranslatedQuestion o", TranslatedQuestion.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<TranslatedQuestion> fromJsonArrayToTranslatedQuestions(String json) {
		return new JSONDeserializer<List<TranslatedQuestion>>().use(null, ArrayList.class)
				.use("values", TranslatedQuestion.class).deserialize(json);
	}

	public static TranslatedQuestion fromJsonToTranslatedQuestion(String json) {
		return new JSONDeserializer<TranslatedQuestion>().use(null, TranslatedQuestion.class).deserialize(json);
	}

	public static void indexTranslatedQuestion(TranslatedQuestion translatedQuestion) {
		List<TranslatedQuestion> translatedquestions = new ArrayList<TranslatedQuestion>();
		translatedquestions.add(translatedQuestion);
		indexTranslatedQuestions(translatedquestions);
	}

	@Async
	public static void indexTranslatedQuestions(Collection<TranslatedQuestion> translatedquestions) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (TranslatedQuestion translatedQuestion : translatedquestions) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "translatedquestion_" + translatedQuestion.getId());
			sid.addField("translatedQuestion.id_t", translatedQuestion.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("translatedquestion_solrsummary_t", new StringBuilder().append(translatedQuestion.getId()));
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
		String searchString = "TranslatedQuestion_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new TranslatedQuestion().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<TranslatedQuestion> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@EmbeddedId
	private TranslatedQuestionPK id;

	@ManyToOne
	@JoinColumn(name = "lang_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Lang langId;

	@ManyToOne
	@JoinColumn(name = "question_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Question questionId;

	@Column(name = "translation", columnDefinition = "text")
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

	public TranslatedQuestionPK getId() {
		return this.id;
	}

	public Lang getLangId() {
		return langId;
	}

	public Question getQuestionId() {
		return questionId;
	}

	public String getTranslation() {
		return translation;
	}

	@Transactional
	public TranslatedQuestion merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		TranslatedQuestion merged = this.entityManager.merge(this);
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
			TranslatedQuestion attached = TranslatedQuestion.findTranslatedQuestion(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(TranslatedQuestionPK id) {
		this.id = id;
	}

	public void setLangId(Lang langId) {
		this.langId = langId;
	}

	public void setQuestionId(Question questionId) {
		this.questionId = questionId;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader")
				.exclude("classAuditReader", "auditReader").serialize(this);
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
		indexTranslatedQuestion(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
