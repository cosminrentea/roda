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
@Table(schema = "public", name = "cms_page")
@Configurable

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

	@ManyToOne
	@JoinColumn(name = "cms_layout_id", referencedColumnName = "id", nullable = false)
	private CmsLayout cmsLayoutId;

	@OneToMany(mappedBy = "cmsPageId")
	private Set<CmsPageContent> cmsPageContents;

	@ManyToOne
	@JoinColumn(name = "cms_page_type_id", referencedColumnName = "id", nullable = false)
	private CmsPageType cmsPageTypeId;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "serial")
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
}
