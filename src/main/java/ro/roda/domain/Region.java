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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(schema = "public", name = "region")
@Configurable
@Audited
public class Region {

	public static long countRegions() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Region o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Region region) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("region_" + region.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Region().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Region> findAllRegions() {
		return entityManager().createQuery("SELECT o FROM Region o", Region.class).getResultList();
	}

	public static Region findRegion(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Region.class, id);
	}

	public static List<Region> findRegionEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Region o", Region.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<Region> fromJsonArrayToRegions(String json) {
		return new JSONDeserializer<List<Region>>().use(null, ArrayList.class).use("values", Region.class)
				.deserialize(json);
	}

	public static Region fromJsonToRegion(String json) {
		return new JSONDeserializer<Region>().use(null, Region.class).deserialize(json);
	}

	public static void indexRegion(Region region) {
		List<Region> regions = new ArrayList<Region>();
		regions.add(region);
		indexRegions(regions);
	}

	@Async
	public static void indexRegions(Collection<Region> regions) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Region region : regions) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "region_" + region.getId());
			sid.addField("region.countryid_t", region.getCountryId());
			sid.addField("region.regiontypeid_t", region.getRegiontypeId());
			sid.addField("region.name_s", region.getName());
			sid.addField("region.regioncode_s", region.getRegionCode());
			sid.addField("region.regioncodename_s", region.getRegionCodeName());
			sid.addField("region.id_i", region.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("region_solrsummary_t",
					new StringBuilder().append(region.getCountryId()).append(" ").append(region.getRegiontypeId())
							.append(" ").append(region.getName()).append(" ").append(region.getRegionCode())
							.append(" ").append(region.getRegionCodeName()).append(" ").append(region.getId()));
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
		String searchString = "Region_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Region().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Region> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>Region</code> (regiune) in
	 * baza de date; in caz afirmativ il returneaza, altfel, metoda il introduce
	 * in baza de date si apoi il returneaza. Verificarea existentei in baza de
	 * date se realizeaza fie dupa identificator, fie dupa un criteriu de
	 * unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>countryId + regiontypeId + name
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul regiunii.
	 * @param name
	 *            - numele regiunii.
	 * @param regiontypeId
	 *            - tipul de regiune.
	 * @param countryId
	 *            - tara in care se afla regiunea.
	 * @param regionCode
	 * @param regionCodeName
	 * @return
	 */
	public static Region checkRegion(Integer id, String name, Regiontype regiontypeId, Country countryId,
			String regionCode, String regionCodeName) {
		Region object;

		if (id != null) {
			object = findRegion(id);

			if (object != null) {
				return object;
			}
		}

		List<Region> queryResult;

		if (countryId != null && regiontypeId != null && name != null) {
			TypedQuery<Region> query = entityManager().createQuery(
					"SELECT o FROM Region o WHERE countryId = :countryId AND " + "regiontypeId = :regiontypeId AND "
							+ "lower(o.name) = lower(:name)", Region.class);
			query.setParameter("countryId", countryId);
			query.setParameter("regiontypeId", regiontypeId);
			query.setParameter("name", name);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new Region();
		object.name = name;
		object.regiontypeId = regiontypeId;
		object.countryId = countryId;
		object.regionCode = regionCode;
		object.regionCodeName = regionCodeName;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@ManyToMany(mappedBy = "regions")
	private Set<City> cities;

	@ManyToOne
	@JoinColumn(name = "country_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false)
	private Country countryId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "text")
	@NotNull
	private String name;

	@Column(name = "region_code", columnDefinition = "varchar", length = 50)
	private String regionCode;

	@Column(name = "region_code_name", columnDefinition = "varchar", length = 50)
	private String regionCodeName;

	@ManyToOne
	@JoinColumn(name = "regiontype_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false)
	private Regiontype regiontypeId;

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

	public Set<City> getCities() {
		return cities;
	}

	public Country getCountryId() {
		return countryId;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public String getRegionCodeName() {
		return regionCodeName;
	}

	public Regiontype getRegiontypeId() {
		return regiontypeId;
	}

	@Transactional
	public Region merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Region merged = this.entityManager.merge(this);
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
			Region attached = Region.findRegion(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setCities(Set<City> cities) {
		this.cities = cities;
	}

	public void setCountryId(Country countryId) {
		this.countryId = countryId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public void setRegionCodeName(String regionCodeName) {
		this.regionCodeName = regionCodeName;
	}

	public void setRegiontypeId(Regiontype regiontypeId) {
		this.regiontypeId = regiontypeId;
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
		indexRegion(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((Region) obj).id))
				|| ((regiontypeId != null && regiontypeId.equals(((Region) obj).regiontypeId))
						&& (countryId != null && countryId.equals(((Region) obj).countryId)) && (name != null && name
						.equalsIgnoreCase(((Region) obj).name)));
	}

	@JsonIgnore public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
