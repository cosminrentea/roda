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
@Table(schema = "public", name = "org_email")
@Configurable

public class OrgEmail {

	public static long countOrgEmails() {
		return entityManager().createQuery("SELECT COUNT(o) FROM OrgEmail o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(OrgEmail orgEmail) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("orgemail_" + orgEmail.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new OrgEmail().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<OrgEmail> findAllOrgEmails() {
		return entityManager().createQuery("SELECT o FROM OrgEmail o", OrgEmail.class).getResultList();
	}

	public static OrgEmail findOrgEmail(OrgEmailPK id) {
		if (id == null)
			return null;
		return entityManager().find(OrgEmail.class, id);
	}

	public static List<OrgEmail> findOrgEmailEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM OrgEmail o", OrgEmail.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<OrgEmail> fromJsonArrayToOrgEmails(String json) {
		return new JSONDeserializer<List<OrgEmail>>().use(null, ArrayList.class).use("values", OrgEmail.class)
				.deserialize(json);
	}

	public static OrgEmail fromJsonToOrgEmail(String json) {
		return new JSONDeserializer<OrgEmail>().use(null, OrgEmail.class).deserialize(json);
	}

	public static void indexOrgEmail(OrgEmail orgEmail) {
		List<OrgEmail> orgemails = new ArrayList<OrgEmail>();
		orgemails.add(orgEmail);
		indexOrgEmails(orgemails);
	}

	@Async
	public static void indexOrgEmails(Collection<OrgEmail> orgemails) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (OrgEmail orgEmail : orgemails) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "orgemail_" + orgEmail.getId());
			sid.addField("orgEmail.emailid_t", orgEmail.getEmailId());
			sid.addField("orgEmail.orgid_t", orgEmail.getOrgId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("orgemail_solrsummary_t", new StringBuilder().append(orgEmail.getEmailId()).append(" ")
					.append(orgEmail.getOrgId()));
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
		String searchString = "OrgEmail_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new OrgEmail().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<OrgEmail> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@ManyToOne
	@JoinColumn(name = "email_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Email emailId;

	@EmbeddedId
	private OrgEmailPK id;

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

	public Email getEmailId() {
		return emailId;
	}

	public OrgEmailPK getId() {
		return this.id;
	}

	public Org getOrgId() {
		return orgId;
	}

	public boolean isMain() {
		return main;
	}

	@Transactional
	public OrgEmail merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		OrgEmail merged = this.entityManager.merge(this);
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
			OrgEmail attached = OrgEmail.findOrgEmail(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setEmailId(Email emailId) {
		this.emailId = emailId;
	}

	public void setId(OrgEmailPK id) {
		this.id = id;
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
		indexOrgEmail(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
