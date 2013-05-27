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
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "keyword")
@Audited
public class Keyword {

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "Keyword_solrsummary_t:" + queryString;
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

	public static void indexKeyword(Keyword keyword) {
		List<Keyword> keywords = new ArrayList<Keyword>();
		keywords.add(keyword);
		indexKeywords(keywords);
	}

	@Async
	public static void indexKeywords(Collection<Keyword> keywords) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Keyword keyword : keywords) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "keyword_" + keyword.getId());
			sid.addField("keyword.name_s", keyword.getName());
			sid.addField("keyword.id_i", keyword.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("keyword_solrsummary_t",
					new StringBuilder().append(keyword.getName()).append(" ").append(keyword.getId()));
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
	public static void deleteIndex(Keyword keyword) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("keyword_" + keyword.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexKeyword(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Keyword().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
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

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static Keyword fromJsonToKeyword(String json) {
		return new JSONDeserializer<Keyword>().use(null, Keyword.class).deserialize(json);
	}

	public static String toJsonArray(Collection<Keyword> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<Keyword> fromJsonArrayToKeywords(String json) {
		return new JSONDeserializer<List<Keyword>>().use(null, ArrayList.class).use("values", Keyword.class)
				.deserialize(json);
	}

	@OneToMany(mappedBy = "keywordId")
	private Set<StudyKeyword> studyKeywords;

	@Column(name = "name", columnDefinition = "text")
	@NotNull
	private String name;

	public Set<StudyKeyword> getStudyKeywords() {
		return studyKeywords;
	}

	public void setStudyKeywords(Set<StudyKeyword> studyKeywords) {
		this.studyKeywords = studyKeywords;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new Keyword().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countKeywords() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Keyword o", Long.class).getSingleResult();
	}

	public static List<Keyword> findAllKeywords() {
		return entityManager().createQuery("SELECT o FROM Keyword o", Keyword.class).getResultList();
	}

	public static Keyword findKeyword(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Keyword.class, id);
	}

	public static List<Keyword> findKeywordEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Keyword o", Keyword.class).setFirstResult(firstResult)
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
			Keyword attached = Keyword.findKeyword(this.id);
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
	public Keyword merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Keyword merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}
}
