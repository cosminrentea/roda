package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

@Configurable
@Entity
@Table(schema = "public", name = "question_type")
@Audited
public class QuestionType {

	public static long countQuestionTypes() {
		return entityManager().createQuery("SELECT COUNT(o) FROM QuestionType o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(QuestionType questionType) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("questiontype_" + questionType.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new QuestionType().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<QuestionType> findAllQuestionTypes() {
		return entityManager().createQuery("SELECT o FROM QuestionType o", QuestionType.class).getResultList();
	}

	public static QuestionType findQuestionType(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(QuestionType.class, id);
	}

	public static List<QuestionType> findQuestionTypeEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM QuestionType o", QuestionType.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<QuestionType> fromJsonArrayToQuestionTypes(String json) {
		return new JSONDeserializer<List<QuestionType>>().use(null, ArrayList.class).use("values", QuestionType.class)
				.deserialize(json);
	}

	public static QuestionType fromJsonToQuestionType(String json) {
		return new JSONDeserializer<QuestionType>().use(null, QuestionType.class).deserialize(json);
	}

	public static void indexQuestionType(QuestionType questionType) {
		List<QuestionType> questiontypes = new ArrayList<QuestionType>();
		questiontypes.add(questionType);
		indexQuestionTypes(questiontypes);
	}

	@Async
	public static void indexQuestionTypes(Collection<QuestionType> questiontypes) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (QuestionType questionType : questiontypes) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "questiontype_" + questionType.getId());
			sid.addField("questionType.id_i", questionType.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("questiontype_solrsummary_t", new StringBuilder().append(questionType.getId()));
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
		String searchString = "QuestionType_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new QuestionType().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<QuestionType> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>QuestionType</code> (tip de
	 * intrebare) in baza de date; in caz afirmativ il returneaza, altfel,
	 * metoda il introduce in baza de date si apoi il returneaza. Verificarea
	 * existentei in baza de date se realizeaza fie dupa identificator, fie dupa
	 * un criteriu de unicitate.
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
	 *            - identificatorul tipului.
	 * @param name
	 *            - numele tipului.
	 * @param description
	 *            - descrierea tipului.
	 * @return
	 */
	public static QuestionType checkQuestionType(Integer id, String name, String description) {
		QuestionType object;

		if (id != null) {
			object = findQuestionType(id);

			if (object != null) {
				return object;
			}
		}

		QuestionType queryResult;

		if (name != null) {
			TypedQuery<QuestionType> query = entityManager().createQuery(
					"SELECT o FROM QuestionType o WHERE lower(o.name) = lower(:name)", QuestionType.class);
			query.setParameter("name", name);

			query.setMaxResults(1);
			query.setFlushMode(FlushModeType.COMMIT);

			try {
				queryResult = query.getSingleResult();
				return queryResult;
			} catch (Exception exception) {
			}
		}

		object = new QuestionType();
		object.name = name;
		object.description = description;
		object.persist();
		object.flush();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	public static int QSTN_TYPE_CATEGORY = 1;

	public static int QSTN_TYPE_CODE = 2;

	public static int QSTN_TYPE_NUMERIC = 3;

	@OneToMany(mappedBy = "questionTypeId")
	private Set<Question> questions;

	@Column(name = "description", columnDefinition = "text")
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "varchar", length = 200)
	@NotNull
	private String name;

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

	public Set<Question> getQuestions() {
		return questions;
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

	@Transactional
	public QuestionType merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		QuestionType merged = this.entityManager.merge(this);
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
			QuestionType attached = QuestionType.findQuestionType(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
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

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexQuestionType(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((QuestionType) obj).id))
				|| (name != null && name.equalsIgnoreCase(((QuestionType) obj).name));
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
