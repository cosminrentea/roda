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
@Table(schema = "public", name = "geodraw")
@Audited
public class GeoDraw {

	public static long countGeoDraws() {
		return entityManager().createQuery("SELECT COUNT(o) FROM GeoDraw o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(GeoDraw geoDraw) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("geodata_" + geoDraw.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new GeoDraw().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<GeoDraw> findAllGeoDraws() {
		return entityManager().createQuery("SELECT o FROM GeoDraw o", GeoDraw.class).getResultList();
	}

	public static GeoDraw findGeoDraw(GeoDrawPK id) {
		if (id == null)
			return null;
		return entityManager().find(GeoDraw.class, id);
	}

	public static List<GeoDraw> findGeoDrawEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM GeoDraw o", GeoDraw.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static List<GeoDraw> findAllGeoDrawsForGeography(Geography geography) {
		return entityManager()
				.createQuery("SELECT o FROM GeoDraw o WHERE geographyId = " + geography.getId() + " ORDER BY pozitie",
						GeoDraw.class).getResultList();
	}

	public static Collection<GeoDraw> fromJsonArrayToGeoDraws(String json) {
		return new JSONDeserializer<List<GeoDraw>>().use(null, ArrayList.class).use("values", GeoDraw.class)
				.deserialize(json);
	}

	public static GeoDraw fromJsonToGeoDraw(String json) {
		return new JSONDeserializer<GeoDraw>().use(null, GeoDraw.class).deserialize(json);
	}

	public static void indexGeoDraw(GeoDraw geoDraw) {
		List<GeoDraw> personorgs = new ArrayList<GeoDraw>();
		personorgs.add(geoDraw);
		indexGeoDraws(personorgs);
	}

	@Async
	public static void indexGeoDraws(Collection<GeoDraw> geodataColl) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (GeoDraw geoDraw : geodataColl) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "geodata_" + geoDraw.getId());
			sid.addField("geoDraw.datatypesid_t", geoDraw.getGeomarginsId());
			sid.addField("geoDraw.geographyid_t", geoDraw.getGeographyId());
			sid.addField("geoDraw.pozitie_i", geoDraw.getPozitie());
			sid.addField("geoDraw.id_t", geoDraw.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("personorg_solrsummary_t",
					new StringBuilder().append(geoDraw.getGeomarginsId()).append(" ").append(geoDraw.getGeographyId())
							.append(" ").append(geoDraw.getPozitie()).append(" ").append(geoDraw.getId()));
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
		String searchString = "GeoDraw_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new GeoDraw().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<GeoDraw> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@EmbeddedId
	private GeoDrawPK id;

	@ManyToOne
	@JoinColumn(name = "geomargins_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private GeoMargin geomarginsId;

	@ManyToOne
	@JoinColumn(name = "geography_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Geography geographyId;

	@Column(name = "pozitie", columnDefinition = "int4")
	private Integer pozitie;

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

	public GeoDrawPK getId() {
		return this.id;
	}

	public GeoMargin getGeomarginsId() {
		return geomarginsId;
	}

	public Geography getGeographyId() {
		return geographyId;
	}

	public Integer getPozitie() {
		return pozitie;
	}

	@Transactional
	public GeoDraw merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		GeoDraw merged = this.entityManager.merge(this);
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
			GeoDraw attached = GeoDraw.findGeoDraw(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(GeoDrawPK id) {
		this.id = id;
	}

	public void setGeomarginsId(GeoMargin datatypesId) {
		this.geomarginsId = geomarginsId;
	}

	public void setGeographyId(Geography geographyId) {
		this.geographyId = geographyId;
	}

	public void setPozitie(Integer pozitie) {
		this.pozitie = pozitie;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@JsonIgnore
	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexGeoDraw(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
