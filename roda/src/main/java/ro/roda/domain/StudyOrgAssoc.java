package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
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
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
@Table(schema = "public", name = "study_org_assoc")
public class StudyOrgAssoc {

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "StudyOrgAssoc_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static QueryResponse search(SolrQuery query) {
		try {
			return solrServer().query(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new QueryResponse();
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

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexStudyOrgAssoc(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new StudyOrgAssoc().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static StudyOrgAssoc fromJsonToStudyOrgAssoc(String json) {
		return new JSONDeserializer<StudyOrgAssoc>().use(null, StudyOrgAssoc.class).deserialize(json);
	}

	public static String toJsonArray(Collection<StudyOrgAssoc> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<StudyOrgAssoc> fromJsonArrayToStudyOrgAssocs(String json) {
		return new JSONDeserializer<List<StudyOrgAssoc>>().use(null, ArrayList.class)
				.use("values", StudyOrgAssoc.class).deserialize(json);
	}

	@OneToMany(mappedBy = "assoctypeId")
	private Set<StudyOrg> studyOrgs;

	@Column(name = "assoc_name", columnDefinition = "text")
	@NotNull
	private String assocName;

	@Column(name = "assoc_description", columnDefinition = "text")
	private String assocDescription;

	public Set<StudyOrg> getStudyOrgs() {
		return studyOrgs;
	}

	public void setStudyOrgs(Set<StudyOrg> studyOrgs) {
		this.studyOrgs = studyOrgs;
	}

	public String getAssocName() {
		return assocName;
	}

	public void setAssocName(String assocName) {
		this.assocName = assocName;
	}

	public String getAssocDescription() {
		return assocDescription;
	}

	public void setAssocDescription(String assocDescription) {
		this.assocDescription = assocDescription;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new StudyOrgAssoc().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countStudyOrgAssocs() {
		return entityManager().createQuery("SELECT COUNT(o) FROM StudyOrgAssoc o", Long.class).getSingleResult();
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

	@Transactional
	public void flush() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.flush();
	}

	@Transactional
	public void clear() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.clear();
	}

	@Transactional
	public StudyOrgAssoc merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		StudyOrgAssoc merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}
}
