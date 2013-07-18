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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(schema = "public", name = "vargroup")
@Configurable
public class Vargroup {

	public static long countVargroups() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Vargroup o",
				Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Vargroup vargroup) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("vargroup_" + vargroup.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Vargroup().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Vargroup> findAllVargroups() {
		return entityManager().createQuery("SELECT o FROM Vargroup o",
				Vargroup.class).getResultList();
	}

	public static Vargroup findVargroup(Long id) {
		if (id == null)
			return null;
		return entityManager().find(Vargroup.class, id);
	}

	public static List<Vargroup> findVargroupEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM Vargroup o", Vargroup.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static Collection<Vargroup> fromJsonArrayToVargroups(String json) {
		return new JSONDeserializer<List<Vargroup>>()
				.use(null, ArrayList.class).use("values", Vargroup.class)
				.deserialize(json);
	}

	public static Vargroup fromJsonToVargroup(String json) {
		return new JSONDeserializer<Vargroup>().use(null, Vargroup.class)
				.deserialize(json);
	}

	public static void indexVargroup(Vargroup vargroup) {
		List<Vargroup> vargroups = new ArrayList<Vargroup>();
		vargroups.add(vargroup);
		indexVargroups(vargroups);
	}

	@Async
	public static void indexVargroups(Collection<Vargroup> vargroups) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Vargroup vargroup : vargroups) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "vargroup_" + vargroup.getId());
			sid.addField("vargroup.name_s", vargroup.getName());
			sid.addField("vargroup.id_l", vargroup.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("vargroup_solrsummary_t",
					new StringBuilder().append(vargroup.getName()).append(" ")
							.append(vargroup.getId()));
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
		String searchString = "Vargroup_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Vargroup().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Vargroup> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui grup de variabile in baza de date; in caz
	 * afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce
	 * grupul de variabile in baza de date si apoi returneaza obiectul
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
	 *            - identificatorul grupului de variabile.
	 * @param name
	 *            - numele grupului de variabile.
	 * @return
	 */
	public static Vargroup checkVargroup(Integer id, String name) {
		// TODO
		return null;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigserial")
	private Long id;

	@Column(name = "name", columnDefinition = "text")
	@NotNull
	private String name;

	@ManyToMany
	@JoinTable(name = "variable_vargroup", joinColumns = { @JoinColumn(name = "vargroup_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "variable_id", nullable = false) })
	private Set<Variable> variables;

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

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public Set<Variable> getVariables() {
		return variables;
	}

	@Transactional
	public Vargroup merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Vargroup merged = this.entityManager.merge(this);
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
			Vargroup attached = Vargroup.findVargroup(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVariables(Set<Variable> variables) {
		this.variables = variables;
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
		indexVargroup(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
