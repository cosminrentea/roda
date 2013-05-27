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
@Table(schema = "public", name = "collection_model_type")
@Configurable
@Audited
public class CollectionModelType {

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static CollectionModelType fromJsonToCollectionModelType(String json) {
		return new JSONDeserializer<CollectionModelType>().use(null, CollectionModelType.class).deserialize(json);
	}

	public static String toJsonArray(Collection<CollectionModelType> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<CollectionModelType> fromJsonArrayToCollectionModelTypes(String json) {
		return new JSONDeserializer<List<CollectionModelType>>().use(null, ArrayList.class)
				.use("values", CollectionModelType.class).deserialize(json);
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "CollectionModelType_solrsummary_t:" + queryString;
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

	public static void indexCollectionModelType(CollectionModelType collectionModelType) {
		List<CollectionModelType> collectionmodeltypes = new ArrayList<CollectionModelType>();
		collectionmodeltypes.add(collectionModelType);
		indexCollectionModelTypes(collectionmodeltypes);
	}

	@Async
	public static void indexCollectionModelTypes(Collection<CollectionModelType> collectionmodeltypes) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (CollectionModelType collectionModelType : collectionmodeltypes) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "collectionmodeltype_" + collectionModelType.getId());
			sid.addField("collectionModelType.name_s", collectionModelType.getName());
			sid.addField("collectionModelType.description_s", collectionModelType.getDescription());
			sid.addField("collectionModelType.id_i", collectionModelType.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"collectionmodeltype_solrsummary_t",
					new StringBuilder().append(collectionModelType.getName()).append(" ")
							.append(collectionModelType.getDescription()).append(" ")
							.append(collectionModelType.getId()));
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
	public static void deleteIndex(CollectionModelType collectionModelType) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("collectionmodeltype_" + collectionModelType.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexCollectionModelType(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new CollectionModelType().solrServer;
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

	@ManyToMany
	@JoinTable(name = "meth_coll_type", joinColumns = { @JoinColumn(name = "collection_model_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "study_id", nullable = false) })
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

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new CollectionModelType().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countCollectionModelTypes() {
		return entityManager().createQuery("SELECT COUNT(o) FROM CollectionModelType o", Long.class).getSingleResult();
	}

	public static List<CollectionModelType> findAllCollectionModelTypes() {
		return entityManager().createQuery("SELECT o FROM CollectionModelType o", CollectionModelType.class)
				.getResultList();
	}

	public static CollectionModelType findCollectionModelType(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(CollectionModelType.class, id);
	}

	public static List<CollectionModelType> findCollectionModelTypeEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM CollectionModelType o", CollectionModelType.class)
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
			CollectionModelType attached = CollectionModelType.findCollectionModelType(this.id);
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
	public CollectionModelType merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		CollectionModelType merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
