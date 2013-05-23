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
@Table(schema = "public",name = "acl_object_identity")






public class AclObjectIdentity {

    @Column(name = "object_id_identity", columnDefinition = "int8")
    @NotNull
    private Long objectIdIdentity;

	@Autowired
    transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
        String searchString = "AclObjectIdentity_solrsummary_t:" + queryString;
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

	public static void indexAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
        List<AclObjectIdentity> aclobjectidentitys = new ArrayList<AclObjectIdentity>();
        aclobjectidentitys.add(aclObjectIdentity);
        indexAclObjectIdentitys(aclobjectidentitys);
    }

	@Async
    public static void indexAclObjectIdentitys(Collection<AclObjectIdentity> aclobjectidentitys) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (AclObjectIdentity aclObjectIdentity : aclobjectidentitys) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "aclobjectidentity_" + aclObjectIdentity.getId());
            sid.addField("aclObjectIdentity.objectidclass_t", aclObjectIdentity.getObjectIdClass());
            sid.addField("aclObjectIdentity.parentobject_t", aclObjectIdentity.getParentObject());
            sid.addField("aclObjectIdentity.ownersid_t", aclObjectIdentity.getOwnerSid());
            sid.addField("aclObjectIdentity.objectididentity_l", aclObjectIdentity.getObjectIdIdentity());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("aclobjectidentity_solrsummary_t", new StringBuilder().append(aclObjectIdentity.getObjectIdClass()).append(" ").append(aclObjectIdentity.getParentObject()).append(" ").append(aclObjectIdentity.getOwnerSid()).append(" ").append(aclObjectIdentity.getObjectIdIdentity()));
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
    public static void deleteIndex(AclObjectIdentity aclObjectIdentity) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("aclobjectidentity_" + aclObjectIdentity.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@PostUpdate
    @PostPersist
    private void postPersistOrUpdate() {
        indexAclObjectIdentity(this);
    }

	@PreRemove
    private void preRemove() {
        deleteIndex(this);
    }

	public static SolrServer solrServer() {
        SolrServer _solrServer = new AclObjectIdentity().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static AclObjectIdentity fromJsonToAclObjectIdentity(String json) {
        return new JSONDeserializer<AclObjectIdentity>().use(null, AclObjectIdentity.class).deserialize(json);
    }

	public static String toJsonArray(Collection<AclObjectIdentity> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<AclObjectIdentity> fromJsonArrayToAclObjectIdentitys(String json) {
        return new JSONDeserializer<List<AclObjectIdentity>>().use(null, ArrayList.class).use("values", AclObjectIdentity.class).deserialize(json);
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

	public Long getObjectIdIdentity() {
        return this.objectIdIdentity;
    }

	public void setObjectIdIdentity(Long objectIdIdentity) {
        this.objectIdIdentity = objectIdIdentity;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@OneToMany(mappedBy = "aclObjectIdentity")
    private Set<AclEntry> aclEntries;

	@OneToMany(mappedBy = "parentObject")
    private Set<AclObjectIdentity> aclObjectIdentities;

	@ManyToOne
    @JoinColumn(name = "object_id_class", referencedColumnName = "id", nullable = false)
    private AclClass objectIdClass;

	@ManyToOne
    @JoinColumn(name = "parent_object", referencedColumnName = "id", insertable = false, updatable = false)
    private AclObjectIdentity parentObject;

	@ManyToOne
    @JoinColumn(name = "owner_sid", referencedColumnName = "id", nullable = false)
    private AclSid ownerSid;

	@Column(name = "entries_inheriting", columnDefinition = "bool")
    @NotNull
    private boolean entriesInheriting;

	public Set<AclEntry> getAclEntries() {
        return aclEntries;
    }

	public void setAclEntries(Set<AclEntry> aclEntries) {
        this.aclEntries = aclEntries;
    }

	public Set<AclObjectIdentity> getAclObjectIdentities() {
        return aclObjectIdentities;
    }

	public void setAclObjectIdentities(Set<AclObjectIdentity> aclObjectIdentities) {
        this.aclObjectIdentities = aclObjectIdentities;
    }

	public AclClass getObjectIdClass() {
        return objectIdClass;
    }

	public void setObjectIdClass(AclClass objectIdClass) {
        this.objectIdClass = objectIdClass;
    }

	public AclObjectIdentity getParentObject() {
        return parentObject;
    }

	public void setParentObject(AclObjectIdentity parentObject) {
        this.parentObject = parentObject;
    }

	public AclSid getOwnerSid() {
        return ownerSid;
    }

	public void setOwnerSid(AclSid ownerSid) {
        this.ownerSid = ownerSid;
    }

	public boolean isEntriesInheriting() {
        return entriesInheriting;
    }

	public void setEntriesInheriting(boolean entriesInheriting) {
        this.entriesInheriting = entriesInheriting;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new AclObjectIdentity().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countAclObjectIdentitys() {
        return entityManager().createQuery("SELECT COUNT(o) FROM AclObjectIdentity o", Long.class).getSingleResult();
    }

	public static List<AclObjectIdentity> findAllAclObjectIdentitys() {
        return entityManager().createQuery("SELECT o FROM AclObjectIdentity o", AclObjectIdentity.class).getResultList();
    }

	public static AclObjectIdentity findAclObjectIdentity(Long id) {
        if (id == null) return null;
        return entityManager().find(AclObjectIdentity.class, id);
    }

	public static List<AclObjectIdentity> findAclObjectIdentityEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM AclObjectIdentity o", AclObjectIdentity.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            AclObjectIdentity attached = AclObjectIdentity.findAclObjectIdentity(this.id);
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
    public AclObjectIdentity merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        AclObjectIdentity merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
