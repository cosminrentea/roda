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
@Table(schema = "public", name = "org_relation_type")
@Configurable
public class OrgRelationType {

	public static long countOrgRelationTypes() {
		return entityManager().createQuery(
				"SELECT COUNT(o) FROM OrgRelationType o", Long.class)
				.getSingleResult();
	}

	@Async
	public static void deleteIndex(OrgRelationType orgRelationType) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("orgrelationtype_" + orgRelationType.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new OrgRelationType().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<OrgRelationType> findAllOrgRelationTypes() {
		return entityManager().createQuery("SELECT o FROM OrgRelationType o",
				OrgRelationType.class).getResultList();
	}

	public static OrgRelationType findOrgRelationType(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(OrgRelationType.class, id);
	}

	public static List<OrgRelationType> findOrgRelationTypeEntries(
			int firstResult, int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM OrgRelationType o",
						OrgRelationType.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<OrgRelationType> fromJsonArrayToOrgRelationTypes(
			String json) {
		return new JSONDeserializer<List<OrgRelationType>>()
				.use(null, ArrayList.class)
				.use("values", OrgRelationType.class).deserialize(json);
	}

	public static OrgRelationType fromJsonToOrgRelationType(String json) {
		return new JSONDeserializer<OrgRelationType>().use(null,
				OrgRelationType.class).deserialize(json);
	}

	public static void indexOrgRelationType(OrgRelationType orgRelationType) {
		List<OrgRelationType> orgrelationtypes = new ArrayList<OrgRelationType>();
		orgrelationtypes.add(orgRelationType);
		indexOrgRelationTypes(orgrelationtypes);
	}

	@Async
	public static void indexOrgRelationTypes(
			Collection<OrgRelationType> orgrelationtypes) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (OrgRelationType orgRelationType : orgrelationtypes) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "orgrelationtype_" + orgRelationType.getId());
			sid.addField("orgRelationType.name_s", orgRelationType.getName());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("orgrelationtype_solrsummary_t",
					new StringBuilder().append(orgRelationType.getName()));
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
		String searchString = "OrgRelationType_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new OrgRelationType().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<OrgRelationType> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>OrgRelationType</code> (tip
	 * de relatie intre organizatii) in baza de date; in caz afirmativ il
	 * returneaza, altfel, metoda il introduce in baza de date si apoi il
	 * returneaza. Verificarea existentei in baza de date se realizeaza fie dupa
	 * identificator, fie dupa un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>name
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul tipului.
	 * @param name
	 *            - numele tipului.
	 * @return
	 */
	public static OrgRelationType checkOrgRelationType(Integer id, String name) {
		OrgRelationType object;

		if (id != null) {
			object = findOrgRelationType(id);

			if (object != null) {
				return object;
			}
		}

		List<OrgRelationType> queryResult;

		if (name != null) {
			TypedQuery<OrgRelationType> query = entityManager()
					.createQuery(
							"SELECT o FROM OrgRelationType o WHERE lower(o.name) = lower(:name)",
							OrgRelationType.class);
			query.setParameter("name", name);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new OrgRelationType();
		object.name = name;
		object.persist();

		return object;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "varchar", length = 100)
	@NotNull
	private String name;

	@OneToMany(mappedBy = "orgRelationTypeId")
	private Set<OrgRelations> orgRelationss;

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

	public Set<OrgRelations> getOrgRelationss() {
		return orgRelationss;
	}

	@Transactional
	public OrgRelationType merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		OrgRelationType merged = this.entityManager.merge(this);
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
			OrgRelationType attached = OrgRelationType
					.findOrgRelationType(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrgRelationss(Set<OrgRelations> orgRelationss) {
		this.orgRelationss = orgRelationss;
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
		indexOrgRelationType(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((OrgRelationType) obj).id))
				|| (name != null && name
						.equalsIgnoreCase(((OrgRelationType) obj).name));
	}
}
