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
import javax.persistence.UniqueConstraint;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(schema = "public", name = "acl_sid", uniqueConstraints = @UniqueConstraint(columnNames = { "sid", "principal" }))
@Configurable
@Audited
public class AclSid {

	public static long countAclSids() {
		return entityManager().createQuery("SELECT COUNT(o) FROM AclSid o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(AclSid aclSid) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("aclsid_" + aclSid.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new AclSid().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static AclSid findAclSid(Long id) {
		if (id == null)
			return null;
		return entityManager().find(AclSid.class, id);
	}

	public static List<AclSid> findAclSidEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM AclSid o", AclSid.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static List<AclSid> findAllAclSids() {
		return entityManager().createQuery("SELECT o FROM AclSid o", AclSid.class).getResultList();
	}

	public static Collection<AclSid> fromJsonArrayToAclSids(String json) {
		return new JSONDeserializer<List<AclSid>>().use(null, ArrayList.class).use("values", AclSid.class)
				.deserialize(json);
	}

	public static AclSid fromJsonToAclSid(String json) {
		return new JSONDeserializer<AclSid>().use(null, AclSid.class).deserialize(json);
	}

	public static void indexAclSid(AclSid aclSid) {
		List<AclSid> aclsids = new ArrayList<AclSid>();
		aclsids.add(aclSid);
		indexAclSids(aclsids);
	}

	@Async
	public static void indexAclSids(Collection<AclSid> aclsids) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (AclSid aclSid : aclsids) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "aclsid_" + aclSid.getId());
			sid.addField("aclSid.sid_s", aclSid.getSid());
			sid.addField("aclSid.id_l", aclSid.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("aclsid_solrsummary_t",
					new StringBuilder().append(aclSid.getSid()).append(" ").append(aclSid.getId()));
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
		String searchString = "AclSid_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new AclSid().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<AclSid> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>AclSid</code> (cod de
	 * securitate ACL) in baza de date; in caz afirmativ il returneaza, altfel,
	 * metoda il introduce in baza de date si apoi il returneaza. Verificarea
	 * existentei in baza de date se realizeaza fie dupa identificator, fie dupa
	 * un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>sid
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul codului.
	 * @param sid
	 *            - numele codului.
	 * @param principal
	 * @return
	 */
	public static AclSid checkAclSid(Long id, String sid, Boolean principal) {
		AclSid object;

		if (id != null) {
			object = findAclSid(id);

			if (object != null) {
				return object;
			}
		}

		List<AclSid> queryResult;

		if (sid != null) {
			TypedQuery<AclSid> query = entityManager().createQuery(
					"SELECT o FROM AclSid o WHERE lower(o.sid) = lower(:sid)", AclSid.class);
			query.setParameter("sid", sid);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new AclSid();
		object.sid = sid;
		object.principal = principal;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@OneToMany(mappedBy = "sid")
	private Set<AclEntry> aclEntries;

	@OneToMany(mappedBy = "ownerSid")
	private Set<AclObjectIdentity> aclObjectIdentities;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "bigserial")
	private Long id;

	@Column(name = "principal", columnDefinition = "bool")
	@NotNull
	private boolean principal;

	@Column(name = "sid", columnDefinition = "text")
	@NotNull
	private String sid;

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

	public Set<AclEntry> getAclEntries() {
		return aclEntries;
	}

	public Set<AclObjectIdentity> getAclObjectIdentities() {
		return aclObjectIdentities;
	}

	public Long getId() {
		return this.id;
	}

	public String getSid() {
		return this.sid;
	}

	public boolean isPrincipal() {
		return this.principal;
	}

	@Transactional
	public AclSid merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		AclSid merged = this.entityManager.merge(this);
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
			AclSid attached = AclSid.findAclSid(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAclEntries(Set<AclEntry> aclEntries) {
		this.aclEntries = aclEntries;
	}

	public void setAclObjectIdentities(Set<AclObjectIdentity> aclObjectIdentities) {
		this.aclObjectIdentities = aclObjectIdentities;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexAclSid(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((AclSid) obj).id))
				|| (sid != null && sid.equalsIgnoreCase(((AclSid) obj).sid));
	}

	@JsonIgnore
	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
