package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
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
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "study_documents")
@Audited
public class StudyDocuments {

	public static long countStudyDocumentses() {
		return entityManager().createQuery("SELECT COUNT(o) FROM StudyDocuments o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(StudyDocuments studyDocuments) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("studydocuments_" + studyDocuments.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new StudyDocuments().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<StudyDocuments> findAllStudyDocumentses() {
		return entityManager().createQuery("SELECT o FROM StudyDocuments o", StudyDocuments.class).getResultList();
	}

	public static StudyDocuments findStudyDocuments(StudyDocumentsPK id) {
		if (id == null)
			return null;
		return entityManager().find(StudyDocuments.class, id);
	}

	public static List<StudyDocuments> findStudyDocumentsEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM StudyDocuments o", StudyDocuments.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<StudyDocuments> fromJsonArrayToStudyDocumentses(String json) {
		return new JSONDeserializer<List<StudyDocuments>>().use(null, ArrayList.class)
				.use("values", StudyDocuments.class).deserialize(json);
	}

	public static StudyDocuments fromJsonToStudyDocuments(String json) {
		return new JSONDeserializer<StudyDocuments>().use(null, StudyDocuments.class).deserialize(json);
	}

	public static void indexStudyDocuments(StudyDocuments studyDocuments) {
		List<StudyDocuments> studydocumentses = new ArrayList<StudyDocuments>();
		studydocumentses.add(studyDocuments);
		indexStudyDocumentses(studydocumentses);
	}

	@Async
	public static void indexStudyDocumentses(Collection<StudyDocuments> studydocumentses) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (StudyDocuments studyDocuments : studydocumentses) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "studydocuments_" + studyDocuments.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("studydocuments_solrsummary_t", new StringBuilder());
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
		String searchString = "StudyDocuments_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new StudyDocuments().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<StudyDocuments> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@EmbeddedId
	private StudyDocumentsPK id;

	@PersistenceContext
	transient EntityManager entityManager;

	@Autowired
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

	public StudyDocumentsPK getId() {
		return this.id;
	}

	@Transactional
	public StudyDocuments merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		StudyDocuments merged = this.entityManager.merge(this);
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
			StudyDocuments attached = StudyDocuments.findStudyDocuments(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(StudyDocumentsPK id) {
		this.id = id;
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
		indexStudyDocuments(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
