package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(schema = "public", name = "time_meth_type")
@Audited
public class TimeMethType {

	public static long countTimeMethTypes() {
		return entityManager().createQuery("SELECT COUNT(o) FROM TimeMethType o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(TimeMethType timeMethType) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("timemethtype_" + timeMethType.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new TimeMethType().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<TimeMethType> findAllTimeMethTypes() {
		return entityManager().createQuery("SELECT o FROM TimeMethType o", TimeMethType.class).getResultList();
	}

	public static TimeMethType findTimeMethType(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(TimeMethType.class, id);
	}

	public static List<TimeMethType> findTimeMethTypeEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM TimeMethType o", TimeMethType.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<TimeMethType> fromJsonArrayToTimeMethTypes(String json) {
		return new JSONDeserializer<List<TimeMethType>>().use(null, ArrayList.class).use("values", TimeMethType.class)
				.deserialize(json);
	}

	public static TimeMethType fromJsonToTimeMethType(String json) {
		return new JSONDeserializer<TimeMethType>().use(null, TimeMethType.class).deserialize(json);
	}

	public static void indexTimeMethType(TimeMethType timeMethType) {
		List<TimeMethType> timemethtypes = new ArrayList<TimeMethType>();
		timemethtypes.add(timeMethType);
		indexTimeMethTypes(timemethtypes);
	}

	@Async
	public static void indexTimeMethTypes(Collection<TimeMethType> timemethtypes) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (TimeMethType timeMethType : timemethtypes) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "timemethtype_" + timeMethType.getId());
			sid.addField("timeMethType.study_t", timeMethType.getStudy());
			sid.addField("timeMethType.name_s", timeMethType.getName());
			sid.addField("timeMethType.description_s", timeMethType.getDescription());
			sid.addField("timeMethType.id_i", timeMethType.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("timemethtype_solrsummary_t", new StringBuilder().append(timeMethType.getStudy()).append(" ")
					.append(timeMethType.getName()).append(" ").append(timeMethType.getDescription()).append(" ")
					.append(timeMethType.getId()));
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
		String searchString = "TimeMethType_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new TimeMethType().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<TimeMethType> collection) {
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

	@OneToOne(mappedBy = "timeMethType")
	private Study study;

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

	public Study getStudy() {
		return study;
	}

	@Transactional
	public TimeMethType merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		TimeMethType merged = this.entityManager.merge(this);
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
			TimeMethType attached = TimeMethType.findTimeMethType(this.id);
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

	public void setStudy(Study study) {
		this.study = study;
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
		indexTimeMethType(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
