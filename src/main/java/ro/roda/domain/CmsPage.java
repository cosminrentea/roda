package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

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
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(schema = "public", name = "cms_page")
@Configurable
@Audited
public class CmsPage implements Comparable<CmsPage> {

	public static final String SOLR_CMSPAGE = "cmspage";

	public static final String SOLR_CMSPAGE_EN = "Page";

	public static final String SOLR_CMSPAGE_RO = "Pagina";

	public static void indexCmsPage(CmsPage cmsPage) {
		List<CmsPage> cmspages = new ArrayList<CmsPage>();
		cmspages.add(cmsPage);
		indexCmsPages(cmspages);
	}

	@Async
	public static void indexCmsPages(Collection<CmsPage> cmspages) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (CmsPage cmsPage : cmspages) {
			if (cmsPage.searchable) {
				SolrInputDocument sid = new SolrInputDocument();
				sid.addField("id", SOLR_CMSPAGE + "_" + cmsPage.getId());
				String language = cmsPage.getLangId().getIso639();
				sid.addField("language", language);
				String entityName = null;
				if ("ro".equalsIgnoreCase(language)) {
					entityName = SOLR_CMSPAGE_RO;
				}
				if ("en".equalsIgnoreCase(language)) {
					entityName = SOLR_CMSPAGE_EN;
				}
				sid.addField("entity", SOLR_CMSPAGE);
				sid.addField("entityname", entityName);
				sid.addField("name", cmsPage.getName());
				sid.addField("description",
						Jsoup.clean(cmsPage.getCmsPageContents().iterator().next().getContentText(), Whitelist.none()));
				sid.addField("url", cmsPage.generateFullRelativeUrl());
				// sid.addField(
				// "summary_t",
				// new StringBuilder().append(cmsPage.getName()).append(" ")
				// .append(cmsPage.getCmsPageContents().iterator().next().getContentText()));
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
	}

	@Async
	public static void deleteIndex(CmsPage cmsPage) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById(SOLR_CMSPAGE + "_" + cmsPage.getId());
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

	public static long countCmsPages() {
		return entityManager().createQuery("SELECT COUNT(o) FROM CmsPage o", Long.class).getSingleResult();
	}

	public static List<CmsPage> findAllCmsPages() {
		return entityManager().createQuery("SELECT o FROM CmsPage o", CmsPage.class).getResultList();
	}

	public static CmsPage findCmsPage(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(CmsPage.class, id);
	}

	public static List<CmsPage> findCmsPage(String url) {
		if (url == null)
			return null;

		String pageByUrlQuery = "SELECT o FROM CmsPage o WHERE url = ?1";
		TypedQuery<CmsPage> query = entityManager().createQuery(pageByUrlQuery, CmsPage.class).setParameter(1, url);
		if (query.getResultList().size() > 0) {
			return query.getResultList();
		}

		return null;
	}

	public static CmsPage findCmsPageByParent(String url, CmsPage parent) {
		if (url == null)
			return null;

		String pageByUrlQuery = "";
		TypedQuery<CmsPage> query;
		if (parent != null) {
			pageByUrlQuery = "SELECT o FROM CmsPage o WHERE url = ?1 and cmsPageId = ?2";

			query = entityManager().createQuery(pageByUrlQuery, CmsPage.class).setParameter(1, url)
					.setParameter(2, parent);
		} else {
			pageByUrlQuery = "SELECT o FROM CmsPage o WHERE url = ?1 and cmsPageId = NULL";

			query = entityManager().createQuery(pageByUrlQuery, CmsPage.class).setParameter(1, url);
		}

		// the query returns only one result, because the import does not allow
		// two identical full URLs
		if (query != null && query.getResultList().size() > 0) {
			return query.getResultList().get(0);
		}

		return null;
	}

	public static List<CmsPage> findCmsPageByLangAndType(Lang lang, CmsPageType pageType) {
		if (lang == null || pageType == null) {
			return null;
		}

		return entityManager()
				.createQuery("SELECT o FROM CmsPage o INNER JOIN o.langId l WHERE l.id = ?1 and o.cmsPageTypeId = ?2",
						CmsPage.class).setParameter(1, lang).setParameter(2, pageType).getResultList();
	}

	public static CmsPage findCmsPageDefault() {
		TypedQuery<CmsPage> query = entityManager().createQuery("SELECT o FROM CmsPage o WHERE defaultPage = true",
				CmsPage.class);

		// the query returns only one result, as there should be only one
		// default page at a moment
		try {
			if (query != null) {
				return query.getSingleResult();
			}
		} catch (Exception e) {
			// TODO log
		}

		return null;
	}

	public static CmsPage findCmsPageByFullUrl(String url) {
		if (url == null)
			return null;

		StringTokenizer tokenizer = new StringTokenizer(url, "/");
		CmsPage prevPage = null;

		while (tokenizer.hasMoreTokens()) {
			String pathUrl = tokenizer.nextToken();

			prevPage = findCmsPageByParent(pathUrl, prevPage);

			if (prevPage == null) {
				return null;
			}
		}

		return prevPage;

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
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
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

	public static void reorderAllCmsPages() {
		// reorders the child pages
		for (CmsPage page : findAllCmsPages()) {
			page.reorderCmsPages();
		}
	}

	@ManyToOne
	@JoinColumn(name = "cms_layout_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false)
	private CmsLayout cmsLayoutId;

	@OneToMany(mappedBy = "cmsPageId")
	private Set<CmsPageContent> cmsPageContents;

	@ManyToOne
	@JoinColumn(name = "cms_page_type_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false)
	private CmsPageType cmsPageTypeId;

	@ManyToOne
	@JoinColumn(name = "lang_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false)
	private Lang langId;

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
	private boolean navigable = true;

	@Column(name = "searchable", columnDefinition = "bool")
	@NotNull
	private boolean searchable = true;

	@Column(name = "url", columnDefinition = "text")
	@NotNull
	private String url;

	@Column(name = "visible", columnDefinition = "bool")
	@NotNull
	private boolean visible = true;

	@Column(name = "published", columnDefinition = "bool")
	@NotNull
	private boolean published = true;

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

	@Column(name = "menu_title", columnDefinition = "text")
	private String menuTitle;

	@Column(name = "sequence_number", columnDefinition = "int")
	private Integer sequenceNumber;

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

	public Lang getLangId() {
		return langId;
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

	public boolean isPublished() {
		return published;
	}

	public boolean isSearchable() {
		return searchable;
	}

	public boolean isVisible() {
		return visible;
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

	public String getMenuTitle() {
		return menuTitle;
	}

	public Integer getSequenceNumber() {
		return sequenceNumber;
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

	public void setLangId(Lang langId) {
		this.langId = langId;
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

	public void setPublished(boolean published) {
		this.published = published;
	}

	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setDefaultPage(boolean defaultPage) {
		CmsPage existantDefaultPage = findCmsPageDefault();
		if (existantDefaultPage != null) {
			existantDefaultPage.defaultPage = false;
		}
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

	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public void move(Integer position) {
		// moves a page on the specified position under the current parent; the
		// position of the pages having a greater or equal sequence number will
		// be increased by 1
		if (cmsPageId != null) {
			TypedQuery<CmsPage> query;
			query = entityManager()
					.createQuery("SELECT o FROM CmsPage o WHERE sequenceNumber >= ?1 and cmsPageId = ?2", CmsPage.class)
					.setParameter(1, position).setParameter(2, cmsPageId);
			List<CmsPage> subsequentPages = query.getResultList();
			for (CmsPage page : subsequentPages) {
				page.setSequenceNumber(page.getSequenceNumber() + 1);
				page.persist();
			}
		}
		this.sequenceNumber = position;
	}

	public void moveFirst() {
		move(0);
		if (cmsPageId != null) {
			cmsPageId.reorderCmsPages();
		}
	}

	public void moveLast() {
		move(1000);
		if (cmsPageId != null) {
			cmsPageId.reorderCmsPages();
		}
	}

	public void reorderCmsPages() {
		// reorders the child pages of the current page
		if (cmsPages != null && cmsPages.size() > 0) {
			TypedQuery<CmsPage> query;
			query = entityManager()
					.createQuery(
							"SELECT o FROM CmsPage o WHERE sequenceNumber IS NOT NULL AND cmsPageId = ?1 ORDER BY sequenceNumber",
							CmsPage.class).setParameter(1, this);
			List<CmsPage> orderedPages = query.getResultList();
			Integer nb = 1;
			for (CmsPage page : orderedPages) {
				if (page.getSequenceNumber() != nb) {
					page.setSequenceNumber(nb);
					page.persist();
				}
				nb++;
			}
		}
	}

	public String generateFullRelativeUrl() {
		String result = url;
		CmsPage parentPage = this.getCmsPageId();
		while (parentPage != null) {
			result = parentPage.getUrl() + "/" + result;
			parentPage = parentPage.getCmsPageId();
		}
		return result;
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
		indexCmsPage(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CmsPage) {
			return (id != null && id.equals(((CmsPage) obj).id));
			// TODO decide the uniqueness conditions (finally, the url and the
			// name are no longer unique)
			// || (name != null && name.equalsIgnoreCase(((CmsPage) obj).name))
			// || (url != null && url.equalsIgnoreCase(((CmsPage) obj).url));
		}
		return false;
	}

	@Override
	public int compareTo(CmsPage cmsPage) {
		// if the pages have the same parent, they are compared by their
		// sequence number; otherwise, they are compared by their ids
		if (cmsPageId != null && cmsPage.getCmsPageId() != null && cmsPageId.getId() == cmsPage.getCmsPageId().getId()
				&& sequenceNumber != null && cmsPage.getSequenceNumber() != null)
			return sequenceNumber.compareTo(cmsPage.getSequenceNumber());
		return id.compareTo(cmsPage.getId());
	}

	@JsonIgnore
	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
