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

@Entity
@Table(schema = "public", name = "cms_layout_group")
@Configurable
public class CmsLayoutGroup {

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "CmsLayoutGroup_solrsummary_t:" + queryString;
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

	public static void indexCmsLayoutGroup(CmsLayoutGroup cmsLayoutGroup) {
		List<CmsLayoutGroup> cmslayoutgroups = new ArrayList<CmsLayoutGroup>();
		cmslayoutgroups.add(cmsLayoutGroup);
		indexCmsLayoutGroups(cmslayoutgroups);
	}

	@Async
	public static void indexCmsLayoutGroups(Collection<CmsLayoutGroup> cmslayoutgroups) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (CmsLayoutGroup cmsLayoutGroup : cmslayoutgroups) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "cmslayoutgroup_" + cmsLayoutGroup.getId());
			sid.addField("cmsLayoutGroup.parentid_t", cmsLayoutGroup.getParentId());
			sid.addField("cmsLayoutGroup.name_s", cmsLayoutGroup.getName());
			sid.addField("cmsLayoutGroup.description_s", cmsLayoutGroup.getDescription());
			sid.addField("cmsLayoutGroup.id_i", cmsLayoutGroup.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("cmslayoutgroup_solrsummary_t", new StringBuilder().append(cmsLayoutGroup.getParentId())
					.append(" ").append(cmsLayoutGroup.getName()).append(" ").append(cmsLayoutGroup.getDescription())
					.append(" ").append(cmsLayoutGroup.getId()));
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
	public static void deleteIndex(CmsLayoutGroup cmsLayoutGroup) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("cmslayoutgroup_" + cmsLayoutGroup.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexCmsLayoutGroup(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new CmsLayoutGroup().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new CmsLayoutGroup().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countCmsLayoutGroups() {
		return entityManager().createQuery("SELECT COUNT(o) FROM CmsLayoutGroup o", Long.class).getSingleResult();
	}

	public static List<CmsLayoutGroup> findAllCmsLayoutGroups() {
		return entityManager().createQuery("SELECT o FROM CmsLayoutGroup o", CmsLayoutGroup.class).getResultList();
	}

	public static CmsLayoutGroup findCmsLayoutGroup(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(CmsLayoutGroup.class, id);
	}

	public static List<CmsLayoutGroup> findCmsLayoutGroupEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM CmsLayoutGroup o", CmsLayoutGroup.class)
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
			CmsLayoutGroup attached = CmsLayoutGroup.findCmsLayoutGroup(this.id);
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
	public CmsLayoutGroup merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		CmsLayoutGroup merged = this.entityManager.merge(this);
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

	@OneToMany(mappedBy = "cmsLayoutGroupId")
	private Set<CmsLayout> cmsLayouts;

	@OneToMany(mappedBy = "parentId")
	private Set<CmsLayoutGroup> cmsLayoutGroups;

	@ManyToOne
	@JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
	private CmsLayoutGroup parentId;

	@Column(name = "name", columnDefinition = "varchar", length = 200)
	@NotNull
	private String name;

	@Column(name = "description", columnDefinition = "text")
	private String description;

	public Set<CmsLayout> getCmsLayouts() {
		return cmsLayouts;
	}

	public void setCmsLayouts(Set<CmsLayout> cmsLayouts) {
		this.cmsLayouts = cmsLayouts;
	}

	public Set<CmsLayoutGroup> getCmsLayoutGroups() {
		return cmsLayoutGroups;
	}

	public void setCmsLayoutGroups(Set<CmsLayoutGroup> cmsLayoutGroups) {
		this.cmsLayoutGroups = cmsLayoutGroups;
	}

	public CmsLayoutGroup getParentId() {
		return parentId;
	}

	public void setParentId(CmsLayoutGroup parentId) {
		this.parentId = parentId;
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

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static CmsLayoutGroup fromJsonToCmsLayoutGroup(String json) {
		return new JSONDeserializer<CmsLayoutGroup>().use(null, CmsLayoutGroup.class).deserialize(json);
	}

	public static String toJsonArray(Collection<CmsLayoutGroup> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<CmsLayoutGroup> fromJsonArrayToCmsLayoutGroups(String json) {
		return new JSONDeserializer<List<CmsLayoutGroup>>().use(null, ArrayList.class)
				.use("values", CmsLayoutGroup.class).deserialize(json);
	}
}
