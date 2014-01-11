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
@Table(schema = "public", name = "person_phone")
@Configurable
@Audited
public class PersonPhone {

	public static long countPersonPhones() {
		return entityManager().createQuery("SELECT COUNT(o) FROM PersonPhone o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(PersonPhone personPhone) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("personphone_" + personPhone.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new PersonPhone().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<PersonPhone> findAllPersonPhones() {
		return entityManager().createQuery("SELECT o FROM PersonPhone o", PersonPhone.class).getResultList();
	}

	public static PersonPhone findPersonPhone(PersonPhonePK id) {
		if (id == null)
			return null;
		return entityManager().find(PersonPhone.class, id);
	}

	public static List<PersonPhone> findPersonPhoneEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM PersonPhone o", PersonPhone.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<PersonPhone> fromJsonArrayToPersonPhones(String json) {
		return new JSONDeserializer<List<PersonPhone>>().use(null, ArrayList.class).use("values", PersonPhone.class)
				.deserialize(json);
	}

	public static PersonPhone fromJsonToPersonPhone(String json) {
		return new JSONDeserializer<PersonPhone>().use(null, PersonPhone.class).deserialize(json);
	}

	public static void indexPersonPhone(PersonPhone personPhone) {
		List<PersonPhone> personphones = new ArrayList<PersonPhone>();
		personphones.add(personPhone);
		indexPersonPhones(personphones);
	}

	@Async
	public static void indexPersonPhones(Collection<PersonPhone> personphones) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (PersonPhone personPhone : personphones) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "personphone_" + personPhone.getId());
			sid.addField("personPhone.personid_t", personPhone.getPersonId());
			sid.addField("personPhone.phoneid_t", personPhone.getPhoneId());
			sid.addField("personPhone.id_t", personPhone.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("personphone_solrsummary_t", new StringBuilder().append(personPhone.getPersonId()).append(" ")
					.append(personPhone.getPhoneId()).append(" ").append(personPhone.getId()));
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
		String searchString = "PersonPhone_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new PersonPhone().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<PersonPhone> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@EmbeddedId
	private PersonPhonePK id;

	@Column(name = "main", columnDefinition = "bool")
	@NotNull
	private boolean main;

	@ManyToOne
	@JoinColumn(name = "person_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Person personId;

	@ManyToOne
	@JoinColumn(name = "phone_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Phone phoneId;

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

	public PersonPhonePK getId() {
		return this.id;
	}

	public Person getPersonId() {
		return personId;
	}

	public Phone getPhoneId() {
		return phoneId;
	}

	public boolean isMain() {
		return main;
	}

	@Transactional
	public PersonPhone merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		PersonPhone merged = this.entityManager.merge(this);
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
			PersonPhone attached = PersonPhone.findPersonPhone(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(PersonPhonePK id) {
		this.id = id;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

	public void setPersonId(Person personId) {
		this.personId = personId;
	}

	public void setPhoneId(Phone phoneId) {
		this.phoneId = phoneId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
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
		indexPersonPhone(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
