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
@Table(schema = "public", name = "question")
@Configurable
@Audited
public class Question {

	public static long countQuestions() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Question o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Question question) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("question_" + question.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Question().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Question> findAllQuestions() {
		return entityManager().createQuery("SELECT o FROM Question o", Question.class).getResultList();
	}

	public static Question findQuestion(Long id) {
		if (id == null)
			return null;
		return entityManager().find(Question.class, id);
	}

	public static List<Question> findQuestionEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Question o", Question.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<Question> fromJsonArrayToQuestions(String json) {
		return new JSONDeserializer<List<Question>>().use(null, ArrayList.class).use("values", Question.class)
				.deserialize(json);
	}

	public static Question fromJsonToQuestion(String json) {
		return new JSONDeserializer<Question>().use(null, Question.class).deserialize(json);
	}

	public static void indexQuestion(Question question) {
		List<Question> questions = new ArrayList<Question>();
		questions.add(question);
		indexQuestions(questions);
	}

	@Async
	public static void indexQuestions(Collection<Question> questions) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Question question : questions) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "question_" + question.getId());
			sid.addField("question.label_s", question.getLabel());
			sid.addField("question.statement_s", question.getStatement());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("question_solrsummary_t",
					new StringBuilder().append(question.getLabel()).append(" ").append(question.getStatement()));
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
		String searchString = "Question_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Question().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Question> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>Question</code> (question) in
	 * baza de date; in caz afirmativ il returneaza, altfel, metoda il introduce
	 * in baza de date si apoi il returneaza. Verificarea existentei in baza de
	 * date se realizeaza fie dupa identificator, fie dupa un criteriu de
	 * unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>label, instanceId
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul intrebarii.
	 * @param label
	 *            - eticheta intrebarii.
	 * @param instanceId
	 *            - identificatorul instantei.
	 * @param statement
	 *            - textul intrebarii.
	 * @return
	 */
	public static Question checkQuestion(Long id, String label, Long instanceId, String statement) {
		Question object;

		if (id != null) {
			object = findQuestion(id);

			if (object != null) {
				return object;
			}
		}

		List<Question> queryResult;

		if (label != null && instanceId != null) {
			TypedQuery<Question> query = entityManager().createQuery(
					"SELECT o FROM Question o WHERE lower(o.label) = lower(:label) AND o.instanceId = :instanceId",
					Question.class);
			query.setParameter("label", label);
			query.setParameter("instanceId", instanceId);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new Question();
		object.label = label;
		object.statement = statement;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@Column(name = "statement", columnDefinition = "text")
	private String statement;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "label", columnDefinition = "text")
	@NotNull
	private String label;

	@OneToMany(mappedBy = "questionId")
	private Set<Variable> variables;

	@ManyToOne
	@JoinColumn(name = "instance_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Instance instanceId;

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

	public String getStatement() {
		return statement;
	}

	public Long getId() {
		return this.id;
	}

	public String getLabel() {
		return label;
	}

	public Set<Variable> getVariables() {
		return variables;
	}

	public Instance getInstanceId() {
		return instanceId;
	}

	@Transactional
	public Question merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Question merged = this.entityManager.merge(this);
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
			Question attached = Question.findQuestion(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setVariables(Set<Variable> variables) {
		this.variables = variables;
	}

	public void setInstanceId(Instance instanceId) {
		this.instanceId = instanceId;
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
		indexQuestion(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return ((id != null && id.equals(((Question) obj).id)) || (label != null
				&& label.equalsIgnoreCase(((Question) obj).label) && instanceId == ((Question) obj).instanceId));
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
