package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

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
@Table(schema = "public", name = "series")
@Configurable
@Audited
public class Series {

	public static long countSerieses() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Series o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Series series) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("series_" + series.getCatalogId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Series().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Series> findAllSerieses() {
		return entityManager().createQuery("SELECT o FROM Series o", Series.class).getResultList();
	}

	public static Series findSeries(Integer catalogId) {
		if (catalogId == null)
			return null;
		return entityManager().find(Series.class, catalogId);
	}

	public static List<Series> findSeriesEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Series o", Series.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<Series> fromJsonArrayToSerieses(String json) {
		return new JSONDeserializer<List<Series>>().use(null, ArrayList.class).use("values", Series.class)
				.deserialize(json);
	}

	public static Series fromJsonToSeries(String json) {
		return new JSONDeserializer<Series>().use(null, Series.class).deserialize(json);
	}

	public static void indexSeries(Series series) {
		List<Series> serieses = new ArrayList<Series>();
		serieses.add(series);
		indexSerieses(serieses);
	}

	@Async
	public static void indexSerieses(Collection<Series> serieses) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Series series : serieses) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "series_" + series.getCatalogId());
			sid.addField("series.catalog_t", series.getCatalog());
			sid.addField("series.catalogid_i", series.getCatalogId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("series_solrsummary_t",
					new StringBuilder().append(series.getCatalog()).append(" ").append(series.getCatalogId()));
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
		String searchString = "Series_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Series().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Series> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>Series</code> (serie de date)
	 * in baza de date; in caz afirmativ il returneaza, altfel, metoda il
	 * introduce in baza de date si apoi il returneaza. Verificarea existentei
	 * in baza de date se realizeaza fie dupa identificator, fie dupa un
	 * criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>catalog
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul catalogului.
	 * @param catalog
	 *            - catalogul din care deriva acest catalog.
	 * @return
	 */
	public static Series checkSeries(Integer id, Catalog catalog) {
		Series object;

		if (id != null) {
			object = findSeries(id);

			if (object != null) {
				return object;
			}
		}

		List<Series> queryResult;

		if (catalog != null) {
			TypedQuery<Series> query = entityManager().createQuery("SELECT o FROM Series o WHERE o.catalog = :catalog",
					Series.class);
			query.setParameter("catalog", catalog);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new Series();
		object.catalog = catalog;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@OneToOne
	@JoinColumn(name = "catalog_id", nullable = false, insertable = false, updatable = false)
	private Catalog catalog;

	// commented-out by Cosmin - ID should not be Serial/auto-incremented,
	// instead it should only reference an existing ID in Catalog
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "catalog_id", columnDefinition = "int4")
	private Integer catalogId;

	@OneToMany(mappedBy = "catalogId")
	private Set<SeriesDescr> seriesDescrs;

	@ManyToMany
	@JoinTable(name = "series_topic", joinColumns = { @JoinColumn(name = "catalog_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "topic_id", nullable = false) })
	private Set<Topic> topics;

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

	public Catalog getCatalog() {
		return catalog;
	}

	public Integer getCatalogId() {
		return this.catalogId;
	}

	public Set<SeriesDescr> getSeriesDescrs() {
		return seriesDescrs;
	}

	public Set<Topic> getTopics() {
		return topics;
	}

	@Transactional
	public Series merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Series merged = this.entityManager.merge(this);
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
			Series attached = Series.findSeries(this.catalogId);
			this.entityManager.remove(attached);
		}
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	public void setCatalogId(Integer id) {
		this.catalogId = id;
	}

	public void setSeriesDescrs(Set<SeriesDescr> seriesDescrs) {
		this.seriesDescrs = seriesDescrs;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("catalog")
				.toString();
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexSeries(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (catalogId != null && catalogId.equals(((Series) obj).catalogId))
				|| (catalog != null && catalog.equals(((Series) obj).catalog));
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
