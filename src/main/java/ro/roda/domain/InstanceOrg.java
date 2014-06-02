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
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
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

	public static long countInstanceOrgs() {
		return entityManager().createQuery("SELECT COUNT(o) FROM InstanceOrg o", Long.class).getSingleResult();
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

	public static final EntityManager entityManager() {
		EntityManager em = new InstanceOrg().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
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

	public static Collection<InstanceOrg> fromJsonArrayToInstanceOrgs(String json) {
		return new JSONDeserializer<List<InstanceOrg>>().use(null, ArrayList.class).use("values", InstanceOrg.class)
				.deserialize(json);
	}

	public static InstanceOrg fromJsonToInstanceOrg(String json) {
		return new JSONDeserializer<InstanceOrg>().use(null, InstanceOrg.class).deserialize(json);
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

	public static QueryResponse search(SolrQuery query) {
		try {
			return solrServer().query(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new QueryResponse();
	}

	public static QueryResponse search(String queryString) {
		String searchString = "InstanceOrg_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new InstanceOrg().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<InstanceOrg> collection) {
		return new JSONSerializer().exclude("*.class").exclude("id", "classAuditReader", "auditReader")
				.serialize(collection);
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@Column(name = "assoc_details", columnDefinition = "text")
	private String assocDetails;

	@ManyToOne
	@JoinColumn(name = "assoc_type_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private InstanceOrgAssoc assocTypeId;

	@EmbeddedId
	private InstanceOrgPK id;

	@ManyToOne
	@JoinColumn(name = "instance_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Instance instanceId;

	@ManyToOne
	@JoinColumn(name = "org_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Org orgId;

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

	public String getAssocDetails() {
		return assocDetails;
	}

	public InstanceOrgAssoc getAssocTypeId() {
		return assocTypeId;
	}

	public InstanceOrgPK getId() {
		return this.id;
	}

	public Instance getInstanceId() {
		return instanceId;
	}

	public Org getOrgId() {
		return orgId;
	}

	@Transactional
	public InstanceOrg merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		InstanceOrg merged = this.entityManager.merge(this);
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
			InstanceOrg attached = InstanceOrg.findInstanceOrg(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAssocDetails(String assocDetails) {
		this.assocDetails = assocDetails;
	}

	public void setAssocTypeId(InstanceOrgAssoc assocTypeId) {
		this.assocTypeId = assocTypeId;
	}

	public void setId(InstanceOrgPK id) {
		this.id = id;
	}

	public void setInstanceId(Instance instanceId) {
		this.instanceId = instanceId;
	}

	public void setOrgId(Org orgId) {
		this.orgId = orgId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("id", "classAuditReader", "auditReader").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
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
}
