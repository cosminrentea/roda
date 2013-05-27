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
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "person_internet")
@Audited
public class PersonInternet {

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static PersonInternet fromJsonToPersonInternet(String json) {
		return new JSONDeserializer<PersonInternet>().use(null, PersonInternet.class).deserialize(json);
	}

	public static String toJsonArray(Collection<PersonInternet> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<PersonInternet> fromJsonArrayToPersonInternets(String json) {
		return new JSONDeserializer<List<PersonInternet>>().use(null, ArrayList.class)
				.use("values", PersonInternet.class).deserialize(json);
	}

	@ManyToOne
	@JoinColumn(name = "internet_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Internet internetId;

	@ManyToOne
	@JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Person personId;

	@Column(name = "main", columnDefinition = "bool")
	@NotNull
	private boolean main;

	public Internet getInternetId() {
		return internetId;
	}

	public void setInternetId(Internet internetId) {
		this.internetId = internetId;
	}

	public Person getPersonId() {
		return personId;
	}

	public void setPersonId(Person personId) {
		this.personId = personId;
	}

	public boolean isMain() {
		return main;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new PersonInternet().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countPersonInternets() {
		return entityManager().createQuery("SELECT COUNT(o) FROM PersonInternet o", Long.class).getSingleResult();
	}

	public static List<PersonInternet> findAllPersonInternets() {
		return entityManager().createQuery("SELECT o FROM PersonInternet o", PersonInternet.class).getResultList();
	}

	public static PersonInternet findPersonInternet(PersonInternetPK id) {
		if (id == null)
			return null;
		return entityManager().find(PersonInternet.class, id);
	}

	public static List<PersonInternet> findPersonInternetEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM PersonInternet o", PersonInternet.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
			PersonInternet attached = PersonInternet.findPersonInternet(this.id);
			this.entityManager.remove(attached);
		}
	}

	@Transactional
	public void flush() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.flush();
	}

	@Transactional
	public void clear() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.clear();
	}

	@Transactional
	public PersonInternet merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		PersonInternet merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	@EmbeddedId
	private PersonInternetPK id;

	public PersonInternetPK getId() {
		return this.id;
	}

	public void setId(PersonInternetPK id) {
		this.id = id;
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "PersonInternet_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static QueryResponse search(SolrQuery query) {
		try {
			return solrServer().query(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new QueryResponse();
	}

	public static void indexPersonInternet(PersonInternet personInternet) {
		List<PersonInternet> personinternets = new ArrayList<PersonInternet>();
		personinternets.add(personInternet);
		indexPersonInternets(personinternets);
	}

	@Async
	public static void indexPersonInternets(Collection<PersonInternet> personinternets) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (PersonInternet personInternet : personinternets) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "personinternet_" + personInternet.getId());
			sid.addField("personInternet.id_t", personInternet.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("personinternet_solrsummary_t", new StringBuilder().append(personInternet.getId()));
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

	@Async
	public static void deleteIndex(PersonInternet personInternet) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("personinternet_" + personInternet.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexPersonInternet(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new PersonInternet().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
