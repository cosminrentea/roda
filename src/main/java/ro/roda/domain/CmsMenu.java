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

@Configurable
@Entity
@Table(schema = "public", name = "cms_menu")
@Audited
public class CmsMenu {

	public static long countCmsMenus() {
		return entityManager().createQuery("SELECT COUNT(o) FROM CmsMenu o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(CmsMenu cmsMenu) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("cmslayout_" + cmsMenu.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new CmsMenu().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<CmsMenu> findAllCmsMenus() {
		return entityManager().createQuery("SELECT o FROM CmsMenu o", CmsMenu.class).getResultList();
	}

	public static CmsMenu findCmsMenu(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(CmsMenu.class, id);
	}

	public static List<CmsMenu> findCmsMenuEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM CmsMenu o", CmsMenu.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<CmsMenu> fromJsonArrayToCmsMenus(String json) {
		return new JSONDeserializer<List<CmsMenu>>().use(null, ArrayList.class).use("values", CmsMenu.class)
				.deserialize(json);
	}

	public static CmsMenu fromJsonToCmsMenu(String json) {
		return new JSONDeserializer<CmsMenu>().use(null, CmsMenu.class).deserialize(json);
	}

	public static void indexCmsMenu(CmsMenu cmsMenu) {
		List<CmsMenu> cmslayouts = new ArrayList<CmsMenu>();
		cmslayouts.add(cmsMenu);
		indexCmsMenus(cmslayouts);
	}

	@Async
	public static void indexCmsMenus(Collection<CmsMenu> cmslayouts) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (CmsMenu cmsMenu : cmslayouts) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "cmsmenu_" + cmsMenu.getId());
			sid.addField("cmsMenu.id_i", cmsMenu.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("cmslayout_solrsummary_t", new StringBuilder().append(cmsMenu.getId()));
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
		String searchString = "CmsMenu_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new CmsMenu().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<CmsMenu> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>CmsMenu</code> (aranjament
	 * CMS) in baza de date; in caz afirmativ il returneaza, altfel, metoda il
	 * introduce in baza de date si apoi il returneaza. Verificarea existentei
	 * in baza de date se realizeaza fie dupa identificator, fie dupa un
	 * criteriu de unicitate.
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
	 *            - identificatorul meniului.
	 * @param text
	 *            - textul meniului.
	 * @param parentId
	 *            - meniul parinte.
	 * 
	 * @return
	 */
	public static CmsMenu checkCmsMenu(Integer id, CmsMenu parentId, String text) {
		CmsMenu object;

		if (id != null) {
			object = findCmsMenu(id);

			if (object != null) {
				return object;
			}
		}

		List<CmsMenu> queryResult;

		if (text != null && parentId != null) {
			TypedQuery<CmsMenu> query = entityManager().createQuery(
					"SELECT o FROM CmsMenu o WHERE lower(o.text) = lower(:text) AND " + "o.parentId = :parentId",
					CmsMenu.class);
			query.setParameter("text", text);
			query.setParameter("parentId", parentId);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new CmsMenu();
		object.text = text;
		object.parentId = parentId;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@ManyToOne
	@JoinColumn(name = "parent_id", columnDefinition = "integer", referencedColumnName = "id", nullable = true)
	private CmsMenu parentId;

	@OneToMany(mappedBy = "parentId")
	private Set<CmsMenu> cmsMenus;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "serial")
	private Integer id;

	@Column(name = "text", columnDefinition = "text")
	@NotNull
	private String text;

	@Column(name = "icon_cls", columnDefinition = "text")
	private String iconCls;

	@Column(name = "class_name", columnDefinition = "varchar", length = 200)
	private String className;

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

	public CmsMenu getParentId() {
		return parentId;
	}

	public Set<CmsMenu> getCmsMenus() {
		return cmsMenus;
	}

	public Integer getId() {
		return this.id;
	}

	public String getText() {
		return text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public String getClassName() {
		return className;
	}

	@Transactional
	public CmsMenu merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		CmsMenu merged = this.entityManager.merge(this);
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
			CmsMenu attached = CmsMenu.findCmsMenu(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setParentId(CmsMenu parentId) {
		this.parentId = parentId;
	}

	public void setCmsMenu(Set<CmsMenu> cmsMenus) {
		this.cmsMenus = cmsMenus;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
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
		indexCmsMenu(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((CmsMenu) obj).id))
				|| ((text != null && text.equalsIgnoreCase(((CmsMenu) obj).text)) && (parentId != null && parentId
						.equals(((CmsMenu) obj).parentId)));
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}

}
