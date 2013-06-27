package ro.roda.domain;

import java.sql.Timestamp;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "instance")
public class Instance {

	public static long countInstances() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Instance o",
				Long.class).getSingleResult();
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
		return entityManager().createQuery("SELECT o FROM Instance o",
				Instance.class).getResultList();
	}

	public static Instance findInstance(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Instance.class, id);
	}

	public static List<Instance> findInstanceEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM Instance o", Instance.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static Collection<Instance> fromJsonArrayToInstances(String json) {
		return new JSONDeserializer<List<Instance>>()
				.use(null, ArrayList.class).use("values", Instance.class)
				.deserialize(json);
	}

	public static Instance fromJsonToInstance(String json) {
		return new JSONDeserializer<Instance>().use(null, Instance.class)
				.deserialize(json);
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
			sid.addField("instance.disseminatoridentifier_s",
					instance.getDisseminatorIdentifier());
			sid.addField("instance.id_i", instance.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"instance_solrsummary_t",
					new StringBuilder().append(instance.getStudyId())
							.append(" ").append(instance.getAddedBy())
							.append(" ").append(instance.getAdded().getTime())
							.append(" ")
							.append(instance.getDisseminatorIdentifier())
							.append(" ").append(instance.getId()));
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
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unei instante (preluata prin valori ale parametrilor
	 * de intrare sau combinatii ale acestora) in baza de date; in caz
	 * afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce
	 * instanta in baza de date si apoi returneaza obiectul corespunzator.
	 * Verificarea existentei in baza de date se realizeaza fie dupa valoarea
	 * cheii primare, fie dupa un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <p>
	 * <ul>
	 * <li>instanceId
	 * <ul>
	 * <p>
	 * 
	 * 
	 * @param instance_id
	 *            - cheia primara a instantei din tabelul de instante
	 * @param studyId
	 *            - cheia primara a studiului din care face parte instanta
	 * @param datestart
	 *            - data de inceput a desfasurarii instantei curente
	 * @param dateend
	 *            - data de final a desfasurarii instantei curente
	 * @param added_by
	 *            - cheia primara a utilizatorului caruia ii apartine instanta
	 *            curenta
	 * @param added
	 *            - data si timpul la care a fost adaugata instanta curenta in
	 *            baza de date
	 * @param version
	 *            - versiunea instantei
	 * @param unit_analysis
	 *            - denumirea unitatii de analiza specifice instantei curente;
	 *            daca unitatea respectiva nu exista, va fi introdusa in baza de
	 *            date.
	 * @param time_meth
	 *            - denumirea metodei temporale care se aplica instantei
	 *            curente; daca metoda temporala respectiva nu exista, va fi
	 *            introdusa in baza de date.
	 * @param insertion_status
	 *            - pasul din wizardul de introducere a metadatelor; atunci cand
	 *            introducerea se face prin wizard, fiecare pas trebuie salvat
	 *            in baza de date.
	 * @param raw_data
	 *            - parametru boolean ce indica daca datele sunt in forma
	 *            digitizata (true) sau in forma de fisiere
	 *            procesabile/editabile (false)
	 * @param raw_metadata
	 *            - parametru boolean ce indica daca metadatele sunt in forma
	 *            digitizata (true) sau in forma de fisiere
	 *            procesabile/editabile (false)
	 * @param orgs
	 *            - lista de organizatii aflate in relatie cu instanta curenta;
	 *            un element al acestei liste contine o organizatie si codul
	 *            asocierii care exista cu organizatia respectiva. O organizatie
	 *            poate fi specificata atat prin codul sau, cat si prin
	 *            informatiile complete ale acesteia. La randul ei, daca
	 *            organizatia nu exista, va fi introdusa in baza de date.
	 * @param persons
	 *            - lista de persoane aflate in relatie cu instanta curenta; un
	 *            element al acestei liste contine o persoana si codul asocierii
	 *            care exista cu persoana respectiva. O persoana poate fi
	 *            specificata atat prin codul sau, cat si prin informatiile
	 *            complete ale acesteia. La randul ei, daca persoana nu exista,
	 *            va fi introdusa in baza de date.
	 * @param topics
	 *            - lista de topicuri asociate instantei curente. Un topic poate
	 *            fi specificat atat prin codul sau, cat si prin denumirea sa.
	 *            La randul lui, daca topicul nu exista, va fi introdus in baza
	 *            de date.
	 * @param keywords
	 *            - lista de cuvinte cheie asociate instantei curente. Un cuvant
	 *            cheie poate fi specificat atat prin codul sau, cat si prin
	 *            denumirea sa. La randul lui, daca nu exista cuvantul cheie, va
	 *            fi introdus in baza de date.
	 * @param variables
	 *            - lista de variabile asociate instantei curente; un element al
	 *            acestei liste contine informatiile specifice unei variabile.
	 *            La randul ei, variabila va fi introdusa in baza de date.
	 * @param forms
	 *            - lista de formulare asociate instantei curente; un element al
	 *            acestei liste contine informatiile specifice unui formular. La
	 *            randul lui, formularul va fi introdus in baza de date.
	 * 
	 * @return
	 */
	public static Instance checkInstance(Integer instanceId, Integer studyId,
			Calendar dateStart, Calendar dateEnd, Integer addedBy,
			Timestamp added, Integer version, String unitAnalysis,
			String timeMeth, Integer insertionStatus, Boolean rawData,
			Boolean rawMetaData, List<Org> orgs, List<Person> persons,
			List<Topic> topics, List<Keyword> keywords,
			List<Variable> variables, List<Form> forms) {
		// TODO
		return null;
	}

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

	@ManyToMany(mappedBy = "instances")
	private Set<File> files;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

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

	@Column(name = "main", columnDefinition = "bool")
	@NotNull
	private boolean main;

	@ManyToOne
	@JoinColumn(name = "study_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false)
	private Study studyId;

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

	public Users getAddedBy() {
		return addedBy;
	}

	public String getDisseminatorIdentifier() {
		return disseminatorIdentifier;
	}

	public Set<File> getFiles() {
		return files;
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

	public Set<InstanceVariable> getInstanceVariables() {
		return instanceVariables;
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

	public void setInstanceRightTargetGroups(
			Set<InstanceRightTargetGroup> instanceRightTargetGroups) {
		this.instanceRightTargetGroups = instanceRightTargetGroups;
	}

	public void setInstanceVariables(Set<InstanceVariable> instanceVariables) {
		this.instanceVariables = instanceVariables;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

	public void setStudyId(Study studyId) {
		this.studyId = studyId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
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
}
