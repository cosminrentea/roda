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
@Table(schema = "public", name = "cms_snippet")
@Configurable

public class CmsSnippet {

	public static long countCmsSnippets() {
		return entityManager().createQuery("SELECT COUNT(o) FROM CmsSnippet o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(CmsSnippet cmsSnippet) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("cmssnippet_" + cmsSnippet.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new CmsSnippet().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<CmsSnippet> findAllCmsSnippets() {
		return entityManager().createQuery("SELECT o FROM CmsSnippet o", CmsSnippet.class).getResultList();
	}

	public static CmsSnippet findCmsSnippet(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(CmsSnippet.class, id);
	}

	public static List<CmsSnippet> findCmsSnippetEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM CmsSnippet o", CmsSnippet.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<CmsSnippet> fromJsonArrayToCmsSnippets(String json) {
		return new JSONDeserializer<List<CmsSnippet>>().use(null, ArrayList.class).use("values", CmsSnippet.class)
				.deserialize(json);
	}

	public static CmsSnippet fromJsonToCmsSnippet(String json) {
		return new JSONDeserializer<CmsSnippet>().use(null, CmsSnippet.class).deserialize(json);
	}

	public static void indexCmsSnippet(CmsSnippet cmsSnippet) {
		List<CmsSnippet> cmssnippets = new ArrayList<CmsSnippet>();
		cmssnippets.add(cmsSnippet);
		indexCmsSnippets(cmssnippets);
	}

	@Async
	public static void indexCmsSnippets(Collection<CmsSnippet> cmssnippets) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (CmsSnippet cmsSnippet : cmssnippets) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "cmssnippet_" + cmsSnippet.getId());
			sid.addField("cmsSnippet.cmssnippetgroupid_t", cmsSnippet.getCmsSnippetGroupId());
			sid.addField("cmsSnippet.name_s", cmsSnippet.getName());
			sid.addField("cmsSnippet.snippetcontent_s", cmsSnippet.getSnippetContent());
			sid.addField("cmsSnippet.id_i", cmsSnippet.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("cmssnippet_solrsummary_t", new StringBuilder().append(cmsSnippet.getCmsSnippetGroupId())
					.append(" ").append(cmsSnippet.getName()).append(" ").append(cmsSnippet.getSnippetContent())
					.append(" ").append(cmsSnippet.getId()));
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
		String searchString = "CmsSnippet_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new CmsSnippet().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<CmsSnippet> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@ManyToOne
	@JoinColumn(name = "cms_snippet_group_id", columnDefinition = "integer", referencedColumnName = "id")
	private CmsSnippetGroup cmsSnippetGroupId;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "varchar", length = 200)
	@NotNull
	private String name;

	@Column(name = "snippet_content", columnDefinition = "text")
	@NotNull
	private String snippetContent;

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

	public CmsSnippetGroup getCmsSnippetGroupId() {
		return cmsSnippetGroupId;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public String getSnippetContent() {
		return snippetContent;
	}

	@Transactional
	public CmsSnippet merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		CmsSnippet merged = this.entityManager.merge(this);
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
			CmsSnippet attached = CmsSnippet.findCmsSnippet(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setCmsSnippetGroupId(CmsSnippetGroup cmsSnippetGroupId) {
		this.cmsSnippetGroupId = cmsSnippetGroupId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSnippetContent(String snippetContent) {
		this.snippetContent = snippetContent;
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
		indexCmsSnippet(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
