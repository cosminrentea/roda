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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "other_statistic")
@Audited
public class OtherStatistic {

	public static long countOtherStatistics() {
		return entityManager().createQuery("SELECT COUNT(o) FROM OtherStatistic o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(OtherStatistic otherStatistic) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("otherstatistic_" + otherStatistic.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new OtherStatistic().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<OtherStatistic> findAllOtherStatistics() {
		return entityManager().createQuery("SELECT o FROM OtherStatistic o", OtherStatistic.class).getResultList();
	}

	public static OtherStatistic findOtherStatistic(Long id) {
		if (id == null)
			return null;
		return entityManager().find(OtherStatistic.class, id);
	}

	public static List<OtherStatistic> findOtherStatisticEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM OtherStatistic o", OtherStatistic.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<OtherStatistic> fromJsonArrayToOtherStatistics(String json) {
		return new JSONDeserializer<List<OtherStatistic>>().use(null, ArrayList.class)
				.use("values", OtherStatistic.class).deserialize(json);
	}

	public static OtherStatistic fromJsonToOtherStatistic(String json) {
		return new JSONDeserializer<OtherStatistic>().use(null, OtherStatistic.class).deserialize(json);
	}

	public static void indexOtherStatistic(OtherStatistic otherStatistic) {
		List<OtherStatistic> otherstatistics = new ArrayList<OtherStatistic>();
		otherstatistics.add(otherStatistic);
		indexOtherStatistics(otherstatistics);
	}

	@Async
	public static void indexOtherStatistics(Collection<OtherStatistic> otherstatistics) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (OtherStatistic otherStatistic : otherstatistics) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "otherstatistic_" + otherStatistic.getId());
			sid.addField("otherStatistic.variableid_t", otherStatistic.getVariableId());
			sid.addField("otherStatistic.name_s", otherStatistic.getName());
			sid.addField("otherStatistic.value_f", otherStatistic.getValue());
			sid.addField("otherStatistic.description_s", otherStatistic.getDescription());
			sid.addField("otherStatistic.id_l", otherStatistic.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"otherstatistic_solrsummary_t",
					new StringBuilder().append(otherStatistic.getVariableId()).append(" ")
							.append(otherStatistic.getName()).append(" ").append(otherStatistic.getValue()).append(" ")
							.append(otherStatistic.getDescription()).append(" ").append(otherStatistic.getId()));
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
		String searchString = "OtherStatistic_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new OtherStatistic().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<OtherStatistic> collection) {
		return new JSONSerializer().exclude("*.class")
				.exclude("classAuditReader", "auditReader", "variableId.classAuditReader", "variableId.auditReader")
				.serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>OtherStatistic</code>
	 * (statistica asociata unei variabile) in baza de date; in caz afirmativ il
	 * returneaza, altfel, metoda il introduce in baza de date si apoi il
	 * returneaza. Verificarea existentei in baza de date se realizeaza fie dupa
	 * identificator, fie dupa un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>name + variableId
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul statisticii.
	 * @param variableId
	 *            - variabila asociata statisticii.
	 * @param name
	 *            - numele statisticii.
	 * @param value
	 *            - valoarea statisticii.
	 * @param description
	 *            - descrierea statisticii.
	 * @return
	 */
	public static OtherStatistic checkOtherStatistic(Long id, Variable variableId, String name, Float value,
			String description) {
		OtherStatistic object;

		if (id != null) {
			object = findOtherStatistic(id);

			if (object != null) {
				return object;
			}
		}

		List<OtherStatistic> queryResult;

		if (name != null && variableId != null) {
			TypedQuery<OtherStatistic> query = entityManager().createQuery(
					"SELECT o FROM OtherStatistic o WHERE lower(o.name) = lower(:name) AND "
							+ "o.variableId = :variableId", OtherStatistic.class);
			query.setParameter("name", name);
			query.setParameter("variableId", variableId);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new OtherStatistic();
		object.variableId = variableId;
		object.name = name;
		object.value = value;
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
	// , columnDefinition = "bigserial")
	private Long id;

	@Column(name = "name", columnDefinition = "varchar", length = 100)
	@NotNull
	private String name;

	@Column(name = "value", columnDefinition = "float4", precision = 8, scale = 8)
	@NotNull
	private Float value;

	@ManyToOne
	@JoinColumn(name = "variable_id", columnDefinition = "bigint", referencedColumnName = "id", nullable = false)
	private Variable variableId;

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

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public Float getValue() {
		return value;
	}

	public Variable getVariableId() {
		return variableId;
	}

	@Transactional
	public OtherStatistic merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		OtherStatistic merged = this.entityManager.merge(this);
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
			OtherStatistic attached = OtherStatistic.findOtherStatistic(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public void setVariableId(Variable variableId) {
		this.variableId = variableId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class")
				.exclude("classAuditReader", "auditReader", "variableId.classAuditReader", "variableId.auditReader")
				.serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		// TODO temporarily disabled
		// indexOtherStatistic(this);
	}

	@PreRemove
	private void preRemove() {
		// TODO temporarily disabled
		// deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((OtherStatistic) obj).id))
				|| ((name != null && name.equalsIgnoreCase(((OtherStatistic) obj).name)) && (variableId != null && variableId
						.equals(((OtherStatistic) obj).variableId)));
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
