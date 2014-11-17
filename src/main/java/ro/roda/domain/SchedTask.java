package ro.roda.domain;

import java.util.ArrayList;
import java.util.Calendar;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(schema = "public", name = "sched_task")
@Configurable
public class SchedTask {

	public static long countTasks() {
		return entityManager().createQuery("SELECT COUNT(o) FROM SchedTask o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(SchedTask schedTask) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("Task_" + schedTask.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new SchedTask().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<SchedTask> findAllTasks() {
		return entityManager().createQuery("SELECT o FROM SchedTask o", SchedTask.class).getResultList();
	}

	public static SchedTask findTask(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(SchedTask.class, id);
	}

	public static List<SchedTask> findTaskEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM SchedTask o", SchedTask.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<SchedTask> fromJsonArrayToTasks(String json) {
		return new JSONDeserializer<List<SchedTask>>().use(null, ArrayList.class).use("values", SchedTask.class)
				.deserialize(json);
	}

	public static SchedTask fromJsonToTask(String json) {
		return new JSONDeserializer<SchedTask>().use(null, SchedTask.class).deserialize(json);
	}

	public static void indexTask(SchedTask schedTask) {
		List<SchedTask> SchedTasks = new ArrayList<SchedTask>();
		SchedTasks.add(schedTask);
		indexTasks(SchedTasks);
	}

	@Async
	public static void indexTasks(Collection<SchedTask> SchedTasks) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (SchedTask schedTask : SchedTasks) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "Task_" + schedTask.getId());
			sid.addField("Task.filename_s", schedTask.getFilename());
			sid.addField("Task.label_s", schedTask.getDescription());
			sid.addField("Task.id_i", schedTask.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			/*
			 * sid.addField("Task_solrsummary_t", new
			 * StringBuilder().append(task
			 * .getCmsFolderId()).append(" ").append(task.getFilename())
			 * .append(
			 * " ").append(task.getLabel()).append(" ").append(task.getFilesize
			 * ()).append(" ") .append(task.getId()));
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
		String searchString = "Task_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new SchedTask().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<SchedTask> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	@OneToMany(mappedBy = "schedTask")
	private Set<SchedExecution> schedExecutions;

	/*
	 * "id" : "1", "name" : "Vacuum", "description" :
	 * "Task for Postgresql VACUUM", "iconCls" : "task", "classname" :
	 * "ro.roda.scheduler.tasks.Vacuum", // "cron" : "timestamp_next_execution":
	 * "2013-04-03 13:00:00", "enabled" : true,
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "text")
	@NotNull
	private String name;

	@Column(name = "classname", columnDefinition = "varchar", length = 200)
	private String classname;

	@Column(name = "cron", columnDefinition = "varchar", length = 300)
	private String cron;

	@Transient
	private Calendar nextExecution;

	@Column(name = "description", columnDefinition = "varchar", length = 200)
	private String description;

	@Column(name = "enabled", columnDefinition = "bool")
	@NotNull
	private boolean enabled;

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

	public String getFilename() {
		return name;
	}

	public Integer getId() {
		return this.id;
	}

	public String getDescription() {
		return description;
	}

	@Transactional
	public SchedTask merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		SchedTask merged = this.entityManager.merge(this);
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
			SchedTask attached = SchedTask.findTask(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setFilename(String filename) {
		this.name = filename;
	}

	public Set<SchedExecution> getExecutions() {
		return schedExecutions;
	}

	public void setExecutions(Set<SchedExecution> schedExecutions) {
		this.schedExecutions = schedExecutions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Calendar getNextExecution() {
		// TODO implement "next execution" timestamp
		return nextExecution;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexTask(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SchedTask) {
			final SchedTask other = (SchedTask) obj;
			return new EqualsBuilder().append(name, other.name).append(classname, other.classname)
					.append(cron, other.cron).append(enabled, other.enabled).isEquals();
		} else {
			return false;
		}
	}
}
