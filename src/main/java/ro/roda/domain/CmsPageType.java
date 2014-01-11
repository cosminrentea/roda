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
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "cms_page_type")
@Audited
public class CmsPageType {

	public static long countCmsPageTypes() {
		return entityManager().createQuery("SELECT COUNT(o) FROM CmsPageType o", Long.class).getSingleResult();
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

	public static final EntityManager entityManager() {
		EntityManager em = new CmsPageType().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
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

	public static Collection<CmsPageType> fromJsonArrayToCmsPageTypes(String json) {
		return new JSONDeserializer<List<CmsPageType>>().use(null, ArrayList.class).use("values", CmsPageType.class)
				.deserialize(json);
	}

	public static CmsPageType fromJsonToCmsPageType(String json) {
		return new JSONDeserializer<CmsPageType>().use(null, CmsPageType.class).deserialize(json);
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

	public static QueryResponse search(SolrQuery query) {
		try {
			return solrServer().query(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new QueryResponse();
	}

	public static QueryResponse search(String queryString) {
		String searchString = "CmsPageType_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new CmsPageType().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<CmsPageType> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>CmsPageType</code> (tip de
	 * pagina CMS) in baza de date; in caz afirmativ il returneaza, altfel,
	 * metoda il introduce in baza de date si apoi il returneaza. Verificarea
	 * existentei in baza de date se realizeaza fie dupa identificator, fie dupa
	 * un criteriu de unicitate.
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
	 * @param description
	 *            - descrierea tipului.
	 * @return
	 */
	public static CmsPageType checkCmsPageType(Integer id, String name, String description) {
		CmsPageType object;

		if (id != null) {
			object = findCmsPageType(id);

			if (object != null) {
				return object;
			}
		}

		List<CmsPageType> queryResult;

		if (name != null) {
			TypedQuery<CmsPageType> query = entityManager().createQuery(
					"SELECT o FROM CmsPageType o WHERE lower(o.name) = lower(:name)", CmsPageType.class);
			query.setParameter("name", name);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new CmsPageType();
		object.name = name;
		object.description = description;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@OneToMany(mappedBy = "cmsPageTypeId")
	private Set<CmsPage> cmsPages;

	@Column(name = "description", columnDefinition = "text")
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "varchar", length = 200)
	@NotNull
	private String name;

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

	public Set<CmsPage> getCmsPages() {
		return cmsPages;
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

	@Transactional
	public CmsPageType merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		CmsPageType merged = this.entityManager.merge(this);
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
			CmsPageType attached = CmsPageType.findCmsPageType(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setCmsPages(Set<CmsPage> cmsPages) {
		this.cmsPages = cmsPages;
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

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((CmsPageType) obj).id))
				|| (name != null && name.equalsIgnoreCase(((CmsPageType) obj).name));
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
