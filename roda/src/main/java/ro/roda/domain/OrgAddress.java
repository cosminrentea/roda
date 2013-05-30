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

@Configurable
@Entity
@Table(schema = "public", name = "org_address")

public class OrgAddress {

	public static long countOrgAddresses() {
		return entityManager().createQuery("SELECT COUNT(o) FROM OrgAddress o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(OrgAddress orgAddress) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("orgaddress_" + orgAddress.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new OrgAddress().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<OrgAddress> findAllOrgAddresses() {
		return entityManager().createQuery("SELECT o FROM OrgAddress o", OrgAddress.class).getResultList();
	}

	public static OrgAddress findOrgAddress(OrgAddressPK id) {
		if (id == null)
			return null;
		return entityManager().find(OrgAddress.class, id);
	}

	public static List<OrgAddress> findOrgAddressEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM OrgAddress o", OrgAddress.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<OrgAddress> fromJsonArrayToOrgAddresses(String json) {
		return new JSONDeserializer<List<OrgAddress>>().use(null, ArrayList.class).use("values", OrgAddress.class)
				.deserialize(json);
	}

	public static OrgAddress fromJsonToOrgAddress(String json) {
		return new JSONDeserializer<OrgAddress>().use(null, OrgAddress.class).deserialize(json);
	}

	public static void indexOrgAddress(OrgAddress orgAddress) {
		List<OrgAddress> orgaddresses = new ArrayList<OrgAddress>();
		orgaddresses.add(orgAddress);
		indexOrgAddresses(orgaddresses);
	}

	@Async
	public static void indexOrgAddresses(Collection<OrgAddress> orgaddresses) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (OrgAddress orgAddress : orgaddresses) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "orgaddress_" + orgAddress.getId());
			sid.addField("orgAddress.id_t", orgAddress.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("orgaddress_solrsummary_t", new StringBuilder().append(orgAddress.getId()));
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
		String searchString = "OrgAddress_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new OrgAddress().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<OrgAddress> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@ManyToOne
	@JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Address addressId;

	@Column(name = "date_end", columnDefinition = "date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date dateEnd;

	@Column(name = "date_start", columnDefinition = "date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date dateStart;

	@EmbeddedId
	private OrgAddressPK id;

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

	public Address getAddressId() {
		return addressId;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public OrgAddressPK getId() {
		return this.id;
	}

	public Org getOrgId() {
		return orgId;
	}

	@Transactional
	public OrgAddress merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		OrgAddress merged = this.entityManager.merge(this);
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
			OrgAddress attached = OrgAddress.findOrgAddress(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAddressId(Address addressId) {
		this.addressId = addressId;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public void setId(OrgAddressPK id) {
		this.id = id;
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
		indexOrgAddress(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
