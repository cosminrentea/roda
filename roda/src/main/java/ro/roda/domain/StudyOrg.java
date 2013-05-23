package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
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
@Table(schema = "public", name = "study_org")
public class StudyOrg {

	@ManyToOne
	@JoinColumn(name = "org_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Org orgId;

	@ManyToOne
	@JoinColumn(name = "study_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Study studyId;

	@ManyToOne
	@JoinColumn(name = "assoctype_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private StudyOrgAssoc assoctypeId;

	@Column(name = "assoc_details", columnDefinition = "text")
	private String assocDetails;

	public Org getOrgId() {
		return orgId;
	}

	public void setOrgId(Org orgId) {
		this.orgId = orgId;
	}

	public Study getStudyId() {
		return studyId;
	}

	public void setStudyId(Study studyId) {
		this.studyId = studyId;
	}

	public StudyOrgAssoc getAssoctypeId() {
		return assoctypeId;
	}

	public void setAssoctypeId(StudyOrgAssoc assoctypeId) {
		this.assoctypeId = assoctypeId;
	}

	public String getAssocDetails() {
		return assocDetails;
	}

	public void setAssocDetails(String assocDetails) {
		this.assocDetails = assocDetails;
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "StudyOrg_solrsummary_t:" + queryString;
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

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexStudyOrg(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new StudyOrg().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	@EmbeddedId
	private StudyOrgPK id;

	public StudyOrgPK getId() {
		return this.id;
	}

	public void setId(StudyOrgPK id) {
		this.id = id;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static StudyOrg fromJsonToStudyOrg(String json) {
		return new JSONDeserializer<StudyOrg>().use(null, StudyOrg.class).deserialize(json);
	}

	public static String toJsonArray(Collection<StudyOrg> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<StudyOrg> fromJsonArrayToStudyOrgs(String json) {
		return new JSONDeserializer<List<StudyOrg>>().use(null, ArrayList.class).use("values", StudyOrg.class)
				.deserialize(json);
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new StudyOrg().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countStudyOrgs() {
		return entityManager().createQuery("SELECT COUNT(o) FROM StudyOrg o", Long.class).getSingleResult();
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
	public StudyOrg merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		StudyOrg merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
