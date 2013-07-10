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
@Table(schema = "public", name = "instance_person_assoc")
public class InstancePersonAssoc {

	public static long countInstancePersonAssocs() {
		return entityManager().createQuery(
				"SELECT COUNT(o) FROM InstancePersonAssoc o", Long.class)
				.getSingleResult();
	}

	@Async
	public static void deleteIndex(InstancePersonAssoc instancePersonAssoc) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("instancepersonassoc_"
					+ instancePersonAssoc.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new InstancePersonAssoc().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<InstancePersonAssoc> findAllInstancePersonAssocs() {
		return entityManager().createQuery(
				"SELECT o FROM InstancePersonAssoc o",
				InstancePersonAssoc.class).getResultList();
	}

	public static InstancePersonAssoc findInstancePersonAssoc(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(InstancePersonAssoc.class, id);
	}

	public static List<InstancePersonAssoc> findInstancePersonAssocEntries(
			int firstResult, int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM InstancePersonAssoc o",
						InstancePersonAssoc.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<InstancePersonAssoc> fromJsonArrayToInstancePersonAssocs(
			String json) {
		return new JSONDeserializer<List<InstancePersonAssoc>>()
				.use(null, ArrayList.class)
				.use("values", InstancePersonAssoc.class).deserialize(json);
	}

	public static InstancePersonAssoc fromJsonToInstancePersonAssoc(String json) {
		return new JSONDeserializer<InstancePersonAssoc>().use(null,
				InstancePersonAssoc.class).deserialize(json);
	}

	public static void indexInstancePersonAssoc(
			InstancePersonAssoc instancePersonAssoc) {
		List<InstancePersonAssoc> instancepersonassocs = new ArrayList<InstancePersonAssoc>();
		instancepersonassocs.add(instancePersonAssoc);
		indexInstancePersonAssocs(instancepersonassocs);
	}

	@Async
	public static void indexInstancePersonAssocs(
			Collection<InstancePersonAssoc> instancepersonassocs) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (InstancePersonAssoc instancePersonAssoc : instancepersonassocs) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id",
					"instancepersonassoc_" + instancePersonAssoc.getId());
			sid.addField("instancePersonAssoc.assocname_s",
					instancePersonAssoc.getAssocName());
			sid.addField("instancePersonAssoc.assocdescription_s",
					instancePersonAssoc.getAssocDescription());
			sid.addField("instancePersonAssoc.id_i",
					instancePersonAssoc.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"instancepersonassoc_solrsummary_t",
					new StringBuilder()
							.append(instancePersonAssoc.getAssocName())
							.append(" ")
							.append(instancePersonAssoc.getAssocDescription())
							.append(" ").append(instancePersonAssoc.getId()));
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
		String searchString = "InstancePersonAssoc_solrsummary_t:"
				+ queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new InstancePersonAssoc().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<InstancePersonAssoc> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui tip de asociere intre persoana si instanta in
	 * baza de date; in caz afirmativ, returneaza obiectul corespunzator,
	 * altfel, metoda introduce tipul de asociere in baza de date si apoi
	 * returneaza obiectul corespunzator. Verificarea existentei in baza de date
	 * se realizeaza fie dupa valoarea identificatorului, fie dupa un criteriu
	 * de unicitate.
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
	 *            - identificatorul tipului de asociere.
	 * @param name
	 *            - numele tipului de asociere.
	 * @param description
	 *            - descrierea tipului de asociere.
	 * @return
	 */
	public static InstancePersonAssoc checkInstancePersonAssoc(Integer id,
			String name, String description) {
		// TODO
		return null;
	}

	@Column(name = "assoc_description", columnDefinition = "text")
	private String assocDescription;

	@Column(name = "assoc_name", columnDefinition = "text")
	@NotNull
	private String assocName;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@OneToMany(mappedBy = "assocTypeId")
	private Set<InstancePerson> instancepeople;

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

	public String getAssocDescription() {
		return assocDescription;
	}

	public String getAssocName() {
		return assocName;
	}

	public Integer getId() {
		return this.id;
	}

	public Set<InstancePerson> getInstancepeople() {
		return instancepeople;
	}

	@Transactional
	public InstancePersonAssoc merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		InstancePersonAssoc merged = this.entityManager.merge(this);
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
			InstancePersonAssoc attached = InstancePersonAssoc
					.findInstancePersonAssoc(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAssocDescription(String assocDescription) {
		this.assocDescription = assocDescription;
	}

	public void setAssocName(String assocName) {
		this.assocName = assocName;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setInstancepeople(Set<InstancePerson> instancepeople) {
		this.instancepeople = instancepeople;
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
		indexInstancePersonAssoc(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
