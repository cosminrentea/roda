package ro.roda.scheduler;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
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

@Entity
@Table(schema = "public", name = "sched_execution")
@Configurable
public class Execution {

	public static long countExecutions() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Execution o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Execution execution) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("Execution_" + execution.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Execution().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Execution> findAllExecutions() {
		return entityManager().createQuery("SELECT o FROM Execution o", Execution.class).getResultList();
	}

	public static Execution findExecution(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Execution.class, id);
	}

	public static List<Execution> findExecutionEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Execution o", Execution.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<Execution> fromJsonArrayToExecutions(String json) {
		return new JSONDeserializer<List<Execution>>().use(null, ArrayList.class).use("values", Execution.class)
				.deserialize(json);
	}

	public static Execution fromJsonToExecution(String json) {
		return new JSONDeserializer<Execution>().use(null, Execution.class).deserialize(json);
	}

	public static void indexExecution(Execution execution) {
		List<Execution> Executions = new ArrayList<Execution>();
		Executions.add(execution);
		indexExecutions(Executions);
	}

	@Async
	public static void indexExecutions(Collection<Execution> Executions) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Execution execution : Executions) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "Execution_" + execution.getId());
			sid.addField("Execution.cmsfolderid_t", execution.getTask());
			sid.addField("Execution.id_i", execution.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			/*
			 * sid.addField( "Execution_solrsummary_t", new
			 * StringBuilder().append
			 * (execution.getTask()).append(" ").append(execution.getFilename())
			 * .
			 * append(" ").append(execution.getLabel()).append(" ").append(execution
			 * .getFilesize()) .append(" ").append(execution.getId()));
			 */
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
		String searchString = "Execution_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Execution().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Execution> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "task_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false)
	private Task task;

	@Column(name = "exec_type", columnDefinition = "text")
	@NotNull
	private String execType;

	@Column(name = "exec_started", columnDefinition = "timestamp")
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Calendar execStarted;

	@Column(name = "exec_result", columnDefinition = "int8")
	private Long result;

	// TODO longer varchar ?
	@Column(name = "stacktrace", columnDefinition = "varchar", length = 200)
	private String stacktrace;

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

	public Task getTask() {
		return task;
	}

	public Integer getId() {
		return this.id;
	}

	@Transactional
	public Execution merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Execution merged = this.entityManager.merge(this);
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
			Execution attached = Execution.findExecution(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setCmsFolderId(Task task) {
		this.task = task;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getExecType() {
		return execType;
	}

	public void setExecType(String execType) {
		this.execType = execType;
	}

	public Calendar getExecStarted() {
		return execStarted;
	}

	public void setExecStarted(Calendar execStarted) {
		this.execStarted = execStarted;
	}

	public Long getResult() {
		return result;
	}

	public void setResult(Long result) {
		this.result = result;
	}

	public String getStacktrace() {
		return stacktrace;
	}

	public void setStacktrace(String stacktrace) {
		this.stacktrace = stacktrace;
	}

	public void setTask(Task task) {
		this.task = task;
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
		indexExecution(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Execution) {
			final Execution other = (Execution) obj;
			return new EqualsBuilder().append(task, other.task).append(execType, other.execType)
					.append(execStarted, other.execStarted).append(result, other.result).isEquals();
		} else {
			return false;
		}
	}
}
