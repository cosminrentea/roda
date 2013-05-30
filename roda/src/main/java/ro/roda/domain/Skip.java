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

@Entity
@Table(schema = "public", name = "skip")
@Configurable

public class Skip {

	public static long countSkips() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Skip o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Skip skip) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("skip_" + skip.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Skip().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Skip> findAllSkips() {
		return entityManager().createQuery("SELECT o FROM Skip o", Skip.class).getResultList();
	}

	public static Skip findSkip(Long id) {
		if (id == null)
			return null;
		return entityManager().find(Skip.class, id);
	}

	public static List<Skip> findSkipEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Skip o", Skip.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<Skip> fromJsonArrayToSkips(String json) {
		return new JSONDeserializer<List<Skip>>().use(null, ArrayList.class).use("values", Skip.class)
				.deserialize(json);
	}

	public static Skip fromJsonToSkip(String json) {
		return new JSONDeserializer<Skip>().use(null, Skip.class).deserialize(json);
	}

	public static void indexSkip(Skip skip) {
		List<Skip> skips = new ArrayList<Skip>();
		skips.add(skip);
		indexSkips(skips);
	}

	@Async
	public static void indexSkips(Collection<Skip> skips) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Skip skip : skips) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "skip_" + skip.getId());
			sid.addField("skip.id_l", skip.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("skip_solrsummary_t", new StringBuilder().append(skip.getId()));
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
		String searchString = "Skip_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Skip().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Skip> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "condition", columnDefinition = "text")
	@NotNull
	private String condition;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "bigserial")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "next_variable_id", referencedColumnName = "id", nullable = false)
	private Variable nextVariableId;

	@ManyToOne
	@JoinColumn(name = "variable_id", referencedColumnName = "id", nullable = false)
	private Variable variableId;

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

	public String getCondition() {
		return condition;
	}

	public Long getId() {
		return this.id;
	}

	public Variable getNextVariableId() {
		return nextVariableId;
	}

	public Variable getVariableId() {
		return variableId;
	}

	@Transactional
	public Skip merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Skip merged = this.entityManager.merge(this);
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
			Skip attached = Skip.findSkip(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNextVariableId(Variable nextVariableId) {
		this.nextVariableId = nextVariableId;
	}

	public void setVariableId(Variable variableId) {
		this.variableId = variableId;
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
		indexSkip(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
