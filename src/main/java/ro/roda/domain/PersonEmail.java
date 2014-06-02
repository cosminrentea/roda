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

@Configurable
@Entity
@Table(schema = "public", name = "person_email")
@Audited
public class PersonEmail {

	public static long countPersonEmails() {
		return entityManager().createQuery("SELECT COUNT(o) FROM PersonEmail o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(PersonEmail personEmail) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("personemail_" + personEmail.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new PersonEmail().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<PersonEmail> findAllPersonEmails() {
		return entityManager().createQuery("SELECT o FROM PersonEmail o", PersonEmail.class).getResultList();
	}

	public static PersonEmail findPersonEmail(PersonEmailPK id) {
		if (id == null)
			return null;
		return entityManager().find(PersonEmail.class, id);
	}

	public static List<PersonEmail> findPersonEmailEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM PersonEmail o", PersonEmail.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<PersonEmail> fromJsonArrayToPersonEmails(String json) {
		return new JSONDeserializer<List<PersonEmail>>().use(null, ArrayList.class).use("values", PersonEmail.class)
				.deserialize(json);
	}

	public static PersonEmail fromJsonToPersonEmail(String json) {
		return new JSONDeserializer<PersonEmail>().use(null, PersonEmail.class).deserialize(json);
	}

	public static void indexPersonEmail(PersonEmail personEmail) {
		List<PersonEmail> personemails = new ArrayList<PersonEmail>();
		personemails.add(personEmail);
		indexPersonEmails(personemails);
	}

	@Async
	public static void indexPersonEmails(Collection<PersonEmail> personemails) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (PersonEmail personEmail : personemails) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "personemail_" + personEmail.getId());
			sid.addField("personEmail.id_t", personEmail.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("personemail_solrsummary_t", new StringBuilder().append(personEmail.getId()));
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
		String searchString = "PersonEmail_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new PersonEmail().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<PersonEmail> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@ManyToOne
	@JoinColumn(name = "email_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Email emailId;

	@EmbeddedId
	private PersonEmailPK id;

	@Column(name = "main", columnDefinition = "bool")
	@NotNull
	private boolean main;

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

	public Email getEmailId() {
		return emailId;
	}

	public PersonEmailPK getId() {
		return this.id;
	}

	public Person getPersonId() {
		return personId;
	}

	public boolean isMain() {
		return main;
	}

	@Transactional
	public PersonEmail merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		PersonEmail merged = this.entityManager.merge(this);
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
			PersonEmail attached = PersonEmail.findPersonEmail(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setEmailId(Email emailId) {
		this.emailId = emailId;
	}

	public void setId(PersonEmailPK id) {
		this.id = id;
	}

	public void setMain(boolean main) {
		this.main = main;
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
		indexPersonEmail(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
