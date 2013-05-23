package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
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
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(schema = "public", name = "unit_analysis")
@Configurable
public class UnitAnalysis {

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "UnitAnalysis_solrsummary_t:" + queryString;
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

	public static void indexUnitAnalysis(UnitAnalysis unitAnalysis) {
		List<UnitAnalysis> unitanalyses = new ArrayList<UnitAnalysis>();
		unitanalyses.add(unitAnalysis);
		indexUnitAnalyses(unitanalyses);
	}

	@Async
	public static void indexUnitAnalyses(Collection<UnitAnalysis> unitanalyses) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (UnitAnalysis unitAnalysis : unitanalyses) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "unitanalysis_" + unitAnalysis.getId());
			sid.addField("unitAnalysis.name_s", unitAnalysis.getName());
			sid.addField("unitAnalysis.description_s", unitAnalysis.getDescription());
			sid.addField("unitAnalysis.id_i", unitAnalysis.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("unitanalysis_solrsummary_t", new StringBuilder().append(unitAnalysis.getName()).append(" ")
					.append(unitAnalysis.getDescription()).append(" ").append(unitAnalysis.getId()));
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
	public static void deleteIndex(UnitAnalysis unitAnalysis) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("unitanalysis_" + unitAnalysis.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexUnitAnalysis(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new UnitAnalysis().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	@OneToMany(mappedBy = "unitAnalysisId")
	private Set<Study> studies;

	@Column(name = "name", columnDefinition = "varchar", length = 100)
	@NotNull
	private String name;

	@Column(name = "description", columnDefinition = "text")
	private String description;

	public Set<Study> getStudies() {
		return studies;
	}

	public void setStudies(Set<Study> studies) {
		this.studies = studies;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static UnitAnalysis fromJsonToUnitAnalysis(String json) {
		return new JSONDeserializer<UnitAnalysis>().use(null, UnitAnalysis.class).deserialize(json);
	}

	public static String toJsonArray(Collection<UnitAnalysis> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<UnitAnalysis> fromJsonArrayToUnitAnalyses(String json) {
		return new JSONDeserializer<List<UnitAnalysis>>().use(null, ArrayList.class).use("values", UnitAnalysis.class)
				.deserialize(json);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new UnitAnalysis().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countUnitAnalyses() {
		return entityManager().createQuery("SELECT COUNT(o) FROM UnitAnalysis o", Long.class).getSingleResult();
	}

	public static List<UnitAnalysis> findAllUnitAnalyses() {
		return entityManager().createQuery("SELECT o FROM UnitAnalysis o", UnitAnalysis.class).getResultList();
	}

	public static UnitAnalysis findUnitAnalysis(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(UnitAnalysis.class, id);
	}

	public static List<UnitAnalysis> findUnitAnalysisEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM UnitAnalysis o", UnitAnalysis.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
			UnitAnalysis attached = UnitAnalysis.findUnitAnalysis(this.id);
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
	public UnitAnalysis merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		UnitAnalysis merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}
}
