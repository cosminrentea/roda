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
import javax.persistence.TypedQuery;
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
@Table(schema = "public", name = "source")
@Configurable
public class Source {

	public static long countSources() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Source o",
				Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Source source) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("source_" + source.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Source().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Source> findAllSources() {
		return entityManager().createQuery("SELECT o FROM Source o",
				Source.class).getResultList();
	}

	public static Source findSource(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Source.class, id);
	}

	public static List<Source> findSourceEntries(int firstResult, int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM Source o", Source.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static Collection<Source> fromJsonArrayToSources(String json) {
		return new JSONDeserializer<List<Source>>().use(null, ArrayList.class)
				.use("values", Source.class).deserialize(json);
	}

	public static Source fromJsonToSource(String json) {
		return new JSONDeserializer<Source>().use(null, Source.class)
				.deserialize(json);
	}

	public static void indexSource(Source source) {
		List<Source> sources = new ArrayList<Source>();
		sources.add(source);
		indexSources(sources);
	}

	@Async
	public static void indexSources(Collection<Source> sources) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Source source : sources) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "source_" + source.getId());
			sid.addField("source.citation_s", source.getCitation());
			sid.addField("source.id_i", source.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("source_solrsummary_t",
					new StringBuilder().append(source.getCitation())
							.append(" ").append(source.getId()));
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
		String searchString = "Source_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Source().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Source> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>Source</code> (sursa pentru
	 * studii) in baza de date; in caz afirmativ il returneaza, altfel, metoda
	 * il introduce in baza de date si apoi il returneaza. Verificarea
	 * existentei in baza de date se realizeaza fie dupa identificator, fie dupa
	 * un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul sursei.
	 * @param citation
	 * @return
	 */
	public static Source checkSource(Integer id, String citation) {
		Source object;

		if (id != null) {
			object = findSource(id);

			if (object != null) {
				return object;
			}
		}

		object = new Source();
		object.citation = citation;
		object.persist();

		return object;
	}

	@Column(name = "citation", columnDefinition = "text")
	@NotNull
	private String citation;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@ManyToMany
	@JoinTable(name = "study_source", joinColumns = { @JoinColumn(name = "source_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "study_id", nullable = false) })
	private Set<Study> studies;

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

	public String getCitation() {
		return citation;
	}

	public Integer getId() {
		return this.id;
	}

	public Set<Study> getStudies() {
		return studies;
	}

	@Transactional
	public Source merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Source merged = this.entityManager.merge(this);
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
			Source attached = Source.findSource(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setCitation(String citation) {
		this.citation = citation;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setStudies(Set<Study> studies) {
		this.studies = studies;
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
		indexSource(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return id != null && id.equals(((Source) obj).id);
	}
}
