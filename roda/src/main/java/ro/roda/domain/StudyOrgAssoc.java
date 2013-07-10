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
@Table(schema = "public", name = "study_org_assoc")
public class StudyOrgAssoc {

	public static long countStudyOrgAssocs() {
		return entityManager().createQuery(
				"SELECT COUNT(o) FROM StudyOrgAssoc o", Long.class)
				.getSingleResult();
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
		return entityManager().createQuery("SELECT o FROM StudyOrgAssoc o",
				StudyOrgAssoc.class).getResultList();
	}

	public static StudyOrgAssoc findStudyOrgAssoc(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(StudyOrgAssoc.class, id);
	}

	public static List<StudyOrgAssoc> findStudyOrgAssocEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM StudyOrgAssoc o",
						StudyOrgAssoc.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<StudyOrgAssoc> fromJsonArrayToStudyOrgAssocs(
			String json) {
		return new JSONDeserializer<List<StudyOrgAssoc>>()
				.use(null, ArrayList.class).use("values", StudyOrgAssoc.class)
				.deserialize(json);
	}

	public static StudyOrgAssoc fromJsonToStudyOrgAssoc(String json) {
		return new JSONDeserializer<StudyOrgAssoc>().use(null,
				StudyOrgAssoc.class).deserialize(json);
	}

	public static void indexStudyOrgAssoc(StudyOrgAssoc studyOrgAssoc) {
		List<StudyOrgAssoc> studyorgassocs = new ArrayList<StudyOrgAssoc>();
		studyorgassocs.add(studyOrgAssoc);
		indexStudyOrgAssocs(studyorgassocs);
	}

	@Async
	public static void indexStudyOrgAssocs(
			Collection<StudyOrgAssoc> studyorgassocs) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (StudyOrgAssoc studyOrgAssoc : studyorgassocs) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "studyorgassoc_" + studyOrgAssoc.getId());
			sid.addField("studyOrgAssoc.id_i", studyOrgAssoc.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("studyorgassoc_solrsummary_t",
					new StringBuilder().append(studyOrgAssoc.getId()));
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
	 * Verifica existenta unei asocieri intre un studiu si o organizatie in baza
	 * de date; in caz afirmativ, returneaza obiectul corespunzator, altfel,
	 * metoda introduce asocierea in baza de date si apoi returneaza obiectul
	 * corespunzator. Verificarea existentei in baza de date se realizeaza fie
	 * dupa valoarea identificatorului, fie dupa un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>id
	 * <li>name
	 * <ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul asocierii.
	 * @param name
	 *            - numele asocierii.
	 * @param description
	 *            - descrierea asocierii.
	 * @return
	 */
	public static StudyOrgAssoc checkStudyOrgAssoc(Integer id, String name,
			String description) {
		// TODO
		return null;
	}

	@Column(name = "assoc_description", columnDefinition = "text")
	private String assocDescription;

	@Column(name = "assoc_name", columnDefinition = "text")
	@NotNull
	private String assocName;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@OneToMany(mappedBy = "assoctypeId")
	private Set<StudyOrg> studyOrgs;

	@PersistenceContext
	transient EntityManager entityManager;

	@Autowired
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
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
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
}
