package ro.roda.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(schema = "public", name = "study_saved")
@Configurable
@Audited
public class StudySaved implements Serializable {

	public static long countStudySaveds() {
		return entityManager().createQuery("SELECT COUNT(o) FROM StudySaved o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(StudySaved StudySaved) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("StudySaved_" + StudySaved.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new StudySaved().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<StudySaved> findAllStudySaveds() {
		return entityManager().createQuery("SELECT o FROM StudySaved o", StudySaved.class).getResultList();
	}

	public static StudySaved findStudySaved(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(StudySaved.class, id);
	}

	public static List<StudySaved> findStudySavedEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM StudySaved o", StudySaved.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<StudySaved> fromJsonArrayToStudySaveds(String json) {
		return new JSONDeserializer<List<StudySaved>>().use(null, ArrayList.class).use("values", StudySaved.class)
				.deserialize(json);
	}

	public static StudySaved fromJsonToStudySaved(String json) {
		return new JSONDeserializer<StudySaved>().use(null, StudySaved.class).deserialize(json);
	}

	public static void indexStudySaved(StudySaved StudySaved) {
		List<StudySaved> StudySaveds = new ArrayList<StudySaved>();
		StudySaveds.add(StudySaved);
		indexStudySaveds(StudySaveds);
	}

	@Async
	public static void indexStudySaveds(Collection<StudySaved> StudySaveds) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (StudySaved StudySaved : StudySaveds) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "StudySaved_" + StudySaved.getId());
			sid.addField("StudySaved.id_i", StudySaved.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("StudySaved_solrsummary_t", new StringBuilder().append(StudySaved.getName()).append(" ")
					.append(StudySaved.getId()));
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
		String searchString = "StudySaved_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new StudySaved().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<StudySaved> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>StudySaved</code> (fisier) in
	 * baza de date; in caz afirmativ il returneaza, altfel, metoda il introduce
	 * in baza de date si apoi il returneaza. Verificarea existentei in baza de
	 * date se realizeaza fie dupa identificator, fie dupa un criteriu de
	 * unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>title
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul fisierului
	 * @param phase
	 *            - etapa de salvare
	 * @param name
	 *            - numele fisierului JSON
	 * @param contentType
	 *            - tipul de continut (de obicei json)
	 * @return
	 */
	public static StudySaved checkStudySaved(Integer id, Integer userId, Integer studyId, Integer phase, String name) {
		StudySaved object;

		if (id != null) {
			object = findStudySaved(id);

			if (object != null) {
				return object;
			}
		}

		Study study = Study.findStudy(studyId);
		Users user = Users.findUsers(userId);

		List<StudySaved> queryResult;
		TypedQuery<StudySaved> query = entityManager()
				.createQuery(
						"SELECT o FROM StudySaved o WHERE o.userId = :userId AND o.studyId = :studyId AND o.phase = :phase AND lower(o.name) = lower(:name)",
						StudySaved.class);
		query.setParameter("studyId", study);
		query.setParameter("userId", user);
		query.setParameter("phase", phase);
		query.setParameter("name", name);

		queryResult = query.getResultList();
		if (queryResult.size() > 0) {
			return queryResult.get(0);
		}

		object = new StudySaved();
		object.studyId = study;
		object.userId = user;
		object.phase = phase;
		object.name = name;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "serial")
	private Integer id;

	@Column(name = "save_time", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Calendar saveTime;

	@ManyToOne
	@JoinColumn(name = "user_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false)
	private Users userId;

	@ManyToOne
	@JoinColumn(name = "study_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false)
	private Study studyId;

	@Column(name = "content_type", columnDefinition = "varchar", length = 100)
	private String contentType;

	@Column(name = "content", columnDefinition = "text")
	private String content;

	@Column(name = "phase", columnDefinition = "int4")
	private Integer phase;

	@Column(name = "name", columnDefinition = "text")
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

	public String getContentType() {
		return contentType;
	}

	public String getContent() {
		return content;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public Integer getPhase() {
		return phase;
	}

	@Transactional
	public StudySaved merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		StudySaved merged = this.entityManager.merge(this);
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
			StudySaved attached = StudySaved.findStudySaved(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhase(Integer phase) {
		this.phase = phase;
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
		indexStudySaved(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StudySaved) {
			final StudySaved other = (StudySaved) obj;
			return new EqualsBuilder().append(userId, other.userId).append(studyId, other.studyId)
					.append(phase, other.phase).append(name, other.name).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(userId).append(studyId).append(phase).append(name).toHashCode();
	}

	@JsonIgnore public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}

	public Calendar getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(Calendar saveTime) {
		this.saveTime = saveTime;
	}

	public Users getUserId() {
		return userId;
	}

	public void setUserId(Users userId) {
		this.userId = userId;
	}

	public Study getStudyId() {
		return studyId;
	}

	public void setStudyId(Study studyId) {
		this.studyId = studyId;
	}
}
