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
@Table(schema = "public", name = "prefix")
@Configurable

public class Prefix {

	public static long countPrefixes() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Prefix o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Prefix prefix) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("prefix_" + prefix.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Prefix().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Prefix> findAllPrefixes() {
		return entityManager().createQuery("SELECT o FROM Prefix o", Prefix.class).getResultList();
	}

	public static Prefix findPrefix(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Prefix.class, id);
	}

	public static List<Prefix> findPrefixEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Prefix o", Prefix.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<Prefix> fromJsonArrayToPrefixes(String json) {
		return new JSONDeserializer<List<Prefix>>().use(null, ArrayList.class).use("values", Prefix.class)
				.deserialize(json);
	}

	public static Prefix fromJsonToPrefix(String json) {
		return new JSONDeserializer<Prefix>().use(null, Prefix.class).deserialize(json);
	}

	public static void indexPrefix(Prefix prefix) {
		List<Prefix> prefixes = new ArrayList<Prefix>();
		prefixes.add(prefix);
		indexPrefixes(prefixes);
	}

	@Async
	public static void indexPrefixes(Collection<Prefix> prefixes) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Prefix prefix : prefixes) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "prefix_" + prefix.getId());
			sid.addField("prefix.name_s", prefix.getName());
			sid.addField("prefix.id_i", prefix.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("prefix_solrsummary_t",
					new StringBuilder().append(prefix.getName()).append(" ").append(prefix.getId()));
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
		String searchString = "Prefix_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Prefix().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Prefix> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "varchar", length = 50)
	@NotNull
	private String name;

	@OneToMany(mappedBy = "prefixId")
	private Set<Person> people;

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

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public Set<Person> getPeople() {
		return people;
	}

	@Transactional
	public Prefix merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Prefix merged = this.entityManager.merge(this);
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
			Prefix attached = Prefix.findPrefix(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPeople(Set<Person> people) {
		this.people = people;
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
		indexPrefix(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
