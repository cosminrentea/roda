package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
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
@Table(schema = "public",name = "instance_variable")
@Configurable






public class InstanceVariable {

	@EmbeddedId
    private InstanceVariablePK id;

	public InstanceVariablePK getId() {
        return this.id;
    }

	public void setId(InstanceVariablePK id) {
        this.id = id;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new InstanceVariable().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countInstanceVariables() {
        return entityManager().createQuery("SELECT COUNT(o) FROM InstanceVariable o", Long.class).getSingleResult();
    }

	public static List<InstanceVariable> findAllInstanceVariables() {
        return entityManager().createQuery("SELECT o FROM InstanceVariable o", InstanceVariable.class).getResultList();
    }

	public static InstanceVariable findInstanceVariable(InstanceVariablePK id) {
        if (id == null) return null;
        return entityManager().find(InstanceVariable.class, id);
    }

	public static List<InstanceVariable> findInstanceVariableEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM InstanceVariable o", InstanceVariable.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            InstanceVariable attached = InstanceVariable.findInstanceVariable(this.id);
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
    public InstanceVariable merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        InstanceVariable merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static InstanceVariable fromJsonToInstanceVariable(String json) {
        return new JSONDeserializer<InstanceVariable>().use(null, InstanceVariable.class).deserialize(json);
    }

	public static String toJsonArray(Collection<InstanceVariable> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<InstanceVariable> fromJsonArrayToInstanceVariables(String json) {
        return new JSONDeserializer<List<InstanceVariable>>().use(null, ArrayList.class).use("values", InstanceVariable.class).deserialize(json);
    }

	@Autowired
    transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
        String searchString = "InstanceVariable_solrsummary_t:" + queryString;
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

	public static void indexInstanceVariable(InstanceVariable instanceVariable) {
        List<InstanceVariable> instancevariables = new ArrayList<InstanceVariable>();
        instancevariables.add(instanceVariable);
        indexInstanceVariables(instancevariables);
    }

	@Async
    public static void indexInstanceVariables(Collection<InstanceVariable> instancevariables) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (InstanceVariable instanceVariable : instancevariables) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "instancevariable_" + instanceVariable.getId());
            sid.addField("instanceVariable.instanceid_t", instanceVariable.getInstanceId());
            sid.addField("instanceVariable.variableid_t", instanceVariable.getVariableId());
            sid.addField("instanceVariable.ordervariableininstance_i", instanceVariable.getOrderVariableInInstance());
            sid.addField("instanceVariable.id_t", instanceVariable.getId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("instancevariable_solrsummary_t", new StringBuilder().append(instanceVariable.getInstanceId()).append(" ").append(instanceVariable.getVariableId()).append(" ").append(instanceVariable.getOrderVariableInInstance()).append(" ").append(instanceVariable.getId()));
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
    public static void deleteIndex(InstanceVariable instanceVariable) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("instancevariable_" + instanceVariable.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@PostUpdate
    @PostPersist
    private void postPersistOrUpdate() {
        indexInstanceVariable(this);
    }

	@PreRemove
    private void preRemove() {
        deleteIndex(this);
    }

	public static SolrServer solrServer() {
        SolrServer _solrServer = new InstanceVariable().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }

	@ManyToOne
    @JoinColumn(name = "instance_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Instance instanceId;

	@ManyToOne
    @JoinColumn(name = "variable_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Variable variableId;

	@Column(name = "order_variable_in_instance", columnDefinition = "int4", unique = true)
    @NotNull
    private Integer orderVariableInInstance;

	public Instance getInstanceId() {
        return instanceId;
    }

	public void setInstanceId(Instance instanceId) {
        this.instanceId = instanceId;
    }

	public Variable getVariableId() {
        return variableId;
    }

	public void setVariableId(Variable variableId) {
        this.variableId = variableId;
    }

	public Integer getOrderVariableInInstance() {
        return orderVariableInInstance;
    }

	public void setOrderVariableInInstance(Integer orderVariableInInstance) {
        this.orderVariableInInstance = orderVariableInInstance;
    }
}
