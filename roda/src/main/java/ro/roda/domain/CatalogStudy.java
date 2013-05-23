package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(schema = "public", name = "catalog_study")
@Configurable
public class CatalogStudy {

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static CatalogStudy fromJsonToCatalogStudy(String json) {
		return new JSONDeserializer<CatalogStudy>().use(null, CatalogStudy.class).deserialize(json);
	}

	public static String toJsonArray(Collection<CatalogStudy> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<CatalogStudy> fromJsonArrayToCatalogStudys(String json) {
		return new JSONDeserializer<List<CatalogStudy>>().use(null, ArrayList.class).use("values", CatalogStudy.class)
				.deserialize(json);
	}

	@ManyToOne
	@JoinColumn(name = "catalog_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Catalog catalogId;

	@ManyToOne
	@JoinColumn(name = "study_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Study studyId;

	@Column(name = "added", columnDefinition = "timestamp")
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Calendar added;

	public Catalog getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Catalog catalogId) {
		this.catalogId = catalogId;
	}

	public Study getStudyId() {
		return studyId;
	}

	public void setStudyId(Study studyId) {
		this.studyId = studyId;
	}

	public Calendar getAdded() {
		return added;
	}

	public void setAdded(Calendar added) {
		this.added = added;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "CatalogStudy_solrsummary_t:" + queryString;
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

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexCatalogStudy(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new CatalogStudy().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new CatalogStudy().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countCatalogStudys() {
		return entityManager().createQuery("SELECT COUNT(o) FROM CatalogStudy o", Long.class).getSingleResult();
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
	public CatalogStudy merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		CatalogStudy merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	@EmbeddedId
	private CatalogStudyPK id;

	public CatalogStudyPK getId() {
		return this.id;
	}

	public void setId(CatalogStudyPK id) {
		this.id = id;
	}
}
