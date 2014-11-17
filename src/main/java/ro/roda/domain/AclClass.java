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
import javax.persistence.TypedQuery;
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

import flexjson.JSON;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "acl_class")
@Audited
public class AclClass {

	public static long countAclClasses() {
		return entityManager().createQuery("SELECT COUNT(o) FROM AclClass o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(AclClass aclClass) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("aclclass_" + aclClass.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new AclClass().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static AclClass findAclClass(Long id) {
		if (id == null)
			return null;
		return entityManager().find(AclClass.class, id);
	}

	public static List<AclClass> findAclClassEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM AclClass o", AclClass.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static List<AclClass> findAllAclClasses() {
		return entityManager().createQuery("SELECT o FROM AclClass o", AclClass.class).getResultList();
	}

	public static Collection<AclClass> fromJsonArrayToAclClasses(String json) {
		return new JSONDeserializer<List<AclClass>>().use(null, ArrayList.class).use("values", AclClass.class)
				.deserialize(json);
	}

	public static AclClass fromJsonToAclClass(String json) {
		return new JSONDeserializer<AclClass>().use(null, AclClass.class).deserialize(json);
	}

	public static void indexAclClass(AclClass aclClass) {
		List<AclClass> aclclasses = new ArrayList<AclClass>();
		aclclasses.add(aclClass);
		indexAclClasses(aclclasses);
	}

	@Async
	public static void indexAclClasses(Collection<AclClass> aclclasses) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (AclClass aclClass : aclclasses) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "aclclass_" + aclClass.getId());
			sid.addField("aclClass.class1_s", aclClass.getClass1());
			sid.addField("aclClass.id_l", aclClass.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("aclclass_solrsummary_t",
					new StringBuilder().append(aclClass.getClass1()).append(" ").append(aclClass.getId()));
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
		String searchString = "AclClass_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new AclClass().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<AclClass> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>AclClass</code> (clasa ACL)
	 * in baza de date; in caz afirmativ il returneaza, altfel, metoda il
	 * introduce in baza de date si apoi il returneaza. Verificarea existentei
	 * in baza de date se realizeaza fie dupa identificator, fie dupa un
	 * criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>class1
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul clasei.
	 * @param class1
	 *            - numele clasei.
	 * @return
	 */
	public static AclClass checkAclClass(Long id, String class1) {
		AclClass object;

		if (id != null) {
			object = findAclClass(id);

			if (object != null) {
				return object;
			}
		}

		List<AclClass> queryResult;

		if (class1 != null) {
			TypedQuery<AclClass> query = entityManager().createQuery(
					"SELECT o FROM AclClass o WHERE lower(o.class1) = lower(:class1)", AclClass.class);
			query.setParameter("class1", class1);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new AclClass();
		object.class1 = class1;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@OneToMany(mappedBy = "objectIdClass")
	private Set<AclObjectIdentity> aclObjectIdentities;

	@Column(name = "class", columnDefinition = "text", unique = true)
	@NotNull
	private String class1;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "bigserial")
	private Long id;

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

	public Set<AclObjectIdentity> getAclObjectIdentities() {
		return aclObjectIdentities;
	}

	public String getClass1() {
		return class1;
	}

	public Long getId() {
		return this.id;
	}

	@Transactional
	public AclClass merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		AclClass merged = this.entityManager.merge(this);
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
			AclClass attached = AclClass.findAclClass(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAclObjectIdentities(Set<AclObjectIdentity> aclObjectIdentities) {
		this.aclObjectIdentities = aclObjectIdentities;
	}

	public void setClass1(String class1) {
		this.class1 = class1;
	}

	public void setId(Long id) {
		this.id = id;
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
		indexAclClass(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((AclClass) obj).id))
				|| (class1 != null && class1.equalsIgnoreCase(((AclClass) obj).class1));
	}

	@JSON(include = false)
	@JsonIgnore
	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
