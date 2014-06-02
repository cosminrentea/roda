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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "city")
@Audited
public class City {

	public static long countCitys() {
		return entityManager().createQuery("SELECT COUNT(o) FROM City o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(City city) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("city_" + city.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new City().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<City> findAllCitys() {
		return entityManager().createQuery("SELECT o FROM City o", City.class).getResultList();
	}

	public static City findCity(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(City.class, id);
	}

	public static List<City> findCityEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM City o", City.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<City> fromJsonArrayToCitys(String json) {
		return new JSONDeserializer<List<City>>().use(null, ArrayList.class).use("values", City.class)
				.deserialize(json);
	}

	public static City fromJsonToCity(String json) {
		return new JSONDeserializer<City>().use(null, City.class).deserialize(json);
	}

	public static void indexCity(City city) {
		List<City> citys = new ArrayList<City>();
		citys.add(city);
		indexCitys(citys);
	}

	@Async
	public static void indexCitys(Collection<City> citys) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (City city : citys) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "city_" + city.getId());
			sid.addField("city.countryid_t", city.getCountryId());
			sid.addField("city.name_s", city.getName());
			sid.addField("city.citycode_s", city.getCityCode());
			sid.addField("city.citycodename_s", city.getCityCodeName());
			sid.addField("city.citycodesup_s", city.getCityCodeSup());
			sid.addField("city.prefix_s", city.getPrefix());
			sid.addField("city.citytype_s", city.getCityType());
			sid.addField("city.citytypesystem_s", city.getCityTypeSystem());
			sid.addField("city.id_i", city.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"city_solrsummary_t",
					new StringBuilder().append(city.getCountryId()).append(" ").append(city.getName()).append(" ")
							.append(city.getCityCode()).append(" ").append(city.getCityCodeName()).append(" ")
							.append(city.getCityCodeSup()).append(" ").append(city.getPrefix()).append(" ")
							.append(city.getCityType()).append(" ").append(city.getCityTypeSystem()).append(" ")
							.append(city.getId()));
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
		String searchString = "City_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new City().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<City> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>City</code> (oras) in baza de
	 * date; in caz afirmativ il returneaza, altfel, metoda il introduce in baza
	 * de date si apoi il returneaza. Verificarea existentei in baza de date se
	 * realizeaza fie dupa identificator, fie dupa un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>name + countryId
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul orasului.
	 * @param name
	 *            - numele orasului.
	 * @param countryId
	 *            - identificatorul tarii unde este situat orasul.
	 * @param cityCode
	 *            - codul orasului in cadrul sistemului de codificare a
	 *            oraselor.
	 * @param cityCodeName
	 *            - numele sistemului de codificare a orasului (exemplu: SIRUTA
	 *            pentru Romania).
	 * @param cityCodeSup
	 *            - codul entitatii superioare orasului.
	 * @param prefix
	 *            - prefixul numelui orasului (exemplu: municipiu).
	 * @param cityType
	 *            - tipul orasului.
	 * @param cityTypeSystem
	 *            - numele sistemului de codificare a tipului orasului (exemplu:
	 *            SIRUTA pentru Romania).
	 * @return
	 */
	public static City checkCity(Integer id, String name, Country countryId, String cityCode, String cityCodeName,
			String cityCodeSup, String prefix, String cityType, String cityTypeSystem) {
		City object;

		if (id != null) {
			object = findCity(id);

			if (object != null) {
				return object;
			}
		}

		List<City> queryResult;

		if (name != null && countryId != null) {
			TypedQuery<City> query = entityManager().createQuery(
					"SELECT o FROM City o WHERE lower(o.name) = lower(:name) AND " + "o.countryId = :countryId",
					City.class);
			query.setParameter("name", name);
			query.setParameter("countryId", countryId);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new City();
		object.name = name;
		object.countryId = countryId;
		object.cityCode = cityCode;
		object.cityCodeName = cityCodeName;
		object.cityCodeSup = cityCodeSup;
		object.prefix = prefix;
		object.cityType = cityType;
		object.cityTypeSystem = cityTypeSystem;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@OneToMany(mappedBy = "cityId")
	private Set<Address> addresses;

	@Column(name = "city_code", columnDefinition = "varchar", length = 50)
	private String cityCode;

	@Column(name = "city_code_name", columnDefinition = "varchar", length = 100)
	private String cityCodeName;

	@Column(name = "city_code_sup", columnDefinition = "varchar", length = 100)
	private String cityCodeSup;

	@Column(name = "city_type", columnDefinition = "varchar", length = 50)
	private String cityType;

	@Column(name = "city_type_system", columnDefinition = "varchar", length = 50)
	private String cityTypeSystem;

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

	@Column(name = "prefix", columnDefinition = "varchar", length = 50)
	private String prefix;

	@ManyToMany
	@JoinTable(name = "region_city", joinColumns = { @JoinColumn(name = "city_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "region_id", nullable = false) })
	private Set<Region> regions;

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

	public Set<Address> getAddresses() {
		return addresses;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getCityCodeName() {
		return cityCodeName;
	}

	public String getCityCodeSup() {
		return cityCodeSup;
	}

	public String getCityType() {
		return cityType;
	}

	public String getCityTypeSystem() {
		return cityTypeSystem;
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

	public String getPrefix() {
		return prefix;
	}

	public Set<Region> getRegions() {
		return regions;
	}

	@Transactional
	public City merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		City merged = this.entityManager.merge(this);
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
			City attached = City.findCity(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public void setCityCodeName(String cityCodeName) {
		this.cityCodeName = cityCodeName;
	}

	public void setCityCodeSup(String cityCodeSup) {
		this.cityCodeSup = cityCodeSup;
	}

	public void setCityType(String cityType) {
		this.cityType = cityType;
	}

	public void setCityTypeSystem(String cityTypeSystem) {
		this.cityTypeSystem = cityTypeSystem;
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

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setRegions(Set<Region> regions) {
		this.regions = regions;
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
		indexCity(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((City) obj).id))
				|| ((countryId != null && countryId.equals(((City) obj).countryId)) && (name != null && name
						.equalsIgnoreCase(((City) obj).name)));
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
