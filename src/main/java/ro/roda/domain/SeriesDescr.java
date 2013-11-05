package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(schema = "public", name = "series_descr")

public class SeriesDescr {

	public static long countSeriesDescrs() {
		return entityManager().createQuery("SELECT COUNT(o) FROM SeriesDescr o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(SeriesDescr seriesDescr) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("seriesdescr_" + seriesDescr.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new SeriesDescr().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<SeriesDescr> findAllSeriesDescrs() {
		return entityManager().createQuery("SELECT o FROM SeriesDescr o", SeriesDescr.class).getResultList();
	}

	public static SeriesDescr findSeriesDescr(SeriesDescrPK id) {
		if (id == null)
			return null;
		return entityManager().find(SeriesDescr.class, id);
	}

	public static List<SeriesDescr> findSeriesDescrEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM SeriesDescr o", SeriesDescr.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<SeriesDescr> fromJsonArrayToSeriesDescrs(String json) {
		return new JSONDeserializer<List<SeriesDescr>>().use(null, ArrayList.class).use("values", SeriesDescr.class)
				.deserialize(json);
	}

	public static SeriesDescr fromJsonToSeriesDescr(String json) {
		return new JSONDeserializer<SeriesDescr>().use(null, SeriesDescr.class).deserialize(json);
	}

	public static void indexSeriesDescr(SeriesDescr seriesDescr) {
		List<SeriesDescr> seriesdescrs = new ArrayList<SeriesDescr>();
		seriesdescrs.add(seriesDescr);
		indexSeriesDescrs(seriesdescrs);
	}

	@Async
	public static void indexSeriesDescrs(Collection<SeriesDescr> seriesdescrs) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (SeriesDescr seriesDescr : seriesdescrs) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "seriesdescr_" + seriesDescr.getId());
			sid.addField("seriesDescr.langid_t", seriesDescr.getLangId());
			sid.addField("seriesDescr.catalogid_t", seriesDescr.getCatalogId());
			sid.addField("seriesDescr.notes_s", seriesDescr.getNotes());
			sid.addField("seriesDescr.title_s", seriesDescr.getTitle());
			sid.addField("seriesDescr.subtitle_s", seriesDescr.getSubtitle());
			sid.addField("seriesDescr.alternativetitle_s", seriesDescr.getAlternativeTitle());
			sid.addField("seriesDescr.abstract1_s", seriesDescr.getAbstract1());
			sid.addField("seriesDescr.timecovered_s", seriesDescr.getTimeCovered());
			sid.addField("seriesDescr.geographiccoverage_s", seriesDescr.getGeographicCoverage());
			sid.addField("seriesDescr.id_t", seriesDescr.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"seriesdescr_solrsummary_t",
					new StringBuilder().append(seriesDescr.getLangId()).append(" ").append(seriesDescr.getCatalogId())
							.append(" ").append(seriesDescr.getNotes()).append(" ").append(seriesDescr.getTitle())
							.append(" ").append(seriesDescr.getSubtitle()).append(" ")
							.append(seriesDescr.getAlternativeTitle()).append(" ").append(seriesDescr.getAbstract1())
							.append(" ").append(seriesDescr.getTimeCovered()).append(" ")
							.append(seriesDescr.getGeographicCoverage()).append(" ").append(seriesDescr.getId()));
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
		String searchString = "SeriesDescr_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new SeriesDescr().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<SeriesDescr> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "abstract", columnDefinition = "text")
	private String abstract1;

	@Column(name = "alternative_title", columnDefinition = "text")
	private String alternativeTitle;

	@ManyToOne
	@JoinColumn(name = "catalog_id", columnDefinition = "integer", referencedColumnName = "catalog_id", nullable = false, insertable = false, updatable = false)
	private Series catalogId;

	@Column(name = "geographic_coverage", columnDefinition = "text")
	private String geographicCoverage;

	@EmbeddedId
	private SeriesDescrPK id;

	@ManyToOne
	@JoinColumn(name = "lang_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Lang langId;

	@Column(name = "notes", columnDefinition = "text")
	private String notes;

	@Column(name = "subtitle", columnDefinition = "text")
	private String subtitle;

	@Column(name = "time_covered", columnDefinition = "text")
	private String timeCovered;

	@Column(name = "title", columnDefinition = "text")
	@NotNull
	private String title;

	@PersistenceContext
	transient EntityManager entityManager;

	@Autowired(required=false)
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

	public String getAbstract1() {
		return abstract1;
	}

	public String getAlternativeTitle() {
		return alternativeTitle;
	}

	public Series getCatalogId() {
		return catalogId;
	}

	public String getGeographicCoverage() {
		return geographicCoverage;
	}

	public SeriesDescrPK getId() {
		return this.id;
	}

	public Lang getLangId() {
		return langId;
	}

	public String getNotes() {
		return notes;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public String getTimeCovered() {
		return timeCovered;
	}

	public String getTitle() {
		return title;
	}

	@Transactional
	public SeriesDescr merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		SeriesDescr merged = this.entityManager.merge(this);
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
			SeriesDescr attached = SeriesDescr.findSeriesDescr(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAbstract1(String abstract1) {
		this.abstract1 = abstract1;
	}

	public void setAlternativeTitle(String alternativeTitle) {
		this.alternativeTitle = alternativeTitle;
	}

	public void setCatalogId(Series catalogId) {
		this.catalogId = catalogId;
	}

	public void setGeographicCoverage(String geographicCoverage) {
		this.geographicCoverage = geographicCoverage;
	}

	public void setId(SeriesDescrPK id) {
		this.id = id;
	}

	public void setLangId(Lang langId) {
		this.langId = langId;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public void setTimeCovered(String timeCovered) {
		this.timeCovered = timeCovered;
	}

	public void setTitle(String title) {
		this.title = title;
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
		indexSeriesDescr(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
