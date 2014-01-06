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
@Table(schema = "public", name = "study_org_assoc")
@Audited
public class StudyOrgAssoc {

	public static long countStudyOrgAssocs() {
		return entityManager().createQuery("SELECT COUNT(o) FROM StudyOrgAssoc o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(StudyOrgAssoc studyOrgAssoc) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("studyorgassoc_" + studyOrgAssoc.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new StudyOrgAssoc().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<StudyOrgAssoc> findAllStudyOrgAssocs() {
		return entityManager().createQuery("SELECT o FROM StudyOrgAssoc o", StudyOrgAssoc.class).getResultList();
	}

	public static StudyOrgAssoc findStudyOrgAssoc(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(StudyOrgAssoc.class, id);
	}

	public static List<StudyOrgAssoc> findStudyOrgAssocEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM StudyOrgAssoc o", StudyOrgAssoc.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<StudyOrgAssoc> fromJsonArrayToStudyOrgAssocs(String json) {
		return new JSONDeserializer<List<StudyOrgAssoc>>().use(null, ArrayList.class)
				.use("values", StudyOrgAssoc.class).deserialize(json);
	}

	public static StudyOrgAssoc fromJsonToStudyOrgAssoc(String json) {
		return new JSONDeserializer<StudyOrgAssoc>().use(null, StudyOrgAssoc.class).deserialize(json);
	}

	public static void indexStudyOrgAssoc(StudyOrgAssoc studyOrgAssoc) {
		List<StudyOrgAssoc> studyorgassocs = new ArrayList<StudyOrgAssoc>();
		studyorgassocs.add(studyOrgAssoc);
		indexStudyOrgAssocs(studyorgassocs);
	}

	@Async
	public static void indexStudyOrgAssocs(Collection<StudyOrgAssoc> studyorgassocs) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (StudyOrgAssoc studyOrgAssoc : studyorgassocs) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "studyorgassoc_" + studyOrgAssoc.getId());
			sid.addField("studyOrgAssoc.id_i", studyOrgAssoc.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("studyorgassoc_solrsummary_t", new StringBuilder().append(studyOrgAssoc.getId()));
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
		String searchString = "StudyOrgAssoc_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new StudyOrgAssoc().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<StudyOrgAssoc> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>StudyOrgAssoc</code>
	 * (asociere intre studiu si organizatie) in baza de date; in caz afirmativ
	 * il returneaza, altfel, metoda il introduce in baza de date si apoi il
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
	 * @param assocName
	 *            - numele asocierii.
	 * @param assocDescription
	 *            - descrierea asocierii.
	 * @return
	 */
	public static StudyOrgAssoc checkStudyOrgAssoc(Integer id, String assocName, String assocDescription) {
		StudyOrgAssoc object;

		if (id != null) {
			object = findStudyOrgAssoc(id);

			if (object != null) {
				return object;
			}
		}

		List<StudyOrgAssoc> queryResult;

		if (assocName != null) {
			TypedQuery<StudyOrgAssoc> query = entityManager().createQuery(
					"SELECT o FROM StudyOrgAssoc o WHERE lower(o.assocName) = lower(:assocName)", StudyOrgAssoc.class);
			query.setParameter("assocName", assocName);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new StudyOrgAssoc();
		object.assocName = assocName;
		object.assocDescription = assocDescription;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@Column(name = "assoc_description", columnDefinition = "text")
	private String assocDescription;

	@Column(name = "assoc_name", columnDefinition = "text")
	@NotNull
	private String assocName;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "serial")
	private Integer id;

	@OneToMany(mappedBy = "assoctypeId")
	private Set<StudyOrg> studyOrgs;

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

	public String getAssocDescription() {
		return assocDescription;
	}

	public String getAssocName() {
		return assocName;
	}

	public Integer getId() {
		return this.id;
	}

	public Set<StudyOrg> getStudyOrgs() {
		return studyOrgs;
	}

	@Transactional
	public StudyOrgAssoc merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		StudyOrgAssoc merged = this.entityManager.merge(this);
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
			StudyOrgAssoc attached = StudyOrgAssoc.findStudyOrgAssoc(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAssocDescription(String assocDescription) {
		this.assocDescription = assocDescription;
	}

	public void setAssocName(String assocName) {
		this.assocName = assocName;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setStudyOrgs(Set<StudyOrg> studyOrgs) {
		this.studyOrgs = studyOrgs;
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
		indexStudyOrgAssoc(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((StudyOrgAssoc) obj).id))
				|| (assocName != null && assocName.equalsIgnoreCase(((StudyOrgAssoc) obj).assocName));
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
