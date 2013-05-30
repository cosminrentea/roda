package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(schema = "public", name = "org_relations")
@Configurable

public class OrgRelations {

	public static long countOrgRelationses() {
		return entityManager().createQuery("SELECT COUNT(o) FROM OrgRelations o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(OrgRelations orgRelations) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("orgrelations_" + orgRelations.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new OrgRelations().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<OrgRelations> findAllOrgRelationses() {
		return entityManager().createQuery("SELECT o FROM OrgRelations o", OrgRelations.class).getResultList();
	}

	public static OrgRelations findOrgRelations(OrgRelationsPK id) {
		if (id == null)
			return null;
		return entityManager().find(OrgRelations.class, id);
	}

	public static List<OrgRelations> findOrgRelationsEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM OrgRelations o", OrgRelations.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<OrgRelations> fromJsonArrayToOrgRelationses(String json) {
		return new JSONDeserializer<List<OrgRelations>>().use(null, ArrayList.class).use("values", OrgRelations.class)
				.deserialize(json);
	}

	public static OrgRelations fromJsonToOrgRelations(String json) {
		return new JSONDeserializer<OrgRelations>().use(null, OrgRelations.class).deserialize(json);
	}

	public static void indexOrgRelations(OrgRelations orgRelations) {
		List<OrgRelations> orgrelationses = new ArrayList<OrgRelations>();
		orgrelationses.add(orgRelations);
		indexOrgRelationses(orgrelationses);
	}

	@Async
	public static void indexOrgRelationses(Collection<OrgRelations> orgrelationses) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (OrgRelations orgRelations : orgrelationses) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "orgrelations_" + orgRelations.getId());
			sid.addField("orgRelations.org2id_t", orgRelations.getOrg2Id());
			sid.addField("orgRelations.org1id_t", orgRelations.getOrg1Id());
			sid.addField("orgRelations.orgrelationtypeid_t", orgRelations.getOrgRelationTypeId());
			sid.addField("orgRelations.datestart_dt", orgRelations.getDateStart());
			sid.addField("orgRelations.dateend_dt", orgRelations.getDateEnd());
			sid.addField("orgRelations.details_s", orgRelations.getDetails());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"orgrelations_solrsummary_t",
					new StringBuilder().append(orgRelations.getOrg2Id()).append(" ").append(orgRelations.getOrg1Id())
							.append(" ").append(orgRelations.getOrgRelationTypeId()).append(" ")
							.append(orgRelations.getDateStart()).append(" ").append(orgRelations.getDateEnd())
							.append(" ").append(orgRelations.getDetails()));
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
		String searchString = "OrgRelations_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new OrgRelations().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<OrgRelations> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "date_end", columnDefinition = "date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date dateEnd;

	@Column(name = "date_start", columnDefinition = "date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date dateStart;

	@Column(name = "details", columnDefinition = "text")
	private String details;

	@EmbeddedId
	private OrgRelationsPK id;

	@ManyToOne
	@JoinColumn(name = "org_1_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Org org1Id;

	@ManyToOne
	@JoinColumn(name = "org_2_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Org org2Id;

	@ManyToOne
	@JoinColumn(name = "org_relation_type_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private OrgRelationType orgRelationTypeId;

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

	public Date getDateEnd() {
		return dateEnd;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public String getDetails() {
		return details;
	}

	public OrgRelationsPK getId() {
		return this.id;
	}

	public Org getOrg1Id() {
		return org1Id;
	}

	public Org getOrg2Id() {
		return org2Id;
	}

	public OrgRelationType getOrgRelationTypeId() {
		return orgRelationTypeId;
	}

	@Transactional
	public OrgRelations merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		OrgRelations merged = this.entityManager.merge(this);
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
			OrgRelations attached = OrgRelations.findOrgRelations(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setId(OrgRelationsPK id) {
		this.id = id;
	}

	public void setOrg1Id(Org org1Id) {
		this.org1Id = org1Id;
	}

	public void setOrg2Id(Org org2Id) {
		this.org2Id = org2Id;
	}

	public void setOrgRelationTypeId(OrgRelationType orgRelationTypeId) {
		this.orgRelationTypeId = orgRelationTypeId;
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
		indexOrgRelations(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
