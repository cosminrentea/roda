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
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "email")
public class Email {

	public static long countEmails() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Email o",
				Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Email email) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("email_" + email.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Email().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Email> findAllEmails() {
		return entityManager()
				.createQuery("SELECT o FROM Email o", Email.class)
				.getResultList();
	}

	public static Email findEmail(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Email.class, id);
	}

	public static List<Email> findEmailEntries(int firstResult, int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM Email o", Email.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static Collection<Email> fromJsonArrayToEmails(String json) {
		return new JSONDeserializer<List<Email>>().use(null, ArrayList.class)
				.use("values", Email.class).deserialize(json);
	}

	public static Email fromJsonToEmail(String json) {
		return new JSONDeserializer<Email>().use(null, Email.class)
				.deserialize(json);
	}

	public static void indexEmail(Email email) {
		List<Email> emails = new ArrayList<Email>();
		emails.add(email);
		indexEmails(emails);
	}

	@Async
	public static void indexEmails(Collection<Email> emails) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Email email : emails) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "email_" + email.getId());
			sid.addField("email.email_s", email.getEmail());
			sid.addField("email.id_i", email.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("email_solrsummary_t",
					new StringBuilder().append(email.getEmail()).append(" ")
							.append(email.getId()));
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
		String searchString = "Email_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Email().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Email> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui email in baza de date; daca exista, returneaza
	 * obiectul respectiv, altfel, il introduce in baza de date si returneaza
	 * obiectul corespunzator.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <p>
	 * <ul>
	 * <li>emailId
	 * <li>email
	 * <ul>
	 * <p>
	 * 
	 * 
	 * @param emailId
	 *            - cheia primara a email-ului
	 * @param email
	 *            - adresa de email
	 * @return
	 */
	public static Email checkEmail(Integer emailId, String email) {
		// TODO
		return null;
	}

	@Column(name = "email", columnDefinition = "varchar", length = 200)
	@NotNull
	private String email;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@OneToMany(mappedBy = "emailId")
	private Set<OrgEmail> orgEmails;

	@OneToMany(mappedBy = "emailId")
	private Set<PersonEmail> personEmails;

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

	public String getEmail() {
		return email;
	}

	public Integer getId() {
		return this.id;
	}

	public Set<OrgEmail> getOrgEmails() {
		return orgEmails;
	}

	public Set<PersonEmail> getPersonEmails() {
		return personEmails;
	}

	@Transactional
	public Email merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Email merged = this.entityManager.merge(this);
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
			Email attached = Email.findEmail(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setOrgEmails(Set<OrgEmail> orgEmails) {
		this.orgEmails = orgEmails;
	}

	public void setPersonEmails(Set<PersonEmail> personEmails) {
		this.personEmails = personEmails;
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
		indexEmail(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
