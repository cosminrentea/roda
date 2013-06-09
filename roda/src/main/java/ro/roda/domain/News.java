package ro.roda.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(schema = "public", name = "news")

public class News {

	public static long countNewspieces() {
		return entityManager().createQuery("SELECT COUNT(o) FROM News o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(News news) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("news_" + news.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new News().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<News> findAllNewspieces() {
		return entityManager().createQuery("SELECT o FROM News o", News.class).getResultList();
	}

	public static News findNews(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(News.class, id);
	}

	public static List<News> findNewsEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM News o", News.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<News> fromJsonArrayToNewspieces(String json) {
		return new JSONDeserializer<List<News>>().use(null, ArrayList.class).use("values", News.class)
				.deserialize(json);
	}

	public static News fromJsonToNews(String json) {
		return new JSONDeserializer<News>().use(null, News.class).deserialize(json);
	}

	public static void indexNews(News news) {
		List<News> newspieces = new ArrayList<News>();
		newspieces.add(news);
		indexNewspieces(newspieces);
	}

	@Async
	public static void indexNewspieces(Collection<News> newspieces) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (News news : newspieces) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "news_" + news.getId());
			sid.addField("news.added_dt", news.getAdded().getTime());
			sid.addField("news.title_s", news.getTitle());
			sid.addField("news.content_s", news.getContent());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("news_solrsummary_t", new StringBuilder().append(news.getAdded().getTime()).append(" ")
					.append(news.getTitle()).append(" ").append(news.getContent()));
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
		String searchString = "News_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new News().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<News> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "added", columnDefinition = "timestamp")
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Calendar added;

	@Column(name = "content", columnDefinition = "text")
	private String content;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@Column(name = "title", columnDefinition = "text")
	@NotNull
	private String title;

	@Column(name = "visible", columnDefinition = "bool")
	@NotNull
	private boolean visible;

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

	public Calendar getAdded() {
		return added;
	}

	public String getContent() {
		return content;
	}

	public Integer getId() {
		return this.id;
	}

	public String getTitle() {
		return title;
	}

	public boolean isVisible() {
		return visible;
	}

	@Transactional
	public News merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		News merged = this.entityManager.merge(this);
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
			News attached = News.findNews(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAdded(Calendar added) {
		this.added = added;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
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
		indexNews(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
