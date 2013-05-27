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
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(schema = "public", name = "cms_snippet_group")
@Configurable
@Audited
public class CmsSnippetGroup {

	public static long countCmsSnippetGroups() {
		return entityManager().createQuery("SELECT COUNT(o) FROM CmsSnippetGroup o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(CmsSnippetGroup cmsSnippetGroup) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("cmssnippetgroup_" + cmsSnippetGroup.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new CmsSnippetGroup().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<CmsSnippetGroup> findAllCmsSnippetGroups() {
		return entityManager().createQuery("SELECT o FROM CmsSnippetGroup o", CmsSnippetGroup.class).getResultList();
	}

	public static CmsSnippetGroup findCmsSnippetGroup(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(CmsSnippetGroup.class, id);
	}

	public static List<CmsSnippetGroup> findCmsSnippetGroupEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM CmsSnippetGroup o", CmsSnippetGroup.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<CmsSnippetGroup> fromJsonArrayToCmsSnippetGroups(String json) {
		return new JSONDeserializer<List<CmsSnippetGroup>>().use(null, ArrayList.class)
				.use("values", CmsSnippetGroup.class).deserialize(json);
	}

	public static CmsSnippetGroup fromJsonToCmsSnippetGroup(String json) {
		return new JSONDeserializer<CmsSnippetGroup>().use(null, CmsSnippetGroup.class).deserialize(json);
	}

	public static void indexCmsSnippetGroup(CmsSnippetGroup cmsSnippetGroup) {
		List<CmsSnippetGroup> cmssnippetgroups = new ArrayList<CmsSnippetGroup>();
		cmssnippetgroups.add(cmsSnippetGroup);
		indexCmsSnippetGroups(cmssnippetgroups);
	}

	@Async
	public static void indexCmsSnippetGroups(Collection<CmsSnippetGroup> cmssnippetgroups) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (CmsSnippetGroup cmsSnippetGroup : cmssnippetgroups) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "cmssnippetgroup_" + cmsSnippetGroup.getId());
			sid.addField("cmsSnippetGroup.parentid_t", cmsSnippetGroup.getParentId());
			sid.addField("cmsSnippetGroup.name_s", cmsSnippetGroup.getName());
			sid.addField("cmsSnippetGroup.description_s", cmsSnippetGroup.getDescription());
			sid.addField("cmsSnippetGroup.id_i", cmsSnippetGroup.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("cmssnippetgroup_solrsummary_t", new StringBuilder().append(cmsSnippetGroup.getParentId())
					.append(" ").append(cmsSnippetGroup.getName()).append(" ").append(cmsSnippetGroup.getDescription())
					.append(" ").append(cmsSnippetGroup.getId()));
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
		String searchString = "CmsSnippetGroup_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new CmsSnippetGroup().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<CmsSnippetGroup> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@OneToMany(mappedBy = "parentId")
	private Set<CmsSnippetGroup> cmsSnippetGroups;

	@OneToMany(mappedBy = "cmsSnippetGroupId")
	private Set<CmsSnippet> cmsSnippets;

	@Column(name = "description", columnDefinition = "text")
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "varchar", length = 200)
	@NotNull
	private String name;

	@ManyToOne
	@JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
	private CmsSnippetGroup parentId;

	@PersistenceContext
	transient EntityManager entityManager;

	@Autowired
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

	public Set<CmsSnippetGroup> getCmsSnippetGroups() {
		return cmsSnippetGroups;
	}

	public Set<CmsSnippet> getCmsSnippets() {
		return cmsSnippets;
	}

	public String getDescription() {
		return description;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public CmsSnippetGroup getParentId() {
		return parentId;
	}

	@Transactional
	public CmsSnippetGroup merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		CmsSnippetGroup merged = this.entityManager.merge(this);
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
			CmsSnippetGroup attached = CmsSnippetGroup.findCmsSnippetGroup(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setCmsSnippetGroups(Set<CmsSnippetGroup> cmsSnippetGroups) {
		this.cmsSnippetGroups = cmsSnippetGroups;
	}

	public void setCmsSnippets(Set<CmsSnippet> cmsSnippets) {
		this.cmsSnippets = cmsSnippets;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentId(CmsSnippetGroup parentId) {
		this.parentId = parentId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexCmsSnippetGroup(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
