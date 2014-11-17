package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;

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

@Entity
@Table(schema = "public", name = "cms_page_lang")
@Configurable
@Audited
public class CmsPageLang {

	public static long countCmsPageLangs() {
		return entityManager().createQuery("SELECT COUNT(o) FROM CmsPageLang o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(CmsPageLang cmsPageLang) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("cmspagelang_" + cmsPageLang.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new CmsPageLang().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<CmsPageLang> findAllCmsPageLangs() {
		return entityManager().createQuery("SELECT o FROM CmsPageLang o", CmsPageLang.class).getResultList();
	}

	public static CmsPageLang findCmsPageLang(CmsPageLangPK id) {
		if (id == null)
			return null;
		return entityManager().find(CmsPageLang.class, id);
	}

	public static List<CmsPageLang> findCmsPageLangEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM CmsPageLang o", CmsPageLang.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<CmsPageLang> fromJsonArrayToCmsPageLangs(String json) {
		return new JSONDeserializer<List<CmsPageLang>>().use(null, ArrayList.class).use("values", CmsPageLang.class)
				.deserialize(json);
	}

	public static CmsPageLang fromJsonToCmsPageLang(String json) {
		return new JSONDeserializer<CmsPageLang>().use(null, CmsPageLang.class).deserialize(json);
	}

	public static void indexCmsPageLang(CmsPageLang cmsPageLang) {
		List<CmsPageLang> cmspagelangs = new ArrayList<CmsPageLang>();
		cmspagelangs.add(cmsPageLang);
		indexCmsPageLangs(cmspagelangs);
	}

	@Async
	public static void indexCmsPageLangs(Collection<CmsPageLang> cmspagelangs) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (CmsPageLang cmsPageLang : cmspagelangs) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "cmspagelang_" + cmsPageLang.getId());
			sid.addField("cmsPageLang.langid_t", cmsPageLang.getLangId());
			sid.addField("cmsPageLang.cmspageid_t", cmsPageLang.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("cmspagelang_solrsummary_t", new StringBuilder().append(cmsPageLang.getLangId()).append(" ")
					.append(cmsPageLang.getCmsPageId()));
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
		String searchString = "CmsPageLang_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new CmsPageLang().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<CmsPageLang> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@ManyToOne
	@JoinColumn(name = "lang_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Lang langId;

	@EmbeddedId
	private CmsPageLangPK id;

	@ManyToOne
	@JoinColumn(name = "cms_page_id", columnDefinition = "integer", referencedColumnName = "id", nullable = true, insertable = false, updatable = false)
	private CmsPage cmsPageId;

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

	public Lang getLangId() {
		return langId;
	}

	public CmsPageLangPK getId() {
		return this.id;
	}

	public CmsPage getCmsPageId() {
		return cmsPageId;
	}

	@Transactional
	public CmsPageLang merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		CmsPageLang merged = this.entityManager.merge(this);
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
			CmsPageLang attached = CmsPageLang.findCmsPageLang(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setLangId(Lang langId) {
		this.langId = langId;
	}

	public void setId(CmsPageLangPK id) {
		this.id = id;
	}

	public void setCmsPageId(CmsPage cmsPageId) {
		this.cmsPageId = cmsPageId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@JsonIgnore public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexCmsPageLang(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
