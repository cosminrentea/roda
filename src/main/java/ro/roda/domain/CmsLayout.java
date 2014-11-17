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
@Table(schema = "public", name = "cms_layout")
@Audited
public class CmsLayout {

	public static long countCmsLayouts() {
		return entityManager().createQuery("SELECT COUNT(o) FROM CmsLayout o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(CmsLayout cmsLayout) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("cmslayout_" + cmsLayout.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new CmsLayout().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<CmsLayout> findAllCmsLayouts() {
		return entityManager().createQuery("SELECT o FROM CmsLayout o", CmsLayout.class).getResultList();
	}

	public static CmsLayout findCmsLayout(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(CmsLayout.class, id);
	}

	public static List<CmsLayout> findCmsLayoutEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM CmsLayout o", CmsLayout.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static CmsLayout findCmsLayout(String name) {
		if (name == null)
			return null;

		String layoutByNameQuery = "SELECT o FROM CmsLayout o WHERE name = ?1";
		return entityManager().createQuery(layoutByNameQuery, CmsLayout.class).setParameter(1, name).getSingleResult();
	}

	public static Collection<CmsLayout> fromJsonArrayToCmsLayouts(String json) {
		return new JSONDeserializer<List<CmsLayout>>().use(null, ArrayList.class).use("values", CmsLayout.class)
				.deserialize(json);
	}

	public static CmsLayout fromJsonToCmsLayout(String json) {
		return new JSONDeserializer<CmsLayout>().use(null, CmsLayout.class).deserialize(json);
	}

	public static void indexCmsLayout(CmsLayout cmsLayout) {
		List<CmsLayout> cmslayouts = new ArrayList<CmsLayout>();
		cmslayouts.add(cmsLayout);
		indexCmsLayouts(cmslayouts);
	}

	@Async
	public static void indexCmsLayouts(Collection<CmsLayout> cmslayouts) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (CmsLayout cmsLayout : cmslayouts) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "cmslayout_" + cmsLayout.getId());
			sid.addField("cmsLayout.id_i", cmsLayout.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("cmslayout_solrsummary_t", new StringBuilder().append(cmsLayout.getId()));
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
		String searchString = "CmsLayout_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new CmsLayout().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<CmsLayout> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>CmsLayout</code> (aranjament
	 * CMS) in baza de date; in caz afirmativ il returneaza, altfel, metoda il
	 * introduce in baza de date si apoi il returneaza. Verificarea existentei
	 * in baza de date se realizeaza fie dupa identificator, fie dupa un
	 * criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>name + cmsLayoutGroupId
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul aranjamentului.
	 * @param name
	 *            - numele aranjamentului.
	 * @param cmsLayoutGroupId
	 *            - grupul din care face parte aranjamentul.
	 * @param layoutContent
	 *            - continutul aranjamentului.
	 * @return
	 */
	public static CmsLayout checkCmsLayout(Integer id, String name, CmsLayoutGroup cmsLayoutGroupId,
			String layoutContent) {
		CmsLayout object;

		if (id != null) {
			object = findCmsLayout(id);

			if (object != null) {
				return object;
			}
		}

		List<CmsLayout> queryResult;

		if (name != null && cmsLayoutGroupId != null) {
			TypedQuery<CmsLayout> query = entityManager().createQuery(
					"SELECT o FROM CmsLayout o WHERE lower(o.name) = lower(:name) AND "
							+ "o.cmsLayoutGroupId = :cmsLayoutGroupId", CmsLayout.class);
			query.setParameter("name", name);
			query.setParameter("cmsLayoutGroupId", cmsLayoutGroupId);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new CmsLayout();
		object.name = name;
		object.cmsLayoutGroupId = cmsLayoutGroupId;
		object.layoutContent = layoutContent;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@ManyToOne
	@JoinColumn(name = "cms_layout_group_id", columnDefinition = "integer", referencedColumnName = "id", nullable = true)
	private CmsLayoutGroup cmsLayoutGroupId;

	@OneToMany(mappedBy = "cmsLayoutId")
	private Set<CmsPage> cmsPages;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "serial")
	private Integer id;

	@Column(name = "layout_content", columnDefinition = "text")
	@NotNull
	private String layoutContent;

	@Column(name = "name", columnDefinition = "varchar", length = 200)
	@NotNull
	private String name;

	@Column(name = "description", columnDefinition = "text")
	private String description;

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

	public CmsLayoutGroup getCmsLayoutGroupId() {
		return cmsLayoutGroupId;
	}

	public Set<CmsPage> getCmsPages() {
		return cmsPages;
	}

	public Integer getId() {
		return this.id;
	}

	public String getLayoutContent() {
		return layoutContent;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@Transactional
	public CmsLayout merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		CmsLayout merged = this.entityManager.merge(this);
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
			CmsLayout attached = CmsLayout.findCmsLayout(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setCmsLayoutGroupId(CmsLayoutGroup cmsLayoutGroupId) {
		this.cmsLayoutGroupId = cmsLayoutGroupId;
	}

	public void setCmsPages(Set<CmsPage> cmsPages) {
		this.cmsPages = cmsPages;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setLayoutContent(String layoutContent) {
		this.layoutContent = layoutContent;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
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
		indexCmsLayout(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CmsLayout) {
			return (id != null && id.equals(((CmsLayout) obj).id))
					|| ((name != null && name.equalsIgnoreCase(((CmsLayout) obj).name)) && (cmsLayoutGroupId != null && cmsLayoutGroupId
							.equals(((CmsLayout) obj).cmsLayoutGroupId)));
		}
		return false;
	}

	@JsonIgnore public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}

}
