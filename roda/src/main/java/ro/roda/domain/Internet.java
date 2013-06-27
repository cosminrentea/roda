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
@Table(schema = "public", name = "internet")
public class Internet {

	public static long countInternets() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Internet o",
				Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Internet internet) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("internet_" + internet.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Internet().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Internet> findAllInternets() {
		return entityManager().createQuery("SELECT o FROM Internet o",
				Internet.class).getResultList();
	}

	public static Internet findInternet(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Internet.class, id);
	}

	public static List<Internet> findInternetEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM Internet o", Internet.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static Collection<Internet> fromJsonArrayToInternets(String json) {
		return new JSONDeserializer<List<Internet>>()
				.use(null, ArrayList.class).use("values", Internet.class)
				.deserialize(json);
	}

	public static Internet fromJsonToInternet(String json) {
		return new JSONDeserializer<Internet>().use(null, Internet.class)
				.deserialize(json);
	}

	public static void indexInternet(Internet internet) {
		List<Internet> internets = new ArrayList<Internet>();
		internets.add(internet);
		indexInternets(internets);
	}

	@Async
	public static void indexInternets(Collection<Internet> internets) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Internet internet : internets) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "internet_" + internet.getId());
			sid.addField("internet.internettype_s", internet.getInternetType());
			sid.addField("internet.internet_s", internet.getInternet());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("internet_solrsummary_t",
					new StringBuilder().append(internet.getInternetType())
							.append(" ").append(internet.getInternet()));
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
		String searchString = "Internet_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Internet().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Internet> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unei adrese Internet in baza de date; daca exista,
	 * returneaza obiectul respectiv, altfel, il introduce in baza de date si
	 * returneaza obiectul corespunzator.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <p>
	 * <ul>
	 * <li>internetId
	 * <li>internet
	 * <ul>
	 * <p>
	 * 
	 * 
	 * @param internetId
	 *            - cheia primara a adresei de Internet
	 * @param internet
	 *            - adresa de Internet
	 * @param internetType
	 *            - tipul adresei de Internet
	 * @return
	 */
	public static Internet checkInternet(Integer internetId, String internet, String internetType) {
		// TODO
		return null;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@Column(name = "internet", columnDefinition = "text")
	@NotNull
	private String internet;

	@Column(name = "internet_type", columnDefinition = "varchar", length = 50)
	private String internetType;

	@OneToMany(mappedBy = "internetId")
	private Set<OrgInternet> orgInternets;

	@OneToMany(mappedBy = "internetId")
	private Set<PersonInternet> personInternets;

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

	public Integer getId() {
		return this.id;
	}

	public String getInternet() {
		return internet;
	}

	public String getInternetType() {
		return internetType;
	}

	public Set<OrgInternet> getOrgInternets() {
		return orgInternets;
	}

	public Set<PersonInternet> getPersonInternets() {
		return personInternets;
	}

	@Transactional
	public Internet merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Internet merged = this.entityManager.merge(this);
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
			Internet attached = Internet.findInternet(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setInternet(String internet) {
		this.internet = internet;
	}

	public void setInternetType(String internetType) {
		this.internetType = internetType;
	}

	public void setOrgInternets(Set<OrgInternet> orgInternets) {
		this.orgInternets = orgInternets;
	}

	public void setPersonInternets(Set<PersonInternet> personInternets) {
		this.personInternets = personInternets;
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
		indexInternet(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
