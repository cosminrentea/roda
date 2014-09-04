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
@Table(schema = "public", name = "question_type_category")
@Configurable
@Audited
public class QuestionTypeCode {

	public static long countSelectionQuestions() {
		return entityManager().createQuery("SELECT COUNT(o) FROM SelectionQuestion o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(QuestionTypeCode selectionQuestion) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("questiontypecode_" + selectionQuestion.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new QuestionTypeCode().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<QuestionTypeCode> findAllSelectionQuestions() {
		return entityManager().createQuery("SELECT o FROM SelectionQuestion o", QuestionTypeCode.class).getResultList();
	}

	public static QuestionTypeCode findSelectionQuestion(QuestionTypeCategoryPK id) {
		if (id == null)
			return null;
		return entityManager().find(QuestionTypeCode.class, id);
	}

	public static List<QuestionTypeCode> findSelectionQuestionEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM SelectionQuestion o", QuestionTypeCode.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<QuestionTypeCode> fromJsonArrayToSelectionQuestions(String json) {
		return new JSONDeserializer<List<QuestionTypeCode>>().use(null, ArrayList.class)
				.use("values", QuestionTypeCode.class).deserialize(json);
	}

	public static QuestionTypeCode fromJsonToSelectionQuestion(String json) {
		return new JSONDeserializer<QuestionTypeCode>().use(null, QuestionTypeCode.class).deserialize(json);
	}

	public static void indexSelectionQuestion(QuestionTypeCode selectionQuestion) {
		List<QuestionTypeCode> selectionvariableitems = new ArrayList<QuestionTypeCode>();
		selectionvariableitems.add(selectionQuestion);
		indexSelectionQuestions(selectionvariableitems);
	}

	@Async
	public static void indexSelectionQuestions(Collection<QuestionTypeCode> selectionvariableitems) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (QuestionTypeCode selectionQuestion : selectionvariableitems) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "selectionquestion_" + selectionQuestion.getId());
			sid.addField("selectionQuestion.categoryid_t", selectionQuestion.getCategoryId());
			sid.addField("selectionQuestion.questionid_t", selectionQuestion.getQuestionId());
			sid.addField("selectionQuestion.label_t", selectionQuestion.getLabel());
			sid.addField("selectionQuestion.id_t", selectionQuestion.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"selectionvariableitem_solrsummary_t",
					new StringBuilder().append(selectionQuestion.getCategoryId()).append(" ")
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
		SolrServer _solrServer = new QuestionTypeCode().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<QuestionTypeCode> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@EmbeddedId
	private QuestionTypeCategoryPK id;

	@Column(name = "category_id", columnDefinition = "bigint", nullable = false, insertable = false, updatable = false)
	private Long categoryId;

	@Column(name = "label", columnDefinition = "varchar", length = 200)
	private String label;

	@Column(name = "value", columnDefinition = "int8")
	private Integer value;

	@ManyToOne
	@JoinColumn(name = "question_id", columnDefinition = "bigint", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
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

	public QuestionTypeCategoryPK getId() {
		return this.id;
	}

	public Question getQuestionId() {
		return questionId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public String getLabel() {
		return label;
	}

	public Integer getValue() {
		return value;
	}

	@Transactional
	public QuestionTypeCode merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		QuestionTypeCode merged = this.entityManager.merge(this);
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
			QuestionTypeCode attached = QuestionTypeCode.findSelectionQuestion(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(QuestionTypeCategoryPK id) {
		this.id = id;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public void setQuestionId(Question questionId) {
		this.questionId = questionId;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setValue(Integer value) {
		this.value = value;
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
