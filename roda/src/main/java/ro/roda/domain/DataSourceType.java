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
@Table(schema = "public", name = "data_source_type")
public class DataSourceType {

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "DataSourceType_solrsummary_t:" + queryString;
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

	public static void indexDataSourceType(DataSourceType dataSourceType) {
		List<DataSourceType> datasourcetypes = new ArrayList<DataSourceType>();
		datasourcetypes.add(dataSourceType);
		indexDataSourceTypes(datasourcetypes);
	}

	@Async
	public static void indexDataSourceTypes(Collection<DataSourceType> datasourcetypes) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (DataSourceType dataSourceType : datasourcetypes) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "datasourcetype_" + dataSourceType.getId());
			sid.addField("dataSourceType.name_s", dataSourceType.getName());
			sid.addField("dataSourceType.id_i", dataSourceType.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("datasourcetype_solrsummary_t",
					new StringBuilder().append(dataSourceType.getName()).append(" ").append(dataSourceType.getId()));
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
	public static void deleteIndex(DataSourceType dataSourceType) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("datasourcetype_" + dataSourceType.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexDataSourceType(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new DataSourceType().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
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

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static DataSourceType fromJsonToDataSourceType(String json) {
		return new JSONDeserializer<DataSourceType>().use(null, DataSourceType.class).deserialize(json);
	}

	public static String toJsonArray(Collection<DataSourceType> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<DataSourceType> fromJsonArrayToDataSourceTypes(String json) {
		return new JSONDeserializer<List<DataSourceType>>().use(null, ArrayList.class)
				.use("values", DataSourceType.class).deserialize(json);
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new DataSourceType().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countDataSourceTypes() {
		return entityManager().createQuery("SELECT COUNT(o) FROM DataSourceType o", Long.class).getSingleResult();
	}

	public static List<DataSourceType> findAllDataSourceTypes() {
		return entityManager().createQuery("SELECT o FROM DataSourceType o", DataSourceType.class).getResultList();
	}

	public static DataSourceType findDataSourceType(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(DataSourceType.class, id);
	}

	public static List<DataSourceType> findDataSourceTypeEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM DataSourceType o", DataSourceType.class)
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
			DataSourceType attached = DataSourceType.findDataSourceType(this.id);
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
	public DataSourceType merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		DataSourceType merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	@ManyToMany
	@JoinTable(name = "study_data_source_type", joinColumns = { @JoinColumn(name = "data_source_type_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "study_id", nullable = false) })
	private Set<Study> studies;

	@Column(name = "name", columnDefinition = "text")
	@NotNull
	private String name;

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
}
