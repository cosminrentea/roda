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

@Entity
@Table(schema = "public", name = "phone")
@Configurable
@Audited
public class Phone {

	public static long countPhones() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Phone o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Phone phone) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("phone_" + phone.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Phone().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Phone> findAllPhones() {
		return entityManager().createQuery("SELECT o FROM Phone o", Phone.class).getResultList();
	}

	public static Phone findPhone(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Phone.class, id);
	}

	public static List<Phone> findPhoneEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Phone o", Phone.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<Phone> fromJsonArrayToPhones(String json) {
		return new JSONDeserializer<List<Phone>>().use(null, ArrayList.class).use("values", Phone.class)
				.deserialize(json);
	}

	public static Phone fromJsonToPhone(String json) {
		return new JSONDeserializer<Phone>().use(null, Phone.class).deserialize(json);
	}

	public static void indexPhone(Phone phone) {
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		indexPhones(phones);
	}

	@Async
	public static void indexPhones(Collection<Phone> phones) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Phone phone : phones) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "phone_" + phone.getId());
			sid.addField("phone.phone_s", phone.getPhone());
			sid.addField("phone.phonetype_s", phone.getPhoneType());
			sid.addField("phone.id_i", phone.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("phone_solrsummary_t",
					new StringBuilder().append(phone.getPhone()).append(" ").append(phone.getPhoneType()).append(" ")
							.append(phone.getId()));
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
		String searchString = "Phone_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Phone().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Phone> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>Phone</code> (telefon) in
	 * baza de date; in caz afirmativ il returneaza, altfel, metoda il introduce
	 * in baza de date si apoi il returneaza. Verificarea existentei in baza de
	 * date se realizeaza fie dupa identificator, fie dupa un criteriu de
	 * unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>phone
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul telefonului.
	 * @param phone
	 *            - numarul telefonului.
	 * @param phoneType
	 *            - tipul telefonului (exemplu: mobil).
	 * @return
	 */
	public static Phone checkPhone(Integer id, String phone, String phoneType) {
		Phone object;

		if (id != null) {
			object = findPhone(id);

			if (object != null) {
				return object;
			}
		}

		List<Phone> queryResult;

		if (phone != null) {
			TypedQuery<Phone> query = entityManager().createQuery(
					"SELECT o FROM Phone o WHERE lower(o.phone) = lower(:phone)", Phone.class);
			query.setParameter("phone", phone);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new Phone();
		object.phone = phone;
		object.phoneType = phoneType;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "serial")
	private Integer id;

	@OneToMany(mappedBy = "phoneId")
	private Set<OrgPhone> orgPhones;

	@OneToMany(mappedBy = "phoneId")
	private Set<PersonPhone> personPhones;

	@Column(name = "phone", columnDefinition = "varchar", length = 30)
	@NotNull
	private String phone;

	@Column(name = "phone_type", columnDefinition = "varchar", length = 50)
	private String phoneType;

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
		return this.id;
	}

	public Set<OrgPhone> getOrgPhones() {
		return orgPhones;
	}

	public Set<PersonPhone> getPersonPhones() {
		return personPhones;
	}

	public String getPhone() {
		return phone;
	}

	public String getPhoneType() {
		return phoneType;
	}

	@Transactional
	public Phone merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Phone merged = this.entityManager.merge(this);
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
			Phone attached = Phone.findPhone(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setOrgPhones(Set<OrgPhone> orgPhones) {
		this.orgPhones = orgPhones;
	}

	public void setPersonPhones(Set<PersonPhone> personPhones) {
		this.personPhones = personPhones;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
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
		indexPhone(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((Phone) obj).id))
				|| (phone != null && phone.equalsIgnoreCase(((Phone) obj).phone));
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
