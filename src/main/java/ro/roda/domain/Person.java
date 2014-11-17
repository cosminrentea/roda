package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
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

@Configurable
@Entity
@Table(schema = "public", name = "person")
@Audited
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
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>Person</code> (persoana) in
	 * baza de date; in caz afirmativ il returneaza, altfel, metoda il introduce
	 * in baza de date si apoi il returneaza. Verificarea existentei in baza de
	 * date se realizeaza fie dupa identificator, fie dupa un criteriu de
	 * unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul persoanei.
	 * @param fname
	 *            - primul nume al persoanei.
	 * @param mname
	 *            - al doilea nume al persoanei.
	 * @param lname
	 *            - ultimul nume al persoanei.
	 * @param prefixId
	 *            - prefixul persoanei.
	 * @param sufixId
	 *            - sufixul persoanei.
	 * @return
	 */
	public static Person checkPerson(Integer id, String fname, String mname, String lname, Prefix prefixId,
			Suffix suffixId) {
		Person object;

		if (id != null) {
			object = findPerson(id);

			if (object != null) {
				return object;
			}
		}

		object = new Person();
		object.fname = fname;
		object.mname = mname;
		object.lname = lname;
		object.prefixId = prefixId;
		object.suffixId = suffixId;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@Column(name = "fname", columnDefinition = "varchar", length = 100)
	@NotNull
	private String fname;

	@OneToMany(mappedBy = "operatorId")
	private Set<Form> forms;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "serial")
	private Integer id;

	@OneToMany(mappedBy = "personId")
	private Set<InstancePerson> instancepeople;

	@Column(name = "lname", columnDefinition = "varchar", length = 100)
	@NotNull
	private String lname;

	@Column(name = "mname", columnDefinition = "varchar", length = 100)
	private String mname;

	@Column(name = "address_line1", columnDefinition = "varchar", length = 200)
	private String addressLine1;

	@Column(name = "address_line2", columnDefinition = "varchar", length = 200)
	private String addressLine2;

	@ManyToOne
	@JoinColumn(name = "org_city", columnDefinition = "integer", referencedColumnName = "id", nullable = true)
	private City city;

	@OneToMany(mappedBy = "personId")
	private Set<PersonLinks> personLinkss;

	@OneToMany(mappedBy = "personId")
	private Set<PersonOrg> personOrgs;

	@OneToMany(mappedBy = "personId")
	private Set<PersonPhone> personPhones;

	@ManyToMany
	@JoinTable(name = "email_person", joinColumns = { @JoinColumn(name = "person_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "email_id", nullable = false) })
	private Set<Email> emails;

	@ManyToMany
	@JoinTable(name = "internet_person", joinColumns = { @JoinColumn(name = "person_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "internet_id", nullable = false) })
	private Set<Internet> internets;

	@ManyToOne
	@JoinColumn(name = "prefix_id", columnDefinition = "integer", referencedColumnName = "id")
	private Prefix prefixId;

	@OneToMany(mappedBy = "personId", fetch = FetchType.LAZY)
	private Set<StudyPerson> studypeople;

	@ManyToOne
	@JoinColumn(name = "suffix_id", columnDefinition = "integer", referencedColumnName = "id")
	private Suffix suffixId;

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

	public Set<Email> getEmails() {
		return emails;
	}

	public Set<Internet> getInternets() {
		return internets;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setCity(City city) {
		this.city = city;
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

	public void setEmails(Set<Email> emails) {
		this.emails = emails;
	}

	public void setInternets(Set<Internet> internets) {
		this.internets = internets;
	}

	public City getCity() {
		return city;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
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
		indexPerson(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return id != null && id.equals(((Person) obj).id);
	}

	@JsonIgnore public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
