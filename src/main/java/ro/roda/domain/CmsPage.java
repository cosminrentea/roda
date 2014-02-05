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

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(schema = "public", name = "cms_page")
@Configurable
@Audited
public class CmsPage {

	public static long countCmsPages() {
		return entityManager().createQuery("SELECT COUNT(o) FROM CmsPage o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(CmsPage cmsPage) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("cmspage_" + cmsPage.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new CmsPage().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<CmsPage> findAllCmsPages() {
		return entityManager().createQuery("SELECT o FROM CmsPage o", CmsPage.class).getResultList();
	}

	public static CmsPage findCmsPage(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(CmsPage.class, id);
	}

	public static List<CmsPage> findCmsPageEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM CmsPage o", CmsPage.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<CmsPage> fromJsonArrayToCmsPages(String json) {
		return new JSONDeserializer<List<CmsPage>>().use(null, ArrayList.class).use("values", CmsPage.class)
				.deserialize(json);
	}

	public static CmsPage fromJsonToCmsPage(String json) {
		return new JSONDeserializer<CmsPage>().use(null, CmsPage.class).deserialize(json);
	}

	public static void indexCmsPage(CmsPage cmsPage) {
		List<CmsPage> cmspages = new ArrayList<CmsPage>();
		cmspages.add(cmsPage);
		indexCmsPages(cmspages);
	}

	@Async
	public static void indexCmsPages(Collection<CmsPage> cmspages) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (CmsPage cmsPage : cmspages) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "cmspage_" + cmsPage.getId());
			sid.addField("cmsPage.cmslayoutid_t", cmsPage.getCmsLayoutId());
			sid.addField("cmsPage.cmspagetypeid_t", cmsPage.getCmsPageTypeId());
			sid.addField("cmsPage.name_s", cmsPage.getName());
			sid.addField("cmsPage.url_s", cmsPage.getUrl());
			sid.addField("cmsPage.id_i", cmsPage.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("cmspage_solrsummary_t",
					new StringBuilder().append(cmsPage.getCmsLayoutId()).append(" ").append(cmsPage.getCmsPageTypeId())
							.append(" ").append(cmsPage.getName()).append(" ").append(cmsPage.getUrl()).append(" ")
							.append(cmsPage.getId()));
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
		String searchString = "CmsPage_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new CmsPage().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<CmsPage> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>CmsPage</code> (pagina CMS)
	 * in baza de date; in caz afirmativ il returneaza, altfel, metoda il
	 * introduce in baza de date si apoi il returneaza. Verificarea existentei
	 * in baza de date se realizeaza fie dupa identificator, fie dupa un
	 * criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>name
	 * <li>url
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul paginii.
	 * @param name
	 *            - numele paginii.
	 * @param cmsLayoutId
	 *            - aranjamentul CMS al paginii.
	 * @param cmsPageTypeId
	 *            - tipul de pagina CMS.
	 * @param visible
	 *            - specifica daca pagina este vizibila.
	 * @param navigable
	 *            - specifica daca pagina este navigabila.
	 * @param url
	 *            - adresa paginii.
	 * @return
	 */
	public static CmsPage checkCmsPage(Integer id, String name, CmsLayout cmsLayoutId, CmsPageType cmsPageTypeId,
			Boolean visible, Boolean navigable, String url, String synopsis, String target, Boolean defaultPage,
			String externalRedirect, String internalRedirect, Integer cacheable) {
		CmsPage object;

		if (id != null) {
			object = findCmsPage(id);

			if (object != null) {
				return object;
			}
		}

		List<CmsPage> queryResult;

		if (url != null) {
			TypedQuery<CmsPage> query = entityManager().createQuery(
					"SELECT o FROM CmsPage o WHERE lower(o.url) = lower(:url)", CmsPage.class);
			query.setParameter("url", url);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		if (name != null) {
			TypedQuery<CmsPage> query = entityManager().createQuery(
					"SELECT o FROM CmsPage o WHERE lower(o.name) = lower(:name)", CmsPage.class);
			query.setParameter("name", name);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new CmsPage();
		object.name = name;
		object.cmsLayoutId = cmsLayoutId;
		object.cmsPageTypeId = cmsPageTypeId;
		object.visible = visible;
		object.navigable = navigable;
		object.url = url;
		object.synopsis = synopsis;
		object.target = target;
		object.defaultPage = defaultPage;
		object.externalRedirect = externalRedirect;
		object.internalRedirect = internalRedirect;
		object.cacheable = cacheable;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@ManyToOne
	@JoinColumn(name = "cms_layout_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false)
	private CmsLayout cmsLayoutId;

	@OneToMany(mappedBy = "cmsPageId")
	private Set<CmsPageContent> cmsPageContents;

	@ManyToOne
	@JoinColumn(name = "cms_page_type_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false)
	private CmsPageType cmsPageTypeId;

	@OneToMany(mappedBy = "cmsPageId")
	private Set<CmsPageLang> cmsPageLangId;

	@OneToMany(mappedBy = "cmsPageId")
	private Set<CmsPage> cmsPages;

	@ManyToOne
	@JoinColumn(name = "cms_page_id", columnDefinition = "integer", referencedColumnName = "id", nullable = true)
	private CmsPage cmsPageId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "text")
	@NotNull
	private String name;

	@Column(name = "navigable", columnDefinition = "bool")
	@NotNull
	private boolean navigable;

	@Column(name = "url", columnDefinition = "text")
	@NotNull
	private String url;

	@Column(name = "visible", columnDefinition = "bool")
	@NotNull
	private boolean visible;

	@Column(name = "default_page", columnDefinition = "bool")
	private boolean defaultPage;

	@Column(name = "synopsis", columnDefinition = "text")
	private String synopsis;

	@Column(name = "target", columnDefinition = "text")
	private String target;

	@Column(name = "internal_redirect", columnDefinition = "text")
	private String internalRedirect;

	@Column(name = "external_redirect", columnDefinition = "text")
	private String externalRedirect;

	@Column(name = "cacheable", columnDefinition = "int")
	private Integer cacheable;

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

	public CmsLayout getCmsLayoutId() {
		return cmsLayoutId;
	}

	public Set<CmsPageContent> getCmsPageContents() {
		return cmsPageContents;
	}

	public CmsPageType getCmsPageTypeId() {
		return cmsPageTypeId;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public boolean isNavigable() {
		return navigable;
	}

	public boolean isVisible() {
		return visible;
	}

	public Set<CmsPageLang> getCmsPageLangId() {
		return cmsPageLangId;
	}

	public boolean isDefaultPage() {
		return defaultPage;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public String getInternalRedirect() {
		return internalRedirect;
	}

	public String getExternalRedirect() {
		return externalRedirect;
	}

	public Integer getCacheable() {
		return cacheable;
	}

	public String getTarget() {
		return target;
	}

	public Set<CmsPage> getCmsPages() {
		return cmsPages;
	}

	public CmsPage getCmsPageId() {
		return cmsPageId;
	}

	@Transactional
	public CmsPage merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		CmsPage merged = this.entityManager.merge(this);
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
			CmsPage attached = CmsPage.findCmsPage(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setCmsLayoutId(CmsLayout cmsLayoutId) {
		this.cmsLayoutId = cmsLayoutId;
	}

	public void setCmsPageContents(Set<CmsPageContent> cmsPageContents) {
		this.cmsPageContents = cmsPageContents;
	}

	public void setCmsPageTypeId(CmsPageType cmsPageTypeId) {
		this.cmsPageTypeId = cmsPageTypeId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNavigable(boolean navigable) {
		this.navigable = navigable;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setCmsPageLangId(Set<CmsPageLang> cmsPageLangId) {
		this.cmsPageLangId = cmsPageLangId;
	}

	public void setDefaultPage(boolean defaultPage) {
		this.defaultPage = defaultPage;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public void setInternalRedirect(String internalRedirect) {
		this.internalRedirect = internalRedirect;
	}

	public void setExternalRedirect(String externalRedirect) {
		this.externalRedirect = externalRedirect;
	}

	public void setCacheable(Integer cacheable) {
		this.cacheable = cacheable;
	}

	public void setCmsPages(Set<CmsPage> cmsPages) {
		this.cmsPages = cmsPages;
	}

	public void setCmsPageId(CmsPage cmsPageId) {
		this.cmsPageId = cmsPageId;
	}

	public void setTarget(String target) {
		this.target = target;
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
		indexCmsPage(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((CmsPage) obj).id))
				|| (name != null && name.equalsIgnoreCase(((CmsPage) obj).name))
				|| (url != null && url.equalsIgnoreCase(((CmsPage) obj).url));
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
