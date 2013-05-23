package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
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
@Table(schema = "public", name = "authorities")
@Configurable
public class Authorities {

	@EmbeddedId
	private AuthoritiesPK id;

	public AuthoritiesPK getId() {
		return this.id;
	}

	public void setId(AuthoritiesPK id) {
		this.id = id;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@ManyToOne
	@JoinColumn(name = "username", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
	private Users username;

	public Users getUsername() {
		return username;
	}

	public void setUsername(Users username) {
		this.username = username;
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "Authorities_solrsummary_t:" + queryString;
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

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexAuthorities(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Authorities().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new Authorities().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countAuthoritieses() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Authorities o", Long.class).getSingleResult();
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
	public Authorities merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Authorities merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static Authorities fromJsonToAuthorities(String json) {
		return new JSONDeserializer<Authorities>().use(null, Authorities.class).deserialize(json);
	}

	public static String toJsonArray(Collection<Authorities> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<Authorities> fromJsonArrayToAuthoritieses(String json) {
		return new JSONDeserializer<List<Authorities>>().use(null, ArrayList.class).use("values", Authorities.class)
				.deserialize(json);
	}
}
