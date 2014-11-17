package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "geodata")
@Audited
public class GeoData {

	public static long countGeoDatas() {
		return entityManager().createQuery("SELECT COUNT(o) FROM GeoData o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(GeoData geoData) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("geodata_" + geoData.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new GeoData().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<GeoData> findAllGeoData() {
		return entityManager().createQuery("SELECT o FROM GeoData o", GeoData.class).getResultList();
	}

	public static GeoData findGeoData(GeoDataPK id) {
		if (id == null)
			return null;
		return entityManager().find(GeoData.class, id);
	}

	public static List<GeoData> findGeoDataEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM GeoData o", GeoData.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<GeoData> fromJsonArrayToGeoDatas(String json) {
		return new JSONDeserializer<List<GeoData>>().use(null, ArrayList.class).use("values", GeoData.class)
				.deserialize(json);
	}

	public static GeoData fromJsonToGeoData(String json) {
		return new JSONDeserializer<GeoData>().use(null, GeoData.class).deserialize(json);
	}

	public static void indexGeoData(GeoData geoData) {
		List<GeoData> personorgs = new ArrayList<GeoData>();
		personorgs.add(geoData);
		indexGeoDatas(personorgs);
	}

	@Async
	public static void indexGeoDatas(Collection<GeoData> geodataColl) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (GeoData geoData : geodataColl) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "geodata_" + geoData.getId());
			sid.addField("geoData.datatypesid_t", geoData.getDatatypesId());
			sid.addField("geoData.geographyid_t", geoData.getGeographyId());
			sid.addField("geoData.datestart_dt", geoData.getStartdate());
			sid.addField("geoData.dateend_dt", geoData.getEnddate());
			sid.addField("geoData.id_t", geoData.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("personorg_solrsummary_t",
					new StringBuilder().append(geoData.getDatatypesId()).append(" ").append(geoData.getGeographyId())
							.append(" ").append(geoData.getStartdate()).append(" ").append(geoData.getEnddate())
							.append(" ").append(geoData.getId()));
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
		String searchString = "GeoData_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new GeoData().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<GeoData> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@Column(name = "enddate", columnDefinition = "date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date enddate;

	@Column(name = "startdate", columnDefinition = "date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date startdate;

	@EmbeddedId
	private GeoDataPK id;

	@ManyToOne
	@JoinColumn(name = "datatypes_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private GeoDatatype datatypesId;

	@ManyToOne
	@JoinColumn(name = "geography_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Geography geographyId;

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

	public Date getEnddate() {
		return enddate;
	}

	public Date getStartdate() {
		return startdate;
	}

	public GeoDataPK getId() {
		return this.id;
	}

	public GeoDatatype getDatatypesId() {
		return datatypesId;
	}

	public Geography getGeographyId() {
		return geographyId;
	}

	@Transactional
	public GeoData merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		GeoData merged = this.entityManager.merge(this);
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
			GeoData attached = GeoData.findGeoData(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(GeoDataPK id) {
		this.id = id;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public void setDatatypesId(GeoDatatype datatypesId) {
		this.datatypesId = datatypesId;
	}

	public void setGeographyId(Geography geographyId) {
		this.geographyId = geographyId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexGeoData(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
