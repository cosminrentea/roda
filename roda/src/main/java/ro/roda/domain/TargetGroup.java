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
@Table(schema = "public", name = "target_group")
@Configurable

public class TargetGroup {

	public static long countTargetGroups() {
		return entityManager().createQuery("SELECT COUNT(o) FROM TargetGroup o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(TargetGroup targetGroup) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("targetgroup_" + targetGroup.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new TargetGroup().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<TargetGroup> findAllTargetGroups() {
		return entityManager().createQuery("SELECT o FROM TargetGroup o", TargetGroup.class).getResultList();
	}

	public static TargetGroup findTargetGroup(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(TargetGroup.class, id);
	}

	public static List<TargetGroup> findTargetGroupEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM TargetGroup o", TargetGroup.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<TargetGroup> fromJsonArrayToTargetGroups(String json) {
		return new JSONDeserializer<List<TargetGroup>>().use(null, ArrayList.class).use("values", TargetGroup.class)
				.deserialize(json);
	}

	public static TargetGroup fromJsonToTargetGroup(String json) {
		return new JSONDeserializer<TargetGroup>().use(null, TargetGroup.class).deserialize(json);
	}

	public static void indexTargetGroup(TargetGroup targetGroup) {
		List<TargetGroup> targetgroups = new ArrayList<TargetGroup>();
		targetgroups.add(targetGroup);
		indexTargetGroups(targetgroups);
	}

	@Async
	public static void indexTargetGroups(Collection<TargetGroup> targetgroups) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (TargetGroup targetGroup : targetgroups) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "targetgroup_" + targetGroup.getId());
			sid.addField("targetGroup.name_s", targetGroup.getName());
			sid.addField("targetGroup.id_i", targetGroup.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("targetgroup_solrsummary_t", new StringBuilder().append(targetGroup.getName()).append(" ")
					.append(targetGroup.getId()));
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
		String searchString = "TargetGroup_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new TargetGroup().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<TargetGroup> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui grup tinta pentru drepturi in baza de date; in
	 * caz afirmativ, returneaza obiectul corespunzator, altfel, metoda
	 * introduce grupul tinta in baza de date si apoi returneaza obiectul
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
	 *            - identificatorul grupului tinta.
	 * @param name
	 *            - numele grupului tinta.
	 * @return
	 */
	public static TargetGroup checkTargetGroup(Integer id, String name) {
		//TODO
		return null;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@OneToMany(mappedBy = "targetGroupId")
	private Set<InstanceRightTargetGroup> instanceRightTargetGroups;

	@Column(name = "name", columnDefinition = "text")
	@NotNull
	private String name;

	@PersistenceContext
	transient EntityManager entityManager;

	@Autowired(required=false)
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

	public Integer getId() {
		return this.id;
	}

	public Set<InstanceRightTargetGroup> getInstanceRightTargetGroups() {
		return instanceRightTargetGroups;
	}

	public String getName() {
		return name;
	}

	@Transactional
	public TargetGroup merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		TargetGroup merged = this.entityManager.merge(this);
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
			TargetGroup attached = TargetGroup.findTargetGroup(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setInstanceRightTargetGroups(Set<InstanceRightTargetGroup> instanceRightTargetGroups) {
		this.instanceRightTargetGroups = instanceRightTargetGroups;
	}

	public void setName(String name) {
		this.name = name;
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
		indexTargetGroup(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
