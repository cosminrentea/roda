package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
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
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(schema = "public", name = "person_role")
@Configurable
public class PersonRole {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToMany(mappedBy = "roleId")
	private Set<PersonOrg> personOrgs;

	@Column(name = "name", columnDefinition = "text")
	@NotNull
	private String name;

	public Set<PersonOrg> getPersonOrgs() {
		return personOrgs;
	}

	public void setPersonOrgs(Set<PersonOrg> personOrgs) {
		this.personOrgs = personOrgs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new PersonRole().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countPersonRoles() {
		return entityManager().createQuery("SELECT COUNT(o) FROM PersonRole o", Long.class).getSingleResult();
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
	public PersonRole merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		PersonRole merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "PersonRole_solrsummary_t:" + queryString;
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

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexPersonRole(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new PersonRole().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static PersonRole fromJsonToPersonRole(String json) {
		return new JSONDeserializer<PersonRole>().use(null, PersonRole.class).deserialize(json);
	}

	public static String toJsonArray(Collection<PersonRole> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<PersonRole> fromJsonArrayToPersonRoles(String json) {
		return new JSONDeserializer<List<PersonRole>>().use(null, ArrayList.class).use("values", PersonRole.class)
				.deserialize(json);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
