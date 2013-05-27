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

@Entity
@Table(schema = "public", name = "instance_org")
@Configurable
@Audited
public class InstanceOrg {

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static InstanceOrg fromJsonToInstanceOrg(String json) {
		return new JSONDeserializer<InstanceOrg>().use(null, InstanceOrg.class).deserialize(json);
	}

	public static String toJsonArray(Collection<InstanceOrg> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<InstanceOrg> fromJsonArrayToInstanceOrgs(String json) {
		return new JSONDeserializer<List<InstanceOrg>>().use(null, ArrayList.class).use("values", InstanceOrg.class)
				.deserialize(json);
	}

	@EmbeddedId
	private InstanceOrgPK id;

	public InstanceOrgPK getId() {
		return this.id;
	}

	public void setId(InstanceOrgPK id) {
		this.id = id;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new InstanceOrg().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countInstanceOrgs() {
		return entityManager().createQuery("SELECT COUNT(o) FROM InstanceOrg o", Long.class).getSingleResult();
	}

	public static List<InstanceOrg> findAllInstanceOrgs() {
		return entityManager().createQuery("SELECT o FROM InstanceOrg o", InstanceOrg.class).getResultList();
	}

	public static InstanceOrg findInstanceOrg(InstanceOrgPK id) {
		if (id == null)
			return null;
		return entityManager().find(InstanceOrg.class, id);
	}

	public static List<InstanceOrg> findInstanceOrgEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM InstanceOrg o", InstanceOrg.class)
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
			InstanceOrg attached = InstanceOrg.findInstanceOrg(this.id);
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
	public InstanceOrg merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		InstanceOrg merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "InstanceOrg_solrsummary_t:" + queryString;
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

	public static void indexInstanceOrg(InstanceOrg instanceOrg) {
		List<InstanceOrg> instanceorgs = new ArrayList<InstanceOrg>();
		instanceorgs.add(instanceOrg);
		indexInstanceOrgs(instanceorgs);
	}

	@Async
	public static void indexInstanceOrgs(Collection<InstanceOrg> instanceorgs) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (InstanceOrg instanceOrg : instanceorgs) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "instanceorg_" + instanceOrg.getId());
			sid.addField("instanceOrg.id_t", instanceOrg.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("instanceorg_solrsummary_t", new StringBuilder().append(instanceOrg.getId()));
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
	public static void deleteIndex(InstanceOrg instanceOrg) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("instanceorg_" + instanceOrg.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexInstanceOrg(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new InstanceOrg().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	@ManyToOne
	@JoinColumn(name = "instance_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Instance instanceId;

	@ManyToOne
	@JoinColumn(name = "assoc_type_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private InstanceOrgAssoc assocTypeId;

	@ManyToOne
	@JoinColumn(name = "org_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Org orgId;

	@Column(name = "assoc_details", columnDefinition = "text")
	private String assocDetails;

	public Instance getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Instance instanceId) {
		this.instanceId = instanceId;
	}

	public InstanceOrgAssoc getAssocTypeId() {
		return assocTypeId;
	}

	public void setAssocTypeId(InstanceOrgAssoc assocTypeId) {
		this.assocTypeId = assocTypeId;
	}

	public Org getOrgId() {
		return orgId;
	}

	public void setOrgId(Org orgId) {
		this.orgId = orgId;
	}

	public String getAssocDetails() {
		return assocDetails;
	}

	public void setAssocDetails(String assocDetails) {
		this.assocDetails = assocDetails;
	}
}
