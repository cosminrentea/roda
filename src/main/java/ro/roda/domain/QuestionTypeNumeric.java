package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(schema = "public", name = "question_type_category")
@Configurable
@Audited
public class QuestionTypeNumeric {

	public static long countQuestionTypeNumerics() {
		return entityManager().createQuery("SELECT COUNT(o) FROM QuestionTypeNumeric o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(QuestionTypeNumeric questionTypeNumeric) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("questiontypenumeric_" + questionTypeNumeric.getQuestionId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new QuestionTypeNumeric().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<QuestionTypeNumeric> findAllQuestionTypeNumerics() {
		return entityManager().createQuery("SELECT o FROM QuestionTypeNumeric o", QuestionTypeNumeric.class)
				.getResultList();
	}

	public static QuestionTypeNumeric findQuestionTypeNumeric(Long id) {
		if (id == null)
			return null;
		return entityManager().find(QuestionTypeNumeric.class, id);
	}

	public static List<QuestionTypeNumeric> findQuestionTypeNumericEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM QuestionTypeNumeric o", QuestionTypeNumeric.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<QuestionTypeNumeric> fromJsonArrayToQuestionTypeNumerics(String json) {
		return new JSONDeserializer<List<QuestionTypeNumeric>>().use(null, ArrayList.class)
				.use("values", QuestionTypeNumeric.class).deserialize(json);
	}

	public static QuestionTypeNumeric fromJsonToQuestionTypeNumeric(String json) {
		return new JSONDeserializer<QuestionTypeNumeric>().use(null, QuestionTypeNumeric.class).deserialize(json);
	}

	public static void indexQuestionTypeNumeric(QuestionTypeNumeric questionTypeNumeric) {
		List<QuestionTypeNumeric> selectionvariableitems = new ArrayList<QuestionTypeNumeric>();
		selectionvariableitems.add(questionTypeNumeric);
		indexQuestionTypeNumerics(selectionvariableitems);
	}

	@Async
	public static void indexQuestionTypeNumerics(Collection<QuestionTypeNumeric> selectionvariableitems) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (QuestionTypeNumeric questionTypeNumeric : selectionvariableitems) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "questiontypenumeric_" + questionTypeNumeric.getQuestionId());
			sid.addField("questionTypeNumeric.type_t", questionTypeNumeric.getType());
			sid.addField("questionTypeNumeric.high_t", questionTypeNumeric.getHigh());
			sid.addField("questionTypeNumeric.low_t", questionTypeNumeric.getLow());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"selectionvariableitem_solrsummary_t",
					new StringBuilder().append(questionTypeNumeric.getQuestionId()).append(" ")
							.append(questionTypeNumeric.getType()).append(questionTypeNumeric.getHigh())
							.append(questionTypeNumeric.getLow()));
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
		String searchString = "QuestionTypeNumeric_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new QuestionTypeNumeric().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<QuestionTypeNumeric> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@OneToOne
	@JoinColumn(name = "question_id", nullable = false, insertable = false, updatable = false)
	private Question question;

	@Column(name = "type", columnDefinition = "varchar", length = 200)
	private String type;

	@Column(name = "high", columnDefinition = "int8")
	private Integer high;

	@Column(name = "low", columnDefinition = "int8")
	private Integer low;

	@Id
	@Column(name = "question_id", columnDefinition = "bigint")
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

	public Question getQuestionId() {
		return questionId;
	}

	@Transactional
	public QuestionTypeNumeric merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		QuestionTypeNumeric merged = this.entityManager.merge(this);
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
			QuestionTypeNumeric attached = QuestionTypeNumeric.findQuestionTypeNumeric(this.questionId.getId());
			this.entityManager.remove(attached);
		}
	}

	public Question getQuestion() {
		return question;
	}

	public String getType() {
		return type;
	}

	public Integer getHigh() {
		return high;
	}

	public Integer getLow() {
		return low;
	}

	public void setQuestionId(Question questionId) {
		this.questionId = questionId;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setHigh(Integer high) {
		this.high = high;
	}

	public void setLow(Integer low) {
		this.low = low;
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
		indexQuestionTypeNumeric(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
