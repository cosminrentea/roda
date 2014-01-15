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
@Table(schema = "public", name = "cms_folder")
@Audited
public class CmsFolder {

	public static long countCmsFolders() {
		return entityManager().createQuery("SELECT COUNT(o) FROM CmsFolder o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(CmsFolder cmsFolder) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("cmsfolder_" + cmsFolder.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new CmsFolder().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<CmsFolder> findAllCmsFolders() {
		return entityManager().createQuery("SELECT o FROM CmsFolder o", CmsFolder.class).getResultList();
	}

	public static CmsFolder findCmsFolder(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(CmsFolder.class, id);
	}

	public static List<CmsFolder> findCmsFolderEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM CmsFolder o", CmsFolder.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<CmsFolder> fromJsonArrayToCmsFolders(String json) {
		return new JSONDeserializer<List<CmsFolder>>().use(null, ArrayList.class).use("values", CmsFolder.class)
				.deserialize(json);
	}

	public static CmsFolder fromJsonToCmsFolder(String json) {
		return new JSONDeserializer<CmsFolder>().use(null, CmsFolder.class).deserialize(json);
	}

	public static void indexCmsFolder(CmsFolder cmsFolder) {
		List<CmsFolder> cmsfolders = new ArrayList<CmsFolder>();
		cmsfolders.add(cmsFolder);
		indexCmsFolders(cmsfolders);
	}

	@Async
	public static void indexCmsFolders(Collection<CmsFolder> cmsfolders) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (CmsFolder cmsFolder : cmsfolders) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "cmsfolder_" + cmsFolder.getId());
			sid.addField("cmsFolder.parentid_t", cmsFolder.getParentId());
			sid.addField("cmsFolder.name_s", cmsFolder.getName());
			sid.addField("cmsFolder.description_s", cmsFolder.getDescription());
			sid.addField("cmsFolder.id_i", cmsFolder.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"cmsfolder_solrsummary_t",
					new StringBuilder().append(cmsFolder.getParentId()).append(" ").append(cmsFolder.getName())
							.append(" ").append(cmsFolder.getDescription()).append(" ").append(cmsFolder.getId()));
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
		String searchString = "CmsFolder_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new CmsFolder().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<CmsFolder> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>CmsFolder</code> (director
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
	 *            - identificatorul directorului.
	 * @param name
	 *            - numele directorului.
	 * @param parentId
	 *            - directorul parinte al directorului.
	 * @param description
	 *            - descrierea directorului.
	 * @return
	 */
	public static CmsFolder checkCmsFolder(Integer id, String name, CmsFolder parentId, String description) {
		CmsFolder object;

		if (id != null) {
			object = findCmsFolder(id);

			if (object != null) {
				return object;
			}
		}

		List<CmsFolder> queryResult;

		if (name != null && parentId != null) {
			TypedQuery<CmsFolder> query = entityManager().createQuery(
					"SELECT o FROM CmsFolder o WHERE lower(o.name) = lower(:name) AND " + "o.parentId = :parentId",
					CmsFolder.class);
			query.setParameter("name", name);
			query.setParameter("parentId", parentId);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new CmsFolder();
		object.name = name;
		object.parentId = parentId;
		object.description = description;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@OneToMany(mappedBy = "cmsFolderId")
	private Set<CmsFile> cmsFiles;

	@OneToMany(mappedBy = "parentId")
	private Set<CmsFolder> cmsFolders;

	@Column(name = "description", columnDefinition = "text")
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "text")
	@NotNull
	private String name;

	@ManyToOne
	@JoinColumn(name = "parent_id", columnDefinition = "integer", referencedColumnName = "id", insertable = true, updatable = true)
	private CmsFolder parentId;

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

	public Set<CmsFile> getCmsFiles() {
		return cmsFiles;
	}

	public Set<CmsFolder> getCmsFolders() {
		return cmsFolders;
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

	public CmsFolder getParentId() {
		return parentId;
	}

	@Transactional
	public CmsFolder merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		CmsFolder merged = this.entityManager.merge(this);
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
			CmsFolder attached = CmsFolder.findCmsFolder(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setCmsFiles(Set<CmsFile> cmsFiles) {
		this.cmsFiles = cmsFiles;
	}

	public void setCmsFolders(Set<CmsFolder> cmsFolders) {
		this.cmsFolders = cmsFolders;
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

	public void setParentId(CmsFolder parentId) {
		this.parentId = parentId;
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
		indexCmsFolder(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((CmsFolder) obj).id))
				|| ((name != null && name.equalsIgnoreCase(((CmsFolder) obj).name)) && (parentId != null && parentId
						.equals(((CmsFolder) obj).parentId)));
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
