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

@Configurable
@Entity
@Table(schema = "public", name = "org_phone")
@Audited
public class OrgPhone {

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new OrgPhone().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countOrgPhones() {
		return entityManager().createQuery("SELECT COUNT(o) FROM OrgPhone o", Long.class).getSingleResult();
	}

	public static List<OrgPhone> findAllOrgPhones() {
		return entityManager().createQuery("SELECT o FROM OrgPhone o", OrgPhone.class).getResultList();
	}

	public static OrgPhone findOrgPhone(OrgPhonePK id) {
		if (id == null)
			return null;
		return entityManager().find(OrgPhone.class, id);
	}

	public static List<OrgPhone> findOrgPhoneEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM OrgPhone o", OrgPhone.class).setFirstResult(firstResult)
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
			OrgPhone attached = OrgPhone.findOrgPhone(this.id);
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
	public OrgPhone merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		OrgPhone merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static OrgPhone fromJsonToOrgPhone(String json) {
		return new JSONDeserializer<OrgPhone>().use(null, OrgPhone.class).deserialize(json);
	}

	public static String toJsonArray(Collection<OrgPhone> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<OrgPhone> fromJsonArrayToOrgPhones(String json) {
		return new JSONDeserializer<List<OrgPhone>>().use(null, ArrayList.class).use("values", OrgPhone.class)
				.deserialize(json);
	}

	@ManyToOne
	@JoinColumn(name = "org_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Org orgId;

	@ManyToOne
	@JoinColumn(name = "phone_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Phone phoneId;

	@Column(name = "main", columnDefinition = "bool")
	@NotNull
	private boolean main;

	public Org getOrgId() {
		return orgId;
	}

	public void setOrgId(Org orgId) {
		this.orgId = orgId;
	}

	public Phone getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(Phone phoneId) {
		this.phoneId = phoneId;
	}

	public boolean isMain() {
		return main;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

	@EmbeddedId
	private OrgPhonePK id;

	public OrgPhonePK getId() {
		return this.id;
	}

	public void setId(OrgPhonePK id) {
		this.id = id;
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "OrgPhone_solrsummary_t:" + queryString;
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

	public static void indexOrgPhone(OrgPhone orgPhone) {
		List<OrgPhone> orgphones = new ArrayList<OrgPhone>();
		orgphones.add(orgPhone);
		indexOrgPhones(orgphones);
	}

	@Async
	public static void indexOrgPhones(Collection<OrgPhone> orgphones) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (OrgPhone orgPhone : orgphones) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "orgphone_" + orgPhone.getId());
			sid.addField("orgPhone.id_t", orgPhone.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("orgphone_solrsummary_t", new StringBuilder().append(orgPhone.getId()));
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
	public static void deleteIndex(OrgPhone orgPhone) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("orgphone_" + orgPhone.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexOrgPhone(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new OrgPhone().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
