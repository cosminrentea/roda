package ro.roda.domain;

import java.util.ArrayList;
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
@Table(schema = "public", name = "acl_entry")
@Audited
public class AclEntry {

	public static long countAclEntrys() {
		return entityManager().createQuery("SELECT COUNT(o) FROM AclEntry o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(AclEntry aclEntry) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("aclentry_" + aclEntry.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new AclEntry().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static AclEntry findAclEntry(Long id) {
		if (id == null)
			return null;
		return entityManager().find(AclEntry.class, id);
	}

	public static List<AclEntry> findAclEntryEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM AclEntry o", AclEntry.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static List<AclEntry> findAllAclEntrys() {
		return entityManager().createQuery("SELECT o FROM AclEntry o", AclEntry.class).getResultList();
	}

	public static Collection<AclEntry> fromJsonArrayToAclEntrys(String json) {
		return new JSONDeserializer<List<AclEntry>>().use(null, ArrayList.class).use("values", AclEntry.class)
				.deserialize(json);
	}

	public static AclEntry fromJsonToAclEntry(String json) {
		return new JSONDeserializer<AclEntry>().use(null, AclEntry.class).deserialize(json);
	}

	public static void indexAclEntry(AclEntry aclEntry) {
		List<AclEntry> aclentrys = new ArrayList<AclEntry>();
		aclentrys.add(aclEntry);
		indexAclEntrys(aclentrys);
	}

	@Async
	public static void indexAclEntrys(Collection<AclEntry> aclentrys) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (AclEntry aclEntry : aclentrys) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "aclentry_" + aclEntry.getId());
			sid.addField("aclEntry.aclobjectidentity_t", aclEntry.getAclObjectIdentity());
			sid.addField("aclEntry.sid_t", aclEntry.getSid());
			sid.addField("aclEntry.mask_i", aclEntry.getMask());
			sid.addField("aclEntry.aceorder_i", aclEntry.getAceOrder());
			sid.addField("aclEntry.id_l", aclEntry.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"aclentry_solrsummary_t",
					new StringBuilder().append(aclEntry.getAclObjectIdentity()).append(" ").append(aclEntry.getSid())
							.append(" ").append(aclEntry.getMask()).append(" ").append(aclEntry.getAceOrder())
							.append(" ").append(aclEntry.getId()));
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
		String searchString = "AclEntry_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new AclEntry().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<AclEntry> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "ace_order", columnDefinition = "int4")
	@NotNull
	private Integer aceOrder;

	@ManyToOne
	@JoinColumn(name = "acl_object_identity", referencedColumnName = "id", nullable = false)
	private AclObjectIdentity aclObjectIdentity;

	@Column(name = "audit_failure", columnDefinition = "bool")
	@NotNull
	private boolean auditFailure;

	@Column(name = "audit_success", columnDefinition = "bool")
	@NotNull
	private boolean auditSuccess;

	@Column(name = "granting", columnDefinition = "bool")
	@NotNull
	private boolean granting;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "bigserial")
	private Long id;

	@Column(name = "mask", columnDefinition = "int4")
	@NotNull
	private Integer mask;

	@ManyToOne
	@JoinColumn(name = "sid", referencedColumnName = "id", nullable = false)
	private AclSid sid;

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

	public Integer getAceOrder() {
		return this.aceOrder;
	}

	public AclObjectIdentity getAclObjectIdentity() {
		return aclObjectIdentity;
	}

	public Long getId() {
		return this.id;
	}

	public Integer getMask() {
		return mask;
	}

	public AclSid getSid() {
		return sid;
	}

	public boolean isAuditFailure() {
		return auditFailure;
	}

	public boolean isAuditSuccess() {
		return auditSuccess;
	}

	public boolean isGranting() {
		return granting;
	}

	@Transactional
	public AclEntry merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		AclEntry merged = this.entityManager.merge(this);
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
			AclEntry attached = AclEntry.findAclEntry(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAceOrder(Integer aceOrder) {
		this.aceOrder = aceOrder;
	}

	public void setAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
		this.aclObjectIdentity = aclObjectIdentity;
	}

	public void setAuditFailure(boolean auditFailure) {
		this.auditFailure = auditFailure;
	}

	public void setAuditSuccess(boolean auditSuccess) {
		this.auditSuccess = auditSuccess;
	}

	public void setGranting(boolean granting) {
		this.granting = granting;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMask(Integer mask) {
		this.mask = mask;
	}

	public void setSid(AclSid sid) {
		this.sid = sid;
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
		indexAclEntry(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
