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

@Entity
@Table(schema = "public", name = "person_address")
@Configurable
@Audited
public class PersonAddress {

	public static long countPersonAddresses() {
		return entityManager().createQuery("SELECT COUNT(o) FROM PersonAddress o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(PersonAddress personAddress) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("personaddress_" + personAddress.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new PersonAddress().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<PersonAddress> findAllPersonAddresses() {
		return entityManager().createQuery("SELECT o FROM PersonAddress o", PersonAddress.class).getResultList();
	}

	public static PersonAddress findPersonAddress(PersonAddressPK id) {
		if (id == null)
			return null;
		return entityManager().find(PersonAddress.class, id);
	}

	public static List<PersonAddress> findPersonAddressEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM PersonAddress o", PersonAddress.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<PersonAddress> fromJsonArrayToPersonAddresses(String json) {
		return new JSONDeserializer<List<PersonAddress>>().use(null, ArrayList.class)
				.use("values", PersonAddress.class).deserialize(json);
	}

	public static PersonAddress fromJsonToPersonAddress(String json) {
		return new JSONDeserializer<PersonAddress>().use(null, PersonAddress.class).deserialize(json);
	}

	public static void indexPersonAddress(PersonAddress personAddress) {
		List<PersonAddress> personaddresses = new ArrayList<PersonAddress>();
		personaddresses.add(personAddress);
		indexPersonAddresses(personaddresses);
	}

	@Async
	public static void indexPersonAddresses(Collection<PersonAddress> personaddresses) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (PersonAddress personAddress : personaddresses) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "personaddress_" + personAddress.getId());
			sid.addField("personAddress.addressid_t", personAddress.getAddressId());
			sid.addField("personAddress.personid_t", personAddress.getPersonId());
			sid.addField("personAddress.datestart_dt", personAddress.getDateStart());
			sid.addField("personAddress.dateend_dt", personAddress.getDateEnd());
			sid.addField("personAddress.id_t", personAddress.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("personaddress_solrsummary_t", new StringBuilder().append(personAddress.getAddressId())
					.append(" ").append(personAddress.getPersonId()).append(" ").append(personAddress.getDateStart())
					.append(" ").append(personAddress.getDateEnd()).append(" ").append(personAddress.getId()));
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
		String searchString = "PersonAddress_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new PersonAddress().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<PersonAddress> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@ManyToOne
	@JoinColumn(name = "address_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Address addressId;

	@Column(name = "date_end", columnDefinition = "date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date dateEnd;

	@Column(name = "date_start", columnDefinition = "date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date dateStart;

	@EmbeddedId
	private PersonAddressPK id;

	@ManyToOne
	@JoinColumn(name = "person_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Person personId;

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

	public Address getAddressId() {
		return addressId;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public PersonAddressPK getId() {
		return this.id;
	}

	public Person getPersonId() {
		return personId;
	}

	@Transactional
	public PersonAddress merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		PersonAddress merged = this.entityManager.merge(this);
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
			PersonAddress attached = PersonAddress.findPersonAddress(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAddressId(Address addressId) {
		this.addressId = addressId;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public void setId(PersonAddressPK id) {
		this.id = id;
	}

	public void setPersonId(Person personId) {
		this.personId = personId;
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
		indexPersonAddress(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
