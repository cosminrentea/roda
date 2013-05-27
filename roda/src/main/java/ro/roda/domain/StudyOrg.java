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
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "study_org")
@Audited
public class StudyOrg {

	public static long countStudyOrgs() {
		return entityManager().createQuery("SELECT COUNT(o) FROM StudyOrg o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(StudyOrg studyOrg) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("studyorg_" + studyOrg.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new StudyOrg().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<StudyOrg> findAllStudyOrgs() {
		return entityManager().createQuery("SELECT o FROM StudyOrg o", StudyOrg.class).getResultList();
	}

	public static StudyOrg findStudyOrg(StudyOrgPK id) {
		if (id == null)
			return null;
		return entityManager().find(StudyOrg.class, id);
	}

	public static List<StudyOrg> findStudyOrgEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM StudyOrg o", StudyOrg.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<StudyOrg> fromJsonArrayToStudyOrgs(String json) {
		return new JSONDeserializer<List<StudyOrg>>().use(null, ArrayList.class).use("values", StudyOrg.class)
				.deserialize(json);
	}

	public static StudyOrg fromJsonToStudyOrg(String json) {
		return new JSONDeserializer<StudyOrg>().use(null, StudyOrg.class).deserialize(json);
	}

	public static void indexStudyOrg(StudyOrg studyOrg) {
		List<StudyOrg> studyorgs = new ArrayList<StudyOrg>();
		studyorgs.add(studyOrg);
		indexStudyOrgs(studyorgs);
	}

	@Async
	public static void indexStudyOrgs(Collection<StudyOrg> studyorgs) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (StudyOrg studyOrg : studyorgs) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "studyorg_" + studyOrg.getId());
			sid.addField("studyOrg.orgid_t", studyOrg.getOrgId());
			sid.addField("studyOrg.studyid_t", studyOrg.getStudyId());
			sid.addField("studyOrg.assoctypeid_t", studyOrg.getAssoctypeId());
			sid.addField("studyOrg.assocdetails_s", studyOrg.getAssocDetails());
			sid.addField("studyOrg.id_t", studyOrg.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"studyorg_solrsummary_t",
					new StringBuilder().append(studyOrg.getOrgId()).append(" ").append(studyOrg.getStudyId())
							.append(" ").append(studyOrg.getAssoctypeId()).append(" ")
							.append(studyOrg.getAssocDetails()).append(" ").append(studyOrg.getId()));
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
		String searchString = "StudyOrg_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new StudyOrg().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<StudyOrg> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "assoc_details", columnDefinition = "text")
	private String assocDetails;

	@ManyToOne
	@JoinColumn(name = "assoctype_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private StudyOrgAssoc assoctypeId;

	@EmbeddedId
	private StudyOrgPK id;

	@ManyToOne
	@JoinColumn(name = "org_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Org orgId;

	@ManyToOne
	@JoinColumn(name = "study_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Study studyId;

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

	public String getAssocDetails() {
		return assocDetails;
	}

	public StudyOrgAssoc getAssoctypeId() {
		return assoctypeId;
	}

	public StudyOrgPK getId() {
		return this.id;
	}

	public Org getOrgId() {
		return orgId;
	}

	public Study getStudyId() {
		return studyId;
	}

	@Transactional
	public StudyOrg merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		StudyOrg merged = this.entityManager.merge(this);
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
			StudyOrg attached = StudyOrg.findStudyOrg(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAssocDetails(String assocDetails) {
		this.assocDetails = assocDetails;
	}

	public void setAssoctypeId(StudyOrgAssoc assoctypeId) {
		this.assoctypeId = assoctypeId;
	}

	public void setId(StudyOrgPK id) {
		this.id = id;
	}

	public void setOrgId(Org orgId) {
		this.orgId = orgId;
	}

	public void setStudyId(Study studyId) {
		this.studyId = studyId;
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
		indexStudyOrg(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
