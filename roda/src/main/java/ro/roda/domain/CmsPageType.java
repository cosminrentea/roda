package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
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
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
@Table(schema = "public", name = "cms_page_type")
public class CmsPageType {

	@OneToMany(mappedBy = "cmsPageTypeId")
	private Set<CmsPage> cmsPages;

	@Column(name = "name", columnDefinition = "varchar", length = 200)
	@NotNull
	private String name;

	@Column(name = "description", columnDefinition = "text")
	private String description;

	public Set<CmsPage> getCmsPages() {
		return cmsPages;
	}

	public void setCmsPages(Set<CmsPage> cmsPages) {
		this.cmsPages = cmsPages;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "CmsPageType_solrsummary_t:" + queryString;
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

	public static void indexCmsPageType(CmsPageType cmsPageType) {
		List<CmsPageType> cmspagetypes = new ArrayList<CmsPageType>();
		cmspagetypes.add(cmsPageType);
		indexCmsPageTypes(cmspagetypes);
	}

	@Async
	public static void indexCmsPageTypes(Collection<CmsPageType> cmspagetypes) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (CmsPageType cmsPageType : cmspagetypes) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "cmspagetype_" + cmsPageType.getId());
			sid.addField("cmsPageType.id_i", cmsPageType.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("cmspagetype_solrsummary_t", new StringBuilder().append(cmsPageType.getId()));
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
	public static void deleteIndex(CmsPageType cmsPageType) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("cmspagetype_" + cmsPageType.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexCmsPageType(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new CmsPageType().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new CmsPageType().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countCmsPageTypes() {
		return entityManager().createQuery("SELECT COUNT(o) FROM CmsPageType o", Long.class).getSingleResult();
	}

	public static List<CmsPageType> findAllCmsPageTypes() {
		return entityManager().createQuery("SELECT o FROM CmsPageType o", CmsPageType.class).getResultList();
	}

	public static CmsPageType findCmsPageType(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(CmsPageType.class, id);
	}

	public static List<CmsPageType> findCmsPageTypeEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM CmsPageType o", CmsPageType.class)
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
			CmsPageType attached = CmsPageType.findCmsPageType(this.id);
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
	public CmsPageType merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		CmsPageType merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static CmsPageType fromJsonToCmsPageType(String json) {
		return new JSONDeserializer<CmsPageType>().use(null, CmsPageType.class).deserialize(json);
	}

	public static String toJsonArray(Collection<CmsPageType> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<CmsPageType> fromJsonArrayToCmsPageTypes(String json) {
		return new JSONDeserializer<List<CmsPageType>>().use(null, ArrayList.class).use("values", CmsPageType.class)
				.deserialize(json);
	}

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
}
