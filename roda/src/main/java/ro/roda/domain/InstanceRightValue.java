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
import javax.persistence.ManyToOne;
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
@Table(schema = "public",name = "instance_right_value")
@Configurable






public class InstanceRightValue {

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

	public static InstanceRightValue fromJsonToInstanceRightValue(String json) {
        return new JSONDeserializer<InstanceRightValue>().use(null, InstanceRightValue.class).deserialize(json);
    }

	public static String toJsonArray(Collection<InstanceRightValue> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<InstanceRightValue> fromJsonArrayToInstanceRightValues(String json) {
        return new JSONDeserializer<List<InstanceRightValue>>().use(null, ArrayList.class).use("values", InstanceRightValue.class).deserialize(json);
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new InstanceRightValue().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countInstanceRightValues() {
        return entityManager().createQuery("SELECT COUNT(o) FROM InstanceRightValue o", Long.class).getSingleResult();
    }

	public static List<InstanceRightValue> findAllInstanceRightValues() {
        return entityManager().createQuery("SELECT o FROM InstanceRightValue o", InstanceRightValue.class).getResultList();
    }

	public static InstanceRightValue findInstanceRightValue(Integer id) {
        if (id == null) return null;
        return entityManager().find(InstanceRightValue.class, id);
    }

	public static List<InstanceRightValue> findInstanceRightValueEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM InstanceRightValue o", InstanceRightValue.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            InstanceRightValue attached = InstanceRightValue.findInstanceRightValue(this.id);
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
    public InstanceRightValue merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        InstanceRightValue merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	@Autowired
    transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
        String searchString = "InstanceRightValue_solrsummary_t:" + queryString;
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

	public static void indexInstanceRightValue(InstanceRightValue instanceRightValue) {
        List<InstanceRightValue> instancerightvalues = new ArrayList<InstanceRightValue>();
        instancerightvalues.add(instanceRightValue);
        indexInstanceRightValues(instancerightvalues);
    }

	@Async
    public static void indexInstanceRightValues(Collection<InstanceRightValue> instancerightvalues) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (InstanceRightValue instanceRightValue : instancerightvalues) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "instancerightvalue_" + instanceRightValue.getId());
            sid.addField("instanceRightValue.id_i", instanceRightValue.getId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("instancerightvalue_solrsummary_t", new StringBuilder().append(instanceRightValue.getId()));
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
    public static void deleteIndex(InstanceRightValue instanceRightValue) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("instancerightvalue_" + instanceRightValue.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@PostUpdate
    @PostPersist
    private void postPersistOrUpdate() {
        indexInstanceRightValue(this);
    }

	@PreRemove
    private void preRemove() {
        deleteIndex(this);
    }

	public static SolrServer solrServer() {
        SolrServer _solrServer = new InstanceRightValue().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }

	@OneToMany(mappedBy = "instanceRightValueId")
    private Set<InstanceRightTargetGroup> instanceRightTargetGroups;

	@ManyToOne
    @JoinColumn(name = "instance_right_id", referencedColumnName = "id", nullable = false)
    private InstanceRight instanceRightId;

	@Column(name = "value", columnDefinition = "int4")
    @NotNull
    private Integer value;

	@Column(name = "description", columnDefinition = "text")
    private String description;

	@Column(name = "fee", columnDefinition = "int4")
    private Integer fee;

	@Column(name = "fee_currency_abbr", columnDefinition = "varchar", length = 3)
    private String feeCurrencyAbbr;

	public Set<InstanceRightTargetGroup> getInstanceRightTargetGroups() {
        return instanceRightTargetGroups;
    }

	public void setInstanceRightTargetGroups(Set<InstanceRightTargetGroup> instanceRightTargetGroups) {
        this.instanceRightTargetGroups = instanceRightTargetGroups;
    }

	public InstanceRight getInstanceRightId() {
        return instanceRightId;
    }

	public void setInstanceRightId(InstanceRight instanceRightId) {
        this.instanceRightId = instanceRightId;
    }

	public Integer getValue() {
        return value;
    }

	public void setValue(Integer value) {
        this.value = value;
    }

	public String getDescription() {
        return description;
    }

	public void setDescription(String description) {
        this.description = description;
    }

	public Integer getFee() {
        return fee;
    }

	public void setFee(Integer fee) {
        this.fee = fee;
    }

	public String getFeeCurrencyAbbr() {
        return feeCurrencyAbbr;
    }

	public void setFeeCurrencyAbbr(String feeCurrencyAbbr) {
        this.feeCurrencyAbbr = feeCurrencyAbbr;
    }
}
