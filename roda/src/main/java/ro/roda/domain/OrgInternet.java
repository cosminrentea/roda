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

@Entity
@Table(schema = "public", name = "org_internet")
@Configurable
@Audited
public class OrgInternet {

	public static long countOrgInternets() {
		return entityManager().createQuery("SELECT COUNT(o) FROM OrgInternet o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(OrgInternet orgInternet) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("orginternet_" + orgInternet.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new OrgInternet().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<OrgInternet> findAllOrgInternets() {
		return entityManager().createQuery("SELECT o FROM OrgInternet o", OrgInternet.class).getResultList();
	}

	public static OrgInternet findOrgInternet(OrgInternetPK id) {
		if (id == null)
			return null;
		return entityManager().find(OrgInternet.class, id);
	}

	public static List<OrgInternet> findOrgInternetEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM OrgInternet o", OrgInternet.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<OrgInternet> fromJsonArrayToOrgInternets(String json) {
		return new JSONDeserializer<List<OrgInternet>>().use(null, ArrayList.class).use("values", OrgInternet.class)
				.deserialize(json);
	}

	public static OrgInternet fromJsonToOrgInternet(String json) {
		return new JSONDeserializer<OrgInternet>().use(null, OrgInternet.class).deserialize(json);
	}

	public static void indexOrgInternet(OrgInternet orgInternet) {
		List<OrgInternet> orginternets = new ArrayList<OrgInternet>();
		orginternets.add(orgInternet);
		indexOrgInternets(orginternets);
	}

	@Async
	public static void indexOrgInternets(Collection<OrgInternet> orginternets) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (OrgInternet orgInternet : orginternets) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "orginternet_" + orgInternet.getId());
			sid.addField("orgInternet.internetid_t", orgInternet.getInternetId());
			sid.addField("orgInternet.orgid_t", orgInternet.getOrgId());
			sid.addField("orgInternet.id_t", orgInternet.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("orginternet_solrsummary_t",
					new StringBuilder().append(orgInternet.getInternetId()).append(" ").append(orgInternet.getOrgId())
							.append(" ").append(orgInternet.getId()));
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
		String searchString = "OrgInternet_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new OrgInternet().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<OrgInternet> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@EmbeddedId
	private OrgInternetPK id;

	@ManyToOne
	@JoinColumn(name = "internet_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Internet internetId;

	@Column(name = "main", columnDefinition = "bool")
	@NotNull
	private boolean main;

	@ManyToOne
	@JoinColumn(name = "org_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Org orgId;

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

	public OrgInternetPK getId() {
		return this.id;
	}

	public Internet getInternetId() {
		return internetId;
	}

	public Org getOrgId() {
		return orgId;
	}

	public boolean isMain() {
		return main;
	}

	@Transactional
	public OrgInternet merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		OrgInternet merged = this.entityManager.merge(this);
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
			OrgInternet attached = OrgInternet.findOrgInternet(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(OrgInternetPK id) {
		this.id = id;
	}

	public void setInternetId(Internet internetId) {
		this.internetId = internetId;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

	public void setOrgId(Org orgId) {
		this.orgId = orgId;
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
		indexOrgInternet(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
