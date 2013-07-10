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
@Table(schema = "public", name = "setting_group")
@Configurable
public class SettingGroup {

	public static long countSettingGroups() {
		return entityManager().createQuery(
				"SELECT COUNT(o) FROM SettingGroup o", Long.class)
				.getSingleResult();
	}

	@Async
	public static void deleteIndex(SettingGroup settingGroup) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("settinggroup_" + settingGroup.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new SettingGroup().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<SettingGroup> findAllSettingGroups() {
		return entityManager().createQuery("SELECT o FROM SettingGroup o",
				SettingGroup.class).getResultList();
	}

	public static SettingGroup findSettingGroup(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(SettingGroup.class, id);
	}

	public static List<SettingGroup> findSettingGroupEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM SettingGroup o", SettingGroup.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static Collection<SettingGroup> fromJsonArrayToSettingGroups(
			String json) {
		return new JSONDeserializer<List<SettingGroup>>()
				.use(null, ArrayList.class).use("values", SettingGroup.class)
				.deserialize(json);
	}

	public static SettingGroup fromJsonToSettingGroup(String json) {
		return new JSONDeserializer<SettingGroup>().use(null,
				SettingGroup.class).deserialize(json);
	}

	public static void indexSettingGroup(SettingGroup settingGroup) {
		List<SettingGroup> settinggroups = new ArrayList<SettingGroup>();
		settinggroups.add(settingGroup);
		indexSettingGroups(settinggroups);
	}

	@Async
	public static void indexSettingGroups(Collection<SettingGroup> settinggroups) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (SettingGroup settingGroup : settinggroups) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "settinggroup_" + settingGroup.getId());
			sid.addField("settingGroup.parentid_t", settingGroup.getParentId());
			sid.addField("settingGroup.name_s", settingGroup.getName());
			sid.addField("settingGroup.description_s",
					settingGroup.getDescription());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"settinggroup_solrsummary_t",
					new StringBuilder().append(settingGroup.getParentId())
							.append(" ").append(settingGroup.getName())
							.append(" ").append(settingGroup.getDescription()));
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
		String searchString = "SettingGroup_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new SettingGroup().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<SettingGroup> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui grup de setari de aplicatie in baza de date; daca
	 * exista, returneaza obiectul corespunzator, altfel, metoda introduce
	 * grupul de setari de aplicatie in baza de date si apoi returneaza obiectul
	 * corespunzator. Verificarea existentei in baza de date se realizeaza fie
	 * dupa valoarea identificatorului, fie dupa un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>id
	 * <li>name + parentId
	 * <ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul grupului de setari de aplicatie.
	 * @param name
	 *            - numele grupului de setari de aplicatie.
	 * @param parentId
	 *            - grupul de setari de aplicatie parinte.
	 * @param description
	 *            - descrierea grupului de setari de aplicatie.
	 * @return
	 */
	public static SettingGroup checkSettingGroup(Integer id, String name,
			Integer parentId, String description) {
		// TODO
		return null;
	}

	@Column(name = "description", columnDefinition = "text")
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "text")
	@NotNull
	private String name;

	@ManyToOne
	@JoinColumn(name = "parent_id", columnDefinition = "integer", referencedColumnName = "id", insertable = false, updatable = false)
	private SettingGroup parentId;

	@OneToMany(mappedBy = "parentId")
	private Set<SettingGroup> settingGroups;

	@OneToMany(mappedBy = "settingGroupId")
	private Set<Setting> settings;

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

	public String getDescription() {
		return description;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public SettingGroup getParentId() {
		return parentId;
	}

	public Set<SettingGroup> getSettingGroups() {
		return settingGroups;
	}

	public Set<Setting> getSettings() {
		return settings;
	}

	@Transactional
	public SettingGroup merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		SettingGroup merged = this.entityManager.merge(this);
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
			SettingGroup attached = SettingGroup.findSettingGroup(this.id);
			this.entityManager.remove(attached);
		}
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

	public void setParentId(SettingGroup parentId) {
		this.parentId = parentId;
	}

	public void setSettingGroups(Set<SettingGroup> settingGroups) {
		this.settingGroups = settingGroups;
	}

	public void setSettings(Set<Setting> settings) {
		this.settings = settings;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexSettingGroup(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
