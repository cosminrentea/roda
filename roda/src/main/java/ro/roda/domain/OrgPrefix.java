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
@Table(schema = "public", name = "org_prefix")
@Configurable

public class OrgPrefix {

	public static long countOrgPrefixes() {
		return entityManager().createQuery("SELECT COUNT(o) FROM OrgPrefix o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(OrgPrefix orgPrefix) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("orgprefix_" + orgPrefix.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new OrgPrefix().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<OrgPrefix> findAllOrgPrefixes() {
		return entityManager().createQuery("SELECT o FROM OrgPrefix o", OrgPrefix.class).getResultList();
	}

	public static OrgPrefix findOrgPrefix(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(OrgPrefix.class, id);
	}

	public static List<OrgPrefix> findOrgPrefixEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM OrgPrefix o", OrgPrefix.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<OrgPrefix> fromJsonArrayToOrgPrefixes(String json) {
		return new JSONDeserializer<List<OrgPrefix>>().use(null, ArrayList.class).use("values", OrgPrefix.class)
				.deserialize(json);
	}

	public static OrgPrefix fromJsonToOrgPrefix(String json) {
		return new JSONDeserializer<OrgPrefix>().use(null, OrgPrefix.class).deserialize(json);
	}

	public static void indexOrgPrefix(OrgPrefix orgPrefix) {
		List<OrgPrefix> orgprefixes = new ArrayList<OrgPrefix>();
		orgprefixes.add(orgPrefix);
		indexOrgPrefixes(orgprefixes);
	}

	@Async
	public static void indexOrgPrefixes(Collection<OrgPrefix> orgprefixes) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (OrgPrefix orgPrefix : orgprefixes) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "orgprefix_" + orgPrefix.getId());
			sid.addField("orgPrefix.name_s", orgPrefix.getName());
			sid.addField("orgPrefix.description_s", orgPrefix.getDescription());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("orgprefix_solrsummary_t",
					new StringBuilder().append(orgPrefix.getName()).append(" ").append(orgPrefix.getDescription()));
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
		String searchString = "OrgPrefix_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new OrgPrefix().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<OrgPrefix> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "description", columnDefinition = "text")
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "varchar", length = 100)
	@NotNull
	private String name;

	@OneToMany(mappedBy = "orgPrefixId")
	private Set<Org> orgs;

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

	public String getDescription() {
		return description;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public Set<Org> getOrgs() {
		return orgs;
	}

	@Transactional
	public OrgPrefix merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		OrgPrefix merged = this.entityManager.merge(this);
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
			OrgPrefix attached = OrgPrefix.findOrgPrefix(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrgs(Set<Org> orgs) {
		this.orgs = orgs;
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
		indexOrgPrefix(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
