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
@Table(schema = "public", name = "org")
public class Org {

	public static long countOrgs() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Org o",
				Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Org org) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("org_" + org.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Org().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Org> findAllOrgs() {
		return entityManager().createQuery("SELECT o FROM Org o", Org.class)
				.getResultList();
	}

	public static Org findOrg(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Org.class, id);
	}

	public static List<Org> findOrgEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Org o", Org.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static Collection<Org> fromJsonArrayToOrgs(String json) {
		return new JSONDeserializer<List<Org>>().use(null, ArrayList.class)
				.use("values", Org.class).deserialize(json);
	}

	public static Org fromJsonToOrg(String json) {
		return new JSONDeserializer<Org>().use(null, Org.class).deserialize(
				json);
	}

	public static void indexOrg(Org org) {
		List<Org> orgs = new ArrayList<Org>();
		orgs.add(org);
		indexOrgs(orgs);
	}

	@Async
	public static void indexOrgs(Collection<Org> orgs) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Org org : orgs) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "org_" + org.getId());
			sid.addField("org.orgprefixid_t", org.getOrgPrefixId());
			sid.addField("org.orgsufixid_t", org.getOrgSufixId());
			sid.addField("org.shortname_s", org.getShortName());
			sid.addField("org.fullname_s", org.getFullName());
			sid.addField("org.id_i", org.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"org_solrsummary_t",
					new StringBuilder().append(org.getOrgPrefixId())
							.append(" ").append(org.getOrgSufixId())
							.append(" ").append(org.getShortName()).append(" ")
							.append(org.getFullName()).append(" ")
							.append(org.getId()));
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
		String searchString = "Org_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Org().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Org> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unei organizatii in baza de date (pe baza parametrilor
	 * furnizati); daca exista, returneaza obiectul respectiv, altfel il
	 * introduce si returneaza obiectul corespunzator.
	 * 
	 * @param orgId
	 * @param shortName
	 * @param fullName
	 * @param orgPrefixId
	 * @param orgSufixId
	 * @return
	 */
	public static Org checkOrg(Integer orgId, String shortName,
			String fullName, Integer orgPrefixId, Integer orgSufixId) {
		// TODO
		return null;
	}

	/**
	 * Verifica existenta unei organizatii in baza de date; daca nu exista,
	 * adaugarea va avea loc doar pe baza campurilor obligatorii.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <p>
	 * <ul>
	 * <li>fullname
	 * <ul>
	 * <p>
	 * 
	 * @param orgId
	 * @param shortName
	 * @param fullName
	 * @return
	 */
	public static Org checkOrg(Integer orgId, String shortName, String fullName) {
		// TODO
		return null;
	}

	/**
	 * Verifica existenta unei organizatii; in cazul inexistentei, aceasta poate
	 * fi introdusa furnizand informatii complete (inclusiv listele de adrese,
	 * email-uri si adrese internet corespunzatoare).
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <p>
	 * <ul>
	 * <li>orgId
	 * <li>fullname
	 * <ul>
	 * <p>
	 * 
	 * 
	 * 
	 * @param orgId
	 *            - cheia primara a organizatiei din tabelul de organizatii
	 * @param fullname
	 *            - denumirea completa a organizatiei
	 * @param shortname
	 *            - denumirea abreviata a organizatiei
	 * @param orgPrefix
	 *            - prefixul organizatiei curente. Acesta va fi cautat in
	 *            tabelul de prefixe pentru organizatii; daca nu este gasit, va
	 *            fi introdus in acest tabel.
	 * @param orgSufix
	 *            - sufixul organizatiei curente. Acesta va fi cautat in tabelul
	 *            de sufixe pentru organizatii; daca nu este gasit, va fi
	 *            introdus in acest tabel.
	 * @param orgAddresses
	 *            - lista ce contine adresele postale ale organizatiei;
	 *            existenta fiecarei adrese va fi verificata in baza de date,
	 *            iar in cazul inexistentei va fi introdusa. Totodata, va fi
	 *            inserata si asocierea dintre adresa postala gasita sau
	 *            inserata si organizatia curenta.
	 * @param orgEmails
	 *            - lista ce contine adresele de email ale organizatiei;
	 *            existenta fiecarei adrese de email va fi verificata in baza de
	 *            date, iar in cazul inexistentei va fi introdusa. Totodata, va
	 *            fi inserata si asocierea dintre adresa de email gasita sau
	 *            inserata si organizatia curenta.
	 * @param orgInternets
	 *            - lista ce contine adresele de internet ale organizatiei;
	 *            existenta fiecarei adrese de internet va fi verificata in baza
	 *            de date, iar in cazul inexistentei va fi introdusa. Totodata,
	 *            va fi inserata si asocierea dintre adresa de internet gasita
	 *            sau inserata si organizatia curenta.
	 * @param orgPhones
	 *            - lista ce contine numerele de telefon ale organizatiei;
	 *            existenta fiecarui numar de telefon va fi verificata in baza
	 *            de date, iar in cazul inexistentei numarul respectiv va fi
	 *            introdus. Totodata, va fi inserata si asocierea dintre numarul
	 *            de telefon gasit sau inserat si organizatia curenta.
	 * @param persons
	 *            - lista de persone aflate in relatie cu organizatia curenta;
	 *            existenta fiecarei persoane este verificata in baza de date,
	 *            iar in cazul inexistentei persoanele sunt inserate in tabelul
	 *            destinat lor. O persoana are un role in cadrul organizatiei;
	 *            de asemenea, acesta este cautat in baza de date si inserat
	 *            daca nu exista. Totodata, in baza de date va fi retinuta
	 *            asocierea dintre persoana, role si organizatia curenta.
	 * 
	 * @return
	 */
	public static Org checkOrg(Integer orgId, String shortname,
			String fullname, String orgPrefix, String orgSufix,
			List<Address> orgAddresses, List<Email> orgEmails,
			List<Internet> orgInternets, List<Phone> orgPhones,
			List<Person> persons) {
		// TODO
		return null;
	}

	@Column(name = "full_name", columnDefinition = "text")
	@NotNull
	private String fullName;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@OneToMany(mappedBy = "orgId")
	private Set<InstanceOrg> instanceOrgs;

	@OneToMany(mappedBy = "orgId")
	private Set<OrgAddress> orgAddresses;

	@OneToMany(mappedBy = "orgId")
	private Set<OrgEmail> orgEmails;

	@OneToMany(mappedBy = "orgId")
	private Set<OrgInternet> orgInternets;

	@OneToMany(mappedBy = "orgId")
	private Set<OrgPhone> orgPhones;

	@ManyToOne
	@JoinColumn(name = "org_prefix_id", columnDefinition = "integer", referencedColumnName = "id")
	private OrgPrefix orgPrefixId;

	@OneToMany(mappedBy = "org2Id")
	private Set<OrgRelations> orgRelationss;

	@OneToMany(mappedBy = "org1Id")
	private Set<OrgRelations> orgRelationss1;

	@ManyToOne
	@JoinColumn(name = "org_sufix_id", columnDefinition = "integer", referencedColumnName = "id")
	private OrgSufix orgSufixId;

	@OneToMany(mappedBy = "orgId")
	private Set<PersonOrg> personOrgs;

	@Column(name = "short_name", columnDefinition = "varchar", length = 100)
	@NotNull
	private String shortName;

	@OneToMany(mappedBy = "orgId")
	private Set<StudyOrg> studyOrgs;

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

	public String getFullName() {
		return fullName;
	}

	public Integer getId() {
		return this.id;
	}

	public Set<InstanceOrg> getInstanceOrgs() {
		return instanceOrgs;
	}

	public Set<OrgAddress> getOrgAddresses() {
		return orgAddresses;
	}

	public Set<OrgEmail> getOrgEmails() {
		return orgEmails;
	}

	public Set<OrgInternet> getOrgInternets() {
		return orgInternets;
	}

	public Set<OrgPhone> getOrgPhones() {
		return orgPhones;
	}

	public OrgPrefix getOrgPrefixId() {
		return orgPrefixId;
	}

	public Set<OrgRelations> getOrgRelationss() {
		return orgRelationss;
	}

	public Set<OrgRelations> getOrgRelationss1() {
		return orgRelationss1;
	}

	public OrgSufix getOrgSufixId() {
		return orgSufixId;
	}

	public Set<PersonOrg> getPersonOrgs() {
		return personOrgs;
	}

	public String getShortName() {
		return shortName;
	}

	public Set<StudyOrg> getStudyOrgs() {
		return studyOrgs;
	}

	@Transactional
	public Org merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Org merged = this.entityManager.merge(this);
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
			Org attached = Org.findOrg(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setInstanceOrgs(Set<InstanceOrg> instanceOrgs) {
		this.instanceOrgs = instanceOrgs;
	}

	public void setOrgAddresses(Set<OrgAddress> orgAddresses) {
		this.orgAddresses = orgAddresses;
	}

	public void setOrgEmails(Set<OrgEmail> orgEmails) {
		this.orgEmails = orgEmails;
	}

	public void setOrgInternets(Set<OrgInternet> orgInternets) {
		this.orgInternets = orgInternets;
	}

	public void setOrgPhones(Set<OrgPhone> orgPhones) {
		this.orgPhones = orgPhones;
	}

	public void setOrgPrefixId(OrgPrefix orgPrefixId) {
		this.orgPrefixId = orgPrefixId;
	}

	public void setOrgRelationss(Set<OrgRelations> orgRelationss) {
		this.orgRelationss = orgRelationss;
	}

	public void setOrgRelationss1(Set<OrgRelations> orgRelationss1) {
		this.orgRelationss1 = orgRelationss1;
	}

	public void setOrgSufixId(OrgSufix orgSufixId) {
		this.orgSufixId = orgSufixId;
	}

	public void setPersonOrgs(Set<PersonOrg> personOrgs) {
		this.personOrgs = personOrgs;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public void setStudyOrgs(Set<StudyOrg> studyOrgs) {
		this.studyOrgs = studyOrgs;
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
		indexOrg(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
