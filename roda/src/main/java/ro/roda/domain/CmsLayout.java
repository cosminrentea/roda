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
@Table(schema = "public",name = "cms_layout")






public class CmsLayout {

	@Autowired
    transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
        String searchString = "CmsLayout_solrsummary_t:" + queryString;
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

	public static void indexCmsLayout(CmsLayout cmsLayout) {
        List<CmsLayout> cmslayouts = new ArrayList<CmsLayout>();
        cmslayouts.add(cmsLayout);
        indexCmsLayouts(cmslayouts);
    }

	@Async
    public static void indexCmsLayouts(Collection<CmsLayout> cmslayouts) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (CmsLayout cmsLayout : cmslayouts) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "cmslayout_" + cmsLayout.getId());
            sid.addField("cmsLayout.id_i", cmsLayout.getId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("cmslayout_solrsummary_t", new StringBuilder().append(cmsLayout.getId()));
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
    public static void deleteIndex(CmsLayout cmsLayout) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("cmslayout_" + cmsLayout.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@PostUpdate
    @PostPersist
    private void postPersistOrUpdate() {
        indexCmsLayout(this);
    }

	@PreRemove
    private void preRemove() {
        deleteIndex(this);
    }

	public static SolrServer solrServer() {
        SolrServer _solrServer = new CmsLayout().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static CmsLayout fromJsonToCmsLayout(String json) {
        return new JSONDeserializer<CmsLayout>().use(null, CmsLayout.class).deserialize(json);
    }

	public static String toJsonArray(Collection<CmsLayout> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<CmsLayout> fromJsonArrayToCmsLayouts(String json) {
        return new JSONDeserializer<List<CmsLayout>>().use(null, ArrayList.class).use("values", CmsLayout.class).deserialize(json);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new CmsLayout().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countCmsLayouts() {
        return entityManager().createQuery("SELECT COUNT(o) FROM CmsLayout o", Long.class).getSingleResult();
    }

	public static List<CmsLayout> findAllCmsLayouts() {
        return entityManager().createQuery("SELECT o FROM CmsLayout o", CmsLayout.class).getResultList();
    }

	public static CmsLayout findCmsLayout(Integer id) {
        if (id == null) return null;
        return entityManager().find(CmsLayout.class, id);
    }

	public static List<CmsLayout> findCmsLayoutEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CmsLayout o", CmsLayout.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            CmsLayout attached = CmsLayout.findCmsLayout(this.id);
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
    public CmsLayout merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CmsLayout merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
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

	@OneToMany(mappedBy = "cmsLayoutId")
    private Set<CmsPage> cmsPages;

	@ManyToOne
    @JoinColumn(name = "cms_layout_group_id", referencedColumnName = "id")
    private CmsLayoutGroup cmsLayoutGroupId;

	@Column(name = "name", columnDefinition = "varchar", length = 200)
    @NotNull
    private String name;

	@Column(name = "layout_content", columnDefinition = "text")
    @NotNull
    private String layoutContent;

	public Set<CmsPage> getCmsPages() {
        return cmsPages;
    }

	public void setCmsPages(Set<CmsPage> cmsPages) {
        this.cmsPages = cmsPages;
    }

	public CmsLayoutGroup getCmsLayoutGroupId() {
        return cmsLayoutGroupId;
    }

	public void setCmsLayoutGroupId(CmsLayoutGroup cmsLayoutGroupId) {
        this.cmsLayoutGroupId = cmsLayoutGroupId;
    }

	public String getName() {
        return name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public String getLayoutContent() {
        return layoutContent;
    }

	public void setLayoutContent(String layoutContent) {
        this.layoutContent = layoutContent;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
