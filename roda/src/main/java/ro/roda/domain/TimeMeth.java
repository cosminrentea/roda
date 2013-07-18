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
import javax.persistence.OneToOne;
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
@Table(schema = "public", name = "time_meth")
public class TimeMeth {

	public static long countTimeMeths() {
		return entityManager().createQuery("SELECT COUNT(o) FROM TimeMeth o",
				Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(TimeMeth timeMethType) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("timemeth_" + timeMethType.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new TimeMeth().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<TimeMeth> findAllTimeMeths() {
		return entityManager().createQuery("SELECT o FROM TimeMeth o",
				TimeMeth.class).getResultList();
	}

	public static TimeMeth findTimeMeth(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(TimeMeth.class, id);
	}

	public static List<TimeMeth> findTimeMethEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM TimeMeth o", TimeMeth.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static Collection<TimeMeth> fromJsonArrayToTimeMeths(String json) {
		return new JSONDeserializer<List<TimeMeth>>()
				.use(null, ArrayList.class).use("values", TimeMeth.class)
				.deserialize(json);
	}

	public static TimeMeth fromJsonToTimeMeth(String json) {
		return new JSONDeserializer<TimeMeth>().use(null, TimeMeth.class)
				.deserialize(json);
	}

	public static void indexTimeMeth(TimeMeth timeMethType) {
		List<TimeMeth> timemethtypes = new ArrayList<TimeMeth>();
		timemethtypes.add(timeMethType);
		indexTimeMeths(timemethtypes);
	}

	@Async
	public static void indexTimeMeths(Collection<TimeMeth> timemethtypes) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (TimeMeth timeMethType : timemethtypes) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "timemeth_" + timeMethType.getId());
			sid.addField("timeMeth.name_s", timeMethType.getName());
			sid.addField("timeMeth.description_s",
					timeMethType.getDescription());
			sid.addField("timeMeth.id_i", timeMethType.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"timemeth_solrsummary_t",
					new StringBuilder().append(timeMethType.getName())
							.append(" ").append(timeMethType.getDescription())
							.append(" ").append(timeMethType.getId()));
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
		String searchString = "TimeMeth_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new TimeMeth().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<TimeMeth> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unei metode temporale in baza de date; in caz
	 * afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce
	 * metoda temporala in baza de date si apoi returneaza obiectul
	 * corespunzator. Verificarea existentei in baza de date se realizeaza fie
	 * dupa valoarea identificatorului, fie dupa un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>id
	 * <li>name
	 * <ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul metodei temporale.
	 * @param name
	 *            - numele metodei temporale.
	 * @param description
	 *            - descrierea metodei temporale.
	 * @return
	 */
	public static TimeMeth checkTimeMeth(Integer id, String name,
			String description) {
		// TODO
		return null;
	}

	@Column(name = "description", columnDefinition = "text")
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "varchar", length = 100)
	@NotNull
	private String name;

	@OneToMany(mappedBy = "timeMethId")
	private Set<Study> studies;

	@PersistenceContext
	transient EntityManager entityManager;

	@Autowired(required=false)
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

	public Set<Study> getStudies() {
		return studies;
	}

	@Transactional
	public TimeMeth merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		TimeMeth merged = this.entityManager.merge(this);
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
			TimeMeth attached = TimeMeth.findTimeMeth(this.id);
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

	public void setStudies(Set<Study> studies) {
		this.studies = studies;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexTimeMeth(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
