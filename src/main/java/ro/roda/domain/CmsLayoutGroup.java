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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(schema = "public", name = "cms_layout_group")
@Configurable
public class CmsLayoutGroup {

	public static long countCmsLayoutGroups() {
		return entityManager().createQuery("SELECT COUNT(o) FROM CmsLayoutGroup o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(CmsLayoutGroup cmsLayoutGroup) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("cmslayoutgroup_" + cmsLayoutGroup.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new CmsLayoutGroup().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<CmsLayoutGroup> findAllCmsLayoutGroups() {
		return entityManager().createQuery("SELECT o FROM CmsLayoutGroup o", CmsLayoutGroup.class).getResultList();
	}

	public static CmsLayoutGroup findCmsLayoutGroup(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(CmsLayoutGroup.class, id);
	}

	public static List<CmsLayoutGroup> findCmsLayoutGroupEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM CmsLayoutGroup o", CmsLayoutGroup.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<CmsLayoutGroup> fromJsonArrayToCmsLayoutGroups(String json) {
		return new JSONDeserializer<List<CmsLayoutGroup>>().use(null, ArrayList.class)
				.use("values", CmsLayoutGroup.class).deserialize(json);
	}

	public static CmsLayoutGroup fromJsonToCmsLayoutGroup(String json) {
		return new JSONDeserializer<CmsLayoutGroup>().use(null, CmsLayoutGroup.class).deserialize(json);
	}

	public static void indexCmsLayoutGroup(CmsLayoutGroup cmsLayoutGroup) {
		List<CmsLayoutGroup> cmslayoutgroups = new ArrayList<CmsLayoutGroup>();
		cmslayoutgroups.add(cmsLayoutGroup);
		indexCmsLayoutGroups(cmslayoutgroups);
	}

	@Async
	public static void indexCmsLayoutGroups(Collection<CmsLayoutGroup> cmslayoutgroups) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (CmsLayoutGroup cmsLayoutGroup : cmslayoutgroups) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "cmslayoutgroup_" + cmsLayoutGroup.getId());
			sid.addField("cmsLayoutGroup.parentid_t", cmsLayoutGroup.getParentId());
			sid.addField("cmsLayoutGroup.name_s", cmsLayoutGroup.getName());
			sid.addField("cmsLayoutGroup.description_s", cmsLayoutGroup.getDescription());
			sid.addField("cmsLayoutGroup.id_i", cmsLayoutGroup.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("cmslayoutgroup_solrsummary_t", new StringBuilder().append(cmsLayoutGroup.getParentId())
					.append(" ").append(cmsLayoutGroup.getName()).append(" ").append(cmsLayoutGroup.getDescription())
					.append(" ").append(cmsLayoutGroup.getId()));
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
		String searchString = "CmsLayoutGroup_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new CmsLayoutGroup().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<CmsLayoutGroup> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>CmsLayoutGroup</code> (grup
	 * de aranjamente CMS) in baza de date; in caz afirmativ il returneaza,
	 * altfel, metoda il introduce in baza de date si apoi il returneaza.
	 * Verificarea existentei in baza de date se realizeaza fie dupa
	 * identificator, fie dupa un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>name + parentId
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul grupului.
	 * @param name
	 *            - numele grupului.
	 * @param parentId
	 *            - grupul parinte al grupului.
	 * @param description
	 *            - descrierea grupului.
	 * @return
	 */
	public static CmsLayoutGroup checkCmsLayoutGroup(Integer id, String name, CmsLayoutGroup parentId,
			String description) {
		CmsLayoutGroup object;

		if (id != null) {
			object = findCmsLayoutGroup(id);

			if (object != null) {
				return object;
			}
		}

		List<CmsLayoutGroup> queryResult;

		if (name != null && parentId != null) {
			TypedQuery<CmsLayoutGroup> query = entityManager()
					.createQuery(
							"SELECT o FROM CmsLayoutGroup o WHERE lower(o.name) = lower(:name) AND "
									+ "o.parentId = :parentId", CmsLayoutGroup.class);
			query.setParameter("name", name);
			query.setParameter("parentId", parentId);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new CmsLayoutGroup();
		object.name = name;
		object.parentId = parentId;
		object.description = description;
		object.persist();

		return object;
	}

	@OneToMany(mappedBy = "parentId")
	private Set<CmsLayoutGroup> cmsLayoutGroups;

	@OneToMany(mappedBy = "cmsLayoutGroupId")
	private Set<CmsLayout> cmsLayouts;

	@Column(name = "description", columnDefinition = "text")
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "varchar", length = 200)
	@NotNull
	private String name;

	@ManyToOne
	@JoinColumn(name = "parent_id", columnDefinition = "integer", referencedColumnName = "id", insertable = true, updatable = true)
	private CmsLayoutGroup parentId;

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

	public Set<CmsLayoutGroup> getCmsLayoutGroups() {
		return cmsLayoutGroups;
	}

	public Set<CmsLayout> getCmsLayouts() {
		return cmsLayouts;
	}

	public String getDescription() {
		return description;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public CmsLayoutGroup getParentId() {
		return parentId;
	}

	@Transactional
	public CmsLayoutGroup merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		CmsLayoutGroup merged = this.entityManager.merge(this);
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
			CmsLayoutGroup attached = CmsLayoutGroup.findCmsLayoutGroup(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setCmsLayoutGroups(Set<CmsLayoutGroup> cmsLayoutGroups) {
		this.cmsLayoutGroups = cmsLayoutGroups;
	}

	public void setCmsLayouts(Set<CmsLayout> cmsLayouts) {
		this.cmsLayouts = cmsLayouts;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentId(CmsLayoutGroup parentId) {
		this.parentId = parentId;
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
		indexCmsLayoutGroup(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((CmsLayoutGroup) obj).id))
				|| ((name != null && name.equalsIgnoreCase(((CmsLayoutGroup) obj).name)) && (parentId != null && parentId
						.equals(((CmsLayoutGroup) obj).parentId)));
	}
}
