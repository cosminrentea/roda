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
	 * Verifica existenta unui obiect de tip <code>Org</code> (organizatie) in
	 * baza de date; in caz afirmativ il returneaza, altfel, metoda il introduce
	 * in baza de date si apoi il returneaza. Verificarea existentei in baza de
	 * date se realizeaza fie dupa identificator, fie dupa un criteriu de
	 * unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>fullName
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul organizatiei.
	 * @param shortName
	 *            - denumirea prescurtata a organizatiei (acronim).
	 * @param fullName
	 *            - denumirea completa a organizatiei.
	 * @param orgPrefixId
	 *            - prefixul organizatiei.
	 * @param orgSufixId
	 *            - sufixul organizatiei.
	 * @return
	 */
	public static Org checkOrg(Integer id, String shortName, String fullName,
			OrgPrefix orgPrefixId, OrgSufix orgSufixId) {
		Org object;

		if (id != null) {
			object = findOrg(id);

			if (object != null) {
				return object;
			}
		}

		List<Org> queryResult;

		if (fullName != null) {
			TypedQuery<Org> query = entityManager()
					.createQuery(
							"SELECT o FROM Org o WHERE lower(o.fullName) = lower(:fullName)",
							Org.class);
			query.setParameter("fullName", fullName);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new Org();
		object.shortName = shortName;
		object.fullName = fullName;
		object.orgPrefixId = orgPrefixId;
		object.orgSufixId = orgSufixId;
		object.persist();

		return object;
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

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((Org) obj).id))
				|| (fullName != null && fullName
						.equalsIgnoreCase(((Org) obj).fullName));
	}
}
