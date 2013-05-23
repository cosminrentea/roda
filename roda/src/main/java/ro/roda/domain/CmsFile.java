package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
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
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(schema = "public", name = "cms_file")
@Configurable
public class CmsFile {

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new CmsFile().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countCmsFiles() {
		return entityManager().createQuery("SELECT COUNT(o) FROM CmsFile o", Long.class).getSingleResult();
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
	public CmsFile merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		CmsFile merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	@ManyToOne
	@JoinColumn(name = "cms_folder_id", referencedColumnName = "id", nullable = false)
	private CmsFolder cmsFolderId;

	@Column(name = "filename", columnDefinition = "text")
	@NotNull
	private String filename;

	@Column(name = "label", columnDefinition = "varchar", length = 100)
	@NotNull
	private String label;

	@Column(name = "filesize", columnDefinition = "int8")
	private Long filesize;

	public CmsFolder getCmsFolderId() {
		return cmsFolderId;
	}

	public void setCmsFolderId(CmsFolder cmsFolderId) {
		this.cmsFolderId = cmsFolderId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Long getFilesize() {
		return filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static CmsFile fromJsonToCmsFile(String json) {
		return new JSONDeserializer<CmsFile>().use(null, CmsFile.class).deserialize(json);
	}

	public static String toJsonArray(Collection<CmsFile> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<CmsFile> fromJsonArrayToCmsFiles(String json) {
		return new JSONDeserializer<List<CmsFile>>().use(null, ArrayList.class).use("values", CmsFile.class)
				.deserialize(json);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "CmsFile_solrsummary_t:" + queryString;
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

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexCmsFile(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new CmsFile().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}
}
