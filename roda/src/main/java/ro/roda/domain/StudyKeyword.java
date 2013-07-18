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
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "study_keyword")

public class StudyKeyword {

	public static long countStudyKeywords() {
		return entityManager().createQuery("SELECT COUNT(o) FROM StudyKeyword o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(StudyKeyword studyKeyword) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("studykeyword_" + studyKeyword.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new StudyKeyword().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<StudyKeyword> findAllStudyKeywords() {
		return entityManager().createQuery("SELECT o FROM StudyKeyword o", StudyKeyword.class).getResultList();
	}

	public static StudyKeyword findStudyKeyword(StudyKeywordPK id) {
		if (id == null)
			return null;
		return entityManager().find(StudyKeyword.class, id);
	}

	public static List<StudyKeyword> findStudyKeywordEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM StudyKeyword o", StudyKeyword.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<StudyKeyword> fromJsonArrayToStudyKeywords(String json) {
		return new JSONDeserializer<List<StudyKeyword>>().use(null, ArrayList.class).use("values", StudyKeyword.class)
				.deserialize(json);
	}

	public static StudyKeyword fromJsonToStudyKeyword(String json) {
		return new JSONDeserializer<StudyKeyword>().use(null, StudyKeyword.class).deserialize(json);
	}

	public static void indexStudyKeyword(StudyKeyword studyKeyword) {
		List<StudyKeyword> studykeywords = new ArrayList<StudyKeyword>();
		studykeywords.add(studyKeyword);
		indexStudyKeywords(studykeywords);
	}

	@Async
	public static void indexStudyKeywords(Collection<StudyKeyword> studykeywords) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (StudyKeyword studyKeyword : studykeywords) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "studykeyword_" + studyKeyword.getId());
			sid.addField("studyKeyword.keywordid_t", studyKeyword.getKeywordId());
			sid.addField("studyKeyword.studyid_t", studyKeyword.getStudyId());
			sid.addField("studyKeyword.addedby_t", studyKeyword.getAddedBy());
			sid.addField("studyKeyword.added_dt", studyKeyword.getAdded().getTime());
			sid.addField("studyKeyword.id_t", studyKeyword.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"studykeyword_solrsummary_t",
					new StringBuilder().append(studyKeyword.getKeywordId()).append(" ")
							.append(studyKeyword.getStudyId()).append(" ").append(studyKeyword.getAddedBy())
							.append(" ").append(studyKeyword.getAdded().getTime()).append(" ")
							.append(studyKeyword.getId()));
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
		String searchString = "StudyKeyword_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new StudyKeyword().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<StudyKeyword> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "added", columnDefinition = "timestamp")
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Calendar added;

	@ManyToOne
	@JoinColumn(name = "added_by", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Users addedBy;

	@EmbeddedId
	private StudyKeywordPK id;

	@ManyToOne
	@JoinColumn(name = "keyword_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Keyword keywordId;

	@ManyToOne
	@JoinColumn(name = "study_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Study studyId;

	@PersistenceContext
	transient EntityManager entityManager;

	@Autowired(required=false)
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

	public Users getAddedBy() {
		return addedBy;
	}

	public StudyKeywordPK getId() {
		return this.id;
	}

	public Keyword getKeywordId() {
		return keywordId;
	}

	public Study getStudyId() {
		return studyId;
	}

	@Transactional
	public StudyKeyword merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		StudyKeyword merged = this.entityManager.merge(this);
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
			StudyKeyword attached = StudyKeyword.findStudyKeyword(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAdded(Calendar added) {
		this.added = added;
	}

	public void setAddedBy(Users addedBy) {
		this.addedBy = addedBy;
	}

	public void setId(StudyKeywordPK id) {
		this.id = id;
	}

	public void setKeywordId(Keyword keywordId) {
		this.keywordId = keywordId;
	}

	public void setStudyId(Study studyId) {
		this.studyId = studyId;
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
		indexStudyKeyword(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
