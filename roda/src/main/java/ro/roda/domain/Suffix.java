package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
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
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
@Table(schema = "public", name = "suffix")
public class Suffix {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static Suffix fromJsonToSuffix(String json) {
		return new JSONDeserializer<Suffix>().use(null, Suffix.class).deserialize(json);
	}

	public static String toJsonArray(Collection<Suffix> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<Suffix> fromJsonArrayToSuffixes(String json) {
		return new JSONDeserializer<List<Suffix>>().use(null, ArrayList.class).use("values", Suffix.class)
				.deserialize(json);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "Suffix_solrsummary_t:" + queryString;
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

	public static void indexSuffix(Suffix suffix) {
		List<Suffix> suffixes = new ArrayList<Suffix>();
		suffixes.add(suffix);
		indexSuffixes(suffixes);
	}

	@Async
	public static void indexSuffixes(Collection<Suffix> suffixes) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Suffix suffix : suffixes) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "suffix_" + suffix.getId());
			sid.addField("suffix.id_i", suffix.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("suffix_solrsummary_t", new StringBuilder().append(suffix.getId()));
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
	public static void deleteIndex(Suffix suffix) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("suffix_" + suffix.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexSuffix(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Suffix().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new Suffix().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countSuffixes() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Suffix o", Long.class).getSingleResult();
	}

	public static List<Suffix> findAllSuffixes() {
		return entityManager().createQuery("SELECT o FROM Suffix o", Suffix.class).getResultList();
	}

	public static Suffix findSuffix(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Suffix.class, id);
	}

	public static List<Suffix> findSuffixEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Suffix o", Suffix.class).setFirstResult(firstResult)
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
			Suffix attached = Suffix.findSuffix(this.id);
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
	public Suffix merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Suffix merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	@OneToMany(mappedBy = "suffixId")
	private Set<Person> people;

	@Column(name = "name", columnDefinition = "varchar", length = 50)
	@NotNull
	private String name;

	public Set<Person> getPeople() {
		return people;
	}

	public void setPeople(Set<Person> people) {
		this.people = people;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
