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
@Table(schema = "public", name = "person")

public class Person {

	public static long countPeople() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Person o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Person person) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("person_" + person.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Person().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Person> findAllPeople() {
		return entityManager().createQuery("SELECT o FROM Person o", Person.class).getResultList();
	}

	public static Person findPerson(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Person.class, id);
	}

	public static List<Person> findPersonEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Person o", Person.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<Person> fromJsonArrayToPeople(String json) {
		return new JSONDeserializer<List<Person>>().use(null, ArrayList.class).use("values", Person.class)
				.deserialize(json);
	}

	public static Person fromJsonToPerson(String json) {
		return new JSONDeserializer<Person>().use(null, Person.class).deserialize(json);
	}

	@Async
	public static void indexPeople(Collection<Person> people) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Person person : people) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "person_" + person.getId());
			sid.addField("person.id_i", person.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("person_solrsummary_t", new StringBuilder().append(person.getId()));
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

	public static void indexPerson(Person person) {
		List<Person> people = new ArrayList<Person>();
		people.add(person);
		indexPeople(people);
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
		String searchString = "Person_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Person().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Person> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "fname", columnDefinition = "varchar", length = 100)
	@NotNull
	private String fname;

	@OneToMany(mappedBy = "operatorId")
	private Set<Form> forms;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@OneToMany(mappedBy = "personId")
	private Set<InstancePerson> instancepeople;

	@Column(name = "lname", columnDefinition = "varchar", length = 100)
	@NotNull
	private String lname;

	@Column(name = "mname", columnDefinition = "varchar", length = 100)
	private String mname;

	@OneToMany(mappedBy = "personId")
	private Set<PersonAddress> personAddresses;

	@OneToMany(mappedBy = "personId")
	private Set<PersonEmail> personEmails;

	@OneToMany(mappedBy = "personId")
	private Set<PersonInternet> personInternets;

	@OneToMany(mappedBy = "personId")
	private Set<PersonLinks> personLinkss;

	@OneToMany(mappedBy = "personId")
	private Set<PersonOrg> personOrgs;

	@OneToMany(mappedBy = "personId")
	private Set<PersonPhone> personPhones;

	@ManyToOne
	@JoinColumn(name = "prefix_id", columnDefinition = "integer", referencedColumnName = "id")
	private Prefix prefixId;

	@OneToMany(mappedBy = "personId")
	private Set<StudyPerson> studypeople;

	@ManyToOne
	@JoinColumn(name = "suffix_id", columnDefinition = "integer", referencedColumnName = "id")
	private Suffix suffixId;

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

	public boolean equals(Object o) {
		boolean r = false;
		if (o instanceof Person) {
			Person p = (Person) o;
			r = this.getFname().equalsIgnoreCase(p.getFname()) && this.getLname().equalsIgnoreCase(p.getLname());
		}
		return r;
	}

	@Transactional
	public void flush() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.flush();
	}

	public String getFname() {
		return fname;
	}

	public Set<Form> getForms() {
		return forms;
	}

	public Integer getId() {
		return this.id;
	}

	public Set<InstancePerson> getInstancepeople() {
		return instancepeople;
	}

	public String getLname() {
		return lname;
	}

	public String getMname() {
		return mname;
	}

	public Set<PersonAddress> getPersonAddresses() {
		return personAddresses;
	}

	public Set<PersonEmail> getPersonEmails() {
		return personEmails;
	}

	public Set<PersonInternet> getPersonInternets() {
		return personInternets;
	}

	public Set<PersonLinks> getPersonLinkss() {
		return personLinkss;
	}

	public Set<PersonOrg> getPersonOrgs() {
		return personOrgs;
	}

	public Set<PersonPhone> getPersonPhones() {
		return personPhones;
	}

	public Prefix getPrefixId() {
		return prefixId;
	}

	public Set<StudyPerson> getStudypeople() {
		return studypeople;
	}

	public Suffix getSuffixId() {
		return suffixId;
	}

	@Transactional
	public Person merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Person merged = this.entityManager.merge(this);
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
			Person attached = Person.findPerson(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public void setForms(Set<Form> forms) {
		this.forms = forms;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setInstancepeople(Set<InstancePerson> instancepeople) {
		this.instancepeople = instancepeople;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public void setPersonAddresses(Set<PersonAddress> personAddresses) {
		this.personAddresses = personAddresses;
	}

	public void setPersonEmails(Set<PersonEmail> personEmails) {
		this.personEmails = personEmails;
	}

	public void setPersonInternets(Set<PersonInternet> personInternets) {
		this.personInternets = personInternets;
	}

	public void setPersonLinkss(Set<PersonLinks> personLinkss) {
		this.personLinkss = personLinkss;
	}

	public void setPersonOrgs(Set<PersonOrg> personOrgs) {
		this.personOrgs = personOrgs;
	}

	public void setPersonPhones(Set<PersonPhone> personPhones) {
		this.personPhones = personPhones;
	}

	public void setPrefixId(Prefix prefixId) {
		this.prefixId = prefixId;
	}

	public void setStudypeople(Set<StudyPerson> studypeople) {
		this.studypeople = studypeople;
	}

	public void setSuffixId(Suffix suffixId) {
		this.suffixId = suffixId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexPerson(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
