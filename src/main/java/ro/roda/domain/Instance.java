package ro.roda.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
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

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "instance")
@Audited
public class Instance {

	public static long countInstances() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Instance o", Long.class).getSingleResult();
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

	public static final EntityManager entityManager() {
		EntityManager em = new Instance().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
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

	public static Collection<Instance> fromJsonArrayToInstances(String json) {
		return new JSONDeserializer<List<Instance>>().use(null, ArrayList.class).use("values", Instance.class)
				.deserialize(json);
	}

	public static Instance fromJsonToInstance(String json) {
		return new JSONDeserializer<Instance>().use(null, Instance.class).deserialize(json);
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

	public static QueryResponse search(SolrQuery query) {
		try {
			return solrServer().query(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new QueryResponse();
	}

	public static QueryResponse search(String queryString) {
		String searchString = "Instance_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Instance().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Instance> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>Instance</code> (instanta) in
	 * baza de date; in caz afirmativ il returneaza, altfel, metoda il introduce
	 * in baza de date si apoi il returneaza. Verificarea existentei in baza de
	 * date se realizeaza fie dupa identificator, fie dupa un criteriu de
	 * unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul instantei.
	 * @param studyId
	 *            - studiul din care face parte instanta.
	 * @param addedBy
	 *            - utilizatorul care a creat instanta.
	 * @param added
	 *            - data cand a fost adaugata instanta.
	 * @param main
	 * @param disseminatorIdentifier
	 * @return
	 */
	public static Instance checkInstance(Integer id, Study studyId, Users addedBy, Calendar added, Boolean main,
			String disseminatorIdentifier) {
		Instance object;

		if (id != null) {
			object = findInstance(id);

			if (object != null) {
				return object;
			}
		}

		object = new Instance();
		object.studyId = studyId;
		object.addedBy = addedBy;
		object.added = added;
		object.main = main;
		object.disseminatorIdentifier = disseminatorIdentifier;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	// @OneToMany(mappedBy = "instanceId", fetch = FetchType.LAZY)
	// private Set<Question> questions;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "serial")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "study_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false)
	private Study studyId;

	@Column(name = "main", columnDefinition = "bool")
	@NotNull
	private boolean main;

	@Column(name = "added", columnDefinition = "timestamp")
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Calendar added;

	@ManyToOne
	@JoinColumn(name = "added_by", columnDefinition = "integer", referencedColumnName = "id", nullable = false)
	private Users addedBy;

	@Column(name = "disseminator_identifier", columnDefinition = "text")
	private String disseminatorIdentifier;

	@ManyToMany(mappedBy = "instances", fetch = FetchType.LAZY)
	private Set<Question> questions;

	// @OneToMany(mappedBy = "instanceId", fetch = FetchType.LAZY)
	// private Set<Question> questions;

	@ManyToMany(mappedBy = "instances", fetch = FetchType.LAZY)
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

	public Users getAddedBy() {
		return addedBy;
	}

	public String getDisseminatorIdentifier() {
		return disseminatorIdentifier;
	}

	public Set<File> getFiles() {
		return files;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public Integer getId() {
		return this.id;
	}

	public Set<InstanceDescr> getInstanceDescrs() {
		return instanceDescrs;
	}

	public Set<InstanceForm> getInstanceForms() {
		return instanceForms;
	}

	public Set<InstanceOrg> getInstanceOrgs() {
		return instanceOrgs;
	}

	public Set<InstancePerson> getInstancepeople() {
		return instancepeople;
	}

	public Set<InstanceRightTargetGroup> getInstanceRightTargetGroups() {
		return instanceRightTargetGroups;
	}

	public Study getStudyId() {
		return studyId;
	}

	public boolean isMain() {
		return main;
	}

	@Transactional
	public Instance merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Instance merged = this.entityManager.merge(this);
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
			Instance attached = Instance.findInstance(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAdded(Calendar added) {
		this.added = added;
	}

	public void setAddedBy(Users addedBy) {
		this.addedBy = addedBy;
	}

	public void setDisseminatorIdentifier(String disseminatorIdentifier) {
		this.disseminatorIdentifier = disseminatorIdentifier;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setInstanceDescrs(Set<InstanceDescr> instanceDescrs) {
		this.instanceDescrs = instanceDescrs;
	}

	public void setInstanceForms(Set<InstanceForm> instanceForms) {
		this.instanceForms = instanceForms;
	}

	public void setInstanceOrgs(Set<InstanceOrg> instanceOrgs) {
		this.instanceOrgs = instanceOrgs;
	}

	public void setInstancepeople(Set<InstancePerson> instancepeople) {
		this.instancepeople = instancepeople;
	}

	public void setInstanceRightTargetGroups(Set<InstanceRightTargetGroup> instanceRightTargetGroups) {
		this.instanceRightTargetGroups = instanceRightTargetGroups;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

	public void setStudyId(Study studyId) {
		this.studyId = studyId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	// public String toString() {
	// return ReflectionToStringBuilder.toString(this,
	// ToStringStyle.SHORT_PREFIX_STYLE);
	// }

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexInstance(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addedBy == null) ? 0 : addedBy.hashCode());
		result = prime * result + ((disseminatorIdentifier == null) ? 0 : disseminatorIdentifier.hashCode());
		result = prime * result + (main ? 1231 : 1237);
		result = prime * result + ((studyId == null) ? 0 : studyId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Instance other = (Instance) obj;
		if (addedBy == null) {
			if (other.addedBy != null)
				return false;
		} else if (!addedBy.equals(other.addedBy))
			return false;
		if (disseminatorIdentifier == null) {
			if (other.disseminatorIdentifier != null)
				return false;
		} else if (!disseminatorIdentifier.equals(other.disseminatorIdentifier))
			return false;
		if (main != other.main)
			return false;
		if (studyId == null) {
			if (other.studyId != null)
				return false;
		} else if (!studyId.equals(other.studyId))
			return false;
		return true;
	}

	// @Override
	// public boolean equals(Object obj) {
	// return id != null && id.equals(((Instance) obj).id);
	// }

	@JsonIgnore public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
