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

@Entity
@Table(schema = "public", name = "question_type_string")
@Configurable
@Audited
public class QuestionTypeString {

	public static long countQuestionTypeCodes() {
		return entityManager().createQuery("SELECT COUNT(o) FROM QuestionTypeString o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(QuestionTypeString selectionQuestion) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("questiontypestring_" + selectionQuestion.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new QuestionTypeString().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<QuestionTypeString> findAllQuestionTypeCodes() {
		return entityManager().createQuery("SELECT o FROM QuestionTypeString o", QuestionTypeString.class)
				.getResultList();
	}

	public static QuestionTypeString findQuestionTypeCode(QuestionTypeStringPK id) {
		if (id == null)
			return null;
		return entityManager().find(QuestionTypeString.class, id);
	}

	public static List<QuestionTypeString> findQuestionTypeCodeEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM QuestionTypeCode o", QuestionTypeString.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<QuestionTypeString> fromJsonArrayToSelectionQuestions(String json) {
		return new JSONDeserializer<List<QuestionTypeString>>().use(null, ArrayList.class)
				.use("values", QuestionTypeString.class).deserialize(json);
	}

	public static QuestionTypeString fromJsonToSelectionQuestion(String json) {
		return new JSONDeserializer<QuestionTypeString>().use(null, QuestionTypeString.class).deserialize(json);
	}

	public static void indexSelectionQuestion(QuestionTypeString selectionQuestion) {
		List<QuestionTypeString> selectionvariableitems = new ArrayList<QuestionTypeString>();
		selectionvariableitems.add(selectionQuestion);
		indexSelectionQuestions(selectionvariableitems);
	}

	@Async
	public static void indexSelectionQuestions(Collection<QuestionTypeString> selectionvariableitems) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (QuestionTypeString selectionQuestion : selectionvariableitems) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "selectionquestion_" + selectionQuestion.getId());
			sid.addField("selectionQuestion.categoryid_t", selectionQuestion.getStringId());
			sid.addField("selectionQuestion.questionid_t", selectionQuestion.getQuestionId());
			sid.addField("selectionQuestion.id_t", selectionQuestion.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"selectionvariableitem_solrsummary_t",
					new StringBuilder().append(selectionQuestion.getStringId()).append(" ")
							.append(selectionQuestion.getQuestionId()).append(" ").append(selectionQuestion.getId()));
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
		String searchString = "SelectionQuestion_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new QuestionTypeString().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<QuestionTypeString> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@EmbeddedId
	private QuestionTypeStringPK id;

	@Column(name = "string_id", columnDefinition = "int4", nullable = false, insertable = false, updatable = false)
	private Integer stringId;

	@ManyToOne
	@JoinColumn(name = "question_id", columnDefinition = "int8", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Question questionId;

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

	public QuestionTypeStringPK getId() {
		return this.id;
	}

	public Question getQuestionId() {
		return questionId;
	}

	public Integer getStringId() {
		return stringId;
	}

	@Transactional
	public QuestionTypeString merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		QuestionTypeString merged = this.entityManager.merge(this);
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
			QuestionTypeString attached = QuestionTypeString.findQuestionTypeCode(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(QuestionTypeStringPK id) {
		this.id = id;
	}

	public void setStringId(Integer stringId) {
		this.stringId = stringId;
	}

	public void setQuestionId(Question questionId) {
		this.questionId = questionId;
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
		indexSelectionQuestion(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
