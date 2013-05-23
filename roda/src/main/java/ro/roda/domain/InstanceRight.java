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
@Table(schema = "public",name = "instance_right")
@Configurable






public class InstanceRight {

	@Autowired
    transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
        String searchString = "InstanceRight_solrsummary_t:" + queryString;
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

	public static void indexInstanceRight(InstanceRight instanceRight) {
        List<InstanceRight> instancerights = new ArrayList<InstanceRight>();
        instancerights.add(instanceRight);
        indexInstanceRights(instancerights);
    }

	@Async
    public static void indexInstanceRights(Collection<InstanceRight> instancerights) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (InstanceRight instanceRight : instancerights) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "instanceright_" + instanceRight.getId());
            sid.addField("instanceRight.id_i", instanceRight.getId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("instanceright_solrsummary_t", new StringBuilder().append(instanceRight.getId()));
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
    public static void deleteIndex(InstanceRight instanceRight) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("instanceright_" + instanceRight.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@PostUpdate
    @PostPersist
    private void postPersistOrUpdate() {
        indexInstanceRight(this);
    }

	@PreRemove
    private void preRemove() {
        deleteIndex(this);
    }

	public static SolrServer solrServer() {
        SolrServer _solrServer = new InstanceRight().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new InstanceRight().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countInstanceRights() {
        return entityManager().createQuery("SELECT COUNT(o) FROM InstanceRight o", Long.class).getSingleResult();
    }

	public static List<InstanceRight> findAllInstanceRights() {
        return entityManager().createQuery("SELECT o FROM InstanceRight o", InstanceRight.class).getResultList();
    }

	public static InstanceRight findInstanceRight(Integer id) {
        if (id == null) return null;
        return entityManager().find(InstanceRight.class, id);
    }

	public static List<InstanceRight> findInstanceRightEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM InstanceRight o", InstanceRight.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            InstanceRight attached = InstanceRight.findInstanceRight(this.id);
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
    public InstanceRight merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        InstanceRight merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static InstanceRight fromJsonToInstanceRight(String json) {
        return new JSONDeserializer<InstanceRight>().use(null, InstanceRight.class).deserialize(json);
    }

	public static String toJsonArray(Collection<InstanceRight> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<InstanceRight> fromJsonArrayToInstanceRights(String json) {
        return new JSONDeserializer<List<InstanceRight>>().use(null, ArrayList.class).use("values", InstanceRight.class).deserialize(json);
    }

	@OneToMany(mappedBy = "instanceRightId")
    private Set<InstanceRightTargetGroup> instanceRightTargetGroups;

	@OneToMany(mappedBy = "instanceRightId")
    private Set<InstanceRightValue> instanceRightValues;

	@Column(name = "name", columnDefinition = "text")
    @NotNull
    private String name;

	@Column(name = "description", columnDefinition = "text")
    private String description;

	public Set<InstanceRightTargetGroup> getInstanceRightTargetGroups() {
        return instanceRightTargetGroups;
    }

	public void setInstanceRightTargetGroups(Set<InstanceRightTargetGroup> instanceRightTargetGroups) {
        this.instanceRightTargetGroups = instanceRightTargetGroups;
    }

	public Set<InstanceRightValue> getInstanceRightValues() {
        return instanceRightValues;
    }

	public void setInstanceRightValues(Set<InstanceRightValue> instanceRightValues) {
        this.instanceRightValues = instanceRightValues;
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
