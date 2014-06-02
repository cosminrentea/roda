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

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(schema = "public", name = "collection_model_type")
@Configurable
@Audited
public class CollectionModelType {

	public static long countCollectionModelTypes() {
		return entityManager().createQuery("SELECT COUNT(o) FROM CollectionModelType o", Long.class).getSingleResult();
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

	public static final EntityManager entityManager() {
		EntityManager em = new CollectionModelType().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
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

	public static Collection<CollectionModelType> fromJsonArrayToCollectionModelTypes(String json) {
		return new JSONDeserializer<List<CollectionModelType>>().use(null, ArrayList.class)
				.use("values", CollectionModelType.class).deserialize(json);
	}

	public static CollectionModelType fromJsonToCollectionModelType(String json) {
		return new JSONDeserializer<CollectionModelType>().use(null, CollectionModelType.class).deserialize(json);
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

	public static QueryResponse search(SolrQuery query) {
		try {
			return solrServer().query(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new QueryResponse();
	}

	public static QueryResponse search(String queryString) {
		String searchString = "CollectionModelType_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new CollectionModelType().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<CollectionModelType> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>CollectionModelType</code>
	 * (tip de model de colectare) in baza de date; in caz afirmativ il
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
	 *            - identificatorul tipului.
	 * @param name
	 *            - numele tipului.
	 * @param description
	 *            - descrierea tipului.
	 * @return
	 */
	public static CollectionModelType checkCollectionModelType(Integer id, String name, String description) {
		CollectionModelType object;

		if (id != null) {
			object = findCollectionModelType(id);

			if (object != null) {
				return object;
			}
		}

		List<CollectionModelType> queryResult;

		if (name != null) {
			TypedQuery<CollectionModelType> query = entityManager()
					.createQuery("SELECT o FROM CollectionModelType o WHERE lower(o.name) = lower(:name)",
							CollectionModelType.class);
			query.setParameter("name", name);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new CollectionModelType();
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
	@JoinTable(name = "meth_coll_type", joinColumns = { @JoinColumn(name = "collection_model_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "study_id", nullable = false) })
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
	public CollectionModelType merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		CollectionModelType merged = this.entityManager.merge(this);
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
			CollectionModelType attached = CollectionModelType.findCollectionModelType(this.id);
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
		indexCollectionModelType(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((CollectionModelType) obj).id))
				|| (name != null && name.equalsIgnoreCase(((CollectionModelType) obj).name));
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
