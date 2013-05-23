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

@Entity
@Table(schema = "public", name = "acl_sid")
@Configurable
public class AclSid {

	@Column(name = "principal", columnDefinition = "bool")
	@NotNull
	private boolean principal;

	@Column(name = "sid", columnDefinition = "text")
	@NotNull
	private String sid;

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new AclSid().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countAclSids() {
		return entityManager().createQuery("SELECT COUNT(o) FROM AclSid o", Long.class).getSingleResult();
	}

	public static List<AclSid> findAllAclSids() {
		return entityManager().createQuery("SELECT o FROM AclSid o", AclSid.class).getResultList();
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
	public AclSid merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		AclSid merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public boolean isPrincipal() {
		return this.principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public String getSid() {
		return this.sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static AclSid fromJsonToAclSid(String json) {
		return new JSONDeserializer<AclSid>().use(null, AclSid.class).deserialize(json);
	}

	public static String toJsonArray(Collection<AclSid> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<AclSid> fromJsonArrayToAclSids(String json) {
		return new JSONDeserializer<List<AclSid>>().use(null, ArrayList.class).use("values", AclSid.class)
				.deserialize(json);
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

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@OneToMany(mappedBy = "sid")
	private Set<AclEntry> aclEntries;

	@OneToMany(mappedBy = "ownerSid")
	private Set<AclObjectIdentity> aclObjectIdentities;

	public Set<AclEntry> getAclEntries() {
		return aclEntries;
	}

	public void setAclEntries(Set<AclEntry> aclEntries) {
		this.aclEntries = aclEntries;
	}

	public Set<AclObjectIdentity> getAclObjectIdentities() {
		return aclObjectIdentities;
	}

	public void setAclObjectIdentities(Set<AclObjectIdentity> aclObjectIdentities) {
		this.aclObjectIdentities = aclObjectIdentities;
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "AclSid_solrsummary_t:" + queryString;
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

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexAclSid(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new AclSid().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}
}
