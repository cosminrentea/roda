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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(schema = "public", name = "sampling_procedure")
public class SamplingProcedure {

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static SamplingProcedure fromJsonToSamplingProcedure(String json) {
		return new JSONDeserializer<SamplingProcedure>().use(null, SamplingProcedure.class).deserialize(json);
	}

	public static String toJsonArray(Collection<SamplingProcedure> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<SamplingProcedure> fromJsonArrayToSamplingProcedures(String json) {
		return new JSONDeserializer<List<SamplingProcedure>>().use(null, ArrayList.class)
				.use("values", SamplingProcedure.class).deserialize(json);
	}

	@ManyToMany
	@JoinTable(name = "instance_sampling_procedure", joinColumns = { @JoinColumn(name = "sampling_procedure_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "study_id", nullable = false) })
	private Set<Study> studies;

	@Column(name = "name", columnDefinition = "varchar", length = 100)
	@NotNull
	private String name;

	@Column(name = "description", columnDefinition = "text")
	private String description;

	public Set<Study> getStudies() {
		return studies;
	}

	public void setStudies(Set<Study> studies) {
		this.studies = studies;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "SamplingProcedure_solrsummary_t:" + queryString;
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

	public static void indexSamplingProcedure(SamplingProcedure samplingProcedure) {
		List<SamplingProcedure> samplingprocedures = new ArrayList<SamplingProcedure>();
		samplingprocedures.add(samplingProcedure);
		indexSamplingProcedures(samplingprocedures);
	}

	@Async
	public static void indexSamplingProcedures(Collection<SamplingProcedure> samplingprocedures) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (SamplingProcedure samplingProcedure : samplingprocedures) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "samplingprocedure_" + samplingProcedure.getId());
			sid.addField("samplingProcedure.id_i", samplingProcedure.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("samplingprocedure_solrsummary_t", new StringBuilder().append(samplingProcedure.getId()));
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
	public static void deleteIndex(SamplingProcedure samplingProcedure) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("samplingprocedure_" + samplingProcedure.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexSamplingProcedure(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new SamplingProcedure().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new SamplingProcedure().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countSamplingProcedures() {
		return entityManager().createQuery("SELECT COUNT(o) FROM SamplingProcedure o", Long.class).getSingleResult();
	}

	public static List<SamplingProcedure> findAllSamplingProcedures() {
		return entityManager().createQuery("SELECT o FROM SamplingProcedure o", SamplingProcedure.class)
				.getResultList();
	}

	public static SamplingProcedure findSamplingProcedure(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(SamplingProcedure.class, id);
	}

	public static List<SamplingProcedure> findSamplingProcedureEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM SamplingProcedure o", SamplingProcedure.class)
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
			SamplingProcedure attached = SamplingProcedure.findSamplingProcedure(this.id);
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
	public SamplingProcedure merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		SamplingProcedure merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

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

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
