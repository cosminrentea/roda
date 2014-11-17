package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(schema = "public", name = "geodatatypes")
@Audited
public class GeoDatatype {

	public static long countGeoDatatypes() {
		return entityManager().createQuery("SELECT COUNT(o) FROM GeoDatatype o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(GeoDatatype geoDatatype) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("geoDatatype_" + geoDatatype.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new GeoDatatype().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<GeoDatatype> findAllGeoDatatypes() {
		return entityManager().createQuery("SELECT o FROM GeoDatatype o", GeoDatatype.class).getResultList();
	}

	public static GeoDatatype findGeoDatatype(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(GeoDatatype.class, id);
	}

	public static List<GeoDatatype> findGeoDatatypeEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM GeoDatatype o", GeoDatatype.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<GeoDatatype> fromJsonArrayToGeoDatatypes(String json) {
		return new JSONDeserializer<List<GeoDatatype>>().use(null, ArrayList.class).use("values", GeoDatatype.class)
				.deserialize(json);
	}

	public static GeoDatatype fromJsonToGeoDatatype(String json) {
		return new JSONDeserializer<GeoDatatype>().use(null, GeoDatatype.class).deserialize(json);
	}

	public static void indexGeoDatatype(GeoDatatype geoDatatype) {
		List<GeoDatatype> geoDatatypes = new ArrayList<GeoDatatype>();
		geoDatatypes.add(geoDatatype);
		indexGeoDatatypes(geoDatatypes);
	}

	@Async
	public static void indexGeoDatatypes(Collection<GeoDatatype> geoDatatypes) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (GeoDatatype geoDatatype : geoDatatypes) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "geoDatatype_" + geoDatatype.getId());
			sid.addField("geoDatatype.name_s", geoDatatype.getName());
			sid.addField("geoDatatype.cod_s", geoDatatype.getCod());
			sid.addField("geoDatatype.description_s", geoDatatype.getDescription());
			sid.addField("geoDatatype.id_i", geoDatatype.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"geoDatatype_solrsummary_t",
					new StringBuilder().append(geoDatatype.getName()).append(" ").append(geoDatatype.getCod())
							.append(" ").append(geoDatatype.getDescription()).append(" ").append(geoDatatype.getId()));
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
		String searchString = "GeoDatatype_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new GeoDatatype().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<GeoDatatype> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>GeoDatatype</code> in baza de
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
	public static GeoDatatype checkGeoDatatype(Integer id, String name, String cod, String description) {
		GeoDatatype object;

		if (id != null) {
			object = findGeoDatatype(id);

			if (object != null) {
				return object;
			}
		}

		object = new GeoDatatype();
		object.name = name;
		object.cod = cod;
		object.description = description;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name", columnDefinition = "varchar", length = 50)
	private String name;

	@Column(name = "cod", columnDefinition = "varchar", length = 10)
	private String cod;

	@Column(name = "description", columnDefinition = "text")
	private String description;

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

	public String getName() {
		return name;
	}

	public String getCod() {
		return cod;
	}

	public String getDescription() {
		return description;
	}

	@Transactional
	public GeoDatatype merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		GeoDatatype merged = this.entityManager.merge(this);
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
			GeoDatatype attached = GeoDatatype.findGeoDatatype(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public void setDescription(String description) {
		this.description = description;
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
		indexGeoDatatype(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		GeoDatatype other = (GeoDatatype) obj;
		if (cod == null) {
			if (other.cod != null)
				return false;
		} else if (!cod.equals(other.cod))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@JsonIgnore
	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
