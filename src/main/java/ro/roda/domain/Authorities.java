package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(schema = "public", name = "authorities")
@Configurable
@Audited
public class Authorities {

	public static long countAuthoritieses() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Authorities o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Authorities authorities) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("authorities_" + authorities.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Authorities().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Authorities> findAllAuthoritieses() {
		return entityManager().createQuery("SELECT o FROM Authorities o", Authorities.class).getResultList();
	}

	public static Authorities findAuthorities(AuthoritiesPK id) {
		if (id == null)
			return null;
		return entityManager().find(Authorities.class, id);
	}

	public static List<Authorities> findAuthoritiesEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Authorities o", Authorities.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<Authorities> fromJsonArrayToAuthoritieses(String json) {
		return new JSONDeserializer<List<Authorities>>().use(null, ArrayList.class).use("values", Authorities.class)
				.deserialize(json);
	}

	public static Authorities fromJsonToAuthorities(String json) {
		return new JSONDeserializer<Authorities>().use(null, Authorities.class).deserialize(json);
	}

	public static void indexAuthorities(Authorities authorities) {
		List<Authorities> authoritieses = new ArrayList<Authorities>();
		authoritieses.add(authorities);
		indexAuthoritieses(authoritieses);
	}

	@Async
	public static void indexAuthoritieses(Collection<Authorities> authoritieses) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Authorities authorities : authoritieses) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "authorities_" + authorities.getId());
			sid.addField("authorities.username_t", authorities.getUsername());
			sid.addField("authorities.id_t", authorities.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("authorities_solrsummary_t", new StringBuilder().append(authorities.getUsername()).append(" ")
					.append(authorities.getId()));
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
		String searchString = "Authorities_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Authorities().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Authorities> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@EmbeddedId
	private AuthoritiesPK id;

	@ManyToOne
	@JoinColumn(name = "username", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
	private Users username;

	@ManyToOne
	@JoinColumn(name = "authority", referencedColumnName = "groupname", nullable = false, insertable = false, updatable = false)
	private UserGroup authority;

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

	public AuthoritiesPK getId() {
		return this.id;
	}

	public Users getUsername() {
		return username;
	}

	public UserGroup getAuthority() {
		return authority;
	}

	@Transactional
	public Authorities merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Authorities merged = this.entityManager.merge(this);
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
			Authorities attached = Authorities.findAuthorities(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(AuthoritiesPK id) {
		this.id = id;
	}

	public void setUsername(Users username) {
		this.username = username;
	}

	public void setAuthority(UserGroup groupname) {
		this.authority = groupname;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@JsonIgnore public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexAuthorities(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
