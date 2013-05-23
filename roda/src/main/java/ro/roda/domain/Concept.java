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
@Table(schema = "public",name = "concept")






public class Concept {

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static Concept fromJsonToConcept(String json) {
        return new JSONDeserializer<Concept>().use(null, Concept.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Concept> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Concept> fromJsonArrayToConcepts(String json) {
        return new JSONDeserializer<List<Concept>>().use(null, ArrayList.class).use("values", Concept.class).deserialize(json);
    }

	@Autowired
    transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
        String searchString = "Concept_solrsummary_t:" + queryString;
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

	public static void indexConcept(Concept concept) {
        List<Concept> concepts = new ArrayList<Concept>();
        concepts.add(concept);
        indexConcepts(concepts);
    }

	@Async
    public static void indexConcepts(Collection<Concept> concepts) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (Concept concept : concepts) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "concept_" + concept.getId());
            sid.addField("concept.name_s", concept.getName());
            sid.addField("concept.description_s", concept.getDescription());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("concept_solrsummary_t", new StringBuilder().append(concept.getName()).append(" ").append(concept.getDescription()));
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
    public static void deleteIndex(Concept concept) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("concept_" + concept.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@PostUpdate
    @PostPersist
    private void postPersistOrUpdate() {
        indexConcept(this);
    }

	@PreRemove
    private void preRemove() {
        deleteIndex(this);
    }

	public static SolrServer solrServer() {
        SolrServer _solrServer = new Concept().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Concept().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countConcepts() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Concept o", Long.class).getSingleResult();
    }

	public static List<Concept> findAllConcepts() {
        return entityManager().createQuery("SELECT o FROM Concept o", Concept.class).getResultList();
    }

	public static Concept findConcept(Long id) {
        if (id == null) return null;
        return entityManager().find(Concept.class, id);
    }

	public static List<Concept> findConceptEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Concept o", Concept.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Concept attached = Concept.findConcept(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public Concept merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Concept merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "bigserial")
    private Long id;

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	@ManyToMany
    @JoinTable(name = "concept_variable", joinColumns = { @JoinColumn(name = "concept_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "variable_id", nullable = false) })
    private Set<Variable> variables;

	@Column(name = "name", columnDefinition = "text")
    @NotNull
    private String name;

	@Column(name = "description", columnDefinition = "text")
    private String description;

	public Set<Variable> getVariables() {
        return variables;
    }

	public void setVariables(Set<Variable> variables) {
        this.variables = variables;
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
}
