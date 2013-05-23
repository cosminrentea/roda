package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
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
@Table(schema = "public", name = "acl_entry")
public class AclEntry {

	@Column(name = "ace_order", columnDefinition = "int4")
	@NotNull
	private Integer aceOrder;

	@ManyToOne
	@JoinColumn(name = "acl_object_identity", referencedColumnName = "id", nullable = false)
	private AclObjectIdentity aclObjectIdentity;

	@ManyToOne
	@JoinColumn(name = "sid", referencedColumnName = "id", nullable = false)
	private AclSid sid;

	@Column(name = "mask", columnDefinition = "int4")
	@NotNull
	private Integer mask;

	@Column(name = "granting", columnDefinition = "bool")
	@NotNull
	private boolean granting;

	@Column(name = "audit_success", columnDefinition = "bool")
	@NotNull
	private boolean auditSuccess;

	@Column(name = "audit_failure", columnDefinition = "bool")
	@NotNull
	private boolean auditFailure;

	public AclObjectIdentity getAclObjectIdentity() {
		return aclObjectIdentity;
	}

	public void setAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
		this.aclObjectIdentity = aclObjectIdentity;
	}

	public AclSid getSid() {
		return sid;
	}

	public void setSid(AclSid sid) {
		this.sid = sid;
	}

	public Integer getMask() {
		return mask;
	}

	public void setMask(Integer mask) {
		this.mask = mask;
	}

	public boolean isGranting() {
		return granting;
	}

	public void setGranting(boolean granting) {
		this.granting = granting;
	}

	public boolean isAuditSuccess() {
		return auditSuccess;
	}

	public void setAuditSuccess(boolean auditSuccess) {
		this.auditSuccess = auditSuccess;
	}

	public boolean isAuditFailure() {
		return auditFailure;
	}

	public void setAuditFailure(boolean auditFailure) {
		this.auditFailure = auditFailure;
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "AclEntry_solrsummary_t:" + queryString;
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

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexAclEntry(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new AclEntry().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static AclEntry fromJsonToAclEntry(String json) {
		return new JSONDeserializer<AclEntry>().use(null, AclEntry.class).deserialize(json);
	}

	public static String toJsonArray(Collection<AclEntry> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<AclEntry> fromJsonArrayToAclEntrys(String json) {
		return new JSONDeserializer<List<AclEntry>>().use(null, ArrayList.class).use("values", AclEntry.class)
				.deserialize(json);
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new AclEntry().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countAclEntrys() {
		return entityManager().createQuery("SELECT COUNT(o) FROM AclEntry o", Long.class).getSingleResult();
	}

	public static List<AclEntry> findAllAclEntrys() {
		return entityManager().createQuery("SELECT o FROM AclEntry o", AclEntry.class).getResultList();
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
	public AclEntry merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		AclEntry merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "bigserial")
	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAceOrder() {
		return this.aceOrder;
	}

	public void setAceOrder(Integer aceOrder) {
		this.aceOrder = aceOrder;
	}
}
