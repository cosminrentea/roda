package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(schema = "public", name = "cms_page_content")
@Configurable
@Audited
public class CmsPageContent {

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

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "CmsPageContent_solrsummary_t:" + queryString;
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

	public static void indexCmsPageContent(CmsPageContent cmsPageContent) {
		List<CmsPageContent> cmspagecontents = new ArrayList<CmsPageContent>();
		cmspagecontents.add(cmsPageContent);
		indexCmsPageContents(cmspagecontents);
	}

	@Async
	public static void indexCmsPageContents(Collection<CmsPageContent> cmspagecontents) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (CmsPageContent cmsPageContent : cmspagecontents) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "cmspagecontent_" + cmsPageContent.getId());
			sid.addField("cmsPageContent.cmspageid_t", cmsPageContent.getCmsPageId());
			sid.addField("cmsPageContent.name_s", cmsPageContent.getName());
			sid.addField("cmsPageContent.contenttitle_s", cmsPageContent.getContentTitle());
			sid.addField("cmsPageContent.contenttext_s", cmsPageContent.getContentText());
			sid.addField("cmsPageContent.orderinpage_i", cmsPageContent.getOrderInPage());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"cmspagecontent_solrsummary_t",
					new StringBuilder().append(cmsPageContent.getCmsPageId()).append(" ")
							.append(cmsPageContent.getName()).append(" ").append(cmsPageContent.getContentTitle())
							.append(" ").append(cmsPageContent.getContentText()).append(" ")
							.append(cmsPageContent.getOrderInPage()));
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
	public static void deleteIndex(CmsPageContent cmsPageContent) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("cmspagecontent_" + cmsPageContent.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexCmsPageContent(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new CmsPageContent().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static CmsPageContent fromJsonToCmsPageContent(String json) {
		return new JSONDeserializer<CmsPageContent>().use(null, CmsPageContent.class).deserialize(json);
	}

	public static String toJsonArray(Collection<CmsPageContent> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<CmsPageContent> fromJsonArrayToCmsPageContents(String json) {
		return new JSONDeserializer<List<CmsPageContent>>().use(null, ArrayList.class)
				.use("values", CmsPageContent.class).deserialize(json);
	}

	@ManyToOne
	@JoinColumn(name = "cms_page_id", referencedColumnName = "id", nullable = false)
	private CmsPage cmsPageId;

	@Column(name = "name", columnDefinition = "varchar", length = 200)
	@NotNull
	private String name;

	@Column(name = "content_title", columnDefinition = "varchar", length = 250)
	@NotNull
	private String contentTitle;

	@Column(name = "content_text", columnDefinition = "text")
	private String contentText;

	@Column(name = "order_in_page", columnDefinition = "int4", unique = true)
	@NotNull
	private Integer orderInPage;

	public CmsPage getCmsPageId() {
		return cmsPageId;
	}

	public void setCmsPageId(CmsPage cmsPageId) {
		this.cmsPageId = cmsPageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public Integer getOrderInPage() {
		return orderInPage;
	}

	public void setOrderInPage(Integer orderInPage) {
		this.orderInPage = orderInPage;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new CmsPageContent().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countCmsPageContents() {
		return entityManager().createQuery("SELECT COUNT(o) FROM CmsPageContent o", Long.class).getSingleResult();
	}

	public static List<CmsPageContent> findAllCmsPageContents() {
		return entityManager().createQuery("SELECT o FROM CmsPageContent o", CmsPageContent.class).getResultList();
	}

	public static CmsPageContent findCmsPageContent(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(CmsPageContent.class, id);
	}

	public static List<CmsPageContent> findCmsPageContentEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM CmsPageContent o", CmsPageContent.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
			CmsPageContent attached = CmsPageContent.findCmsPageContent(this.id);
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
	public CmsPageContent merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		CmsPageContent merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}
}
