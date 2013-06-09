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

@Entity
@Table(schema = "public", name = "person_role")
@Configurable

public class PersonRole {

	public static long countPersonRoles() {
		return entityManager().createQuery("SELECT COUNT(o) FROM PersonRole o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(PersonRole personRole) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("personrole_" + personRole.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new PersonRole().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<PersonRole> findAllPersonRoles() {
		return entityManager().createQuery("SELECT o FROM PersonRole o", PersonRole.class).getResultList();
	}

	public static PersonRole findPersonRole(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(PersonRole.class, id);
	}

	public static List<PersonRole> findPersonRoleEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM PersonRole o", PersonRole.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<PersonRole> fromJsonArrayToPersonRoles(String json) {
		return new JSONDeserializer<List<PersonRole>>().use(null, ArrayList.class).use("values", PersonRole.class)
				.deserialize(json);
	}

	public static PersonRole fromJsonToPersonRole(String json) {
		return new JSONDeserializer<PersonRole>().use(null, PersonRole.class).deserialize(json);
	}

	public static void indexPersonRole(PersonRole personRole) {
		List<PersonRole> personroles = new ArrayList<PersonRole>();
		personroles.add(personRole);
		indexPersonRoles(personroles);
	}

	@Async
	public static void indexPersonRoles(Collection<PersonRole> personroles) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (PersonRole personRole : personroles) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "personrole_" + personRole.getId());
			sid.addField("personRole.name_s", personRole.getName());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("personrole_solrsummary_t", new StringBuilder().append(personRole.getName()));
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
		String searchString = "PersonRole_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new PersonRole().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<PersonRole> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "text")
	@NotNull
	private String name;

	@OneToMany(mappedBy = "roleId")
	private Set<PersonOrg> personOrgs;

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

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public Set<PersonOrg> getPersonOrgs() {
		return personOrgs;
	}

	@Transactional
	public PersonRole merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		PersonRole merged = this.entityManager.merge(this);
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
			PersonRole attached = PersonRole.findPersonRole(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPersonOrgs(Set<PersonOrg> personOrgs) {
		this.personOrgs = personOrgs;
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
		indexPersonRole(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
