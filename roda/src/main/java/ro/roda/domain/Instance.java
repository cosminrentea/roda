package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

@Configurable
@Entity
@Table(schema = "public", name = "instance")
public class Instance {

	@ManyToMany(mappedBy = "instances")
	private Set<File> files;

	@OneToMany(mappedBy = "instanceId")
	private Set<InstanceDescr> instanceDescrs;

	@OneToMany(mappedBy = "instanceId")
	private Set<InstanceForm> instanceForms;

	@OneToMany(mappedBy = "instanceId")
	private Set<InstanceOrg> instanceOrgs;

	@OneToMany(mappedBy = "instanceId")
	private Set<InstancePerson> instancepeople;

	@OneToMany(mappedBy = "instanceId")
	private Set<InstanceRightTargetGroup> instanceRightTargetGroups;

	@OneToMany(mappedBy = "instanceId")
	private Set<InstanceVariable> instanceVariables;

	@ManyToOne
	@JoinColumn(name = "study_id", referencedColumnName = "id", nullable = false)
	private Study studyId;

	@ManyToOne
	@JoinColumn(name = "added_by", referencedColumnName = "id", nullable = false)
	private Users addedBy;

	@Column(name = "added", columnDefinition = "timestamp")
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Calendar added;

	@Column(name = "disseminator_identifier", columnDefinition = "text")
	private String disseminatorIdentifier;

	@Column(name = "main", columnDefinition = "bool")
	@NotNull
	private boolean main;

	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}

	public Set<InstanceDescr> getInstanceDescrs() {
		return instanceDescrs;
	}

	public void setInstanceDescrs(Set<InstanceDescr> instanceDescrs) {
		this.instanceDescrs = instanceDescrs;
	}

	public Set<InstanceForm> getInstanceForms() {
		return instanceForms;
	}

	public void setInstanceForms(Set<InstanceForm> instanceForms) {
		this.instanceForms = instanceForms;
	}

	public Set<InstanceOrg> getInstanceOrgs() {
		return instanceOrgs;
	}

	public void setInstanceOrgs(Set<InstanceOrg> instanceOrgs) {
		this.instanceOrgs = instanceOrgs;
	}

	public Set<InstancePerson> getInstancepeople() {
		return instancepeople;
	}

	public void setInstancepeople(Set<InstancePerson> instancepeople) {
		this.instancepeople = instancepeople;
	}

	public Set<InstanceRightTargetGroup> getInstanceRightTargetGroups() {
		return instanceRightTargetGroups;
	}

	public void setInstanceRightTargetGroups(Set<InstanceRightTargetGroup> instanceRightTargetGroups) {
		this.instanceRightTargetGroups = instanceRightTargetGroups;
	}

	public Set<InstanceVariable> getInstanceVariables() {
		return instanceVariables;
	}

	public void setInstanceVariables(Set<InstanceVariable> instanceVariables) {
		this.instanceVariables = instanceVariables;
	}

	public Study getStudyId() {
		return studyId;
	}

	public void setStudyId(Study studyId) {
		this.studyId = studyId;
	}

	public Users getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(Users addedBy) {
		this.addedBy = addedBy;
	}

	public Calendar getAdded() {
		return added;
	}

	public void setAdded(Calendar added) {
		this.added = added;
	}

	public String getDisseminatorIdentifier() {
		return disseminatorIdentifier;
	}

	public void setDisseminatorIdentifier(String disseminatorIdentifier) {
		this.disseminatorIdentifier = disseminatorIdentifier;
	}

	public boolean isMain() {
		return main;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new Instance().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countInstances() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Instance o", Long.class).getSingleResult();
	}

	public static List<Instance> findAllInstances() {
		return entityManager().createQuery("SELECT o FROM Instance o", Instance.class).getResultList();
	}

	public static Instance findInstance(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Instance.class, id);
	}

	public static List<Instance> findInstanceEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Instance o", Instance.class).setFirstResult(firstResult)
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
			Instance attached = Instance.findInstance(this.id);
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
	public Instance merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Instance merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static Instance fromJsonToInstance(String json) {
		return new JSONDeserializer<Instance>().use(null, Instance.class).deserialize(json);
	}

	public static String toJsonArray(Collection<Instance> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<Instance> fromJsonArrayToInstances(String json) {
		return new JSONDeserializer<List<Instance>>().use(null, ArrayList.class).use("values", Instance.class)
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
		String searchString = "Instance_solrsummary_t:" + queryString;
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

	public static void indexInstance(Instance instance) {
		List<Instance> instances = new ArrayList<Instance>();
		instances.add(instance);
		indexInstances(instances);
	}

	@Async
	public static void indexInstances(Collection<Instance> instances) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Instance instance : instances) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "instance_" + instance.getId());
			sid.addField("instance.studyid_t", instance.getStudyId());
			sid.addField("instance.addedby_t", instance.getAddedBy());
			sid.addField("instance.added_dt", instance.getAdded().getTime());
			sid.addField("instance.disseminatoridentifier_s", instance.getDisseminatorIdentifier());
			sid.addField("instance.id_i", instance.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("instance_solrsummary_t", new StringBuilder().append(instance.getStudyId()).append(" ")
					.append(instance.getAddedBy()).append(" ").append(instance.getAdded().getTime()).append(" ")
					.append(instance.getDisseminatorIdentifier()).append(" ").append(instance.getId()));
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
	public static void deleteIndex(Instance instance) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("instance_" + instance.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexInstance(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Instance().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}
}
