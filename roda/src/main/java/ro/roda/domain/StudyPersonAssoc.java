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
@Table(schema = "public", name = "study_person_assoc")
public class StudyPersonAssoc {

	public static long countStudyPersonAssocs() {
		return entityManager().createQuery(
				"SELECT COUNT(o) FROM StudyPersonAssoc o", Long.class)
				.getSingleResult();
	}

	@Async
	public static void deleteIndex(StudyPersonAssoc studyPersonAssoc) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("studypersonassoc_"
					+ studyPersonAssoc.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new StudyPersonAssoc().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<StudyPersonAssoc> findAllStudyPersonAssocs() {
		return entityManager().createQuery("SELECT o FROM StudyPersonAssoc o",
				StudyPersonAssoc.class).getResultList();
	}

	public static StudyPersonAssoc findStudyPersonAssoc(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(StudyPersonAssoc.class, id);
	}

	public static List<StudyPersonAssoc> findStudyPersonAssocEntries(
			int firstResult, int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM StudyPersonAssoc o",
						StudyPersonAssoc.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<StudyPersonAssoc> fromJsonArrayToStudyPersonAssocs(
			String json) {
		return new JSONDeserializer<List<StudyPersonAssoc>>()
				.use(null, ArrayList.class)
				.use("values", StudyPersonAssoc.class).deserialize(json);
	}

	public static StudyPersonAssoc fromJsonToStudyPersonAssoc(String json) {
		return new JSONDeserializer<StudyPersonAssoc>().use(null,
				StudyPersonAssoc.class).deserialize(json);
	}

	public static void indexStudyPersonAssoc(StudyPersonAssoc studyPersonAssoc) {
		List<StudyPersonAssoc> studypersonassocs = new ArrayList<StudyPersonAssoc>();
		studypersonassocs.add(studyPersonAssoc);
		indexStudyPersonAssocs(studypersonassocs);
	}

	@Async
	public static void indexStudyPersonAssocs(
			Collection<StudyPersonAssoc> studypersonassocs) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (StudyPersonAssoc studyPersonAssoc : studypersonassocs) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "studypersonassoc_" + studyPersonAssoc.getId());
			sid.addField("studyPersonAssoc.asocname_s",
					studyPersonAssoc.getAsocName());
			sid.addField("studyPersonAssoc.asocdescription_s",
					studyPersonAssoc.getAsocDescription());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"studypersonassoc_solrsummary_t",
					new StringBuilder().append(studyPersonAssoc.getAsocName())
							.append(" ")
							.append(studyPersonAssoc.getAsocDescription()));
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
		String searchString = "StudyPersonAssoc_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new StudyPersonAssoc().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<StudyPersonAssoc> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>StudyOrgAssoc</code>
	 * (asociere intre studiu si persoana) in baza de date; in caz afirmativ il
	 * returneaza, altfel, metoda il introduce in baza de date si apoi il
	 * returneaza. Verificarea existentei in baza de date se realizeaza fie dupa
	 * identificator, fie dupa un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>asocName
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul asocierii.
	 * @param asocName
	 *            - numele asocierii.
	 * @param asocDescription
	 *            - descrierea asocierii.
	 * @return
	 */
	public static StudyPersonAssoc checkStudyPersonAssoc(Integer id,
			String asocName, String asocDescription) {
		StudyPersonAssoc object;

		if (id != null) {
			object = findStudyPersonAssoc(id);

			if (object != null) {
				return object;
			}
		}

		List<StudyPersonAssoc> queryResult;

		if (asocName != null) {
			TypedQuery<StudyPersonAssoc> query = entityManager()
					.createQuery(
							"SELECT o FROM StudyPersonAssoc o WHERE lower(o.asocName) = lower(:asocName)",
							StudyPersonAssoc.class);
			query.setParameter("asocName", asocName);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new StudyPersonAssoc();
		object.asocName = asocName;
		object.asocDescription = asocDescription;
		object.persist();

		return object;
	}

	@Column(name = "asoc_description", columnDefinition = "text")
	private String asocDescription;

	@Column(name = "asoc_name", columnDefinition = "text")
	@NotNull
	private String asocName;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@OneToMany(mappedBy = "assoctypeId")
	private Set<StudyPerson> studypeople;

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

	public String getAsocDescription() {
		return asocDescription;
	}

	public String getAsocName() {
		return asocName;
	}

	public Integer getId() {
		return this.id;
	}

	public Set<StudyPerson> getStudypeople() {
		return studypeople;
	}

	@Transactional
	public StudyPersonAssoc merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		StudyPersonAssoc merged = this.entityManager.merge(this);
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
			StudyPersonAssoc attached = StudyPersonAssoc
					.findStudyPersonAssoc(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAsocDescription(String asocDescription) {
		this.asocDescription = asocDescription;
	}

	public void setAsocName(String asocName) {
		this.asocName = asocName;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setStudypeople(Set<StudyPerson> studypeople) {
		this.studypeople = studypeople;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexStudyPersonAssoc(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((StudyPersonAssoc) obj).id))
				|| (asocName != null && asocName
						.equalsIgnoreCase(((StudyPersonAssoc) obj).asocName));
	}
}
