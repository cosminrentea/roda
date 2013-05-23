package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.format.annotation.DateTimeFormat;
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
@Table(schema = "public", name = "catalog")
public class Catalog {

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

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new Catalog().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countCatalogs() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Catalog o", Long.class).getSingleResult();
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
	public Catalog merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Catalog merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("parentId")
				.toString();
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static Catalog fromJsonToCatalog(String json) {
		return new JSONDeserializer<Catalog>().use(null, Catalog.class).deserialize(json);
	}

	public static String toJsonArray(Collection<Catalog> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<Catalog> fromJsonArrayToCatalogs(String json) {
		return new JSONDeserializer<List<Catalog>>().use(null, ArrayList.class).use("values", Catalog.class)
				.deserialize(json);
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "Catalog_solrsummary_t:" + queryString;
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

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexCatalog(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Catalog().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	@OneToOne(mappedBy = "catalog")
	private Series series;

	@OneToMany(mappedBy = "parentId")
	private Set<Catalog> catalogs;

	@OneToMany(mappedBy = "catalogId")
	private Set<CatalogStudy> catalogStudies;

	@ManyToOne
	@JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Catalog parentId;

	@ManyToOne
	@JoinColumn(name = "owner", referencedColumnName = "id", nullable = false)
	private Users owner;

	@Column(name = "name", columnDefinition = "varchar", length = 200)
	@NotNull
	private String name;

	@Column(name = "added", columnDefinition = "timestamp")
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Calendar added;

	@Column(name = "sequencenr", columnDefinition = "int4")
	private Integer sequencenr;

	@Column(name = "description", columnDefinition = "text")
	private String description;

	public Series getSeries() {
		return series;
	}

	public void setSeries(Series series) {
		this.series = series;
	}

	public Set<Catalog> getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(Set<Catalog> catalogs) {
		this.catalogs = catalogs;
	}

	public Set<CatalogStudy> getCatalogStudies() {
		return catalogStudies;
	}

	public void setCatalogStudies(Set<CatalogStudy> catalogStudies) {
		this.catalogStudies = catalogStudies;
	}

	public Catalog getParentId() {
		return parentId;
	}

	public void setParentId(Catalog parentId) {
		this.parentId = parentId;
	}

	public Users getOwner() {
		return owner;
	}

	public void setOwner(Users owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getAdded() {
		return added;
	}

	public void setAdded(Calendar added) {
		this.added = added;
	}

	public Integer getSequencenr() {
		return sequencenr;
	}

	public void setSequencenr(Integer sequencenr) {
		this.sequencenr = sequencenr;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
