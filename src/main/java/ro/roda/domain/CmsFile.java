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
@Table(schema = "public", name = "cms_file")
@Configurable
@Audited
public class CmsFile {

	public static long countCmsFiles() {
		return entityManager().createQuery("SELECT COUNT(o) FROM CmsFile o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(CmsFile cmsFile) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("cmsfile_" + cmsFile.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new CmsFile().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<CmsFile> findAllCmsFiles() {
		return entityManager().createQuery("SELECT o FROM CmsFile o", CmsFile.class).getResultList();
	}

	public static CmsFile findCmsFile(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(CmsFile.class, id);
	}

	public static List<CmsFile> findCmsFileEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM CmsFile o", CmsFile.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<CmsFile> fromJsonArrayToCmsFiles(String json) {
		return new JSONDeserializer<List<CmsFile>>().use(null, ArrayList.class).use("values", CmsFile.class)
				.deserialize(json);
	}

	public static CmsFile fromJsonToCmsFile(String json) {
		return new JSONDeserializer<CmsFile>().use(null, CmsFile.class).deserialize(json);
	}

	public static void indexCmsFile(CmsFile cmsFile) {
		List<CmsFile> cmsfiles = new ArrayList<CmsFile>();
		cmsfiles.add(cmsFile);
		indexCmsFiles(cmsfiles);
	}

	@Async
	public static void indexCmsFiles(Collection<CmsFile> cmsfiles) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (CmsFile cmsFile : cmsfiles) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "cmsfile_" + cmsFile.getId());
			sid.addField("cmsFile.cmsfolderid_t", cmsFile.getCmsFolderId());
			sid.addField("cmsFile.filename_s", cmsFile.getFilename());
			sid.addField("cmsFile.label_s", cmsFile.getLabel());
			sid.addField("cmsFile.filesize_l", cmsFile.getFilesize());
			sid.addField("cmsFile.id_i", cmsFile.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"cmsfile_solrsummary_t",
					new StringBuilder().append(cmsFile.getCmsFolderId()).append(" ").append(cmsFile.getFilename())
							.append(" ").append(cmsFile.getLabel()).append(" ").append(cmsFile.getFilesize())
							.append(" ").append(cmsFile.getId()));
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
		String searchString = "CmsFile_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new CmsFile().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<CmsFile> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>CmsFile</code> (fisier CMS)
	 * in baza de date; in caz afirmativ il returneaza, altfel, metoda il
	 * introduce in baza de date si apoi il returneaza. Verificarea existentei
	 * in baza de date se realizeaza fie dupa identificator, fie dupa un
	 * criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>filename + cmsFolderId
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul fisierului.
	 * @param filename
	 *            - numele fisierului.
	 * @param label
	 *            - eticheta sau aliasul fisierului.
	 * @param cmsFolderId
	 *            - directorul parinte al fisierului.
	 * @param filesize
	 *            - marimea fisierului in octeti.
	 * @return
	 */
	public static CmsFile checkCmsFile(Integer id, String filename, String label, CmsFolder cmsFolderId, Long filesize) {
		CmsFile object;

		if (id != null) {
			object = findCmsFile(id);

			if (object != null) {
				return object;
			}
		}

		List<CmsFile> queryResult;

		if (filename != null && cmsFolderId != null) {
			TypedQuery<CmsFile> query = entityManager().createQuery(
					"SELECT o FROM CmsFile o WHERE lower(o.filename) = lower(:filename) AND "
							+ "o.cmsFolderId = :cmsFolderId", CmsFile.class);
			query.setParameter("filename", filename);
			query.setParameter("cmsFolderId", cmsFolderId);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new CmsFile();
		object.filename = filename;
		object.label = label;
		object.cmsFolderId = cmsFolderId;
		object.filesize = filesize;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@ManyToOne
	@JoinColumn(name = "cms_folder_id", columnDefinition = "integer", referencedColumnName = "id", nullable = true)
	private CmsFolder cmsFolderId;

	@Column(name = "filename", columnDefinition = "text")
	@NotNull
	private String filename;

	@Column(name = "filesize", columnDefinition = "int8")
	private Long filesize;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "serial")
	private Integer id;

	@Column(name = "label", columnDefinition = "varchar", length = 100)
	@NotNull
	private String label;

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

	public CmsFolder getCmsFolderId() {
		return cmsFolderId;
	}

	public String getFilename() {
		return filename;
	}

	public Long getFilesize() {
		return filesize;
	}

	public Integer getId() {
		return this.id;
	}

	public String getLabel() {
		return label;
	}

	@Transactional
	public CmsFile merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		CmsFile merged = this.entityManager.merge(this);
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
			CmsFile attached = CmsFile.findCmsFile(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setCmsFolderId(CmsFolder cmsFolderId) {
		this.cmsFolderId = cmsFolderId;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setLabel(String label) {
		this.label = label;
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
		indexCmsFile(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((CmsFile) obj).id))
				|| ((filename != null && filename.equalsIgnoreCase(((CmsFile) obj).filename)) && (cmsFolderId != null && cmsFolderId
						.equals(((CmsFile) obj).cmsFolderId)));
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
