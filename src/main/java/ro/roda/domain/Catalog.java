package ro.roda.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "catalog")
@Audited
public class Catalog {

	public static long countCatalogs() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Catalog o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Catalog catalog) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("catalog_" + catalog.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Catalog().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Catalog> findAllCatalogs() {
		return entityManager().createQuery("SELECT o FROM Catalog o", Catalog.class).getResultList();
	}

	public static Catalog findCatalog(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Catalog.class, id);
	}

	public static List<Catalog> findCatalogEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Catalog o", Catalog.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<Catalog> fromJsonArrayToCatalogs(String json) {
		return new JSONDeserializer<List<Catalog>>().use(null, ArrayList.class).use("values", Catalog.class)
				.deserialize(json);
	}

	public static Catalog fromJsonToCatalog(String json) {
		return new JSONDeserializer<Catalog>().use(null, Catalog.class).deserialize(json);
	}

	public static void indexCatalog(Catalog catalog) {
		List<Catalog> catalogs = new ArrayList<Catalog>();
		catalogs.add(catalog);
		indexCatalogs(catalogs);
	}

	@Async
	public static void indexCatalogs(Collection<Catalog> catalogs) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Catalog catalog : catalogs) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "catalog_" + catalog.getId());
			sid.addField("catalog.series_t", catalog.getSeries());
			sid.addField("catalog.parentid_t", catalog.getParentId());
			sid.addField("catalog.owner_t", catalog.getOwner());
			sid.addField("catalog.name_s", catalog.getName());
			sid.addField("catalog.added_dt", catalog.getAdded().getTime());
			sid.addField("catalog.sequencenr_i", catalog.getSequencenr());
			sid.addField("catalog.description_s", catalog.getDescription());
			sid.addField("catalog.id_i", catalog.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"catalog_solrsummary_t",
					new StringBuilder().append(catalog.getSeries()).append(" ").append(catalog.getParentId())
							.append(" ").append(catalog.getOwner()).append(" ").append(catalog.getName()).append(" ")
							.append(catalog.getAdded().getTime()).append(" ").append(catalog.getSequencenr())
							.append(" ").append(catalog.getDescription()).append(" ").append(catalog.getId()));
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
		String searchString = "Catalog_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Catalog().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Catalog> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader")
				.exclude("catalogs", "catalogStudies", "parentId", "owner", "series").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>Catalog</code> (catalog) in
	 * baza de date; in caz afirmativ il returneaza, altfel, metoda il introduce
	 * in baza de date si apoi il returneaza. Verificarea existentei in baza de
	 * date se realizeaza fie dupa identificator, fie dupa un criteriu de
	 * unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>name + parentId + owner
	 * <ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul catalogului.
	 * @param parentId
	 *            - catalogul parinte al catalogului.
	 * @param owner
	 *            - utilizatorul posesor al catalogului.
	 * @param name
	 *            - numele catalogului.
	 * @param added
	 *            - data adaugarii catalogului.
	 * @param sequencenr
	 *            - numarul de ordine al catalogului in cadrul parintelui.
	 * @param description
	 *            - descrierea catalogului.
	 * @return
	 */
	public static Catalog checkCatalog(Integer id, Catalog parentId, Users owner, String name, Calendar added,
			Integer sequencenr, String description) {
		Catalog object;

		if (id != null) {
			object = findCatalog(id);

			if (object != null) {
				return object;
			}
		}

		List<Catalog> queryResult;

		if (name != null && parentId != null && owner != null) {
			TypedQuery<Catalog> query = entityManager().createQuery(
					"SELECT o FROM Catalog o WHERE lower(o.name) = lower(:name) AND "
							+ "o.parentId = :parentId AND o.owner = :owner", Catalog.class);
			query.setParameter("name", name);
			query.setParameter("parentId", parentId);
			query.setParameter("owner", owner);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new Catalog();
		object.parentId = parentId;
		object.owner = owner;
		object.name = name;
		object.added = added;
		object.sequencenr = sequencenr;
		object.description = description;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@Column(name = "added", columnDefinition = "timestamp")
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Calendar added;

	@OneToMany(mappedBy = "parentId", fetch = FetchType.LAZY)
	private Set<Catalog> catalogs;

	@OneToMany(mappedBy = "catalogId", fetch = FetchType.LAZY)
	private Set<CatalogStudy> catalogStudies;

	@Column(name = "description", columnDefinition = "text")
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "varchar", length = 200)
	@NotNull
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner", columnDefinition = "integer", referencedColumnName = "id", nullable = false)
	private Users owner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", columnDefinition = "integer", referencedColumnName = "id", nullable = true, insertable = false, updatable = false)
	private Catalog parentId;

	@Column(name = "sequencenr", columnDefinition = "int4")
	private Integer sequencenr;

	@OneToOne(mappedBy = "catalog")
	private Series series;

	// necesar pentru JSON
	private Integer level;

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

	public Calendar getAdded() {
		return added;
	}

	public Set<Catalog> getCatalogs() {
		return catalogs;
	}

	public Set<CatalogStudy> getCatalogStudies() {
		return catalogStudies;
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

	public Users getOwner() {
		return owner;
	}

	public Catalog getParentId() {
		return parentId;
	}

	public Integer getSequencenr() {
		return sequencenr;
	}

	public Series getSeries() {
		return series;
	}

	@Transactional
	public Catalog merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Catalog merged = this.entityManager.merge(this);
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
			Catalog attached = Catalog.findCatalog(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAdded(Calendar added) {
		this.added = added;
	}

	public void setCatalogs(Set<Catalog> catalogs) {
		this.catalogs = catalogs;
	}

	public void setCatalogStudies(Set<CatalogStudy> catalogStudies) {
		this.catalogStudies = catalogStudies;
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

	public void setOwner(Users owner) {
		this.owner = owner;
	}

	public void setParentId(Catalog parentId) {
		this.parentId = parentId;
	}

	public void setSequencenr(Integer sequencenr) {
		this.sequencenr = sequencenr;
	}

	public void setSeries(Series series) {
		this.series = series;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader")
				.exclude("catalogs", "catalogStudies", "parentId", "owner", "series").serialize(this);
	}

	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("parentId")
				.toString();
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexCatalog(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Catalog) {
			Catalog cat = (Catalog) obj;
			return (id != null && id.equals(cat.id))
					|| ((parentId != null && parentId.equals(cat.parentId))
							&& (owner != null && owner.equals(cat.owner)) && (name != null && name
							.equalsIgnoreCase(cat.name)));
		} else {
			return false;
		}

	}

	@JsonIgnore public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
