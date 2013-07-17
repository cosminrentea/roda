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
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(schema = "public", name = "instance_org_assoc")
@Configurable
public class InstanceOrgAssoc {

	public static long countInstanceOrgAssocs() {
		return entityManager().createQuery(
				"SELECT COUNT(o) FROM InstanceOrgAssoc o", Long.class)
				.getSingleResult();
	}

	@Async
	public static void deleteIndex(InstanceOrgAssoc instanceOrgAssoc) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("instanceorgassoc_"
					+ instanceOrgAssoc.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new InstanceOrgAssoc().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<InstanceOrgAssoc> findAllInstanceOrgAssocs() {
		return entityManager().createQuery("SELECT o FROM InstanceOrgAssoc o",
				InstanceOrgAssoc.class).getResultList();
	}

	public static InstanceOrgAssoc findInstanceOrgAssoc(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(InstanceOrgAssoc.class, id);
	}

	public static List<InstanceOrgAssoc> findInstanceOrgAssocEntries(
			int firstResult, int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM InstanceOrgAssoc o",
						InstanceOrgAssoc.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<InstanceOrgAssoc> fromJsonArrayToInstanceOrgAssocs(
			String json) {
		return new JSONDeserializer<List<InstanceOrgAssoc>>()
				.use(null, ArrayList.class)
				.use("values", InstanceOrgAssoc.class).deserialize(json);
	}

	public static InstanceOrgAssoc fromJsonToInstanceOrgAssoc(String json) {
		return new JSONDeserializer<InstanceOrgAssoc>().use(null,
				InstanceOrgAssoc.class).deserialize(json);
	}

	public static void indexInstanceOrgAssoc(InstanceOrgAssoc instanceOrgAssoc) {
		List<InstanceOrgAssoc> instanceorgassocs = new ArrayList<InstanceOrgAssoc>();
		instanceorgassocs.add(instanceOrgAssoc);
		indexInstanceOrgAssocs(instanceorgassocs);
	}

	@Async
	public static void indexInstanceOrgAssocs(
			Collection<InstanceOrgAssoc> instanceorgassocs) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (InstanceOrgAssoc instanceOrgAssoc : instanceorgassocs) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "instanceorgassoc_" + instanceOrgAssoc.getId());
			sid.addField("instanceOrgAssoc.assocname_s",
					instanceOrgAssoc.getAssocName());
			sid.addField("instanceOrgAssoc.assocdescription_s",
					instanceOrgAssoc.getAssocDescription());
			sid.addField("instanceOrgAssoc.id_i", instanceOrgAssoc.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("instanceorgassoc_solrsummary_t", new StringBuilder()
					.append(instanceOrgAssoc.getAssocName()).append(" ")
					.append(instanceOrgAssoc.getAssocDescription()).append(" ")
					.append(instanceOrgAssoc.getId()));
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
		String searchString = "InstanceOrgAssoc_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new InstanceOrgAssoc().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<InstanceOrgAssoc> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>InstanceOrgAssoc</code>
	 * (asociere intre instanta si organizatie) in baza de date; in caz
	 * afirmativ il returneaza, altfel, metoda il introduce in baza de date si
	 * apoi il returneaza. Verificarea existentei in baza de date se realizeaza
	 * fie dupa identificator, fie dupa un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>assocName
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul asocierii.
	 * @param assocName
	 *            - numele asocierii.
	 * @param assocDescription
	 *            - descrierea asocierii.
	 * @return
	 */
	public static InstanceOrgAssoc checkInstanceOrgAssoc(Integer id,
			String assocName, String assocDescription) {
		InstanceOrgAssoc object;

		if (id != null) {
			object = findInstanceOrgAssoc(id);

			if (object != null) {
				return object;
			}
		}

		List<InstanceOrgAssoc> queryResult;

		if (assocName != null) {
			TypedQuery<InstanceOrgAssoc> query = entityManager()
					.createQuery(
							"SELECT o FROM InstanceOrgAssoc o WHERE lower(o.assocName) = lower(:assocName)",
							InstanceOrgAssoc.class);
			query.setParameter("assocName", assocName);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new InstanceOrgAssoc();
		object.assocName = assocName;
		object.assocDescription = assocDescription;
		object.persist();

		return object;
	}

	@Column(name = "assoc_description", columnDefinition = "text")
	private String assocDescription;

	@Column(name = "assoc_name", columnDefinition = "text")
	@NotNull
	private String assocName;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@OneToMany(mappedBy = "assocTypeId")
	private Set<InstanceOrg> instanceOrgs;

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

	public String getAssocDescription() {
		return assocDescription;
	}

	public String getAssocName() {
		return assocName;
	}

	public Integer getId() {
		return this.id;
	}

	public Set<InstanceOrg> getInstanceOrgs() {
		return instanceOrgs;
	}

	@Transactional
	public InstanceOrgAssoc merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		InstanceOrgAssoc merged = this.entityManager.merge(this);
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
			InstanceOrgAssoc attached = InstanceOrgAssoc
					.findInstanceOrgAssoc(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAssocDescription(String assocDescription) {
		this.assocDescription = assocDescription;
	}

	public void setAssocName(String assocName) {
		this.assocName = assocName;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setInstanceOrgs(Set<InstanceOrg> instanceOrgs) {
		this.instanceOrgs = instanceOrgs;
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
		indexInstanceOrgAssoc(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((InstanceOrgAssoc) obj).id))
				|| (assocName != null && assocName
						.equalsIgnoreCase(((InstanceOrgAssoc) obj).assocName));
	}
}
