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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(schema = "public", name = "address")
@Configurable
public class Address {

	public static long countAddresses() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Address o",
				Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Address address) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("address_" + address.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Address().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static Address findAddress(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Address.class, id);
	}

	public static List<Address> findAddressEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM Address o", Address.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static TypedQuery<Address> findAddressesByCityId(City cityId) {
		if (cityId == null)
			throw new IllegalArgumentException(
					"The cityId argument is required");
		EntityManager em = Address.entityManager();
		TypedQuery<Address> q = em.createQuery(
				"SELECT o FROM Address AS o WHERE o.cityId = :cityId",
				Address.class);
		q.setParameter("cityId", cityId);
		return q;
	}

	public static TypedQuery<Address> findAddressesByCityIdAndPostalCodeEquals(
			City cityId, String postalCode) {
		if (cityId == null)
			throw new IllegalArgumentException(
					"The cityId argument is required");
		if (postalCode == null || postalCode.length() == 0)
			throw new IllegalArgumentException(
					"The postalCode argument is required");
		EntityManager em = Address.entityManager();
		TypedQuery<Address> q = em
				.createQuery(
						"SELECT o FROM Address AS o WHERE o.cityId = :cityId AND o.postalCode = :postalCode",
						Address.class);
		q.setParameter("cityId", cityId);
		q.setParameter("postalCode", postalCode);
		return q;
	}

	public static TypedQuery<Address> findAddressesByPostalCodeEquals(
			String postalCode) {
		if (postalCode == null || postalCode.length() == 0)
			throw new IllegalArgumentException(
					"The postalCode argument is required");
		EntityManager em = Address.entityManager();
		TypedQuery<Address> q = em.createQuery(
				"SELECT o FROM Address AS o WHERE o.postalCode = :postalCode",
				Address.class);
		q.setParameter("postalCode", postalCode);
		return q;
	}

	public static List<Address> findAllAddresses() {
		return entityManager().createQuery("SELECT o FROM Address o",
				Address.class).getResultList();
	}

	public static Collection<Address> fromJsonArrayToAddresses(String json) {
		return new JSONDeserializer<List<Address>>().use(null, ArrayList.class)
				.use("values", Address.class).deserialize(json);
	}

	public static Address fromJsonToAddress(String json) {
		return new JSONDeserializer<Address>().use(null, Address.class)
				.deserialize(json);
	}

	public static void indexAddress(Address address) {
		List<Address> addresses = new ArrayList<Address>();
		addresses.add(address);
		indexAddresses(addresses);
	}

	@Async
	public static void indexAddresses(Collection<Address> addresses) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Address address : addresses) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "address_" + address.getId());
			sid.addField("address.id_i", address.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("address_solrsummary_t",
					new StringBuilder().append(address.getId()));
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
		String searchString = "Address_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Address().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Address> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>Address</code> (adresa) in
	 * baza de date; in caz afirmativ il returneaza, altfel, metoda il introduce
	 * in baza de date si apoi il returneaza. Verificarea existentei in baza de
	 * date se realizeaza fie dupa identificator, fie dupa un criteriu de
	 * unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>cityId + postalCode + address1 + address2 (codurile postale sunt
	 * unice pe tari)
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul adresei.
	 * @param cityId
	 *            - orasul adresei.
	 * @param postalCode
	 *            - codul postal al adresei.
	 * @param address1
	 *            - primele elemente ale adresei (exemplu: strada si numar).
	 * @param address2
	 *            - elementele suplimentare ale adresei.
	 * @param subdivCode
	 *            - codul subdiviziunii orasului in care se gaseste adresa. In
	 *            cazul in care se foloseste acest parametru, el trebuie sa fie
	 *            obligatoriu insotit de parametrul subdivName.
	 * @param subdivName
	 *            - numele subdiviziunii orasului in care se gaseste adresa
	 *            (exemplu: sector). In cazul in care se foloseste acest
	 *            parametru, el trebuie sa fie obligatoriu insotit de parametrul
	 *            subdivCode.
	 * @return
	 */
	public static Address checkAddress(Integer id, City cityId,
			String postalCode, String address1, String address2,
			String subdivCode, String subdivName) {
		Address object;

		if (id != null) {
			object = findAddress(id);

			if (object != null) {
				return object;
			}
		}

		List<Address> queryResult;

		if (cityId != null && postalCode != null && address1 != null
				&& address2 != null) {
			TypedQuery<Address> query = entityManager().createQuery(
					"SELECT o FROM Address o WHERE o.cityId = :cityId AND "
							+ "lower(o.postalCode) = lower(:postalCode) AND "
							+ "lower(o.address1) = lower(:address1) AND "
							+ "lower(o.address2) = lower(:address2)",
					Address.class);
			query.setParameter("cityId", cityId);
			query.setParameter("postalCode", postalCode);
			query.setParameter("address1", address1);
			query.setParameter("address2", address2);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new Address();
		object.cityId = cityId;
		object.postalCode = postalCode;
		object.address1 = address1;
		object.address2 = address2;
		object.subdivCode = subdivCode;
		object.subdivName = subdivName;
		object.persist();

		return object;
	}

	@Column(name = "address1", columnDefinition = "text")
	@NotNull
	private String address1;

	@Column(name = "address2", columnDefinition = "text")
	private String address2;

	@ManyToOne
	@JoinColumn(name = "city_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false)
	private City cityId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@OneToMany(mappedBy = "addressId")
	private Set<OrgAddress> orgAddresses;

	@OneToMany(mappedBy = "addressId")
	private Set<PersonAddress> personAddresses;

	@Column(name = "postal_code", columnDefinition = "varchar", length = 30)
	private String postalCode;

	@Column(name = "subdiv_code", columnDefinition = "varchar", length = 50)
	private String subdivCode;

	@Column(name = "subdiv_name", columnDefinition = "varchar", length = 200)
	private String subdivName;

	@PersistenceContext
	transient EntityManager entityManager;

	@Autowired
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

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public City getCityId() {
		return cityId;
	}

	public Integer getId() {
		return this.id;
	}

	public Set<OrgAddress> getOrgAddresses() {
		return orgAddresses;
	}

	public Set<PersonAddress> getPersonAddresses() {
		return personAddresses;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getSubdivCode() {
		return subdivCode;
	}

	public String getSubdivName() {
		return subdivName;
	}

	@Transactional
	public Address merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Address merged = this.entityManager.merge(this);
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
			Address attached = Address.findAddress(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public void setCityId(City cityId) {
		this.cityId = cityId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setOrgAddresses(Set<OrgAddress> orgAddresses) {
		this.orgAddresses = orgAddresses;
	}

	public void setPersonAddresses(Set<PersonAddress> personAddresses) {
		this.personAddresses = personAddresses;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setSubdivCode(String subdivCode) {
		this.subdivCode = subdivCode;
	}

	public void setSubdivName(String subdivName) {
		this.subdivName = subdivName;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexAddress(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((Address) obj).id))
				|| ((cityId != null && cityId.equals(((Address) obj).cityId))
						&& (postalCode != null && postalCode
								.equalsIgnoreCase(((Address) obj).postalCode))
						&& (address1 != null && address1
								.equalsIgnoreCase(((Address) obj).address1)) && (address2 != null && address2
						.equalsIgnoreCase(((Address) obj).address2)));
	}
}
