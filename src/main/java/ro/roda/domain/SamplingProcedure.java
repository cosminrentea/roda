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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

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

@Configurable
@Entity
@Table(schema = "public", name = "sampling_procedure")
@Audited
public class SamplingProcedure {

	public static long countSamplingProcedures() {
		return entityManager().createQuery("SELECT COUNT(o) FROM SamplingProcedure o", Long.class).getSingleResult();
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

	public static final EntityManager entityManager() {
		EntityManager em = new SamplingProcedure().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
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

	public static Collection<SamplingProcedure> fromJsonArrayToSamplingProcedures(String json) {
		return new JSONDeserializer<List<SamplingProcedure>>().use(null, ArrayList.class)
				.use("values", SamplingProcedure.class).deserialize(json);
	}

	public static SamplingProcedure fromJsonToSamplingProcedure(String json) {
		return new JSONDeserializer<SamplingProcedure>().use(null, SamplingProcedure.class).deserialize(json);
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

	public static QueryResponse search(SolrQuery query) {
		try {
			return solrServer().query(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new QueryResponse();
	}

	public static QueryResponse search(String queryString) {
		String searchString = "SamplingProcedure_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new SamplingProcedure().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<SamplingProcedure> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>SamplingProcedure</code>
	 * (procedura de esantionare) in baza de date; in caz afirmativ il
	 * returneaza, altfel, metoda il introduce in baza de date si apoi il
	 * returneaza. Verificarea existentei in baza de date se realizeaza fie dupa
	 * identificator, fie dupa un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>name
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul procedurii.
	 * @param name
	 *            - numele procedurii.
	 * @param description
	 *            - descrierea procedurii.
	 * @return
	 */
	public static SamplingProcedure checkSamplingProcedure(Integer id, String name, String description) {
		SamplingProcedure object;

		if (id != null) {
			object = findSamplingProcedure(id);

			if (object != null) {
				return object;
			}
		}

		List<SamplingProcedure> queryResult;

		if (name != null) {
			TypedQuery<SamplingProcedure> query = entityManager().createQuery(
					"SELECT o FROM SamplingProcedure o WHERE lower(o.name) = lower(:name)", SamplingProcedure.class);
			query.setParameter("name", name);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new SamplingProcedure();
		object.name = name;
		object.description = description;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@Column(name = "description", columnDefinition = "text")
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "varchar", length = 100)
	@NotNull
	private String name;

	@ManyToMany
	@JoinTable(name = "instance_sampling_procedure", joinColumns = { @JoinColumn(name = "sampling_procedure_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "study_id", nullable = false) })
	private Set<Study> studies;

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
	public SamplingProcedure merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		SamplingProcedure merged = this.entityManager.merge(this);
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
			SamplingProcedure attached = SamplingProcedure.findSamplingProcedure(this.id);
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
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((SamplingProcedure) obj).id))
				|| (name != null && name.equalsIgnoreCase(((SamplingProcedure) obj).name));
	}

	@JsonIgnore public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
