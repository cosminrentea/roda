package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(schema = "public",name = "other_statistic")






public class OtherStatistic {

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new OtherStatistic().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countOtherStatistics() {
        return entityManager().createQuery("SELECT COUNT(o) FROM OtherStatistic o", Long.class).getSingleResult();
    }

	public static List<OtherStatistic> findAllOtherStatistics() {
        return entityManager().createQuery("SELECT o FROM OtherStatistic o", OtherStatistic.class).getResultList();
    }

	public static OtherStatistic findOtherStatistic(Long id) {
        if (id == null) return null;
        return entityManager().find(OtherStatistic.class, id);
    }

	public static List<OtherStatistic> findOtherStatisticEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM OtherStatistic o", OtherStatistic.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            OtherStatistic attached = OtherStatistic.findOtherStatistic(this.id);
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
    public OtherStatistic merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        OtherStatistic merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@ManyToOne
    @JoinColumn(name = "variable_id", referencedColumnName = "id", nullable = false)
    private Variable variableId;

	@Column(name = "name", columnDefinition = "varchar", length = 100)
    @NotNull
    private String name;

	@Column(name = "value", columnDefinition = "float4", precision = 8, scale = 8)
    @NotNull
    private Float value;

	@Column(name = "description", columnDefinition = "text")
    private String description;

	public Variable getVariableId() {
        return variableId;
    }

	public void setVariableId(Variable variableId) {
        this.variableId = variableId;
    }

	public String getName() {
        return name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public Float getValue() {
        return value;
    }

	public void setValue(Float value) {
        this.value = value;
    }

	public String getDescription() {
        return description;
    }

	public void setDescription(String description) {
        this.description = description;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static OtherStatistic fromJsonToOtherStatistic(String json) {
        return new JSONDeserializer<OtherStatistic>().use(null, OtherStatistic.class).deserialize(json);
    }

	public static String toJsonArray(Collection<OtherStatistic> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<OtherStatistic> fromJsonArrayToOtherStatistics(String json) {
        return new JSONDeserializer<List<OtherStatistic>>().use(null, ArrayList.class).use("values", OtherStatistic.class).deserialize(json);
    }

	@Autowired
    transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
        String searchString = "OtherStatistic_solrsummary_t:" + queryString;
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

	public static void indexOtherStatistic(OtherStatistic otherStatistic) {
        List<OtherStatistic> otherstatistics = new ArrayList<OtherStatistic>();
        otherstatistics.add(otherStatistic);
        indexOtherStatistics(otherstatistics);
    }

	@Async
    public static void indexOtherStatistics(Collection<OtherStatistic> otherstatistics) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (OtherStatistic otherStatistic : otherstatistics) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "otherstatistic_" + otherStatistic.getId());
            sid.addField("otherStatistic.variableid_t", otherStatistic.getVariableId());
            sid.addField("otherStatistic.name_s", otherStatistic.getName());
            sid.addField("otherStatistic.value_f", otherStatistic.getValue());
            sid.addField("otherStatistic.description_s", otherStatistic.getDescription());
            sid.addField("otherStatistic.id_l", otherStatistic.getId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("otherstatistic_solrsummary_t", new StringBuilder().append(otherStatistic.getVariableId()).append(" ").append(otherStatistic.getName()).append(" ").append(otherStatistic.getValue()).append(" ").append(otherStatistic.getDescription()).append(" ").append(otherStatistic.getId()));
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
    public static void deleteIndex(OtherStatistic otherStatistic) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("otherstatistic_" + otherStatistic.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@PostUpdate
    @PostPersist
    private void postPersistOrUpdate() {
        indexOtherStatistic(this);
    }

	@PreRemove
    private void preRemove() {
        deleteIndex(this);
    }

	public static SolrServer solrServer() {
        SolrServer _solrServer = new OtherStatistic().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
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
}
