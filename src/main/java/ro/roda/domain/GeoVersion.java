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
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(schema = "public", name = "geoversion")
@Audited
public class GeoVersion {

	public static long countGeoVersions() {
		return entityManager().createQuery("SELECT COUNT(o) FROM GeoVersion o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(GeoVersion geoVersion) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("geoVersion_" + geoVersion.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new GeoVersion().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<GeoVersion> findAllGeoVersions() {
		return entityManager().createQuery("SELECT o FROM GeoVersion o", GeoVersion.class).getResultList();
	}

	public static GeoVersion findGeoVersion(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(GeoVersion.class, id);
	}

	public static List<GeoVersion> findGeoVersionEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM GeoVersion o", GeoVersion.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<GeoVersion> fromJsonArrayToGeoVersions(String json) {
		return new JSONDeserializer<List<GeoVersion>>().use(null, ArrayList.class).use("values", GeoVersion.class)
				.deserialize(json);
	}

	public static GeoVersion fromJsonToGeoVersion(String json) {
		return new JSONDeserializer<GeoVersion>().use(null, GeoVersion.class).deserialize(json);
	}

	public static void indexGeoVersion(GeoVersion geoVersion) {
		List<GeoVersion> geoVersions = new ArrayList<GeoVersion>();
		geoVersions.add(geoVersion);
		indexGeoVersions(geoVersions);
	}

	@Async
	public static void indexGeoVersions(Collection<GeoVersion> geoVersions) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (GeoVersion geoVersion : geoVersions) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "geoVersion_" + geoVersion.getId());
			sid.addField("geoVersion.startdate_dt", geoVersion.getStartdate().getTime());
			sid.addField("geoVersion.enddate_dt", geoVersion.getEnddate().getTime());
			sid.addField("geoVersion.notes_s", geoVersion.getNotes());
			sid.addField("geoVersion.id_i", geoVersion.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("geoVersion_solrsummary_t", new StringBuilder().append(geoVersion.getStartdate().getTime())
					.append(" ").append(geoVersion.getEnddate().getTime()).append(" ").append(geoVersion.getNotes())
					.append(" ").append(geoVersion.getId()));
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
		String searchString = "GeoVersion_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new GeoVersion().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<GeoVersion> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>GeoVersion</code> in baza de
	 * date; in caz afirmativ il returneaza, altfel, metoda il introduce in baza
	 * de date si apoi il returneaza. Verificarea existentei in baza de date se
	 * realizeaza fie dupa identificator, fie dupa un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul tipului de date.
	 * @param name
	 *            - numele tipului de date.
	 * @param cod
	 *            - codul tipului de date.
	 * @param description
	 *            - descrierea tipului de date.
	 * @return
	 */
	public static GeoVersion checkGeoVersion(Integer id, Calendar startdate, Calendar enddate, String notes) {
		GeoVersion object;

		if (id != null) {
			object = findGeoVersion(id);

			if (object != null) {
				return object;
			}
		}

		object = new GeoVersion();
		object.startdate = startdate;
		object.enddate = enddate;
		object.notes = notes;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "startdate", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Calendar startdate;

	@Column(name = "enddate", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Calendar enddate;

	@Column(name = "notes", columnDefinition = "varchar", length = 200)
	private String notes;

	@OneToMany(mappedBy = "geoVersionId", fetch = FetchType.LAZY)
	private Set<Geography> geographies;

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

	public Integer getId() {
		return id;
	}

	public Calendar getStartdate() {
		return startdate;
	}

	public Calendar getEnddate() {
		return enddate;
	}

	public String getNotes() {
		return notes;
	}

	public Set<Geography> getGeographies() {
		return geographies;
	}

	@Transactional
	public GeoVersion merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		GeoVersion merged = this.entityManager.merge(this);
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
			GeoVersion attached = GeoVersion.findGeoVersion(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setStartdate(Calendar startdate) {
		this.startdate = startdate;
	}

	public void setEnddate(Calendar enddate) {
		this.enddate = enddate;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setGeographies(Set<Geography> geographies) {
		this.geographies = geographies;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	// public String toString() {
	// return ReflectionToStringBuilder.toString(this,
	// ToStringStyle.SHORT_PREFIX_STYLE);
	// }

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		// indexGeoVersion(this);
	}

	@PreRemove
	private void preRemove() {
		// deleteIndex(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeoVersion other = (GeoVersion) obj;
		if (startdate == null) {
			if (other.startdate != null)
				return false;
		} else if (!startdate.equals(other.startdate))
			return false;
		if (enddate == null) {
			if (other.enddate != null)
				return false;
		} else if (!enddate.equals(other.enddate))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		return true;
	}

	@JsonIgnore
	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
