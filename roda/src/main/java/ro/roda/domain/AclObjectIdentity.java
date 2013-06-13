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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
@Table(schema = "public", name = "acl_object_identity", uniqueConstraints = @UniqueConstraint(columnNames = {
		"object_id_class", "object_id_Identity" }))
@Audited
public class AclObjectIdentity {

	public static long countAclObjectIdentitys() {
		return entityManager().createQuery("SELECT COUNT(o) FROM AclObjectIdentity o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(AclObjectIdentity aclObjectIdentity) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("aclobjectidentity_" + aclObjectIdentity.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new AclObjectIdentity().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static AclObjectIdentity findAclObjectIdentity(Long id) {
		if (id == null)
			return null;
		return entityManager().find(AclObjectIdentity.class, id);
	}

	public static List<AclObjectIdentity> findAclObjectIdentityEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM AclObjectIdentity o", AclObjectIdentity.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static List<AclObjectIdentity> findAllAclObjectIdentitys() {
		return entityManager().createQuery("SELECT o FROM AclObjectIdentity o", AclObjectIdentity.class)
				.getResultList();
	}

	public static Collection<AclObjectIdentity> fromJsonArrayToAclObjectIdentitys(String json) {
		return new JSONDeserializer<List<AclObjectIdentity>>().use(null, ArrayList.class)
				.use("values", AclObjectIdentity.class).deserialize(json);
	}

	public static AclObjectIdentity fromJsonToAclObjectIdentity(String json) {
		return new JSONDeserializer<AclObjectIdentity>().use(null, AclObjectIdentity.class).deserialize(json);
	}

	public static void indexAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
		List<AclObjectIdentity> aclobjectidentitys = new ArrayList<AclObjectIdentity>();
		aclobjectidentitys.add(aclObjectIdentity);
		indexAclObjectIdentitys(aclobjectidentitys);
	}

	@Async
	public static void indexAclObjectIdentitys(Collection<AclObjectIdentity> aclobjectidentitys) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (AclObjectIdentity aclObjectIdentity : aclobjectidentitys) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "aclobjectidentity_" + aclObjectIdentity.getId());
			sid.addField("aclObjectIdentity.objectidclass_t", aclObjectIdentity.getObjectIdClass());
			sid.addField("aclObjectIdentity.parentobject_t", aclObjectIdentity.getParentObject());
			sid.addField("aclObjectIdentity.ownersid_t", aclObjectIdentity.getOwnerSid());
			sid.addField("aclObjectIdentity.objectididentity_l", aclObjectIdentity.getObjectIdIdentity());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"aclobjectidentity_solrsummary_t",
					new StringBuilder().append(aclObjectIdentity.getObjectIdClass()).append(" ")
							.append(aclObjectIdentity.getParentObject()).append(" ")
							.append(aclObjectIdentity.getOwnerSid()).append(" ")
							.append(aclObjectIdentity.getObjectIdIdentity()));
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
		String searchString = "AclObjectIdentity_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new AclObjectIdentity().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<AclObjectIdentity> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@OneToMany(mappedBy = "aclObjectIdentity")
	private Set<AclEntry> aclEntries;

	@OneToMany(mappedBy = "parentObject")
	private Set<AclObjectIdentity> aclObjectIdentities;

	@Column(name = "entries_inheriting", columnDefinition = "bool")
	@NotNull
	private boolean entriesInheriting;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigserial")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "object_id_class", columnDefinition = "bigint", referencedColumnName = "id", nullable = false)
	private AclClass objectIdClass;

	@Column(name = "object_id_identity", columnDefinition = "int8")
	@NotNull
	private Long objectIdIdentity;

	@ManyToOne
	@JoinColumn(name = "owner_sid", columnDefinition = "bigint", referencedColumnName = "id", nullable = false)
	private AclSid ownerSid;

	@ManyToOne
	@JoinColumn(name = "parent_object", columnDefinition = "bigint", referencedColumnName = "id", insertable = false, updatable = false)
	private AclObjectIdentity parentObject;

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

	public Set<AclEntry> getAclEntries() {
		return aclEntries;
	}

	public Set<AclObjectIdentity> getAclObjectIdentities() {
		return aclObjectIdentities;
	}

	public Long getId() {
		return this.id;
	}

	public AclClass getObjectIdClass() {
		return objectIdClass;
	}

	public Long getObjectIdIdentity() {
		return this.objectIdIdentity;
	}

	public AclSid getOwnerSid() {
		return ownerSid;
	}

	public AclObjectIdentity getParentObject() {
		return parentObject;
	}

	public boolean isEntriesInheriting() {
		return entriesInheriting;
	}

	@Transactional
	public AclObjectIdentity merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		AclObjectIdentity merged = this.entityManager.merge(this);
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
			AclObjectIdentity attached = AclObjectIdentity.findAclObjectIdentity(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAclEntries(Set<AclEntry> aclEntries) {
		this.aclEntries = aclEntries;
	}

	public void setAclObjectIdentities(Set<AclObjectIdentity> aclObjectIdentities) {
		this.aclObjectIdentities = aclObjectIdentities;
	}

	public void setEntriesInheriting(boolean entriesInheriting) {
		this.entriesInheriting = entriesInheriting;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setObjectIdClass(AclClass objectIdClass) {
		this.objectIdClass = objectIdClass;
	}

	public void setObjectIdIdentity(Long objectIdIdentity) {
		this.objectIdIdentity = objectIdIdentity;
	}

	public void setOwnerSid(AclSid ownerSid) {
		this.ownerSid = ownerSid;
	}

	public void setParentObject(AclObjectIdentity parentObject) {
		this.parentObject = parentObject;
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
		indexAclObjectIdentity(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
