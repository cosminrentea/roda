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

@Entity
@Table(schema = "public", name = "instance_right")
@Configurable
public class InstanceRight {

	public static long countInstanceRights() {
		return entityManager().createQuery(
				"SELECT COUNT(o) FROM InstanceRight o", Long.class)
				.getSingleResult();
	}

	@Async
	public static void deleteIndex(InstanceRight instanceRight) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("instanceright_" + instanceRight.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new InstanceRight().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<InstanceRight> findAllInstanceRights() {
		return entityManager().createQuery("SELECT o FROM InstanceRight o",
				InstanceRight.class).getResultList();
	}

	public static InstanceRight findInstanceRight(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(InstanceRight.class, id);
	}

	public static List<InstanceRight> findInstanceRightEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM InstanceRight o",
						InstanceRight.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<InstanceRight> fromJsonArrayToInstanceRights(
			String json) {
		return new JSONDeserializer<List<InstanceRight>>()
				.use(null, ArrayList.class).use("values", InstanceRight.class)
				.deserialize(json);
	}

	public static InstanceRight fromJsonToInstanceRight(String json) {
		return new JSONDeserializer<InstanceRight>().use(null,
				InstanceRight.class).deserialize(json);
	}

	public static void indexInstanceRight(InstanceRight instanceRight) {
		List<InstanceRight> instancerights = new ArrayList<InstanceRight>();
		instancerights.add(instanceRight);
		indexInstanceRights(instancerights);
	}

	@Async
	public static void indexInstanceRights(
			Collection<InstanceRight> instancerights) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (InstanceRight instanceRight : instancerights) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "instanceright_" + instanceRight.getId());
			sid.addField("instanceRight.id_i", instanceRight.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("instanceright_solrsummary_t",
					new StringBuilder().append(instanceRight.getId()));
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
		String searchString = "InstanceRight_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new InstanceRight().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<InstanceRight> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui drept de instanta in baza de date; in caz
	 * afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce
	 * dreptul de instanta in baza de date si apoi returneaza obiectul
	 * corespunzator. Verificarea existentei in baza de date se realizeaza fie
	 * dupa valoarea identificatorului, fie dupa un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>id
	 * <li>name
	 * <ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul dreptului de instanta.
	 * @param name
	 *            - numele dreptului de instanta.
	 * @param description
	 *            - descrierea dreptului de instanta.
	 * @return
	 */
	public static InstanceRight checkInstanceRight(Integer id, String name,
			String description) {
		// TODO
		return null;
	}

	@Column(name = "description", columnDefinition = "text")
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@OneToMany(mappedBy = "instanceRightId")
	private Set<InstanceRightTargetGroup> instanceRightTargetGroups;

	@OneToMany(mappedBy = "instanceRightId")
	private Set<InstanceRightValue> instanceRightValues;

	@Column(name = "name", columnDefinition = "text")
	@NotNull
	private String name;

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

	public String getDescription() {
		return description;
	}

	public Integer getId() {
		return this.id;
	}

	public Set<InstanceRightTargetGroup> getInstanceRightTargetGroups() {
		return instanceRightTargetGroups;
	}

	public Set<InstanceRightValue> getInstanceRightValues() {
		return instanceRightValues;
	}

	public String getName() {
		return name;
	}

	@Transactional
	public InstanceRight merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		InstanceRight merged = this.entityManager.merge(this);
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
			InstanceRight attached = InstanceRight.findInstanceRight(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setInstanceRightTargetGroups(
			Set<InstanceRightTargetGroup> instanceRightTargetGroups) {
		this.instanceRightTargetGroups = instanceRightTargetGroups;
	}

	public void setInstanceRightValues(
			Set<InstanceRightValue> instanceRightValues) {
		this.instanceRightValues = instanceRightValues;
	}

	public void setName(String name) {
		this.name = name;
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
		indexInstanceRight(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
