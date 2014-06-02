package ro.roda.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(schema = "public", name = "catalog_study")
@Configurable
@Audited
public class CatalogStudy {

	public static long countCatalogStudys() {
		return entityManager().createQuery("SELECT COUNT(o) FROM CatalogStudy o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(CatalogStudy catalogStudy) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("catalogstudy_" + catalogStudy.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new CatalogStudy().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<CatalogStudy> findAllCatalogStudys() {
		return entityManager().createQuery("SELECT o FROM CatalogStudy o", CatalogStudy.class).getResultList();
	}

	public static CatalogStudy findCatalogStudy(CatalogStudyPK id) {
		if (id == null)
			return null;
		return entityManager().find(CatalogStudy.class, id);
	}

	public static List<CatalogStudy> findCatalogStudyEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM CatalogStudy o", CatalogStudy.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<CatalogStudy> fromJsonArrayToCatalogStudys(String json) {
		return new JSONDeserializer<List<CatalogStudy>>().use(null, ArrayList.class).use("values", CatalogStudy.class)
				.deserialize(json);
	}

	public static CatalogStudy fromJsonToCatalogStudy(String json) {
		return new JSONDeserializer<CatalogStudy>().use(null, CatalogStudy.class).deserialize(json);
	}

	public static void indexCatalogStudy(CatalogStudy catalogStudy) {
		List<CatalogStudy> catalogstudys = new ArrayList<CatalogStudy>();
		catalogstudys.add(catalogStudy);
		indexCatalogStudys(catalogstudys);
	}

	@Async
	public static void indexCatalogStudys(Collection<CatalogStudy> catalogstudys) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (CatalogStudy catalogStudy : catalogstudys) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "catalogstudy_" + catalogStudy.getId());
			sid.addField("catalogStudy.catalogid_t", catalogStudy.getCatalogId());
			sid.addField("catalogStudy.studyid_t", catalogStudy.getStudyId());
			sid.addField("catalogStudy.added_dt", catalogStudy.getAdded().getTime());
			sid.addField("catalogStudy.id_t", catalogStudy.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"catalogstudy_solrsummary_t",
					new StringBuilder().append(catalogStudy.getCatalogId()).append(" ")
							.append(catalogStudy.getStudyId()).append(" ").append(catalogStudy.getAdded().getTime())
							.append(" ").append(catalogStudy.getId()));
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
		String searchString = "CatalogStudy_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new CatalogStudy().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<CatalogStudy> collection) {
		return new JSONSerializer().exclude("*.class").exclude("id", "classAuditReader", "auditReader")
				.serialize(collection);
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@Column(name = "added", columnDefinition = "timestamp default now()", insertable = false)
	// @NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	@Generated(GenerationTime.INSERT)
	private Calendar added;

	@ManyToOne
	@JoinColumn(name = "catalog_id", columnDefinition = "integer", referencedColumnName = "id", nullable = true, insertable = false, updatable = false)
	private Catalog catalogId;

	@EmbeddedId
	private CatalogStudyPK id;

	@ManyToOne
	@JoinColumn(name = "study_id", columnDefinition = "integer", referencedColumnName = "id", nullable = true, insertable = false, updatable = false)
	private Study studyId;

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

	public Calendar getAdded() {
		return added;
	}

	public Catalog getCatalogId() {
		return catalogId;
	}

	public CatalogStudyPK getId() {
		return this.id;
	}

	public Study getStudyId() {
		return studyId;
	}

	@Transactional
	public CatalogStudy merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		CatalogStudy merged = this.entityManager.merge(this);
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
			CatalogStudy attached = CatalogStudy.findCatalogStudy(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAdded(Calendar added) {
		this.added = added;
	}

	public void setCatalogId(Catalog catalogId) {
		this.catalogId = catalogId;
	}

	public void setId(CatalogStudyPK id) {
		this.id = id;
	}

	public void setStudyId(Study studyId) {
		this.studyId = studyId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("id", "classAuditReader", "auditReader").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexCatalogStudy(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	// @PrePersist
	// private void onCreate() {
	// added = Calendar.getInstance();
	// }

}
