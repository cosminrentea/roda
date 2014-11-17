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

import com.fasterxml.jackson.annotation.JsonIgnore;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "person_org")
@Audited
public class PersonOrg {

	public static long countPersonOrgs() {
		return entityManager().createQuery("SELECT COUNT(o) FROM PersonOrg o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(PersonOrg personOrg) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("personorg_" + personOrg.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new PersonOrg().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<PersonOrg> findAllPersonOrgs() {
		return entityManager().createQuery("SELECT o FROM PersonOrg o", PersonOrg.class).getResultList();
	}

	public static PersonOrg findPersonOrg(PersonOrgPK id) {
		if (id == null)
			return null;
		return entityManager().find(PersonOrg.class, id);
	}

	public static List<PersonOrg> findPersonOrgEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM PersonOrg o", PersonOrg.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<PersonOrg> fromJsonArrayToPersonOrgs(String json) {
		return new JSONDeserializer<List<PersonOrg>>().use(null, ArrayList.class).use("values", PersonOrg.class)
				.deserialize(json);
	}

	public static PersonOrg fromJsonToPersonOrg(String json) {
		return new JSONDeserializer<PersonOrg>().use(null, PersonOrg.class).deserialize(json);
	}

	public static void indexPersonOrg(PersonOrg personOrg) {
		List<PersonOrg> personorgs = new ArrayList<PersonOrg>();
		personorgs.add(personOrg);
		indexPersonOrgs(personorgs);
	}

	@Async
	public static void indexPersonOrgs(Collection<PersonOrg> personorgs) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (PersonOrg personOrg : personorgs) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "personorg_" + personOrg.getId());
			sid.addField("personOrg.orgid_t", personOrg.getOrgId());
			sid.addField("personOrg.personid_t", personOrg.getPersonId());
			sid.addField("personOrg.roleid_t", personOrg.getRoleId());
			sid.addField("personOrg.datestart_dt", personOrg.getDateStart());
			sid.addField("personOrg.dateend_dt", personOrg.getDateEnd());
			sid.addField("personOrg.id_t", personOrg.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("personorg_solrsummary_t",
					new StringBuilder().append(personOrg.getOrgId()).append(" ").append(personOrg.getPersonId())
							.append(" ").append(personOrg.getRoleId()).append(" ").append(personOrg.getDateStart())
							.append(" ").append(personOrg.getDateEnd()).append(" ").append(personOrg.getId()));
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
		String searchString = "PersonOrg_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new PersonOrg().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<PersonOrg> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@Column(name = "date_end", columnDefinition = "date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date dateEnd;

	@Column(name = "date_start", columnDefinition = "date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date dateStart;

	@EmbeddedId
	private PersonOrgPK id;

	@ManyToOne
	@JoinColumn(name = "org_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Org orgId;

	@ManyToOne
	@JoinColumn(name = "person_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Person personId;

	@ManyToOne
	@JoinColumn(name = "role_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private PersonRole roleId;

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

	public Date getDateEnd() {
		return dateEnd;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public PersonOrgPK getId() {
		return this.id;
	}

	public Org getOrgId() {
		return orgId;
	}

	public Person getPersonId() {
		return personId;
	}

	public PersonRole getRoleId() {
		return roleId;
	}

	@Transactional
	public PersonOrg merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		PersonOrg merged = this.entityManager.merge(this);
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
			PersonOrg attached = PersonOrg.findPersonOrg(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public void setId(PersonOrgPK id) {
		this.id = id;
	}

	public void setOrgId(Org orgId) {
		this.orgId = orgId;
	}

	public void setPersonId(Person personId) {
		this.personId = personId;
	}

	public void setRoleId(PersonRole roleId) {
		this.roleId = roleId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@JsonIgnore public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexPersonOrg(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
