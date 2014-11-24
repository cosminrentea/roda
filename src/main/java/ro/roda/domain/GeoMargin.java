package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;

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
@Table(schema = "public", name = "geomargins")
@Audited
public class GeoMargin {

	public static long countGeoMargins() {
		return entityManager().createQuery("SELECT COUNT(o) FROM GeoMargin o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(GeoMargin geoMargin) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("geoMargin_" + geoMargin.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new GeoMargin().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<GeoMargin> findAllGeoMargins() {
		return entityManager().createQuery("SELECT o FROM GeoMargin o", GeoMargin.class).getResultList();
	}

	public static GeoMargin findGeoMargin(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(GeoMargin.class, id);
	}

	public static List<GeoMargin> findGeoMarginEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM GeoMargin o", GeoMargin.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<GeoMargin> fromJsonArrayToGeoMargins(String json) {
		return new JSONDeserializer<List<GeoMargin>>().use(null, ArrayList.class).use("values", GeoMargin.class)
				.deserialize(json);
	}

	public static GeoMargin fromJsonToGeoMargin(String json) {
		return new JSONDeserializer<GeoMargin>().use(null, GeoMargin.class).deserialize(json);
	}

	public static void indexGeoMargin(GeoMargin geoMargin) {
		List<GeoMargin> geoMargins = new ArrayList<GeoMargin>();
		geoMargins.add(geoMargin);
		indexGeoMargins(geoMargins);
	}

	@Async
	public static void indexGeoMargins(Collection<GeoMargin> geoMargins) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (GeoMargin geoMargin : geoMargins) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "geoMargin_" + geoMargin.getId());
			sid.addField("geoMargin.pozitie_i", geoMargin.getPozitie());
			sid.addField("geoMargin.mpath_s", geoMargin.getMpath());
			sid.addField("geoMargin.id_i", geoMargin.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("geoMargin_solrsummary_t", new StringBuilder().append(geoMargin.getPozitie()).append(" ")
					.append(geoMargin.getMpath()).append(" ").append(geoMargin.getId()));
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
		String searchString = "GeoMargin_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new GeoMargin().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<GeoMargin> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>GeoMargin</code> in baza de
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
	public static GeoMargin checkGeoMargin(Integer id, Long pozitie, String mpath) {
		GeoMargin object;

		if (id != null) {
			object = findGeoMargin(id);

			if (object != null) {
				return object;
			}
		}

		object = new GeoMargin();
		object.pozitie = pozitie;
		object.mpath = mpath;
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

	@Column(name = "pozitie", columnDefinition = "int8")
	private Long pozitie;

	@Column(name = "mpath", columnDefinition = "varchar", length = 550)
	private String mpath;

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

	public Long getPozitie() {
		return pozitie;
	}

	public String getMpath() {
		return mpath;
	}

	@Transactional
	public GeoMargin merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		GeoMargin merged = this.entityManager.merge(this);
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
			GeoMargin attached = GeoMargin.findGeoMargin(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPozitie(Long pozitie) {
		this.pozitie = pozitie;
	}

	public void setMpath(String mpath) {
		this.mpath = mpath;
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
		indexGeoMargin(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mpath == null) ? 0 : mpath.hashCode());
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
		GeoMargin other = (GeoMargin) obj;
		if (pozitie == null) {
			if (other.pozitie != null)
				return false;
		} else if (!pozitie.equals(other.pozitie))
			return false;
		if (mpath == null) {
			if (other.mpath != null)
				return false;
		} else if (!mpath.equals(other.mpath))
			return false;
		return true;
	}

	@JsonIgnore
	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
